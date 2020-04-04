package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.AchieveitApplication;
import com.softwaredevelopment.achieveit.PO.entity.RoleBasics;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author RainkQ
 * @date 2020/3/30 09:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AchieveitApplication.class)
@Transactional
class UserDetailServiceTest {

    @Autowired
    UserDetailService userDetailService;

    @Test
    void loadUserByUsername() {
        System.out.println(userDetailService.loadUserByUsername("wangwu"));
        System.out.println(userDetailService.loadUserByUsername("zhangsan"));
        System.out.println(userDetailService.loadUserByUsername("epg_leader"));
        assertThrows(UsernameNotFoundException.class,
                () -> System.out.println(userDetailService.loadUserByUsername("noOne")));
    }

    @Test
    void save() {
        assertThrows(Exception.class,
                () -> System.out.println(userDetailService.save(new UserDetail())));
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername("wowTest");
        userDetail.setPassword("123456");

        System.out.println(userDetailService.save(userDetail));

        UserDetail userDetail1 = new UserDetail();
        userDetail1.setUsername("wowTest2");
        userDetail1.setPassword("123456");
        RoleBasics roleBasics = new RoleBasics();
        roleBasics.setName("ROLE_EPG");
        roleBasics.setDetail("EPG");
        roleBasics.setId(13);
        List<RoleBasics> roleBasicsList = new ArrayList<>();
        roleBasicsList.add(roleBasics);
        userDetail1.setRoles(roleBasicsList);
        System.out.println(userDetailService.save(userDetail1));

    }
}
