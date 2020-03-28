package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.EmployeeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/28 16:01
 */
@RestController
@Api(tags = "员工信息接口")
@RequestMapping("employee")
public class EmployeeController extends BaseController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("employees_by_project_id")
    public HttpResponse<List<EmployeeBasics>> getEmployeesByProjectId(String projectId) throws BussinessException {
        return responseOK(employeeService.getEmployeesByProjectId(projectId));
    }

}
