package com.softwaredevelopment.achieveit.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author RainkQ
 * @date 2020/4/2 22:05
 */
@SpringBootTest
class ManHourServiceTest {
    @Autowired
    ManHourService service;

    @Test
    void getManHourSearchPage() {
    }

    @Test
    void saveManHour() {
    }

    @Test
    void getAllActivities() {
        System.out.println(service.getAllActivities());

    }
}
