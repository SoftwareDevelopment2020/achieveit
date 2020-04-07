package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.PO.entity.ManHour;
import com.softwaredevelopment.achieveit.PO.entity.ProjectEmployee;
import com.softwaredevelopment.achieveit.PO.entity.User;
import com.softwaredevelopment.achieveit.controller.BussinessException;
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
    @Autowired
    AuthService authService;

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


    @Test
    void auditManHour() {
        Integer employeeId = 1;
        Integer projectId = 1;
        ProjectEmployee projectEmployee = service.getIProjectEmployeeService().getOne(
                new QueryWrapper<ProjectEmployee>()
                        .lambda().eq(ProjectEmployee::getProjectId, projectId).eq(ProjectEmployee::getEmployeeId, employeeId));
        EmployeeBasics byId = service.getIEmployeeBasicsService().getById(projectEmployee.getEmployeeId());
        User one = service.getIUserService().getOne(new QueryWrapper<User>()
                .lambda().eq(User::getEmployeeBasicsId, byId.getId()));
        authService.login(one.getUsername(), "123456");
        service.auditManHour(1);
        authService.login("epg_leader", "123456");
        service.auditManHour(1);
    }


}
