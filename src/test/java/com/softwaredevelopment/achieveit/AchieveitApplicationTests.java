package com.softwaredevelopment.achieveit;

import com.softwaredevelopment.achieveit.controller.JwtAuthController;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.service.AuthService;
import com.softwaredevelopment.achieveit.service.UserDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AchieveitApplicationTests {

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    AuthService authService;

    @Autowired
    JwtAuthController jwtAuthController;

    @Test
    void contextLoads() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername("lisi");
        userDetail.setPassword("123456");
        System.out.println(authService.register(userDetail));

    }

}
