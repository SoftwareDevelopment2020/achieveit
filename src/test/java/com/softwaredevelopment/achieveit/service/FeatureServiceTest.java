package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.AchieveitApplication;
import com.softwaredevelopment.achieveit.PO.entity.Feature;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/25 18:36
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AchieveitApplication.class)
@Transactional
class FeatureServiceTest {

    @Autowired
    FeatureService featureService;


    @Test
    void getFeaturesByProjectId() {
        System.out.println(featureService.getFeaturesByProjectId("12345678901"));
    }

    @Test
    void saveFeature() {
        Feature newFeature = new Feature();
        newFeature.setProjectId(1);
        newFeature.setFeatureName("测试功能1");
        newFeature.setFeatureDetail("测试功能详细");
        newFeature.setFirstTierId(111);
        newFeature.setSecondTierId(222);
        featureService.saveFeature(newFeature);

    }

    @Test
    void saveFeatureBatch() {
        Feature newFeature = new Feature();
        newFeature.setProjectId(1);
        newFeature.setFeatureName("测试功能1");
        newFeature.setFeatureDetail("测试功能详细");
        newFeature.setFirstTierId(111);
        newFeature.setSecondTierId(222);
        List<Feature> features = new ArrayList<>();
        features.add(newFeature);
        featureService.saveFeatureBatch(features);
    }

    @Test
    @Transactional
    void uploadFeatures() throws Exception {
        MultipartFile file;
        file = new MockMultipartFile("测试.xlsx", "测试.xlsx", null, new FileInputStream("./excels/测试功能导入xlsx.xlsx"));
        featureService.uploadFeature(file, "12345678901");

    }
}
