package com.softwaredevelopment.achieveit.config;

import com.softwaredevelopment.achieveit.service.UserDetailService;
import com.softwaredevelopment.achieveit.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtFilter
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-28 11:26
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // get authHeader
        String authHeader = request.getHeader(Const.HEADER_STRING);
        // if authHeader has token
        if (authHeader != null && authHeader.startsWith(Const.TOKEN_PREFIX)) {
            // get token
            final String authToken = authHeader.substring(Const.TOKEN_PREFIX.length());
            // get username from token
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            // if got username and SecurityContext has no authentication
            // will go to retrieve userDetail
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // got userDetail from db (or cache)
                UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
                // validate the token with the userDetail
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    // set authentication
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
