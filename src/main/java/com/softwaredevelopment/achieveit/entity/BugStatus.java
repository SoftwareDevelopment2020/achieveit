package com.softwaredevelopment.achieveit.entity;

import lombok.Getter;

/**
 * @author RainkQ
 * @date 2020/3/31 18:35
 */
@Getter
public enum BugStatus {
    NEW(0),
    OPENED(1),
    PROCESSED(2),
    SOLVED(3),
    CLOSED(4),
    ;

    static String[] statusName = new String[]{
            "NEW", "OPENED", "PROCESSED", "SOLVED", "CLOSED"
    };
    private String name;
    private Integer status;

    BugStatus(Integer i) {
        this.status = i;
        this.name = statusToString(i);
    }

    public static String statusToString(Integer i) {
        return statusName[i + 1];
    }
}
