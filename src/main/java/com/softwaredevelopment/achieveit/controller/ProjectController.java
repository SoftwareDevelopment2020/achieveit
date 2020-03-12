package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.PO.mapper.ProjectBasicsMapper;
import com.softwaredevelopment.achieveit.entity.Project;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ProjectController extends BaseController {

    @Autowired
    ProjectBasicsMapper projectBasicsMapper;

    @Autowired
    ProjectService projectService;

    @ApiOperation("项目基本信息 需要有该项目的权限才能访问")
    @GetMapping("project_basics")
    public HttpResponse<ProjectBasics> getProjectBasics(Integer projectId) {

        return responseOK(projectBasicsMapper.selectById(projectId));
    }


    @ApiOperation("新建项目")
    @PostMapping("new_project")
    public HttpResponse<Project> makeNewProjectBasics(@RequestBody Project newProject) {
        // TODO 未完成
        return responseOK(newProject);

    }

}
