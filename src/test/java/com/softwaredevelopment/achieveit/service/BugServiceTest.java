package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.PO.entity.Bug;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RainkQ
 * @date 2020/4/4 16:07
 */
@SpringBootTest
@Transactional
class BugServiceTest {
    @Autowired
    BugService service;
    @Autowired
    AuthService authService;

    Bug mockBug() {
        Bug bug = new Bug();
        bug.setId(1);
        bug.setPriority(0);
        bug.setStartTime(new Date(System.currentTimeMillis()));
        bug.setStatus(0);
        bug.setBugIntroducerId(1);
        bug.setProjectId(1);
        bug.setEndTime(new Date(System.currentTimeMillis()));
        bug.setBugDescription("des");
        bug.setBugTitle("title");
        bug.setBugResponsibleId(1);
        return bug;
    }

    @Test
    void getBugsByProjectId() {
        try {
            service.getBugsByProjectId("12345678901", 1, 10);
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveBugByProjectId() {
        try {
            authService.login("zhangsan", "123456");
            service.saveBugByProjectId("12345678901", mockBug());
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateBugByProjectId() {
        try {
            service.updateBugByProjectId(mockBug());
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void bugToVO() {
        try {
            System.out.println(service.bugToVO(mockBug()));

        } catch (BussinessException e) {
            e.printStackTrace();
        }
        try {
            service.bugToVO(mockBug().setStatus(-1));
        } catch (BussinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBugsByPage() throws BussinessException {
        service.getBugsByPage("12345678901", new PageSearchRequest<>());
        PageSearchRequest<Map<String, String>> request = new PageSearchRequest<>();
        request.setCurrent(1);
        request.setSize(10);
        Map<String, String> map = new HashMap<>();
        map.put("title", "");
        map.put("status", "1");
        map.put("bugIntroducer", "张三");
        request.setSearchCondition(map);
        service.getBugsByPage("12345678901", request);
    }
}
