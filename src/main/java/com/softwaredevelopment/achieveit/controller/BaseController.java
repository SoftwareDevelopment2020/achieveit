package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.http.response.HttpResponse;

public class BaseController {

    /**
     * 成功 返回数据或默认信息
     */
    protected <T> HttpResponse<T> responseOK(T data) {
        HttpResponse<T> response = new HttpResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    /**
     * 失败 返回信息
     */
    protected <T> HttpResponse<T> responseFail(T data) {
        HttpResponse<T> response = new HttpResponse<>();
        response.setSuccess(false);
        response.setData(data);
        return response;
    }
}
