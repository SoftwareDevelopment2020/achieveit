package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.Bug;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.BugStatus;
import com.softwaredevelopment.achieveit.entity.BugVO;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    public Page<BugVO> getBugsByProjectId(String projectId, Integer current, Integer size) throws BussinessException {
        Integer id = projectIdToId(projectId);
        Page<Bug> page = iBugService.page(new Page<>(current, size), new QueryWrapper<Bug>().lambda().eq(Bug::getProjectId, id));
        Page<BugVO> voPage = new Page<>();
        List<BugVO> voRecords = new ArrayList<>();
        for (Bug b :
                page.getRecords()) {
            voRecords.add(bugToVO(b));
        }
        voPage.setCurrent(page.getCurrent());
        voPage.setSize(page.getSize());
        voPage.setTotal(page.getTotal());
        voPage.setRecords(voRecords);
        voPage.setOrders(page.getOrders());
        voPage.setPages(page.getPages());
        return voPage;

    }

    public Boolean saveBugByProjectId(String projectId, Bug bug) throws BussinessException {
        Integer id = projectIdToId(projectId);
        bug.setProjectId(id);
        // bug提出人就是自己
        bug.setBugIntroducerId(currentUserDetail().getEmployeeId());
        // bug状态是new
        bug.setStatus(BugStatus.NEW.getStatus());
        // 结束时间未确定
        bug.setEndTime(null);
        bug.setStartTime(new Date(System.currentTimeMillis()));
        return iBugService.save(bug);
    }

    public boolean updateBugByProjectId(String projectId, Bug bug) {
        Integer id = projectIdToId(projectId);
        bug.setProjectId(id);
        return iBugService.updateById(bug);
    }


    public BugVO bugToVO(Bug bug) throws BussinessException {
        BugVO vo = new BugVO();
        vo.setId(bug.getId());
        vo.setProjectId(bug.getProjectId());
        vo.setBugTitle(bug.getBugTitle());
        try {
            vo.setBugIntroducer(
                    iEmployeeBasicsService.getById(
                            bug.getBugIntroducerId()
                    )
            );
        } catch (Exception e) {
            throw new BussinessException("bug提出人有问题", e.getCause());
        }
        try {
            vo.setBugResponsible(
                    iEmployeeBasicsService.getById(
                            bug.getBugResponsibleId()
                    )
            );
        } catch (Exception e) {
            throw new BussinessException("bug负责人有问题", e.getCause());
        }
        vo.setStartTime(bug.getStartTime());
        vo.setEndTime(bug.getEndTime());
        vo.setBugDescription(bug.getBugDescription());
        vo.setPriority(bug.getPriority());
        try {
            vo.setStatus(BugStatus.statusToString(bug.getStatus()));
        } catch (Exception e) {
            throw new BussinessException("状态有问题", e.getCause());
        }
        return vo;
    }
}
