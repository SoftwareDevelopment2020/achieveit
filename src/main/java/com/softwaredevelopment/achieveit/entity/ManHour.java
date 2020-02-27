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
@ApiModel(value = "ManHour对象", description = "")
public class ManHour implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    private Integer employeeId;

    private Integer featureId;

    private String featureName;

    @ApiModelProperty(value = "活动名称")
    private Integer activityId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ApiModelProperty(value = "审核状态")
    private Integer auditingStatus;


}
