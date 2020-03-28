package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.PO.entity.ProjectEmployee;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/28 16:26
 */
@Service
public class EmployeeService extends BaseService {
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
}
