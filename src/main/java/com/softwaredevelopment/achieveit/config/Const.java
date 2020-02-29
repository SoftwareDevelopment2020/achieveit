package com.softwaredevelopment.achieveit.config;

/**
 * Const
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-28 10:42
 */
public class Const {
    // 5天(以毫秒ms计)
    public static final long EXPIRATION_TIME = 432_000_000;
    // JWT密码
    public static final String SECRET = "achieveitSecret";
    // Token前缀
    public static final String TOKEN_PREFIX = "Bearer";
    // 存放Token的Header Key
    public static final String HEADER_STRING = "Authorization";
}
