package com.softwaredevelopment.achieveit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.*;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.MailBean;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/11 15:57
 */
@Service
public class ProjectService extends BaseService {
    /**
     * 插入新ProjectBasics
     *
     * @param newProjectBasics
     * @return 插入不成功返回false
     */
    public Boolean newProjectBasics(ProjectBasics newProjectBasics) throws Exception {
        // TODO 从外部系统判断客户信息是否存在并获取客户信息

        // 生成projectId
        String projectIdPrefix = String.format("%s%s%s",
                Calendar.getInstance().get(Calendar.YEAR),   // 年 4位
                newProjectBasics.getClientId(),                    // 客户ID 4位
                newProjectBasics.getProjectId());                 // 研发类型 1位
        QueryWrapper<ProjectBasics> qw = new QueryWrapper<>();
        qw.lambda()
                .select(ProjectBasics::getProjectId)                               // 获取projectId
                .likeRight(ProjectBasics::getProjectId, projectIdPrefix)    // 根据年份、客户ID、研发类型查找相关项目ID
                .orderByDesc(ProjectBasics::getProjectId)                     // 降序
                .last("limit 0,1");                                            // 获取第一条
        ProjectBasics lastProject = iProjectBasicsService.getOne(qw);
        String projectId = String.format("%s%s",
                projectIdPrefix, lastProject == null ? "01" : new DecimalFormat("00").format(Integer.parseInt(lastProject.getProjectId().substring(9, 11)) + 1));

        // 加锁
        String key = String.format("new-project:project-id:%s", projectId);
        // 当前projectId无锁时，加锁并跳出循环
        while ("1".equals(redisUtils.getAndSetValue(key, "1"))) {
            projectId = String.format("%s%s", projectIdPrefix, new DecimalFormat("00").format(Integer.parseInt(projectId.substring(9, 11)) + 1));
        }
        try {
            // region
            // projectId
            newProjectBasics.setProjectId(projectId);
            // 获取用户对应的EmployeeId和姓名
            UserDetail userDetail = getUserDetail();
            newProjectBasics.setProjectManagerId(userDetail.getEmployeeBasics().getId());
            newProjectBasics.setProjectManagerName(userDetail.getEmployeeBasics().getName());
            // 初始状态
            newProjectBasics.setStatusId(1);
            newProjectBasics.setIsArchived(false);
            // endregion


            iProjectBasicsService.save(newProjectBasics);

            // user有employee信息 把项目经理本人添加到项目中
            ProjectEmployee projectEmployee = new ProjectEmployee();
            projectEmployee.setEmployeeId(userDetail.getEmployeeId());
            projectEmployee.setProjectId(newProjectBasics.getId());
            iProjectEmployeeService.save(projectEmployee);

        } catch (Exception e) {
            throw new BussinessException(e.getMessage(), e.getCause(), "立项失败");
        } finally {
            // 去锁
            redisUtils.delete(key);
        }

        // 向项目上级异步发送邮件
        sendEmailForSettingUpProject(newProjectBasics);

        return true;
    }


    /**
     * 异步发送邮件
     *
     * @param newProjectBasics
     */
    @Async("taskExecutor")
    public void sendEmailForSettingUpProject(ProjectBasics newProjectBasics) {
        EmployeeBasics superior = iEmployeeBasicsService.getOne(
                new QueryWrapper<EmployeeBasics>().lambda().eq(EmployeeBasics::getId, newProjectBasics.getSuperior()));
        MailBean mailBean = new MailBean();
        mailBean.setRecipient(superior.getEmailAddress());
        mailBean.setSubject("新项目申请立项");
        mailBean.setContent("新项目申请立项，请去系统内审批：\n" + newProjectBasics.toString());
        mailUtil.sendSimpleMail(mailBean);
    }


    /**
     * 分页综合查询项目基本信息
     * 用户只能看到与自己参与的项目：项目经理、项目上级、其他参与人员、资产管理者； 组织级配置管理员、EPG Leader、QA经理全部可以看到
     *
     * @param projectBasics
     * @return
     */
    public IPage<ProjectBasics> searchProjects(Page<ProjectBasics> page, ProjectBasics projectBasics) {
        QueryWrapper<ProjectBasics> qw = new QueryWrapper<>();
        // 先在qw里加入like的name和项目经理姓名的条件
        // 然后删掉实体的条件
        if (projectBasics.getName() != null) {
            qw.like("name", projectBasics.getName());
            projectBasics.setName(null);
        }
        if (projectBasics.getProjectManagerName() != null) {
            qw.like("project_manager_name", projectBasics.getProjectManagerName());
            projectBasics.setProjectManagerName(null);
        }
        // 如果状态值为3 需要查询3xxx
        if (projectBasics.getStatusId() != null && projectBasics.getStatusId() == 3) {
            qw.ge("status_id", 3000);
            projectBasics.setStatusId(null);
        }
        // 时间降序
        page.addOrder(OrderItem.desc("scheduled_date"));
        // 把实体剩下的条件全部加入qw 且是alleq条件
        qw.setEntity(projectBasics);

        // 用户只能看到与自己参与的项目：项目经理、项目上级、其他参与人员、资产管理者； 组织级配置管理员、EPG Leader、QA经理全部可以看到
        // 获取当前用户
        UserDetail userDetail = getUserDetail();
        List<RoleBasics> roles = userDetail.getRoles();

        // 如果不是组织级配置管理员、EPG Leader、QA经理 就只能看到自己参与的
        boolean global = false;
        for (RoleBasics rb :
                roles) {
            if (rb.getName().startsWith("ROLE_GLOBAL")) {
                global = true;
                break;
            }
        }
        // 如果没有Global的role 就找project_employee里面的 我参加的项目
        if (!global) {
            List<ProjectEmployee> projectForMe = iProjectEmployeeService.list(
                    new QueryWrapper<ProjectEmployee>().lambda().eq(ProjectEmployee::getId, userDetail.getEmployeeId()));
            List<Integer> projectForMeId = new ArrayList<>();
            for (ProjectEmployee pe :
                    projectForMe) {
                projectForMeId.add(pe.getProjectId());
            }
            qw.lambda().in(ProjectBasics::getId, projectForMeId);
        }


        return iProjectBasicsService.page(page, qw);
    }




    /**
     * 删除一个项目相关的所有数据
     *
     * @return
     */
    @Transactional
    public boolean deleteProjectAndItsData(Integer projectId) {
        // 通过projectId获取project_id(11位)
        ProjectBasics byProjectId = iProjectBasicsService.getOne(new QueryWrapper<ProjectBasics>().eq("projectId", projectId));
        // 获取表的id
        Integer id = byProjectId.getId();

        // 如果删除ProjectBasics成功
        if (iProjectBasicsService.removeById(id)) {
            // 删除ProjectEmployee
            List<ProjectEmployee> projectEmployees = iProjectEmployeeService.list(new QueryWrapper<ProjectEmployee>().eq("project_id", id));
            iProjectEmployeeService.remove(new QueryWrapper<ProjectEmployee>().eq("project_id", id));
            List<Integer> projectEmployeeIds = new ArrayList<>();
            for (ProjectEmployee p :
                    projectEmployees) {
                projectEmployeeIds.add(p.getId());
            }
            if (!projectEmployeeIds.isEmpty()) {
                // 删除PersonPermission
                iPersonPermissionService.remove(new QueryWrapper<PersonPermission>().in("project_employee_id", projectEmployeeIds));
            }
            // 删除Risk
            iRiskService.remove(new QueryWrapper<Risk>().eq("project_id", id));
            // 删除bug
            iBugService.remove(new QueryWrapper<Bug>().eq("project_id", id));
            // 删除Feature
            iFeatureService.remove(new QueryWrapper<Feature>().eq("project_id", id));
            // 删除ManHour
            iManHourService.remove(new QueryWrapper<ManHour>().eq("project_id", id));
            // 删除Property
            iPropertyService.remove(new QueryWrapper<Property>().eq("project_id", id));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新项目
     *
     * @param projectBasics
     * @return
     */
    public Boolean updateProject(ProjectBasics projectBasics) {
        String projectId = projectBasics.getProjectId();
        // 查有没有这个项目
        ProjectBasics byProjectId = iProjectBasicsService.getOne(
                new QueryWrapper<ProjectBasics>().lambda().eq(ProjectBasics::getProjectId, projectId));
        if (byProjectId != null) {
            projectBasics.setId(byProjectId.getId());
            return iProjectBasicsService.updateById(projectBasics);
        } else {
            return false;
        }
    }



    public ProjectBasics getProjectBasicsByProjectId(Integer projectId) {
        // 通过projectId获取projectBasics
        return iProjectBasicsService.getOne(
                new QueryWrapper<ProjectBasics>().lambda()
                        .eq(ProjectBasics::getProjectId, projectId));
    }
}
