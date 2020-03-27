package com.softwaredevelopment.achieveit.controller;

import lombok.Data;

@Data
public class BussinessException extends Exception {

    private String returnMessage;

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
        this.returnMessage = "服务器异常";
    }

    public BussinessException(String message, Throwable cause, String returnMessage) {
        super(message, cause);
        this.returnMessage = returnMessage;
    }
}
