package com.softwaredevelopment.achieveit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softwaredevelopment.achieveit.entity.Property;
import com.softwaredevelopment.achieveit.mapper.PropertyMapper;
import com.softwaredevelopment.achieveit.service.IPropertyService;
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
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements IPropertyService {

}
