package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.entity.UserDetail;

/**
 * AuthService
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-29 01:43
 */
public interface AuthService {

    UserDetail register(UserDetail userToAdd);

    String login(String username, String password);
}
