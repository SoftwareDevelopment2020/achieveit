package com.softwaredevelopment.achieveit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.PO.entity.PermissionBasics;
import com.softwaredevelopment.achieveit.PO.entity.RoleBasics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/30 10:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ProjectEmployee逻辑对象")
public class ProjectEmployeeVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "员工ID")
    private Integer employeeId;

    @ApiModelProperty(value = "加入时间")
    private LocalDate joinTime;

    @ApiModelProperty(value = "退出时间")
    private LocalDate exitTime;

    @ApiModelProperty(value = "上级id")
    private Integer superiorId;

    @ApiModelProperty(value = "角色")
    private List<RoleBasics> roles;

    @ApiModelProperty(value = "权限")
    private List<PermissionBasics> permissions;

    @ApiModelProperty(value = "基本信息")
    private EmployeeBasics employeeBasics;

    @ApiModelProperty(value = "上级基本信息")
    private EmployeeBasics superiorBasics;

}

