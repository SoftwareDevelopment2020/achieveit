package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.AchieveitApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RainkQ
 * @date 2020/3/30 14:44
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AchieveitApplication.class)
@Transactional
class ProjectEmployeeServiceTest {
    @Autowired
    ProjectEmployeeService projectEmployeeService;

    @Test
    void getProjectEmployeeVOByProjectId() {
        projectEmployeeService.getProjectEmployeeVOByProjectId("12345678901");
    }
}
