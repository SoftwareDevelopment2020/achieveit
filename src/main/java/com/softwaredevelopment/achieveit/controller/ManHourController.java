package com.softwaredevelopment.achieveit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.softwaredevelopment.achieveit.PO.entity.ManHour;
import com.softwaredevelopment.achieveit.entity.ActivityVO;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.ManHourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/4/2 16:32
 */
@Api(tags = "工时接口")
@RestController
@RequestMapping("manhour")
public class ManHourController extends BaseController {
    @Autowired
    ManHourService manHourService;

    @ApiOperation("分页查询工时信息")
    @PostMapping("search_manhour")
    public HttpResponse<IPage<ManHour>> getManHourSearchPage(@RequestBody PageSearchRequest<ManHour> request) throws BussinessException {
        return responseOK(manHourService.getManHourSearchPage(request));
    }

    @ApiOperation("添加工时")
    @PostMapping("add_manhour")
    public HttpResponse<Boolean> addManHour(@RequestBody ManHour manHour) throws BussinessException {
        return responseOK(manHourService.addManHour(manHour));
    }

    @ApiOperation("修改工时")
    @PostMapping("update_manhour")
    public HttpResponse<Boolean> updateManHour(@RequestBody ManHour manHour) throws BussinessException {
        return responseOK(manHourService.updateManHour(manHour));
    }

    @ApiOperation("获取所有Activities")
    @GetMapping("activities")
    public HttpResponse<List<ActivityVO>> getAllActivities() {
        return responseOK(manHourService.getAllActivities());
    }
}
