package com.softwaredevelopment.achieveit.entity;

import lombok.Data;

/**
 * PermissionByProject
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-28 23:42
 */
@Data
public class PermissionByProject {
    private Integer projectId;
    private String permissions;
}
