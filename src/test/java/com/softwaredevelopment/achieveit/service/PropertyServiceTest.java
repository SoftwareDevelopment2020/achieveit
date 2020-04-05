package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.PO.entity.Property;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RainkQ
 * @date 2020/4/4 17:10
 */
@Transactional
@SpringBootTest
class PropertyServiceTest {
    @Autowired
    PropertyService service;

    @Test
    void searchProperties() {
        PageSearchRequest<Property> request = new PageSearchRequest<>();
        Property searchCondition = new Property();
        searchCondition.setProjectId(1);
        request.setSearchCondition(searchCondition);
        service.searchProperties(request);
    }
}
