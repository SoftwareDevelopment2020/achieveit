package com.softwaredevelopment.achieveit.http.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel(value = "测试请求")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class TestRequest extends BaseRequest {

    @ApiModelProperty(value = "测试信息", required = true)
    public String testMessage;
}
