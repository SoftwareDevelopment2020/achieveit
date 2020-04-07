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
    @GetMapping("search_manhour")
    public HttpResponse<IPage<ManHour>> getManHourSearchPage(@RequestBody PageSearchRequest<ManHour> request) {
        return responseOK(manHourService.getManHourSearchPage(request));
    }

    @ApiOperation("添加工时")
    @PostMapping("add_manhour")
    public HttpResponse<Boolean> addManHour(@RequestBody ManHour manHour) throws BussinessException {
        return responseOK(manHourService.addManHour(manHour));
    }

    @ApiOperation("审批工时 只需工时里有id")
    @PostMapping("audit_manhour")
    public HttpResponse<String> auditManHour(@RequestBody ManHour manHour) {
        if (manHourService.auditManHour(manHour.getId())) {
            return responseOK("审批成功");
        } else {
            return responseFail("审批失败");
        }
    }

    @ApiOperation("获取所有Activities")
    @GetMapping("activities")
    public HttpResponse<List<ActivityVO>> getAllActivities() {
        return responseOK(manHourService.getAllActivities());
    }
}
