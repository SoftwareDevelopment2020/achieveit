package com.softwaredevelopment.achieveit.PO.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softwaredevelopment.achieveit.PO.entity.Bug;
import com.softwaredevelopment.achieveit.PO.mapper.BugMapper;
import com.softwaredevelopment.achieveit.PO.service.IBugService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RainkQ
 * @since 2020-03-18
 */
@Service
public class BugServiceImpl extends ServiceImpl<BugMapper, Bug> implements IBugService {

}
