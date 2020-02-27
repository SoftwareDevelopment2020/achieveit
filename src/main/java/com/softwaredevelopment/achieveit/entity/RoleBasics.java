package com.softwaredevelopment.achieveit.entity;

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
@ApiModel(value = "RoleBasics对象", description = "")
public class RoleBasics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "role_name")
    private String name;

    @ApiModelProperty(value = "role_detail  explaination")
    private String detail;


}
