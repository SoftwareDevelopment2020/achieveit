package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.PO.service.*;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.utils.MailUtil;
import com.softwaredevelopment.achieveit.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author RainkQ
 * @date 2020/3/25 15:42
 */
public class BaseService {

    @Autowired
    IEmployeeBasicsService iEmployeeBasicsService;
    @Autowired
    IProjectBasicsService iProjectBasicsService;
    @Autowired
    IProjectEmployeeService iProjectEmployeeService;
    @Autowired
    IPersonPermissionService iPersonPermissionService;
    @Autowired
    IPersonRoleService iPersonRoleService;
    @Autowired
    IRiskService iRiskService;
    @Autowired
    IBugService iBugService;
    @Autowired
    IFeatureService iFeatureService;
    @Autowired
    IManHourService iManHourService;
    @Autowired
    IPropertyService iPropertyService;
    @Autowired
    IUserRoleService iUserRoleService;
    @Autowired
    IUserService iUserService;
    @Autowired
    IRoleBasicsService iRoleBasicsService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    MailUtil mailUtil;

    public UserDetail getUserDetail() {
        return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Integer projectIdToId(String projectId) {
        return iProjectBasicsService.getOne(
                new QueryWrapper<ProjectBasics>().lambda().eq(ProjectBasics::getProjectId, projectId)).getId();
    }

    public String idToProjectId(Integer id) {
        return iProjectBasicsService.getById(id).getProjectId();
    }
}
