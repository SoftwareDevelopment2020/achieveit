package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.http.request.TestRequest;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.http.response.TestResponse;
import com.softwaredevelopment.achieveit.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@Api(tags = "测试连接")
public class TestController extends BaseController{

    @Autowired
    private TestService testService;

    @GetMapping
    @ApiOperation("测试GET方法")
    public HttpResponse<TestResponse> testGet() {
        return responseOK(testService.testGet());
    }

    @PostMapping
    @ApiOperation("测试POST方法")
    public HttpResponse<TestResponse> testPost(@RequestBody TestRequest testRequest) {
        return responseOK(testService.testPost(testRequest));
    }
}
