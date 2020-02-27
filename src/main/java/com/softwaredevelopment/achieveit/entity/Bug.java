package com.softwaredevelopment.achieveit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author RainkQ
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Bug对象", description = "")
public class Bug implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    private String bugTitle;

    @ApiModelProperty(value = "bug提出人的ID")
    private Integer bugIntroducerId;

    @ApiModelProperty(value = "bug负责人的ID")
    private Integer bugResponsibleId;

    @ApiModelProperty(value = "bug开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "bug结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "bug描述")
    private String bugDescription;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "缺陷状态：New Opened Processed Solved Closed")
    private Integer status;


}
