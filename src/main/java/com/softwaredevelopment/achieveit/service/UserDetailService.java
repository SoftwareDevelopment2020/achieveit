package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.RoleBasics;
import com.softwaredevelopment.achieveit.PO.entity.User;
import com.softwaredevelopment.achieveit.PO.entity.UserRole;
import com.softwaredevelopment.achieveit.entity.PermissionByProject;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.mapper.UserDetailMapper;
import com.softwaredevelopment.achieveit.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserService
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-28 11:34
 */
@CacheConfig(cacheNames = "userDetail")
@Service
public class UserDetailService extends BaseService implements UserDetailsService {

    @Autowired
    UserDetailMapper userDetailMapper;

    /**
     * 从数据库拿取UserDetail 会缓存入Redis
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
//    @Cacheable(key = "#username")
    @Override
    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail = userDetailMapper.selectOneUserByUsername(username);
        // 如果没有这个用户
        if (userDetail == null) {
            throw new UsernameNotFoundException("用户不存在");
        }


        // 获取角色
        int userId = userDetail.getId();
        // 先拿出所有的角色基本信息
        List<RoleBasics> roleBasics = iRoleBasicsService.list();
        Map<Integer, RoleBasics> roleBasicsMap = new HashMap<>();
        // 放入map中好取
        for (RoleBasics rb :
                roleBasics) {
            roleBasicsMap.put(rb.getId(), rb);
        }
        // 根据userId拿到userRole的映射
        List<UserRole> userRolesByUserId = iUserRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId));
        // 如果有角色
        List<RoleBasics> resultRoles = new ArrayList<>();
        if (userRolesByUserId.size() > 0) {
            // 从map中拿出角色
            for (UserRole ur :
                    userRolesByUserId) {
                RoleBasics basics = roleBasicsMap.get(ur.getRoleId());
                resultRoles.add(basics);
            }
            // 放入userDetail
        }
        userDetail.setRoles(resultRoles);
        // 获取角色完成


        // 如果有这个employee信息 拿取employee信息和权限
        if (userDetail.getEmployeeId() != null) {

            Map<Integer, List<String>> map = new HashMap<>(10);
            List<PermissionByProject> permissionByProjects =
                    userDetailMapper.selectPermissionsPerProjectByEmployeeId(userDetail.getEmployeeId());
            for (PermissionByProject permissionByProject :
                    permissionByProjects) {
                map.put(permissionByProject.getProjectId(),
                        StringHelper.stringsDivideByCommaToList(permissionByProject.getPermissions()));
            }
            userDetail.setPermissionsMap(map);
        }

        return userDetail;
    }


    /**
     * 注册用户 只需要有username 和 password
     *
     * @param userToAdd
     * @return
     */
    @Transactional
    public UserDetail save(UserDetail userToAdd) {
        User user = new User();
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsEnabled(true);

        user.setUsername(userToAdd.getUsername());
        user.setPassword(userToAdd.getPassword());
        iUserService.save(user);

        userToAdd.setId(user.getId());
        userToAdd.setAccountNonExpired(user.getIsAccountNonExpired());
        userToAdd.setAccountNonLocked(user.getIsAccountNonLocked());
        userToAdd.setCredentialsNonExpired(user.getIsCredentialsNonExpired());
        userToAdd.setEnabled(user.getIsEnabled());

        List<RoleBasics> roleBasics = userToAdd.getRoles();
        if (roleBasics != null && roleBasics.size() > 0) {
            List<UserRole> userRoles = new ArrayList<>();
            for (RoleBasics rb :
                    roleBasics) {
                UserRole ur = new UserRole();
                ur.setUserId(userToAdd.getId());
                ur.setRoleId(rb.getId());
                userRoles.add(ur);
            }
            iUserRoleService.saveBatch(userRoles);
        } else {
            // 如果没有role
            UserRole ur = new UserRole();
            ur.setUserId(userToAdd.getId());
            ur.setRoleId(3);
            iUserRoleService.save(ur);
        }
        return userToAdd;
    }

}
