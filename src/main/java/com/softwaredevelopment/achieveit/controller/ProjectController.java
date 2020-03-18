package com.softwaredevelopment.achieveit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.entity.PageSearchRequest;
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
    ProjectService projectService;

//    @ApiOperation("项目基本信息")
//    @GetMapping("project_basics")
//    public HttpResponse<ProjectBasics> getProjectBasics(Integer id) {
//        return responseOK(projectService.selectById(id));
//    }
//
//    @ApiOperation("分页查询所有项目的基本信息")
//    @GetMapping("all_projects")
//    public HttpResponse<List<Project>> getAllProjects(
//            @RequestParam(name = "current", required = false, defaultValue = "1") Integer current,
//            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
//        List<Project> projectsByPage = projectService.selectAllProjectsByPage(new Page<>(current, size));
//        return responseOK(projectsByPage);
//    }
//
//    @ApiOperation("分页模糊查询名字")
//    @GetMapping("projects_by_name")
//    public HttpResponse<List<ProjectBasics>> getProjectBasicsByName(
//            @RequestParam(name = "current", required = false, defaultValue = "1") Integer current,
//            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
//            @RequestParam(name = "name") String name) {
//        Page<ProjectBasics> page = new Page<>(current, size);
//        List<ProjectBasics> selectListByName = projectService.selectListByName(page, name);
//        return responseOK(selectListByName);
//    }
//
//    @ApiOperation("按project_id 查询一个")
//    @GetMapping("project_basics_by_id")
//    public HttpResponse<ProjectBasics> getProjectBasicsByProjectId(String projectId) {
//        return responseOK(projectService.selectByProjectId(projectId));
//    }


    @ApiOperation("新建项目 要求project_id 不重复 否则返回Fail")
    @PostMapping("new_project")
    public HttpResponse<Object> makeNewProjectBasics(@RequestBody ProjectBasics newProjectBasics) {
        Boolean successToSave = projectService.saveProjectBasics(newProjectBasics);
        if (!successToSave) {
            return responseFail("project_id duplicated");
        }
        return responseOK(newProjectBasics);

    }


    @ApiOperation("综合查询接口")
    @PostMapping("search_projects")
    public HttpResponse<IPage<ProjectBasics>> searchProjects(
//            @RequestParam(name = "current", required = false, defaultValue = "1") Integer current,
//            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
//            @RequestBody ProjectBasics projectBasics
            @RequestBody PageSearchRequest<ProjectBasics> projectBasics
    ) {
        return responseOK(projectService.searchProjects(
                new Page<>(projectBasics.getCurrent(), projectBasics.getSize()),
                projectBasics.getSearchCondition()
        ));
    }
}
