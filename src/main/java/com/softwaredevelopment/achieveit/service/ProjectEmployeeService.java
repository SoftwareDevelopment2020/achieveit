package com.softwaredevelopment.achieveit.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.softwaredevelopment.achieveit.entity.ProjectEmployeeVO;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.entity.request.ProjectEmployeeRequest;
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

    /**
     * 根据条件分页获取人员信息
     */
    public PageInfo<ProjectEmployeeVO> getProjectEmployeeVO(PageSearchRequest<ProjectEmployeeRequest> request) {
        ProjectEmployeeRequest condition = request.getSearchCondition();
        PageHelper.startPage(request.getCurrent(), request.getSize());
        List<Integer> projectEmployeeIds = projectEmployeeVOMapper.selectProjectEmployeeIds(
                condition.getProjectId(),
                condition.getEmployeeName(),
                condition.getRoles());
        return projectEmployeeIds.isEmpty() ? new PageInfo<>() : new PageInfo<>(projectEmployeeVOMapper.selectProjectEmployeeVO(projectEmployeeIds));
    }
}
