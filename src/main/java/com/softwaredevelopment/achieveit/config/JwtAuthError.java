package com.softwaredevelopment.achieveit.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author RainkQ
 * @date 2020/3/19 14:00
 */
@Component
public class JwtAuthError implements AuthenticationEntryPoint, AccessDeniedHandler {
    /**
     * 认证失败处理，返回401 json数据
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("{\n" +
                "    \"status\": 401,\n" +
                "    \"error\": \"Forbidden\",\n" +
                "    \"message\": \"认证失败\",\n" +
                "    \"path\": \" " + request.getRequestURI() + "\"\n" +
                "}");
        printWriter.flush();
        printWriter.close();

    }

    /**
     * 鉴权失败处理，返回403 json数据
     *
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("{\n" +
                "    \"status\": 403,\n" +
                "    \"error\": \"Forbidden\",\n" +
                "    \"message\": \"鉴权失败\",\n" +
                "    \"path\": \" " + request.getRequestURI() + "\"\n" +
                "}");
        printWriter.flush();
        printWriter.close();
    }
}
