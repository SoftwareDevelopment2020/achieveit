package com.softwaredevelopment.achieveit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.service.AuthService;
import com.softwaredevelopment.achieveit.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author RainkQ
 * @date 2020/3/18 10:08
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectService service;

    @Autowired
    AuthService authService;

    @Before
    public void setUp() {
        authService.login("wangwu", "123456");
    }

    @Test
    public void searchProjectsTest() throws Exception {
        ProjectBasics projectBasics = new ProjectBasics();
        projectBasics.setProjectId("2020");
        projectBasics.setName("测");
        projectBasics.setProjectManagerName("   ");
        projectBasics.setMainFunction("");
        projectBasics.setIsArchived(false);
        IPage<ProjectBasics> listHttpResponse = service.searchProjects(new Page<>(1, 10), projectBasics);
        System.out.println(listHttpResponse.getCurrent());
        System.out.println(listHttpResponse.getSize());
        System.out.println(listHttpResponse.getTotal());
        System.out.println(listHttpResponse.getRecords());
    }
}
