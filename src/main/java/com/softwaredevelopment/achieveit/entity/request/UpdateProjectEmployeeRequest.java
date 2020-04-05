package com.softwaredevelopment.achieveit.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "更新项目人员请求")
public class UpdateProjectEmployeeRequest {

    @ApiModelProperty(value = "项目人员key", required = true)
    public Integer projectEmployeeId;

    @ApiModelProperty(value = "角色")
    public List<String> roles;

    @ApiModelProperty(value = "权限")
    public List<String> permissions;
}
