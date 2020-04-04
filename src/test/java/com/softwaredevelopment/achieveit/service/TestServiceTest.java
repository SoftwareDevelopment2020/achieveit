package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.http.request.TestRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RainkQ
 * @date 2020/4/4 16:33
 */
@Transactional
@SpringBootTest
class TestServiceTest {
    @Autowired
    TestService testService;

    @Test
    void testGet() {
        System.out.println(testService.testGet());
    }

    @Test
    void testPost() {
        TestRequest testRequest = new TestRequest();
        testRequest.setTestMessage("wow");
        System.out.println(testService.testPost(testRequest));
    }
}
