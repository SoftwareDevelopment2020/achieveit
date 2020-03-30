package com.softwaredevelopment.achieveit.service;

import com.softwaredevelopment.achieveit.entity.ProjectEmployeeVO;
import com.softwaredevelopment.achieveit.mapper.ProjectEmployeeVOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/30 14:39
 */
@Service
public class ProjectEmployeeService extends BaseService {
    @Autowired
    ProjectEmployeeVOMapper projectEmployeeVOMapper;

    public List<ProjectEmployeeVO> getProjectEmployeeVOByProjectId(String projectId) {
        return projectEmployeeVOMapper.selectProjectEmployeesByProjectId(projectId);
    }
}
