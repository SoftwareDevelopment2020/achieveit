package com.softwaredevelopment.achieveit;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Test
    public void searchProjectsTest() {
        ProjectBasics projectBasics = new ProjectBasics();
        projectBasics.setProjectManagerName("张");
        projectBasics.setName("测");
        projectBasics.setIsArchived(false);
        List<ProjectBasics> listHttpResponse = service.searchProjects(new Page<>(1, 10), projectBasics);
        System.out.println(listHttpResponse);

    }
}
