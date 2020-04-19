package com.softwaredevelopment.achieveit.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("添加项目人员请求")
public class AddProjectEmployeeRequest {

    @ApiModelProperty(value = "项目key", required = true)
    public Integer projectKey;

    @ApiModelProperty(value = "员工ID", required = true)
    public Integer employeeId;

    @ApiModelProperty(value = "角色", required = true)
    public List<String> roles;

    @ApiModelProperty(value = "权限")
    public List<String> permissions;

    @ApiModelProperty(value = "项目上级key")
    public Integer superiorKey;
}
