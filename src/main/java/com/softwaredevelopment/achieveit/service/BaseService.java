package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.PO.service.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author RainkQ
 * @date 2020/3/25 15:42
 */
public class BaseService {

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

}
