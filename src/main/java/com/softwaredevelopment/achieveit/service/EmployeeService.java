package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.*;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RainkQ
 * @date 2020/3/28 16:26
 */
@Service
public class EmployeeService extends BaseService {
//    public List<EmployeeBasics> getEmployeesByProjectId(String projectId) throws BussinessException {
//        Integer id = projectIdToId(projectId);
//        if (id == null) {
//            throw new BussinessException("查找失败", new Exception(), "没有这个项目");
//        }
//        // 先从project_employee中查找这个项目的employee_id
//        List<ProjectEmployee> projectEmployees = iProjectEmployeeService.list(
//                new QueryWrapper<ProjectEmployee>().lambda().eq(ProjectEmployee::getProjectId, id));
//        List<Integer> employeeIds = new ArrayList<>();
//        for (ProjectEmployee pe :
//                projectEmployees) {
//            employeeIds.add(pe.getEmployeeId());
//        }
//        // 再从EmployeeBasics中查找这些员工
//        return iEmployeeBasicsService.list(new QueryWrapper<EmployeeBasics>().lambda().in(EmployeeBasics::getId, employeeIds));
//    }

    public List<EmployeeBasics> getEmployeesByProjectId(String projectId) throws BussinessException {
        Integer id = projectIdToId(projectId);
        if (id == null) {
            throw new BussinessException("查找失败", new Exception(), "没有这个项目");
        }
        // 先从project_employee中查找这个项目的employee_id
        List<ProjectEmployee> projectEmployees = iProjectEmployeeService.list(
                new QueryWrapper<ProjectEmployee>().lambda().eq(ProjectEmployee::getProjectId, id));
        List<Integer> employeeIds = new ArrayList<>();
        for (ProjectEmployee pe :
                projectEmployees) {
            employeeIds.add(pe.getEmployeeId());
        }
        // 再从EmployeeBasics中查找这些员工
        return iEmployeeBasicsService.list(new QueryWrapper<EmployeeBasics>().lambda().in(EmployeeBasics::getId, employeeIds));
    }

    public IPage<EmployeeBasics> searchEmployeeByGlobalRoles(PageSearchRequest<String> request) throws BussinessException {
        if (!request.getSearchCondition().startsWith("ROLE_")) {
            throw new BussinessException("role字符串有问题", null);
        }
        // 先查到role对应的id们
        List<RoleBasics> list = iRoleBasicsService.list(new QueryWrapper<RoleBasics>().lambda()
                .likeRight(RoleBasics::getName, request.getSearchCondition()));
        List<Integer> roleIds = list.stream().map(RoleBasics::getId).collect(Collectors.toList());
        if (roleIds.size() == 0) {
            return new Page<>(request.getCurrent(), request.getSize(), 0);
        }
        // 然后查到users
        List<Integer> userIds = iUserRoleService.list(
                new QueryWrapper<UserRole>().lambda()
                        .in(UserRole::getRoleId, roleIds)).stream().map(UserRole::getUserId).collect(Collectors.toList());
        if (userIds.size() == 0) {
            return new Page<>(request.getCurrent(), request.getSize(), 0);
        }
        List<User> users = iUserService.listByIds(userIds);
        // 然后拿到employee的id们
        List<Integer> employeeIds = users.stream().map(User::getEmployeeBasicsId).collect(Collectors.toList());
        if (employeeIds.size() == 0) {
            return new Page<>(request.getCurrent(), request.getSize(), 0);
        }
        // 最后查到employees
        Page<EmployeeBasics> page = new Page<>(request.getCurrent(), request.getSize());
        // 如果-1就全部
        if (request.getCurrent() == -1) {
            int count = iEmployeeBasicsService.count();
            page.setCurrent(1);
            page.setSize(count);
        }
        return iEmployeeBasicsService.page(page,
                new QueryWrapper<EmployeeBasics>().lambda().in(EmployeeBasics::getId, employeeIds));

    }
}
