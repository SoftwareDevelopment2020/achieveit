package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.Property;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/4/2 11:47
 */

@Service
public class PropertyService extends BaseService {
    public List<Property> getPropertiesByProjectId(String projectId) {
        return iPropertyService.list(new QueryWrapper<Property>().lambda()
                .eq(Property::getProjectId, projectId));
    }
}
