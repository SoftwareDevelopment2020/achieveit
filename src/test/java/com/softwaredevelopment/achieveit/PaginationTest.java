package com.softwaredevelopment.achieveit;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.ProjectBasics;
import com.softwaredevelopment.achieveit.PO.mapper.ProjectBasicsMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author RainkQ
 * @date 2020/3/16 17:15
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaginationTest {
    @Resource
    ProjectBasicsMapper mapper;

    @Test
    public void tests1() {
        log.error("---------------baseMapper 自带分页--------------------");
        Page<ProjectBasics> page = new Page<>(1, 1);
        page.addOrder(OrderItem.asc("project_id"));
        Page<ProjectBasics> projectPage = mapper.selectPage(page, null);
        assertThat(page).isSameAs(projectPage);
        log.error("总条数 -------------> {}", projectPage.getTotal());
        log.error("当前页数 -------------> {}", projectPage.getCurrent());
        log.error("当前每页显示数 -------------> {}", projectPage.getSize());
        List<ProjectBasics> records = projectPage.getRecords();
        assertThat(records).isNotEmpty();
    }
}
