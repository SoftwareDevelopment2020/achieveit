package com.softwaredevelopment.achieveit.PO.entity;

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
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "RoleBasics对象", description = "")
public class RoleBasics implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "role_name")
    private String name;

    @ApiModelProperty(value = "role_detail  explanation")
    private String detail;


}
