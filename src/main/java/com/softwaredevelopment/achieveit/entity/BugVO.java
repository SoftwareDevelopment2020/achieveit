package com.softwaredevelopment.achieveit.entity;

import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author RainkQ
 * @date 2020/4/4 14:09
 */
@Data
public class BugVO {

    private static final long serialVersionUID = 1L;
    private Integer id;
    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    private String bugTitle;

    @ApiModelProperty(value = "bug提出人的ID")
    private EmployeeBasics bugIntroducer;

    @ApiModelProperty(value = "bug负责人的ID")
    private EmployeeBasics bugResponsible;

    @ApiModelProperty(value = "bug开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "bug结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "bug描述")
    private String bugDescription;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "缺陷状态：New Opened Processed Solved Closed")
    private String status;
}
