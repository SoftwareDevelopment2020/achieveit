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
//@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ProjectController extends BaseController {

    @Autowired
    ProjectService projectService;

    // TODO 新建项目需要权限
    //      @RolesAllowed("")

    @ApiOperation("新建项目 要求project_id 不重复 否则返回Fail")
    @PostMapping("new_project")
    public HttpResponse<Object> makeNewProjectBasics(@RequestBody ProjectBasics newProjectBasics) throws Exception {
        return responseOK(projectService.newProjectBasics(newProjectBasics));
    }


    @ApiOperation("综合查询接口")
    @PostMapping("search_projects")
    public HttpResponse<IPage<ProjectBasics>> searchProjects(@RequestBody PageSearchRequest<ProjectBasics> search) throws Exception {
        return responseOK(projectService.searchProjects(new Page<>(search.getCurrent(), search.getSize()), search.getSearchCondition()));
    }

    @ApiOperation("删除项目接口 测试用")
    @PostMapping("delete_project")
    public HttpResponse<String> deleteProjectAndItsData(String projectId) {
        if (projectService.deleteProjectAndItsData(projectId)) {
            return responseOK("删除成功");
        } else {
            return responseFail("删除失败 可能不存在此项目");
        }
    }

    @ApiOperation("更新项目信息")
    @PostMapping("update_project")
    public HttpResponse<String> updateProject(@RequestBody ProjectBasics projectBasics) {
        if (projectService.updateProject(projectBasics)) {
            return responseOK("更新成功");
        } else {
            return responseFail("更新失败 可能不存在此项目");
        }
    }

    @ApiOperation("审批或否决项目")
    @PostMapping("examine_project")
    public HttpResponse<String> examineProject(@RequestParam(name = "project_id") String projectId,
                                               @RequestParam(name = "approved") Boolean approved) throws Exception {
        return responseOK(projectService.examineProject(projectId, approved));
    }

    @ApiOperation("三个Global级的配置之后各自通过")
    @PostMapping("init_project")
    public HttpResponse<String> initProject(@RequestParam(name = "project_id") String projectId) throws Exception {
        return responseOK(projectService.initProject(projectId));
    }
}
