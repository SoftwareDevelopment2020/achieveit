package com.softwaredevelopment.achieveit.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RainkQ
 * @date 2020/4/4 17:10
 */
@Transactional
@SpringBootTest
class PropertyServiceTest {
    @Autowired
    ProjectService service;

    @Test
    void getPropertiesByProjectId() {
        service.getProjectBasicsByProjectId("12345678901");
    }
}
