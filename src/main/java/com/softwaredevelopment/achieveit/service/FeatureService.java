package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.Feature;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/25 18:22
 */
@Service
public class FeatureService extends BaseService {

    public List<Feature> getFeaturesByProjectId(Integer projectId) {
        return iFeatureService.list(new QueryWrapper<Feature>().lambda().eq(Feature::getProjectId, projectId));
    }

    public boolean saveFeature(Feature feature) {
        return iFeatureService.save(feature);
    }

    public boolean saveFeatureBatch(List<Feature> features) {
        return iFeatureService.saveBatch(features);
    }
}
