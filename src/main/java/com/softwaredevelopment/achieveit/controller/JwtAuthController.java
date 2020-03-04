package com.softwaredevelopment.achieveit.controller;


import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * JwtAuthController
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-29 10:32
 */
@RequestMapping("auth")
@Api(tags = "登录注册接口")
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class JwtAuthController extends BaseController {

    @Autowired
    private AuthService authService;

    // 登录
    @ApiOperation("登录接口 post username 和 password 返回 jwt token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HttpResponse<String> createToken(@RequestBody Map<String,String> params) throws AuthenticationException {
        String username = params.get("username");
        String password = params.get("password");
        return responseOK(authService.login(username, password));
    }

    // 注册
    @ApiOperation("注册接口 post 一个 UserDetail对象（只需要有username 和 password） 返回UserDetail对象")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpResponse<UserDetail> register(@RequestBody UserDetail addedUser) throws AuthenticationException {
        return responseOK(authService.register(addedUser));
    }


}
