package com.softwaredevelopment.achieveit.entity;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "PersonPermission对象", description = "")
public class PersonPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer projectEmployeeId;

    private Integer permissionId;


}
