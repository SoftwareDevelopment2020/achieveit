package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.PO.entity.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

/**
 * @author RainkQ
 * @date 2020/3/25 18:36
 */
@SpringBootTest
class FeatureServiceTest {

    @Autowired
    FeatureService featureService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getFeaturesByProjectId() {
        System.out.println(featureService.getFeaturesByProjectId(1));
    }

    @Test
    void saveFeature() {
        Feature newFeature = new Feature();
        newFeature.setProjectId(1);
        newFeature.setFeatureName("测试功能1");
        newFeature.setFeatureDetail("测试功能详细");
        newFeature.setFirstTierId(111);
        newFeature.setSecondTierId(222);
//        featureService.saveFeature(newFeature);

    }

    @Test
    void saveFeatureBatch() {
    }

    @Test
    void uploadFeatures() throws Exception {
        MultipartFile file;
        file = new MockMultipartFile("测试.xlsx", "测试.xlsx", null, new FileInputStream("./excels/测试功能导入xlsx.xlsx"));
//        featureService.uploadFeature(file,1);

    }
}
