package com.softwaredevelopment.achieveit.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.PO.entity.Property;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.utils.ObjectHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author RainkQ
 * @date 2020/4/2 11:47
 */

@Service
public class PropertyService extends BaseService {

    /**
     * 查询设备
     */
    public IPage<Property> searchProperties(PageSearchRequest<Property> request) {
        Property searchCondition = request.getSearchCondition();
        if (searchCondition == null) {
            searchCondition = new Property();
        } else {
            // 将实体中的空字符串设置为NULL
            ObjectHelper.setObjectEmptyToNull(searchCondition);
        }

        // region 查询条件
        QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
        // 模糊查询type
        if (!StringUtils.isEmpty(searchCondition.getType())) {
            queryWrapper.like("type", searchCondition.getType());
            searchCondition.setType(null);
        }
        // 其他条件
        queryWrapper.setEntity(searchCondition);
        // endregion

        // 分页
        Page<Property> page = new Page<>(request.getCurrent(), request.getSize());
        page.addOrder(OrderItem.asc("return_date"), OrderItem.asc("type"));

        // 获取设备信息
        IPage<Property> result = iPropertyService.page(page, queryWrapper);
        List<Property> properties = result.getRecords();

        // 获取设备管理员信息
        if (!CollectionUtils.isEmpty(properties)) {
            List<EmployeeBasics> employeeBasics = iEmployeeBasicsService.list(
                    new QueryWrapper<EmployeeBasics>()
                            .in("id", properties.stream().map(Property::getManagerId).collect(Collectors.toList()))
            );
            // 转换为map
            Map<Integer, EmployeeBasics> employeeBasicsMap = employeeBasics.stream().collect(Collectors.toMap(EmployeeBasics::getId, Function.identity()));
            // 设置设备管理员信息
            properties.forEach(property ->
                    property.setManagerBasics(employeeBasicsMap.get(property.getManagerId())));
        }

        return result;
    }

    /**
     * 添加设备
     */
    public boolean addProperty(Property property) throws BussinessException {
        // 设置资产管理者ID
        property.setManagerId(currentUserDetail().getEmployeeId());
        // 设置是否归还
        property.setIsReturned(false);

        return iPropertyService.save(property);
    }

    /**
     * 更新设备信息
     */
    public boolean updateProperty(Property property) {
        return iPropertyService.updateById(property);
    }
}
