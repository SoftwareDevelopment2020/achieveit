package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.PO.entity.ManHour;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
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
    void getManHourSearchPage() {
        PageSearchRequest<ManHour> request = new PageSearchRequest<>();
        request.setCurrent(1);
        request.setSize(10);

        request.setSearchCondition(mockManHour());
        System.out.println("service.getManHourSearchPage(request)");
    }

    @Test
    void saveManHour() {
        service.saveManHour(mockManHour());
    }

    @Test
    void getAllActivities() {
        System.out.println(service.getAllActivities());
    }


    @Test
    void auditManHour() {
        service.auditManHour(mockManHour());
    }


}
