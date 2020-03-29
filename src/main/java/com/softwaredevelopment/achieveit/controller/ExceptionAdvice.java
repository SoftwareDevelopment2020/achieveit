package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.utils.log.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionAdvice extends BaseController {

    /**
     * 业务异常
     */
    @ExceptionHandler(value = BussinessException.class)
    public HttpResponse<String> errorHandler(BussinessException e) {
        Logger.logError(e, e.getMessage());
        return responseFail(e.getReturnMessage());
    }

    /**
     * 其他异常
     * 返回默认信息：服务器异常
     */
    @ExceptionHandler(value = Exception.class)
    public HttpResponse<String> errorHandler(Exception e) {
        Logger.logError(e, e.getMessage());
        return responseFail("服务器异常");
    }

}
