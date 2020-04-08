package com.softwaredevelopment.achieveit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.softwaredevelopment.achieveit.PO.entity.GlobalRisk;
import com.softwaredevelopment.achieveit.PO.entity.Risk;
import com.softwaredevelopment.achieveit.entity.RiskVO;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.RiskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author RainkQ
 * @date 2020/3/30 15:02
 */
@Api(tags = "风险接口")
@RequestMapping("risk")
@RestController
public class RiskController extends BaseController {
    @Autowired
    RiskService riskService;

    @ApiOperation("分页查询Risks")
    @PostMapping("search_risks")
    public HttpResponse<IPage<RiskVO>> getRisksByPage(@RequestParam String projectId, @RequestBody PageSearchRequest<Map<String, String>> pageSearchRequest) throws BussinessException {
        return responseOK(riskService.getRisksByPage(projectId, pageSearchRequest));
    }


    @ApiOperation("通过projectId获取Risks")
    @GetMapping("risks_by_project_id")
    public HttpResponse<List<RiskVO>> getRisksByProjectId(String projectId) throws BussinessException {
        return responseOK(riskService.getRisksByProjectId(projectId));
    }


    @ApiOperation("向project中添加risk")
    @PostMapping("save_risk")
    public HttpResponse<String> saveRiskByProjectId(@RequestBody Risk risk, @RequestParam String projectId) {
        if (riskService.saveRiskByProjectId(risk, projectId)) {
            return responseOK("添加成功");
        }
        return responseFail("添加失败");
    }

    @Scheduled(cron = "0 0 8 ? * 2")
    public void sendRiskMail() {
        riskService.sendRiskMail();
    }

    @ApiOperation("所有组织标准库中风险")
    @GetMapping("global_risks")
    public HttpResponse<List<GlobalRisk>> allGlobalRisks() {
        return responseOK(riskService.getAllGlobalRisks());
    }

    @ApiOperation("更新风险信息")
    @PostMapping("update_risk")
    public HttpResponse<String> updateRisk(@RequestBody Risk risk, @RequestParam String projectId) {
        if (riskService.updateRisk(risk, projectId)) {
            return responseOK("更新成功");
        } else {
            return responseFail("更新失败");
        }
    }

}
