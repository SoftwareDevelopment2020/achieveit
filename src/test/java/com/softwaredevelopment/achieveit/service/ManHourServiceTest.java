package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.PO.entity.ManHour;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author RainkQ
 * @date 2020/4/2 22:05
 */
@SpringBootTest
@Transactional
class ManHourServiceTest {
    @Autowired
    ManHourService service;
    @Autowired
    AuthService authService;

    @BeforeEach
    void setUp() {
        authService.login("zhangsan", "123456");
    }

    ManHour mockManHour() {
        ManHour manHour = new ManHour();
        manHour.setId(100);
        manHour.setAuditingStatus(1);
        manHour.setActivityId(1);
        manHour.setEmployeeId(1);
        manHour.setStartTime(LocalDateTime.now());
        manHour.setEndTime(LocalDateTime.now());
        manHour.setFeatureId(1);
        manHour.setFeatureName("nice");
        manHour.setProjectId(1);
        return manHour;
    }

    @Test
    void getManHourSearchPage() throws BussinessException {
        PageSearchRequest<ManHour> request = new PageSearchRequest<>();
        request.setCurrent(1);
        request.setSize(10);

        request.setSearchCondition(mockManHour());
        System.out.println(service.getManHourSearchPage(request));
    }

    @Test
    void saveManHour() throws BussinessException {
        service.addManHour(mockManHour());
    }

    @Test
    void getAllActivities() {
        System.out.println(service.getAllActivities());
    }

}
