package com.softwaredevelopment.achieveit.mapper;

import com.softwaredevelopment.achieveit.AchieveitApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RainkQ
 * @date 2020/3/30 14:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AchieveitApplication.class)
@Transactional
class ProjectEmployeeVOMapperTest {
    @Autowired
    ProjectEmployeeVOMapper mapper;

    @Test
    void selectProjectEmployeesByProjectId() {
        System.out.println(mapper.selectProjectEmployeesByProjectId("12345678901"));
    }
}
