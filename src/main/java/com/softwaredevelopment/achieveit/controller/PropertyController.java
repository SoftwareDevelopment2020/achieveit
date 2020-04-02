package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.PO.entity.Property;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/4/2 08:37
 */
@Api("设备接口")
@RestController
@RequestMapping("device")
public class PropertyController extends BaseController {

    @Autowired
    PropertyService propertyService;

    @ApiOperation("按projectId拿到资产")
    @GetMapping("property_by_project_id")
    public HttpResponse<List<Property>> getPropertiesByProjectId(String projectId) {
        return responseOK(propertyService.getPropertiesByProjectId(projectId));
    }
}
