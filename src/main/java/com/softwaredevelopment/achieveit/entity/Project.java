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

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "预定时间 项目预期开始时间")
    private LocalDate scheduledDate;

    @ApiModelProperty(value = "项目经理姓名")
    private String projectManagerName;

}
