package com.softwaredevelopment.achieveit.entity;

import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/4/4 11:12
 */
@Data
@ApiModel("风险VO")
public class RiskVO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "风险类型")
    private String type;

    @ApiModelProperty(value = "风险描述")
    private String description;

    @ApiModelProperty(value = "风险级别")
    private String level;

    @ApiModelProperty(value = "风险影响度")
    private String affect;

    @ApiModelProperty(value = "风险应对")
    private String react;

    @ApiModelProperty(value = "策略")
    private String strategy;

    @ApiModelProperty(value = "风险状态")
    private String status;

    @ApiModelProperty(value = "风险责任人")
    private EmployeeBasics responsible;

    @ApiModelProperty(value = "风险跟踪频度 (几天一次提醒）")
    private Integer trackFreq;

    @ApiModelProperty(value = "风险相关者")
    private List<EmployeeBasics> related;

}
