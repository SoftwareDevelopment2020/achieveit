package com.softwaredevelopment.achieveit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public Boolean newProjectBasics(ProjectBasics newProjectBasics) {
        // 先看有没有这个projectId对应的项目
        QueryWrapper<ProjectBasics> qw = new QueryWrapper<>();
        qw.lambda()
                .eq(ProjectBasics::getProjectId, newProjectBasics.getProjectId());
        ProjectBasics hadOne = iProjectBasicsService.getOne(qw);
        if (hadOne != null) {
            return false;
        }
        return iProjectBasicsService.save(newProjectBasics);
    }


    /**
     * 分页综合查询项目基本信息
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


    //    @Cacheable(key = "#projectId")
    public ProjectBasics getProjectBasicsByProjectId(Integer projectId) {
        // 通过projectId获取projectBasics
        return iProjectBasicsService.getOne(
                new QueryWrapper<ProjectBasics>().lambda()
                        .eq(ProjectBasics::getProjectId, projectId));
    }
}
