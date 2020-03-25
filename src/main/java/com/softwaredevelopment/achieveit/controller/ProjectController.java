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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
//@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ProjectController extends BaseController {


    @Autowired
    ProjectService projectService;


    @ApiOperation("新建项目 要求project_id 不重复 否则返回Fail")
    @PostMapping("new_project")
    public HttpResponse<Object> makeNewProjectBasics(@RequestBody ProjectBasics newProjectBasics) {
        if (projectService.newProjectBasics(newProjectBasics)) {
            return responseOK(newProjectBasics);
        }
        return responseFail("project_id duplicated");

    }


    @ApiOperation("综合查询接口")
    @PostMapping("search_projects")
    public HttpResponse<IPage<ProjectBasics>> searchProjects(
//            @RequestParam(name = "current", required = false, defaultValue = "1") Integer current,
//            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
//            @RequestBody ProjectBasics projectBasics
            @RequestBody PageSearchRequest<ProjectBasics> search
    ) {
        if (search.getSearchCondition() == null) {
            search.setSearchCondition(new ProjectBasics());
        }
        return responseOK(projectService.searchProjects(
                new Page<>(search.getCurrent(), search.getSize()),
                search.getSearchCondition()
        ));
    }

    @ApiOperation("删除项目接口 测试用")
    @PostMapping("delete_project")
    public HttpResponse<String> deleteProjectAndItsData(Integer projectId) {
        if (projectService.deleteProjectAndItsData(projectId)) {
            return responseOK("删除成功");
        } else {
            return responseFail("删除失败 可能不存在此项目");
        }
    }

    @ApiOperation("更新项目信息")
    @PostMapping("update_project")
    public HttpResponse<Object> updateProject(@RequestBody ProjectBasics projectBasics) {
        if (projectService.updateProject(projectBasics)) {
            return responseOK("更新成功");
        } else {
            return responseFail("更新失败 可能不存在此项目");
        }
    }
}
