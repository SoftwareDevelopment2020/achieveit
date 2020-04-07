package com.softwaredevelopment.achieveit.entity;

import lombok.Data;

import java.util.List;

@Data
public class ActivityVO {

    private Integer id;

    private String name;

    private List<ActivityVO> children;

}
