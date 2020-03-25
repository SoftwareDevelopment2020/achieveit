package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.User;
import com.softwaredevelopment.achieveit.PO.service.IUserService;
import com.softwaredevelopment.achieveit.entity.UserDetail;
import com.softwaredevelopment.achieveit.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * AuthService
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-29 01:43
 */
@Service
public class AuthService extends BaseService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUserService poUserService;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return jwt token
     */
    public String login(String username, String password) throws AuthenticationException {

        // TODO 第三方登录？

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        final UserDetails userDetails = userDetailService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }


    /**
     * 注册
     *
     * @param userToAdd
     * @return
     */
    @Transactional
    public UserDetail register(UserDetail userToAdd) {

        final String username = userToAdd.getUsername();
        if (poUserService.getOne(new QueryWrapper<User>().eq("username", username)) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        return userDetailService.save(userToAdd);
    }

    /**
     * 鉴权
     *
     * @param request
     * @param authentication
     * @return pass or not
     */
    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        UserDetail userDetail;
        // 如果authentication的principal不正常 比如为空 直接拒绝
        if (authentication.getPrincipal() instanceof UserDetail) {
            userDetail = (UserDetail) authentication.getPrincipal();
        } else {
            return false;
        }
        Map<Integer, List<String>> permissionsMap = userDetail.getPermissionsMap();
        String projectIdString = request.getParameter("projectId");

        try {
            int projectId = Integer.parseInt(projectIdString);
            // 如果permissionMap里有projectId
            // TODO 现在只是判断有这个项目的权限 未判断具体权限
            if (permissionsMap != null
                    && permissionsMap.get(projectId) != null
                    && !permissionsMap.get(projectId).isEmpty()) {
                return true;
            }
        } catch (NumberFormatException e) {
            // projectIdString 不是数字类型
            return false;
        }

        return false;
    }
}
