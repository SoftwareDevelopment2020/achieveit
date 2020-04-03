package com.softwaredevelopment.achieveit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.*;
import com.softwaredevelopment.achieveit.service.AuthService;
import com.softwaredevelopment.achieveit.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author RainkQ
 * @date 2020/3/31 19:59
 */
@SpringBootTest
@Transactional
public class MockData {

    @Autowired
    ProjectService s;
    @Autowired
    AuthService authService;


    @Test
    void mockAll() {
        int total = 100;
        Random random = new Random();
        List<User> usersList = new ArrayList<>(total);
        List<EmployeeBasics> employeeBasicsList = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            EmployeeBasics eb = new EmployeeBasics();
            eb.setName("测试雇员" + i);
            // 随即两个部门
            eb.setDepartment(random.nextBoolean() ? "技术部" : "运营部");
            eb.setEmailAddress("testEmail" + i);
            eb.setTel("12345" + (random.nextInt(800000) + 100000));
            employeeBasicsList.add(eb);
        }
        s.getIEmployeeBasicsService().saveBatch(employeeBasicsList);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // User
        for (int i = 0; i < total; i++) {
            if (employeeBasicsList.get(i).getId() == null) {
                return;
            }

            User user = new User();
            user.setUsername("testUser" + i);
            user.setPassword(encoder.encode("123456"));
            user.setIsAccountNonExpired(true);
            user.setIsCredentialsNonExpired(true);
            user.setIsAccountNonLocked(true);
            user.setIsEnabled(true);
            user.setEmployeeBasicsId(employeeBasicsList.get(i).getId());
            usersList.add(user);
        }
        s.getIUserService().saveBatch(usersList);

        List<ProjectBasics> projectBasicsList = new ArrayList<>(total);
        int[] pBStatusId = new int[]{
                0, 1, 2, 3000, 3001, 3010, 3100, 3011, 3101, 3110, 3111
        };
        char[] developType = new char[]{
                'D', 'M', 'S', 'O'
        };
        // 生成新项目
        for (int i = 0; i < total; i++) {
            ProjectBasics pb = new ProjectBasics();
            pb.setClientId(i % 9000 + 1000);
            // 1位开发类型代码
            pb.setProjectId(String.valueOf(developType[random.nextInt(developType.length)]));
            pb.setSuperior(employeeBasicsList.get(random.nextInt(employeeBasicsList.size())).getId());
            projectBasicsList.add(pb);
        }
        // 插入新项目 并且修改statusId
        for (ProjectBasics pb :
                projectBasicsList) {
            User user = usersList.get(random.nextInt(total));
            authService.login(user.getUsername(), "123456");
            try {
                s.newProjectBasics(pb);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pb.setStatusId(pBStatusId[random.nextInt(pBStatusId.length)]);
            s.getIProjectBasicsService().updateById(pb);
        }

        // ProjectEmployee关系
        List<ProjectEmployee> projectEmployeeList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            int num = random.nextInt(10);
            for (int j = 0; j < num; j++) {
                ProjectEmployee pe = new ProjectEmployee();
                EmployeeBasics eb = employeeBasicsList.get(random.nextInt(employeeBasicsList.size()));
                EmployeeBasics eb2 = employeeBasicsList.get(random.nextInt(employeeBasicsList.size()));
                pe.setEmployeeId(eb.getId());
                pe.setSuperiorId(eb2.getId());
                pe.setProjectId(i);
                pe.setJoinTime(LocalDate.now());
                pe.setExitTime(LocalDate.ofYearDay(2021, random.nextInt(364) + 1));
                projectEmployeeList.add(pe);
            }
        }
        s.getIProjectEmployeeService().saveBatch(projectEmployeeList);


    }

    @Test
    void mockUserRoles() {
        Random random = new Random();
        List<User> users = s.getIUserService().list(new QueryWrapper<User>().lambda().likeRight(User::getUsername, "testUser"));
        List<RoleBasics> roleBasicsList = s.getIRoleBasicsService().list().stream()
                .filter((r) -> {
                    return !r.getName().startsWith("ROLE_GLOBAL");
                }).collect(Collectors.toList());

        List<UserRole> userRoleList = s.getIUserRoleService().list(
                new QueryWrapper<UserRole>().lambda()
                        .in(UserRole::getUserId, users.stream().map(User::getId).collect(Collectors.toList())));
        List<UserRole> newList = new ArrayList<>();

        List<User> userListHasNoRole = users.stream()
                .filter((s) -> {
                    for (UserRole ur :
                            userRoleList) {
                        return !ur.getUserId().equals(s.getId());
                    }
                    return false;
                }).collect(Collectors.toList());
        for (User u :
                userListHasNoRole) {
            UserRole ur = new UserRole();
            ur.setUserId(u.getId());
            ur.setRoleId(roleBasicsList.get(random.nextInt(roleBasicsList.size())).getId());
            newList.add(ur);
        }
        s.getIUserRoleService().saveBatch(newList);

    }


    @Test
    void mockRisks() {
        //Risk
        int total = 100;
        Random random = new Random();
        List<EmployeeBasics> employeeBasicsList = s.getIEmployeeBasicsService().list();
        List<ProjectBasics> projectBasicsList = s.getIProjectBasicsService().list();
        List<Risk> riskList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            int num = random.nextInt(10);
            for (int j = 0; j < num; j++) {
                Risk risk = new Risk();
                ProjectBasics pb = projectBasicsList.get(i);
                risk.setProjectId(pb.getId());
                risk.setType("风险类型" + num);
                risk.setDescription("风险描述");
                risk.setLevel(random.nextInt(3));
                risk.setAffect(random.nextInt(3));
                risk.setReact("风险应对");
                risk.setStrategy("风险策略");
                risk.setStatus(random.nextInt(5));
                risk.setResponsible("" + employeeBasicsList.get(random.nextInt(employeeBasicsList.size())).getId());
                StringBuffer related = new StringBuffer();
                for (int k = 0; k < random.nextInt(5); k++) {
                    related.append(employeeBasicsList.get(random.nextInt(employeeBasicsList.size())).getId() + ",");
                }
                // 删掉最后的逗号
                if (related.length() > 0) {
                    related.deleteCharAt(related.length() - 1);
                }

                risk.setRelated(related.toString());
                risk.setTrackFreq(num);
                riskList.add(risk);
            }
        }
        s.getIRiskService().saveBatch(riskList);
    }
}
