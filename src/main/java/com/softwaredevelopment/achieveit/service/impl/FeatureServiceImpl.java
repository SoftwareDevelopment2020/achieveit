package com.softwaredevelopment.achieveit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softwaredevelopment.achieveit.entity.Feature;
import com.softwaredevelopment.achieveit.mapper.FeatureMapper;
import com.softwaredevelopment.achieveit.service.IFeatureService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RainkQ
 * @since 2020-02-27
 */
@Service
public class FeatureServiceImpl extends ServiceImpl<FeatureMapper, Feature> implements IFeatureService {

}
