package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.PO.service.*;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.utils.MailUtil;
import com.softwaredevelopment.achieveit.utils.RedisUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/25 15:42
 */
@Getter
@Transactional
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
    IActivityService iActivityService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    MailUtil mailUtil;

    public UserDetail currentUserDetail() throws Exception {
        try {
            return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new Exception("用户未登录");
        }
    }

    public Integer projectIdToId(String projectId) {
        return iProjectBasicsService.getOne(
                new QueryWrapper<ProjectBasics>().lambda().eq(ProjectBasics::getProjectId, projectId)).getId();
    }

    public String idToProjectId(Integer id) {
        return iProjectBasicsService.getById(id).getProjectId();
    }

    public <T> PageInfo<T> getPageInfo(PageSearchRequest request, MapperFunction<T> function) {
        PageHelper.startPage(request.getCurrent(), request.getSize());
        return (PageInfo<T>) new PageInfo(function.run());
    }

    public <T> IPage<T> getIPage(PageInfo pageInfo, MapperFunction<T> function) {
        IPage<T> page = new Page<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getTotal());
        page.setRecords(pageInfo.getList().isEmpty() ? new ArrayList<>() : function.run());
        return page;
    }

    @FunctionalInterface
    interface MapperFunction<T> {
        List<T> run();
    }
}
