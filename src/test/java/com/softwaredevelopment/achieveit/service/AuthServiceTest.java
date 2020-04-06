package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.AchieveitApplication;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author RainkQ
 * @date 2020/3/30 09:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AchieveitApplication.class)
@Transactional
class AuthServiceTest {

    @Autowired
    AuthService authService;


    @Test
    void login() {
        authService.login("zhangsan", "123456");
        assertThrows(Exception.class, () -> authService.login("noOne", "111111"));
    }

    @Test
    void register() {
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername("wow");
        userDetail.setPassword("testpass");
        authService.register(userDetail);
    }

    @Test
    void canAccess() {
        authService.canAccess(new MockHttpServletRequest(), new UsernamePasswordAuthenticationToken(null, null));
        authService.login("zhangsan", "123456");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("projectId", "1");
        authService.canAccess(request, authentication);
        request.setParameter("projectId", "ww");
        authService.canAccess(request, authentication);

    }
}
