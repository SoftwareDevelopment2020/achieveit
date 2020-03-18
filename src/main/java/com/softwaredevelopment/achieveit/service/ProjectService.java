package com.softwaredevelopment.achieveit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.PO.mapper.ProjectBasicsMapper;
import com.softwaredevelopment.achieveit.PO.service.IProjectBasicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/11 15:57
 */
@Service
public class ProjectService {
    @Autowired
    ProjectBasicsMapper projectBasicsMapper;

    @Autowired
    IProjectBasicsService iProjectBasicsService;


    /**
     * 插入新ProjectBasics
     *
     * @param newProjectBasics
     * @return 插入不成功返回false
     */
    public Boolean saveProjectBasics(ProjectBasics newProjectBasics) {
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
    public List<ProjectBasics> searchProjects(Page<ProjectBasics> page, ProjectBasics projectBasics) {
        QueryWrapper<ProjectBasics> qw = new QueryWrapper<>();
        // 先在qw里假如like的name和项目经理姓名的条件
        // 然后删掉实体的条件
        qw.like("name", projectBasics.getName());
        projectBasics.setName(null);
        qw.like("project_manager_name", projectBasics.getProjectManagerName());
        projectBasics.setProjectManagerName(null);

        // 把实体剩下的条件全部加入qw 且是alleq条件
        qw.setEntity(projectBasics);
        return iProjectBasicsService.page(page, qw).getRecords();
    }
}
