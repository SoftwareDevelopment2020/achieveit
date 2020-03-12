package com.softwaredevelopment.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author RainkQ
 * @date 2020/3/11 15:55
 */
@Data
@ApiModel("逻辑上的项目类")
public class Project {
    private Integer id;

    @ApiModelProperty(value = "project_id  由“四位年份-四位客户代码-研发类型1位（开发：D，维护：M，服务：S，其他：O）-顺序号2位”构成，且从外部系统导入，是一个选择项，不可更改。")
    private String projectId;

    @ApiModelProperty(value = "客户ID 从客户管理系统中拉取详细信息")
    private Integer clientId;

    @ApiModelProperty(value = "预定时间 项目预期开始时间")
    private LocalDate scheduledDate;

    @ApiModelProperty(value = "交付日期 项目预期结束时间")
    private LocalDate deliveryDate;

    @ApiModelProperty(value = "项目上级 负责项目立项审批")
    private String superior;

    @ApiModelProperty(value = "主要里程碑?????")
    private String majorMilestone;

    @ApiModelProperty(value = "主要技术 语言 平台 架构 工具等")
    private String mainTechnique;

    @ApiModelProperty(value = "业务领域")
    private String businessField;

    @ApiModelProperty(value = "主要业务功能")
    private String mainFunction;

    @ApiModelProperty(value = "git仓库地址")
    private String gitAddress;

    @ApiModelProperty(value = "项目状态id")
    private Integer statusId;

    @ApiModelProperty(value = "项目是否归档")
    private Boolean isArchived;

}
