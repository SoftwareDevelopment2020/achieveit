package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.softwaredevelopment.achieveit.PO.entity.Risk;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.RiskVO;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author RainkQ
 * @date 2020/3/31 21:47
 */
@SpringBootTest
@Transactional
class RiskServiceTest {
    @Autowired
    RiskService service;

    Risk mockRisk() {
        Risk risk = new Risk();
        risk.setProjectId(1);
        risk.setType("风险类型" + 1);
        risk.setDescription("风险描述");
        risk.setLevel(2);
        risk.setAffect(2);
        risk.setReact("风险应对");
        risk.setStrategy("风险策略");
        risk.setStatus(4);
        risk.setResponsible("1");
        risk.setRelated("1,2");
        risk.setTrackFreq(2);
        return risk;
    }

    @Test
    void getRisksByProjectId() {
        try {
            service.getRisksByProjectId("12345678901");
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveRiskByProjectId() {
        service.saveRiskByProjectId(
                mockRisk(), "12345678901"
        );
    }

    @Test
    void sendRiskMail() {
        service.sendRiskMail();
    }


    @Test
    void riskToVO() {
        try {
            System.out.println(service.riskToVO(mockRisk()));
        } catch (BussinessException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(service.riskToVO(mockRisk().setLevel(-1)));
        } catch (BussinessException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(service.riskToVO(mockRisk().setStatus(-1)));
        } catch (BussinessException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(service.riskToVO(mockRisk().setAffect(-1)));
        } catch (BussinessException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(service.riskToVO(mockRisk().setResponsible("aa")));
        } catch (BussinessException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(service.riskToVO(mockRisk().setRelated(null)));
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getRisksByPage() {
        PageSearchRequest<Map<String, String>> pageSearchRequest = new PageSearchRequest<>();

        pageSearchRequest.setCurrent(1);
        pageSearchRequest.setSize(10);
        Map<String, String> map = new HashMap<>();
//        map.put("id", "1");
//        map.put("name", "张三");
        pageSearchRequest.setSearchCondition(map);

        try {
            IPage<RiskVO> risksByPage = service.getRisksByPage(
                    "12345678901", pageSearchRequest
            );
            System.out.println(risksByPage);
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIntOrNull() {
        Assert.assertTrue(service.getIntOrNull("123") == 123);
        Assert.assertNull(service.getIntOrNull(null));
    }
}
