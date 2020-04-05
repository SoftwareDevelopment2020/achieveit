package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.AchieveitApplication;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RainkQ
 * @date 2020/3/25 18:36
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AchieveitApplication.class)
@Transactional
class ProjectServiceTest {


    @Autowired
    ProjectService service;

    @Autowired
    AuthService authService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void searchProjects() throws Exception {
        authService.login("wangwu", "123456");
        ProjectBasics projectBasics = new ProjectBasics();
        projectBasics.setProjectId("2020");
        projectBasics.setName("测");
        projectBasics.setProjectManagerName("   ");
        projectBasics.setMainFunction("");
        projectBasics.setIsArchived(false);
        IPage<ProjectBasics> listHttpResponse = service.searchProjects(new Page<>(1, 10), projectBasics);
        System.out.println(listHttpResponse);

        authService.login("zhangsan", "123456");
        System.out.println(service.searchProjects(new Page<>(1, 1), null));
    }


    @Test
    void deleteProjectAndItsData() {
        service.deleteProjectAndItsData("47516845162");
    }

    @Test
    void updateProject() {
        ProjectBasics projectBasics = new ProjectBasics();
        projectBasics.setProjectId("2020");
        projectBasics.setId(10);
        projectBasics.setName("测");
        service.updateProject(projectBasics);
    }

    @Test
    void newProjectBasics() {
        try {
            service.newProjectBasics(new ProjectBasics());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            authService.login("zhangsan", "123456");
            ProjectBasics pb = new ProjectBasics();
            pb.setClientId(100);
            // 1位开发类型代码
            pb.setProjectId("M");
            pb.setSuperior(1);
            service.newProjectBasics(pb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProjectBasicsByProjectId() {
        service.getProjectBasicsByProjectId("12345678901");
    }

    @Test
    void examineProject() throws Exception {
        authService.login("wangwu", "123456");
        service.examineProject("23456789012", true);
    }

    @Test
    void initProject() throws Exception {
        authService.login("epg_leader", "123456");
        service.initProject("12310239109");
    }
}
