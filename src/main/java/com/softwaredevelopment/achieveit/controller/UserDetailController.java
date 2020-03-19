package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.UserDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RainkQ
 * @date 2020/3/19 11:26
 */

//@Secured("authenticated")
@RestController
@Api(tags = "用户信息接口")
@RequestMapping("user_detail")
public class UserDetailController extends BaseController {
    @Autowired
    UserDetailService userDetailService;

    @ApiOperation("如果没有username参数 返回自己的信息")
    @GetMapping("get_user_info")
    public HttpResponse<Object> getUserInfo(@RequestParam(name = "username", required = false) String username) {
        if (username == null) {
            UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userDetail.setPassword(null);
            return responseOK(userDetail);
        }
        // 否则返回按username查询到的
        UserDetail userDetail = userDetailService.loadUserByUsername(username);
        userDetail.setPassword(null);
        return responseOK(userDetail);
    }
}
