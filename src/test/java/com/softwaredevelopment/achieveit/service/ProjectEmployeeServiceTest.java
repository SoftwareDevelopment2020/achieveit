package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.AchieveitApplication;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.request.AddProjectEmployeeRequest;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.entity.request.ProjectEmployeeRequest;
import com.softwaredevelopment.achieveit.entity.request.UpdateProjectEmployeeRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author RainkQ
 * @date 2020/3/30 14:44
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AchieveitApplication.class)
@Transactional
class ProjectEmployeeServiceTest {
    @Autowired
    ProjectEmployeeService service;

    @Test
    void getProjectEmployeeVOByProjectId() {
        service.getProjectEmployeeVOByProjectId("12345678901");
    }

    @Test
    void getProjectEmployeeVOTest() {
        PageSearchRequest<ProjectEmployeeRequest> request = new PageSearchRequest<>();
        request.setCurrent(1);
        request.setSize(1);
        ProjectEmployeeRequest condition = new ProjectEmployeeRequest();
        condition.setProjectId(1);
        request.setSearchCondition(condition);
        System.out.println(service.getProjectEmployeeVO(request));
    }

    @Test
    void getEmployeeBasics() {
        PageSearchRequest<EmployeeBasics> request = new PageSearchRequest<>();
        request.setCurrent(1);
        request.setSize(10);
        EmployeeBasics employeeBasics = new EmployeeBasics();
        employeeBasics.setDepartment("技术部");
        request.setSearchCondition(employeeBasics);

        System.out.println(service.getEmployeeBasics(request));
    }

    @Test
    void getRolesByPerson() {
        System.out.println(service.getRolesByPerson(1, 1));
    }

    @Test
    void addProjectEmployee() throws Exception {
        AddProjectEmployeeRequest request = new AddProjectEmployeeRequest();
        request.setProjectKey(1);
        request.setEmployeeId("222");
        request.setPermissions(new ArrayList<>(Arrays.asList("bug", "git")));
        request.setRoles(new ArrayList<>(Arrays.asList("ROLE_DEV")));
        request.setSuperiorKey(1);
        service.addProjectEmployee(request);
    }

    @Test
    void deleteProjectEmployee() {
        service.deleteProjectEmployee(620);
    }

    @Test
    void setRole() {
        try {
            service.setRole(new UpdateProjectEmployeeRequest());
        } catch (BussinessException e) {
            e.printStackTrace();
        }
        UpdateProjectEmployeeRequest request = new UpdateProjectEmployeeRequest();
        request.setProjectEmployeeId(620);
        request.setPermissions(Arrays.asList("bug", "git"));
        request.setRoles(Arrays.asList("ROLE_TEST"));
        try {
            service.setRole(request);
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setPermission() {
        try {
            service.setPermission(new UpdateProjectEmployeeRequest());
        } catch (BussinessException e) {
            e.printStackTrace();
        }
        UpdateProjectEmployeeRequest request = new UpdateProjectEmployeeRequest();
        request.setProjectEmployeeId(620);
        request.setPermissions(Arrays.asList("bug", "git"));
        request.setRoles(Arrays.asList("ROLE_TEST"));
        try {
            service.setPermission(request);
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testGetEmployeeBasics() {
        service.getEmployeeBasics(1);
    }
}
