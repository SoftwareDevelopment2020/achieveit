package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.PO.entity.Property;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author RainkQ
 * @date 2020/4/4 17:10
 */
@Transactional
@SpringBootTest
class PropertyServiceTest {
    @Autowired
    PropertyService service;
    @Autowired
    AuthService authService;

    Property mockProperty() {
        Property property = new Property();
        property.setId(100);
        property.setProjectId(1);
        property.setIsReturned(false);
        property.setManagerBasics(service.getIEmployeeBasicsService().getById(1));
        property.setManagerId(1);
        property.setType("电脑");
        property.setDeviceCondition(true);
        property.setReturnDate(LocalDate.now());
        property.setUsageTimeLimit(LocalDate.now());
        return property;
    }

    @Test
    void searchProperties() {
        PageSearchRequest<Property> request = new PageSearchRequest<>();
        Property searchCondition = new Property();
        searchCondition.setProjectId(1);
        request.setSearchCondition(searchCondition);
        service.searchProperties(request);
    }

    @Test
    void addProperty() throws BussinessException {
        authService.login("zhangsan", "123456");
        service.addProperty(mockProperty());
    }

    @Test
    void updateProperty() throws BussinessException {
        authService.login("zhangsan", "123456");
        addProperty();
        service.updateProperty(mockProperty());
    }


}
