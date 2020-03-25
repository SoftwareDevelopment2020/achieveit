package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.PO.entity.Feature;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.FeatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/25 17:21
 */
@Api(tags = "功能接口")
@RequestMapping("feature")
@RestController
public class FeatureController extends BaseController {
    @Autowired
    FeatureService featureService;

    @ApiOperation("通过projectId(11位) 取得项目的所有Feature")
    @GetMapping("features_by_project_id")
    public HttpResponse<List<Feature>> getFeaturesByProjectId(Integer projectId) {
        return responseOK(featureService.getFeaturesByProjectId(projectId));
    }


}
