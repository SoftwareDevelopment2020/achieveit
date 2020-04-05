package com.softwaredevelopment.achieveit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.softwaredevelopment.achieveit.PO.entity.Property;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("查询设备")
    @PostMapping("search_properties")
    public HttpResponse<IPage<Property>> searchProperties(@RequestBody PageSearchRequest<Property> request) {
        return responseOK(propertyService.searchProperties(request));
    }

    @ApiOperation("添加设备")
    @PostMapping("add_property")
    public HttpResponse<Boolean> addProperty(@RequestBody Property property) throws BussinessException {
        return responseOK(propertyService.addProperty(property));
    }

    @ApiOperation("更新设备信息")
    @PostMapping("update_property")
    public HttpResponse<Boolean> updateProperty(@RequestBody Property property) {
        return responseOK(propertyService.updateProperty(property));
    }
}
