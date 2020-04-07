package com.softwaredevelopment.achieveit.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 功能接口传回的树形结构
 *
 * @author RainkQ
 * @date 2020/3/26 09:34
 */
@Data
public class FeatureVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String label;

    private String detail;

    private List<FeatureVO> children;

}
