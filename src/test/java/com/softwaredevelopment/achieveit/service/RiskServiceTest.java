package com.softwaredevelopment.achieveit.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RainkQ
 * @date 2020/3/31 21:47
 */
@SpringBootTest
@Transactional
class RiskServiceTest {
    @Autowired
    RiskService service;

    @Test
    void getRisksByProjectId() {
    }

    @Test
    void saveRiskByProjectId() {
    }

    @Test
    void sendRiskMail() {
        service.sendRiskMail();
    }
}
