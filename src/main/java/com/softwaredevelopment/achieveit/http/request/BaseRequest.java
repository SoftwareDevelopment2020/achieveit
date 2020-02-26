package com.softwaredevelopment.achieveit.http.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BaseRequest {

    @ApiModelProperty(value = "请求标识", hidden = true)
    public String requestId;

    @ApiModelProperty(value = "用户标识", hidden = true)
    public String curUserId;

    @ApiModelProperty(value = "用户IP", hidden = true)
    public String curUserIp;

    @ApiModelProperty(value = "当前时间", hidden = true)
    public Date curTime;

}
