package com.softwaredevelopment.achieveit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softwaredevelopment.achieveit.entity.ProjectEmployee;
import com.softwaredevelopment.achieveit.mapper.ProjectEmployeeMapper;
import com.softwaredevelopment.achieveit.service.IProjectEmployeeService;
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
public class ProjectEmployeeServiceImpl extends ServiceImpl<ProjectEmployeeMapper, ProjectEmployee> implements IProjectEmployeeService {

}
