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
 * @since 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Feature对象", description = "")
public class Feature implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty("id (弃用）")
//    private Integer id = 1;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "第一层需求ID")
    private Integer firstTierId;

    @ApiModelProperty(value = "第二层需求ID")
    private Integer secondTierId;

    @ApiModelProperty(value = "功能名称")
    private String featureName;

    @ApiModelProperty(value = "功能细节")
    private String featureDetail;


}
