package com.softwaredevelopment.achieveit.service;


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


    public ProjectBasics selectById(Integer projectId) {
        return projectBasicsMapper.selectById(projectId);
    }


    public List<Project> selectAllProjectsByPage(Page<ProjectBasics> page) {
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
}
