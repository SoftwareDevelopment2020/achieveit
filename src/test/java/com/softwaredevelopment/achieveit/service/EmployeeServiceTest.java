package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.AchieveitApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author RainkQ
 * @date 2020/3/30 09:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AchieveitApplication.class)
class EmployeeServiceTest {
    @Autowired
    EmployeeService employeeService;

    @Test
    void getEmployeesByProjectId() {
        assertDoesNotThrow(() -> System.out.println(employeeService.getEmployeesByProjectId("12345678901")));
        assertThrows(Exception.class, () -> System.out.println(employeeService.getEmployeesByProjectId("00000000000")));
    }
}
