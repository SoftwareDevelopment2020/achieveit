package com.softwaredevelopment.achieveit.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("项目人员搜索条件")
public class ProjectEmployeeRequest {

    @ApiModelProperty(value = "项目ID", required = true)
    public String projectId;

    @ApiModelProperty(value = "员工姓名")
    public String employeeName;

    @ApiModelProperty(value = "角色")
    public List<String> roles;
}
