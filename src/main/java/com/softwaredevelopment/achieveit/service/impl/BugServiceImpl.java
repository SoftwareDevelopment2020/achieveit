package com.softwaredevelopment.achieveit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softwaredevelopment.achieveit.entity.Bug;
import com.softwaredevelopment.achieveit.mapper.BugMapper;
import com.softwaredevelopment.achieveit.service.IBugService;
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
public class BugServiceImpl extends ServiceImpl<BugMapper, Bug> implements IBugService {

}
