package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.Bug;
import org.springframework.stereotype.Service;

/**
 * @author RainkQ
 * @date 2020/3/31 11:04
 */
@Service
public class BugService extends BaseService {

    /**
     * 按projectId获取bugs
     *
     * @param projectId
     * @param current
     * @param size
     * @return
     */
    public Page<Bug> getBugsByProjectId(String projectId, Integer current, Integer size) {
        Integer id = projectIdToId(projectId);
        return iBugService.page(new Page<>(current, size), new QueryWrapper<Bug>().lambda().eq(Bug::getProjectId, id));

    }

    public Boolean saveBugByProjectId(String projectId, Bug bug) {
        Integer id = projectIdToId(projectId);
        bug.setProjectId(id);
        return iBugService.save(bug);
    }

    public boolean updateBugByProjectId(String projectId, Bug bug) {
        Integer id = projectIdToId(projectId);
        bug.setProjectId(id);
        return iBugService.updateById(bug);
    }
}
