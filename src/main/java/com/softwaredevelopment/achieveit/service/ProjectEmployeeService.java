package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
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
    public IPage<ProjectEmployeeVO> getProjectEmployeeVO(PageSearchRequest<ProjectEmployeeRequest> request) {
        PageInfo<Integer> pageInfo = getPageInfo(request, () -> projectEmployeeVOMapper.selectProjectEmployeeIds(
                request.getSearchCondition().getProjectId(),
                request.getSearchCondition().getEmployeeId(),
                request.getSearchCondition().getEmployeeName(),
                request.getSearchCondition().getRoles())
        );

        return getIPage(pageInfo, () -> projectEmployeeVOMapper.selectProjectEmployeeVO(pageInfo.getList()));
    }

    public IPage<EmployeeBasics> getEmployeeBasics(PageSearchRequest<EmployeeBasics> request) {
        return iEmployeeBasicsService.page(
                new Page<>(request.getCurrent(), request.getSize()),
                new QueryWrapper<>(request.getSearchCondition()));
    }
}
