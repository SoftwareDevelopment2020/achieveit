package com.softwaredevelopment.achieveit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@ApiModel(value = "Risk对象", description = "")
public class Risk implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "风险类型")
    private String type;

    @ApiModelProperty(value = "风险描述")
    private String description;

    @ApiModelProperty(value = "风险级别")
    private Integer level;

    @ApiModelProperty(value = "风险影响度")
    private Integer affect;

    @ApiModelProperty(value = "风险应对")
    private String react;

    @ApiModelProperty(value = "策略")
    private String strategy;

    @ApiModelProperty(value = "风险状态")
    private Integer status;

    @ApiModelProperty(value = "风险责任人")
    private String responsible;

    @ApiModelProperty(value = "风险跟踪频度")
    private String trackFreq;

    @ApiModelProperty(value = "风险相关者")
    private String related;


}
