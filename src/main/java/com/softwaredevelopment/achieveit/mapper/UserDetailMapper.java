package com.softwaredevelopment.achieveit.mapper;

import com.softwaredevelopment.achieveit.entity.PermissionByProject;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMapper
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-28 13:45
 */
@Repository
public interface UserDetailMapper {

    UserDetail selectOneUserByUsername(String username);

    List<PermissionByProject> selectPermissionsPerProjectByUserId(Integer id);

    UserDetail insertUserToAdd(UserDetail userToAdd);

}
