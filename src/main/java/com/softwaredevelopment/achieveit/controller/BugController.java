package com.softwaredevelopment.achieveit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.Bug;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.BugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RainkQ
 * @date 2020/3/30 17:22
 */
@Api(tags = "缺陷接口")
@RequestMapping("bug")
@RestController
public class BugController extends BaseController {
    @Autowired
    BugService bugService;

    @ApiOperation("按projectId获取bug 分页")
    @GetMapping("bugs_by_project_id")
    public HttpResponse<Page<Bug>> getBugsByProjectId(String projectId, Integer current, Integer size) {
        return responseOK(bugService.getBugsByProjectId(projectId, current, size));
    }

    @ApiOperation("添加bug")
    @PostMapping("save_bug")
    public HttpResponse<String> saveBugByProjectId(String projectId, Bug bug) {
        if (bugService.saveBugByProjectId(projectId, bug)) {
            return responseOK("添加成功");
        } else {
            return responseFail("添加失败");
        }
    }

    @ApiOperation("更新bug信息（包括状态等）")
    @PostMapping("update_bug")
    public HttpResponse<String> updateBugByProjectId(String projectId, Bug bug) {
        if (bugService.updateBugByProjectId(projectId, bug)) {
            return responseOK("添加成功");
        } else {
            return responseFail("添加失败");
        }
    }
}