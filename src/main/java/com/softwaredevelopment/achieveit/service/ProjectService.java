package com.softwaredevelopment.achieveit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.PO.mapper.ProjectBasicsMapper;
import com.softwaredevelopment.achieveit.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/11 15:57
 */
@Service
public class ProjectService {
    @Autowired
    ProjectBasicsMapper projectBasicsMapper;


    /**
     * 按表的Id查询 返回一个
     *
     * @param id
     * @return
     */
    public ProjectBasics selectById(Integer id) {
        return projectBasicsMapper.selectById(id);
    }


    /**
     * 分页查询所有项目
     *
     * @param page
     * @return
     */
    public List<Project> selectAllProjectsByPage(Page<ProjectBasics> page) {
        // 按时间倒序
        page.addOrder(OrderItem.desc("scheduled_date"));
        Page<ProjectBasics> selectPage = projectBasicsMapper.selectPage(page, null);
        List<Project> projectsByPage = new ArrayList<>();
        for (ProjectBasics pb :
                selectPage.getRecords()) {
            // 从分页查到的projectBasics中构造project
            // 只有几个基本属性
            Project project = new Project();
            project.setId(pb.getId());
            project.setProjectId(pb.getProjectId());
            project.setName(pb.getName());
            project.setProjectManagerName(pb.getProjectManagerName());
            project.setScheduledDate(pb.getScheduledDate());

            projectsByPage.add(project);
        }
        return projectsByPage;
    }

    /**
     * 按项目ID（11位）查询 返回一个
     *
     * @param projectId
     * @return
     */
    public ProjectBasics selectByProjectId(String projectId) {
        QueryWrapper<ProjectBasics> qw = new QueryWrapper<>();
        qw.lambda()
                .eq(ProjectBasics::getProjectId, projectId);
        return projectBasicsMapper.selectOne(qw);
    }

    /**
     * 分页模糊查询 名字
     *
     * @param name
     * @return
     */
    public List<ProjectBasics> selectListByName(Page<ProjectBasics> page, String name) {
        QueryWrapper<ProjectBasics> qw = new QueryWrapper<>();
        qw.lambda()
                .like(ProjectBasics::getName, name);

        return projectBasicsMapper.selectPage(page, qw).getRecords();

    }

    /**
     * 插入新ProjectBasics
     *
     * @param newProjectBasics
     * @return 如果project_id重复返回-1 成功返回新id
     */
    public Integer insertProjectBasics(ProjectBasics newProjectBasics) {
        // 先看有没有这个projectId对应的项目
        QueryWrapper<ProjectBasics> qw = new QueryWrapper<>();
        qw.lambda()
                .eq(ProjectBasics::getProjectId, newProjectBasics.getProjectId());
        ProjectBasics hadOne = projectBasicsMapper.selectOne(qw);
        if (hadOne != null) {
            return -1;
        }
        return projectBasicsMapper.insert(newProjectBasics);
    }
}
