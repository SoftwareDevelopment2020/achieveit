package com.softwaredevelopment.achieveit.PO.entity;

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
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "GlobalRisk对象", description = "")
public class GlobalRisk implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty(value = "风险类型")
    private String type;

    @ApiModelProperty(value = "风险描述")
    private String decription;

    @ApiModelProperty(value = "风险应对")
    private String react;

    @ApiModelProperty(value = "风险策略")
    private String strategy;


}
