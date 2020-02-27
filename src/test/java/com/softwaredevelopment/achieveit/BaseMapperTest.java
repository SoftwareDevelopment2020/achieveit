package com.softwaredevelopment.achieveit;

import com.softwaredevelopment.achieveit.entity.Bug;
import com.softwaredevelopment.achieveit.mapper.BugMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * BaseMapperTest
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-27 14:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseMapperTest {

    @Autowired
    private BugMapper bugMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Bug> bugList = bugMapper.selectList(null);
        Assert.assertEquals(1, bugList.size());
        bugList.forEach(System.out::println);
    }

}
