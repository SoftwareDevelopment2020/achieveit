package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.PO.entity.User;
import com.softwaredevelopment.achieveit.PO.mapper.UserMapper;
import com.softwaredevelopment.achieveit.entity.PermissionByProject;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.mapper.UserDetailMapper;
import com.softwaredevelopment.achieveit.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@CacheConfig(cacheNames = "userdetail")
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserDetailMapper userDetailMapper;

    // PO层
    @Autowired
    UserMapper userMapper;

    @Cacheable(key = "#s")
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetail userDetail = userDetailMapper.selectOneUserByUsername(s);
//        System.out.println(userDetail);
        Map<Integer, List<String>> map = new HashMap<>();
        // 如果有这个用户 拿取权限
        if (userDetail != null) {
            List<PermissionByProject> permissionByProjects = userDetailMapper.selectPermissionsPerProjectByUserId(userDetail.getId());
            for (PermissionByProject permissionByProject :
                    permissionByProjects) {
                map.put(permissionByProject.getProjectId(),
                        StringHelper.stringsDivideByCommaToList(permissionByProject.getPermissions()));
            }
            userDetail.setPermissionsMap(map);
        }
//        System.out.println(userDetail);
        return userDetail;
    }


    @Transactional
    public UserDetail save(UserDetail userToAdd) {
        User user = new User();
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsEnabled(true);

        user.setUsername(userToAdd.getUsername());
        user.setPassword(userToAdd.getPassword());
        userMapper.insert(user);
        userToAdd.setId(user.getId());
        userToAdd.setAccountNonExpired(user.getIsAccountNonExpired());
        userToAdd.setAccountNonLocked(user.getIsAccountNonLocked());
        userToAdd.setCredentialsNonExpired(user.getIsCredentialsNonExpired());
        userToAdd.setEnabled(user.getIsEnabled());
        return userToAdd;
    }

}
