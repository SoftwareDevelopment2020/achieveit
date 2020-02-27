package com.softwaredevelopment.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

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
@ApiModel(value = "ProjectEmployee对象", description = "")
public class ProjectEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer projectId;

    private Integer employeeId;

    private LocalDate joinTime;

    private LocalDate exitTime;

    @ApiModelProperty(value = "上级id")
    private Integer superiorId;


}
