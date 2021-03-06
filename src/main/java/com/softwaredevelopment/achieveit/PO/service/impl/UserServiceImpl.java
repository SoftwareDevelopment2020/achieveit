package com.softwaredevelopment.achieveit.PO.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softwaredevelopment.achieveit.PO.entity.User;
import com.softwaredevelopment.achieveit.PO.mapper.UserMapper;
import com.softwaredevelopment.achieveit.PO.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
