package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.PO.mapper.ProjectBasicsMapper;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProjectController
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-03-01 13:32
 */
@RequestMapping("project")
@Api(tags = "项目信息接口")
@RestController
public class ProjectController extends BaseController {

    @Autowired
    ProjectBasicsMapper projectBasicsMapper;

    // TODO just a test
    @ApiOperation("项目基本信息 需要有该项目的权限才能访问")
    @GetMapping("project_basics")
    public HttpResponse<ProjectBasics> testAuthorities(Integer projectId) {

        return responseOK(projectBasicsMapper.selectById(projectId));
    }
}
