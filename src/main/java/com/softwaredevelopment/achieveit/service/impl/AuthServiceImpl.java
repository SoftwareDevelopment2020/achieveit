package com.softwaredevelopment.achieveit.service.impl;


import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.service.AuthService;
import com.softwaredevelopment.achieveit.service.UserDetailService;
import com.softwaredevelopment.achieveit.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-29 01:44
 */

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * 登录
     *
     * @param username
     * @param password
     * @return jwt token
     */
    @Override
    public String login(String username, String password) {

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    // 注册
    @Override
    public UserDetail register(UserDetail userToAdd) {

        final String username = userToAdd.getUsername();
        if (userDetailService.loadUserByUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        return userDetailService.save(userToAdd);
    }
}
