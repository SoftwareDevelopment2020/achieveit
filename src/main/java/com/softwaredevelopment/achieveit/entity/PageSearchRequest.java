package com.softwaredevelopment.achieveit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RainkQ
 * @date 2020/3/18 16:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSearchRequest<T> {
    // 默认页数
    private int current = 1;
    // 默认一页数量
    private int size = 10;
    private T searchCondition;
}
