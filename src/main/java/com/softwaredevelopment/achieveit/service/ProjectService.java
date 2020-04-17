package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.*;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.MailBean;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.utils.ObjectHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RainkQ
 * @date 2020/3/11 15:57
 */
@Service
public class ProjectService extends BaseService {
    private static boolean[] statusIdToBoolean(Integer statusId) {
        boolean[] b = new boolean[4];
        b[0] = false;
        statusId -= 3000;
        b[1] = statusId / 100 == 1;
        statusId %= 100;
        b[2] = statusId / 10 == 1;
        statusId %= 10;
        b[3] = statusId == 1;
        return b;
    }

    private static Integer booleanToStatusId(boolean[] booleans) {
        int r = 3000;
        if (booleans[1]) {
            r += 100;
        }
        if (booleans[2]) {
            r += 10;
        }
        if (booleans[3]) {
            r += 1;
        }
        return r;
    }

    /**
     * 分页综合查询项目基本信息
     * 用户只能看到与自己参与的项目：项目经理、项目上级、其他参与人员、资产管理者； 组织级配置管理员、EPG Leader、QA经理全部可以看到
     *
     * @param projectBasics
     * @return
     */
    public IPage<ProjectBasics> searchProjects(Page<ProjectBasics> page, ProjectBasics projectBasics) throws Exception {
        if (projectBasics == null) {
            projectBasics = new ProjectBasics();
        } else {
            // 将实体中的空字符串设置为NULL
            ObjectHelper.setObjectEmptyToNull(projectBasics);
        }

        QueryWrapper<ProjectBasics> qw = new QueryWrapper<>();
        // 先在qw里加入like的name和项目经理姓名的条件
        // 然后删掉实体的条件
        if (projectBasics.getProjectId() != null) {
            qw.like("project_id", projectBasics.getProjectId());
            projectBasics.setProjectId(null);
        }
        if (projectBasics.getName() != null) {
            qw.like("name", projectBasics.getName());
            projectBasics.setName(null);
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
        UserDetail userDetail = currentUserDetail();
        List<RoleBasics> roles = userDetail.getRoles();

        // 如果不是组织级配置管理员、EPG Leader、QA经理 就只能看到自己参与的
        boolean global = false;
        for (RoleBasics rb : roles) {
            if (rb.getName().startsWith("ROLE_GLOBAL")) {
                global = true;
                break;
            }
        }
        // 如果没有Global的role 就找project_employee里面的 我参加的项目
        if (!global) {
            List<ProjectEmployee> projectForMe = iProjectEmployeeService.list(
                    new QueryWrapper<ProjectEmployee>()
                            .eq("employee_id", userDetail.getEmployeeId())
                            .isNull("exit_time")
            );
            List<Integer> projectForMeId = new ArrayList<>();
            for (ProjectEmployee pe : projectForMe) {
                projectForMeId.add(pe.getProjectId());
            }
            qw.lambda().in(ProjectBasics::getId, projectForMeId);
        }

        return iProjectBasicsService.page(page, qw);
    }

    /**
     * 根据id获取project
     */
    public ProjectBasics selectProjectById(Integer id) throws Exception {
        // 当前用户信息
        UserDetail userDetail = currentUserDetail();

        ProjectBasics projectBasics = new ProjectBasics();

        if (id == null) {
            // 项目id为空，只拿global角色
            projectBasics.setRoles(userDetail.getRoles());
            // 设置用户信息
            projectBasics.setUserDetail(userDetail);
            return projectBasics;
        }

        // 根据id获取project
        projectBasics = iProjectBasicsService.getById(id);
        // region 获取权限
        List<RoleBasics> roles = new ArrayList<>();
        // 获取project权限
        ProjectEmployee projectEmployee = iProjectEmployeeService
                .getOne(new QueryWrapper<ProjectEmployee>()
                        .eq("project_id", id)
                        .eq("employee_id", userDetail.getEmployeeId())
                        .isNull("exit_time")
                );
        if (projectEmployee != null) {
            List<PersonRole> personRoles = iPersonRoleService
                    .list(new QueryWrapper<PersonRole>()
                            .eq("project_employee_id", projectEmployee.getId()));
            Map<Integer, RoleBasics> roleBasicsMap = getRoleBasicsMap();
            for (PersonRole personRole : personRoles) {
                roles.add(roleBasicsMap.get(personRole.getRoleId()));
            }
        }
        // 获取global权限
        roles.addAll(userDetail.getRoles());
        // 设置权限
        projectBasics.setRoles(roles);
        // endregion

        // 设置用户信息
        projectBasics.setUserDetail(userDetail);

        return projectBasics;
    }

    /**
     * 删除一个项目相关的所有数据
     *
     * @return
     */
    @Transactional
    public boolean deleteProjectAndItsData(String projectId) {
        ProjectBasics byProjectId = iProjectBasicsService.getOne(new QueryWrapper<ProjectBasics>().lambda().eq(ProjectBasics::getProjectId, projectId));
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
            // 如果所有归档都提交了
            if (projectBasics.AllArchived()) {
                // 设置状态为已归档
                projectBasics.setStatusId(0);
                // 这个属性已经废弃了 不过安全起见设置一下
                projectBasics.setIsArchived(true);
            }

            return iProjectBasicsService.updateById(projectBasics);
        } else {
            return false;
        }
    }

    /**
     * 插入新ProjectBasics
     * TODO 添加新项目中 项目上级的ID 不应该填物理ID，而是员工ID或者姓名，从外部系统导入或者直接读表获取物理ID  后续这一块需要进行优化
     * @param newProjectBasics
     * @return 插入不成功返回false
     */
    @Transactional
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
        // 如果有同类同客户同年的项目 序号加1 否则用01
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
            // 设置用户的EmployeeId和姓名
            UserDetail userDetail = currentUserDetail();
            newProjectBasics.setProjectManagerId(userDetail.getEmployeeId());
            newProjectBasics.setProjectManagerName(userDetail.getName());
            // 初始状态
            newProjectBasics.setStatusId(1);
            newProjectBasics.setIsArchived(false);
            // endregion


            iProjectBasicsService.save(newProjectBasics);

            // user有employee信息 把项目经理本人添加到项目中
            ProjectEmployee projectEmployee = new ProjectEmployee();
            projectEmployee.setEmployeeId(userDetail.getEmployeeId());
            projectEmployee.setProjectId(newProjectBasics.getId());
            projectEmployee.setSuperiorId(newProjectBasics.getSuperior());
            projectEmployee.setJoinTime(LocalDate.now());
            iProjectEmployeeService.save(projectEmployee);
            // 设置角色
            PersonRole personRole = new PersonRole();
            personRole.setProjectEmployeeId(projectEmployee.getId());
            personRole.setRoleId(iRoleBasicsService.getOne(
                    new QueryWrapper<RoleBasics>().eq("name", "ROLE_PM"))
                    .getId()
            );
            iPersonRoleService.save(personRole);
            // 设置权限 给pm所有权限
            List<Integer> permissionIds = iPermissionBasicsService.list().stream().map(PermissionBasics::getId).collect(Collectors.toList());
            List<PersonPermission> personPermissions = new ArrayList<>();
            for (Integer permissionId :
                    permissionIds) {
                PersonPermission personPermission = new PersonPermission();
                personPermission.setProjectEmployeeId(projectEmployee.getId());
                personPermission.setPermissionId(permissionId);
                personPermissions.add(personPermission);
            }
            iPersonPermissionService.saveBatch(personPermissions);

            // 上级添加到项目中
            ProjectEmployee superior = new ProjectEmployee();
            superior.setEmployeeId(newProjectBasics.getSuperior());
            superior.setProjectId(newProjectBasics.getId());
            superior.setJoinTime(LocalDate.now());
            iProjectEmployeeService.save(superior);
            // 设置角色
            PersonRole superiorRole = new PersonRole();
            superiorRole.setProjectEmployeeId(projectEmployee.getId());
            superiorRole.setRoleId(iRoleBasicsService.getOne(
                    new QueryWrapper<RoleBasics>().eq("name", "ROLE_SUPERIOR"))
                    .getId());
            iPersonRoleService.save(superiorRole);

        } catch (Exception e) {
            throw new BussinessException(e.getMessage(), e.getCause(), "立项失败");
        } finally {
            // 去锁
            redisUtils.delete(key);
        }

        //

        // 向项目上级异步发送邮件
        EmployeeBasics superior = iEmployeeBasicsService.getOne(
                new QueryWrapper<EmployeeBasics>().lambda().eq(EmployeeBasics::getId, newProjectBasics.getSuperior()));
        MailBean mailBean = new MailBean();
        mailBean.setRecipient(superior.getEmailAddress());
        mailBean.setSubject("新项目申请立项");
        mailBean.setContent("新项目申请立项，请去系统内审批：\n" + newProjectBasics.toString());
        mailUtil.sendSimpleMail(mailBean);

        return true;
    }

    public ProjectBasics getProjectBasicsByProjectId(String projectId) {
        // 通过projectId获取projectBasics
        return iProjectBasicsService.getOne(
                new QueryWrapper<ProjectBasics>().lambda()
                        .eq(ProjectBasics::getProjectId, projectId));
    }

    /**
     * 审批项目 通过或驳回
     *
     * @param projectId
     * @param approved
     * @return
     * @throws BussinessException
     */
    public ProjectBasics examineProject(String projectId, Boolean approved) throws Exception {
        ProjectBasics pb = getProjectBasicsByProjectId(projectId);
        // 如果pb为空
        if (pb == null) {
            throw new BussinessException("审批失败", new Exception(), "项目不存在");
        }

        //如果不是未审批状态
        if (pb.getStatusId() != 1) {
            throw new BussinessException("审批失败", new Exception(), "项目不在未审批状态");
        }

        // 如果项目上级不是该用户
        if (!currentUserDetail().getEmployeeId().equals(pb.getSuperior())) {
            throw new BussinessException("审批失败", new Exception(), "非该项目上级，无法审批");
        }

        try {
            // 审批通过或驳回
            pb.setStatusId(approved ? 3000 : 2);
            iProjectBasicsService.updateById(pb);


            // 发送邮件给 组织及配置管理员 EPG Leader 和 QA经理
            MailBean mailBean = new MailBean();
            mailBean.setSubject("新项目审批通过，请上线配置");
            mailBean.setContent("新项目审批通过，请您进入系统进行配置：\n" + pb.toString());
            // 组织级配置管理员
            String emailAddress = iEmployeeBasicsService.getOne(
                    new QueryWrapper<EmployeeBasics>().lambda()
                            .eq(EmployeeBasics::getName, "组织级配置管理员")).getEmailAddress();
            mailBean.setRecipient(emailAddress);
            mailUtil.sendSimpleMail(mailBean);
            // EPG Leader
            emailAddress = iEmployeeBasicsService.getOne(
                    new QueryWrapper<EmployeeBasics>().lambda()
                            .eq(EmployeeBasics::getName, "EPG Leader")).getEmailAddress();
            mailBean.setRecipient(emailAddress);
            mailUtil.sendSimpleMail(mailBean);
            // QA经理
            emailAddress = iEmployeeBasicsService.getOne(
                    new QueryWrapper<EmployeeBasics>().lambda()
                            .eq(EmployeeBasics::getName, "QA经理")).getEmailAddress();
            mailBean.setRecipient(emailAddress);
            mailUtil.sendSimpleMail(mailBean);
            // 发送邮件完毕

        } catch (Exception e) {
            throw new BussinessException(e.getMessage(), e.getCause(), "审核失败");
        }
        return pb;
    }

    /**
     * CONFIG EPG QA 配置后确认
     *
     * @param projectId
     * @param role
     * @return
     */
    public ProjectBasics initProject(String projectId) throws Exception {
        int role;
        List<RoleBasics> roles = currentUserDetail().getRoles();
        RoleBasics roleBasics = roles.get(0);
        switch (roleBasics.getName()) {
            case "ROLE_GLOBAL_CONFIG":
                role = 1;
                break;
            case "ROLE_GLOBAL_EPGLEADER":
                role = 2;
                break;
            case "ROLE_GLOBAL_QAM":
                role = 3;
                break;
            default:
                throw new BussinessException("确认失败", new Exception("没有对应角色"), "没有对应角色");
        }

        ProjectBasics pb = getProjectBasicsByProjectId(projectId);
        // 如果pb为空
        if (pb == null) {
            throw new BussinessException("确认失败", new Exception("项目不存在"), "项目不存在");
        }
        // 如果状态不是已审批未通过
        if (pb.getStatusId() < 3000) {
            throw new BussinessException("确认失败", new Exception("项目未审批通过"), "项目未审批通过");
        }

        // 加锁
        String key = String.format("init-project:project-id:%s", projectId);
        // 当前projectId无锁时，加锁并跳出循环
        while ("1".equals(redisUtils.getAndSetValue(key, "1"))) {
        }
        try {

            boolean[] status = statusIdToBoolean(pb.getStatusId());
            // 如果这一项为0
            if (!status[role]) {
                // 修改为新的statusId
                status[role] = true;
                Integer newStatusId = booleanToStatusId(status);
                pb.setStatusId(newStatusId);
                iProjectBasicsService.updateById(pb);

                // 获取pm
                EmployeeBasics pm = iEmployeeBasicsService.getOne(
                        new QueryWrapper<EmployeeBasics>().lambda().eq(EmployeeBasics::getId, pb.getProjectManagerId()));
                // 准备发邮件
                MailBean mailBean = new MailBean();
                mailBean.setRecipient(pm.getEmailAddress());
                switch (role) {
                    case 1:    //组织级配置
                        mailBean.setSubject("配置库已配置");
                        mailBean.setContent("配置库已配置：\n" + pb.toString());
                        break;
                    case 2:    // EPG
                        mailBean.setSubject("EPG 已分配");
                        mailBean.setContent("EPG 已分配：\n" + pb.toString());
                        break;
                    case 3:    // QA:
                        mailBean.setSubject("QA 已分配");
                        mailBean.setContent("QA 已分配：\n" + pb.toString());
                        break;
                    default:
                        break;
                }
                // 说明全部完成
                if (newStatusId == 3111) {
                    mailBean.setContent("项目已确认完毕:\n" + pb.toString());
                }
                // 异步发给pm通知邮件
                mailUtil.sendSimpleMail(mailBean);

            } else {
                throw new BussinessException("确认失败", new Exception("已经确认过"), "已经确认过");
            }
        } catch (Exception e) {
            throw new BussinessException(e.getMessage(), e.getCause(), "确认失败");
        } finally {
            // 去锁
            redisUtils.delete(key);
        }
        return pb;


    }

}
