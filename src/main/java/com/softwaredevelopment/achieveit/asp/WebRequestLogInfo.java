package com.softwaredevelopment.achieveit.asp;

import com.softwaredevelopment.achieveit.utils.SerialNumberUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 访问信息
 */
@Data
public class WebRequestLogInfo {

    /**
     * 请求标识
     */
    private String requestId = SerialNumberUtils.UUID();

    /**
     * 请求时间
     */
    private Date requestTime = new Date();

    /**
     * 访问用户标识
     */
    private String userId;

    /**
     * 访问IP
     */
    private String remoteAddr;

    /**
     * 访问主机
     */
    private String remoteHost;

    /**
     * 访问端口
     */
    private int remotePort;

    /**
     * 本机IP
     */
    private String localAddr;

    /**
     * 本机主机
     */
    private String localName;

    /**
     * 本机端口
     */
    private int localPort;

    /**
     * 请求路径
     */
    private String uri;

    /**
     * 请求方式
     */
    private String httpMethod;

    /**
     * 请求类
     */
    private String className;

    /**
     * 请求类方法名
     */
    private String classMethodName;

    /**
     * 请求参数
     */
    private List<String> params = new ArrayList<>();

    /**
     * 添加参数
     */
    public void addParam(String param) {
        this.params.add(param);
    }

}
