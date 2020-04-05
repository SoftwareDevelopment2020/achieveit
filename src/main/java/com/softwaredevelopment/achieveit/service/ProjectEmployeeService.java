package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.softwaredevelopment.achieveit.PO.entity.*;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.ProjectEmployeeVO;
import com.softwaredevelopment.achieveit.entity.request.AddProjectEmployeeRequest;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.entity.request.ProjectEmployeeRequest;
import com.softwaredevelopment.achieveit.mapper.ProjectEmployeeVOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RainkQ
 * @date 2020/3/30 14:39
 */
@Service
public class ProjectEmployeeService extends BaseService {
    @Autowired
    ProjectEmployeeVOMapper projectEmployeeVOMapper;

    public List<ProjectEmployeeVO> getProjectEmployeeVOByProjectId(String projectId) {
        return projectEmployeeVOMapper.selectProjectEmployeesByProjectId(projectId);
    }

    /**
     * 根据条件分页获取人员信息
     */
    public IPage<ProjectEmployeeVO> getProjectEmployeeVO(PageSearchRequest<ProjectEmployeeRequest> request) {
        PageInfo<Integer> pageInfo = getPageInfo(request, () -> projectEmployeeVOMapper.selectProjectEmployeeIds(
                request.getSearchCondition().getProjectId(),
                request.getSearchCondition().getEmployeeId(),
                request.getSearchCondition().getEmployeeName(),
                request.getSearchCondition().getRoles())
        );

        return getIPage(pageInfo, () -> projectEmployeeVOMapper.selectProjectEmployeeVO(pageInfo.getList()));
    }

    public IPage<EmployeeBasics> getEmployeeBasics(PageSearchRequest<EmployeeBasics> request) {
        return iEmployeeBasicsService.page(
                new Page<>(request.getCurrent(), request.getSize()),
                new QueryWrapper<>(request.getSearchCondition()));
    }

    /**
     * 按person查roles
     *
     * @param employeeId
     * @param projectId
     * @return
     */
    public List<RoleBasics> getRolesByPerson(Integer employeeId, Integer projectId) {
        ProjectEmployee one = iProjectEmployeeService.getOne(
                new QueryWrapper<ProjectEmployee>().lambda()
                        .eq(ProjectEmployee::getEmployeeId, employeeId)
                        .eq(ProjectEmployee::getProjectId, projectId)
        );
        List<PersonRole> personRoles = iPersonRoleService.list(
                new QueryWrapper<PersonRole>().lambda().eq(PersonRole::getProjectEmployeeId, one.getId())
        );
        List<RoleBasics> roleBasics =
                iRoleBasicsService.listByIds(
                        personRoles.stream()
                                .map(PersonRole::getRoleId)
                                .collect(Collectors.toList()));
        return roleBasics;
    }

    /**
     * 添加项目人员
     */
    @Transactional
    public boolean addProjectEmployee(AddProjectEmployeeRequest request) throws Exception {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        // 设置项目key
        projectEmployee.setProjectId(request.getProjectKey());

        // region 设置员工key
        // 获取employee
        EmployeeBasics employeeBasics = iEmployeeBasicsService.getOne(
                new QueryWrapper<EmployeeBasics>()
                        .eq("employee_id", request.getEmployeeId())
        );
        if (employeeBasics == null) {
            employeeBasics = getOriginalEmployeeBasics(request.getEmployeeId());
        } else {
            // 判断当前项目是否已有该员工
            if (iProjectEmployeeService.getOne(
                    new QueryWrapper<ProjectEmployee>()
                            .eq("project_id", request.getProjectKey())
                            .eq("employee_id", employeeBasics.getId())
                            .isNull("exit_time")) != null) {
                throw new BussinessException("添加项目人员失败", new Exception(), "该员工已加入该项目");
            }
        }
        projectEmployee.setEmployeeId(employeeBasics.getId());
        // endregion

        // 加入时间
        projectEmployee.setJoinTime(LocalDate.now());

        // 项目上级，判断此人是否在项目中
        if (request.getSuperiorKey() != null) {
            if (iProjectEmployeeService.getOne(
                    new QueryWrapper<ProjectEmployee>()
                            .eq("project_id", request.getProjectKey())
                            .eq("employee_id", request.getSuperiorKey())
                            .isNull("exit_time")) == null) {
                throw new BussinessException("添加项目人员失败", new Exception(), "项目上级已退出该项目");
            }

            projectEmployee.setSuperiorId(request.getSuperiorKey());
        }

        // 添加员工
        iProjectEmployeeService.save(projectEmployee);

        // region 设置角色
        boolean bugPermission = false;
        if (CollectionUtils.isEmpty(request.getRoles())) {
            throw new BussinessException("添加项目人员失败", new Exception(), "该员工角色为空");
        }
        List<RoleBasics> roles = iRoleBasicsService.list(new QueryWrapper<RoleBasics>().in("name", request.roles));
        if (CollectionUtils.isEmpty(roles)) {
            throw new BussinessException("添加项目人员失败", new Exception(), "该员工角色为空");
        }
        List<PersonRole> personRoles = new ArrayList<>();
        for (RoleBasics roleBasics : roles) {
            PersonRole personRole = new PersonRole();
            personRole.setProjectEmployeeId(projectEmployee.getId());
            personRole.setRoleId(roleBasics.getId());
            personRoles.add(personRole);

            if (roleBasics.getName().equals("ROLE_PM") || roleBasics.getName().equals("ROLE_DEVLEADER") || roleBasics.getName().equals("ROLE_TESTLEADER")) {
                bugPermission = true;
            }
        }
        iPersonRoleService.saveBatch(personRoles);
        // endregion

        // region 设置权限
        if (bugPermission) {
            // 角色是否有项目经理、开发Leader、测试Leader
            if (!request.getPermissions().contains("bug")) {
                if (request.getPermissions() == null) {
                    request.setPermissions(new ArrayList<>());
                }
                // 没有bug权限，自动添加
                request.getPermissions().add("bug");
            }
        }
        if (!CollectionUtils.isEmpty(request.getPermissions())) {
            List<PersonPermission> personPermissions = new ArrayList<>();
            List<PermissionBasics> permissions = iPermissionBasicsService.list(
                    new QueryWrapper<PermissionBasics>()
                            .in("name", request.getPermissions()));
            for (PermissionBasics permissionBasics : permissions) {
                PersonPermission personPermission = new PersonPermission();
                personPermission.setProjectEmployeeId(projectEmployee.getId());
                personPermission.setPermissionId(permissionBasics.getId());
                personPermissions.add(personPermission);
            }
            iPersonPermissionService.saveBatch(personPermissions);
        }
        // endregion

        return true;
    }

    /**
     * 获取项目所有人员基本信息
     */
    public List<EmployeeBasics> getEmployeeBasics(Integer id) {
        // 查询所有项目人员
        List<ProjectEmployee> projectEmployees = iProjectEmployeeService.list(
                new QueryWrapper<ProjectEmployee>()
                        .eq("project_id", id)
                        .isNull("exit_time")
        );

        // 如果为空(不可能为空)
        if (CollectionUtils.isEmpty(projectEmployees)) {
            return new ArrayList<>();
        }

        return iEmployeeBasicsService.listByIds(
                projectEmployees.stream().map(ProjectEmployee::getEmployeeId).collect(Collectors.toList()));
    }
}
