package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.http.request.TestRequest;
import com.softwaredevelopment.achieveit.http.response.TestResponse;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public TestResponse testGet() {
        TestResponse response = new TestResponse();
        response.setMessage("success");
        return response;
    }

    public TestResponse testPost(TestRequest testRequest) {
        TestResponse response = new TestResponse();
        response.setMessage(testRequest.getTestMessage());
        return response;
    }
}
