package com.softwaredevelopment.achieveit.asp;

import lombok.Data;

import java.util.Date;

@Data
public class WebResponseLogInfo {

    /**
     * 请求标识
     */
    private String requestId;

    /**
     * 响应时间
     */
    private Date responseTime = new Date();

    /**
     * 响应耗时
     */
    private long timeConsuming;

    /**
     * 响应结果
     */
    private Boolean responseSuccess;

    /**
     * 响应数据
     */
    private String responseData;

}
