package com.softwaredevelopment.achieveit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.PO.entity.RoleBasics;
import com.softwaredevelopment.achieveit.entity.ProjectEmployeeVO;
import com.softwaredevelopment.achieveit.entity.request.AddProjectEmployeeRequest;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.entity.request.ProjectEmployeeRequest;
import com.softwaredevelopment.achieveit.entity.request.UpdateProjectEmployeeRequest;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.EmployeeService;
import com.softwaredevelopment.achieveit.service.ProjectEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    ProjectEmployeeService projectEmployeeService;

    @ApiOperation("返回项目中还在的成员 不包括这个项目的上级")
    @GetMapping("employees_by_project_id")
    public HttpResponse<List<EmployeeBasics>> getEmployeesByProjectId(String projectId) throws BussinessException {
        return responseOK(employeeService.getEmployeesByProjectId(projectId));
    }

    @ApiOperation("获取projectEmployee的详细信息（包括Role Permission等）")
    @GetMapping("project_employee_vo_by_project_id")
    public HttpResponse<List<ProjectEmployeeVO>> getProjectEmployeeVOByProjectId(String projectId) {
        return responseOK(projectEmployeeService.getProjectEmployeeVOByProjectId(projectId));
    }

    @ApiOperation("获取projectEmployee的详细信息")
    @PostMapping("project_employee_vo")
    public HttpResponse<IPage<ProjectEmployeeVO>> getProjectEmployeeVO(@RequestBody PageSearchRequest<ProjectEmployeeRequest> request) {
        return responseOK(projectEmployeeService.getProjectEmployeeVO(request));
    }

    @ApiOperation("分页查询EmployeeBasics")
    @PostMapping("employee_basics_page")
    public HttpResponse<IPage<EmployeeBasics>> getEmployeeBasics(@RequestBody PageSearchRequest<EmployeeBasics> request) {
        return responseOK(projectEmployeeService.getEmployeeBasics(request));
    }

    @ApiOperation("按employeeId和ProjectId查项目中的role")
    @GetMapping("person_role")
    public HttpResponse<List<RoleBasics>> getRolesByPerson(Integer employeeId, Integer projectId) {
        return responseOK(projectEmployeeService.getRolesByPerson(employeeId, projectId));
    }

    @ApiOperation("添加项目人员")
    @PostMapping("add_project_employee")
    public HttpResponse<Boolean> addProjectEmployee(@RequestBody AddProjectEmployeeRequest request) throws Exception {
        return responseOK(projectEmployeeService.addProjectEmployee(request));
    }

    @ApiOperation("删除项目人员")
    @DeleteMapping("delete_project_employee")
    public HttpResponse<Boolean> deleteProjectEmployee(Integer id) {
        return responseOK(projectEmployeeService.deleteProjectEmployee(id));
    }

    @ApiOperation("设置角色")
    @PostMapping("set_role")
    public HttpResponse<Boolean> setRole(@RequestBody UpdateProjectEmployeeRequest request) throws BussinessException {
        return responseOK(projectEmployeeService.setRole(request));
    }

    @ApiOperation("设置权限")
    @PostMapping("set_permission")
    public HttpResponse<Boolean> setPermission(@RequestBody UpdateProjectEmployeeRequest request) throws BussinessException {
        return responseOK(projectEmployeeService.setPermission(request));
    }

    @ApiOperation("获取项目所有人员基本信息")
    @GetMapping("get_project_employee_basics")
    public HttpResponse<List<EmployeeBasics>> getEmployeeBasics(Integer id) {
        return responseOK(projectEmployeeService.getEmployeeBasics(id));
    }

    @ApiOperation("按全局角色查找雇员 request传 ROLE_ 格式的字符串 current -1 则为全部")
    @PostMapping("search_employee_by_global_roles")
    public HttpResponse<IPage<EmployeeBasics>> searchEmployeeByGlobalRoles(@RequestBody PageSearchRequest<String> request) throws BussinessException {
        return responseOK(employeeService.searchEmployeeByGlobalRoles(request));
    }

    @ApiOperation("获取所有需要填写工时的项目人员信息")
    @GetMapping("get_project_manhour_employee")
    public HttpResponse<List<EmployeeBasics>> getProjectManHourEmployee(int id, boolean isSubordinate) throws BussinessException {
        return responseOK(employeeService.getProjectManhourEmployee(id, isSubordinate));
    }
}
