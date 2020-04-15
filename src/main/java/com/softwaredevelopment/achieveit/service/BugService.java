package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.Bug;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.BugStatus;
import com.softwaredevelopment.achieveit.entity.BugVO;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RainkQ
 * @date 2020/3/31 11:04
 */
@Service
public class BugService extends BaseService {

    /**
     * 复杂条件的分页查询
     *
     * @param pageSearchRequest
     * @return
     */
    public Page<BugVO> getBugsByPage(String projectId, PageSearchRequest<Map<String, String>> pageSearchRequest) throws BussinessException {
        Integer project_id = projectIdToId(projectId);

        Page<Bug> page = new Page<>(pageSearchRequest.getCurrent(), pageSearchRequest.getSize());
        Map<String, String> searchCondition = pageSearchRequest.getSearchCondition();
        QueryWrapper<Bug> qw = new QueryWrapper<>();
        if (searchCondition != null) {
            // id
            String idFromMap = searchCondition.getOrDefault("id", null);
            Integer id = getIntOrNull(idFromMap);
            // 标题
            String title = searchCondition.getOrDefault("bugTitle", null);
            // 状态
            Integer status = getIntOrNull(searchCondition.getOrDefault("status", null));
            // 构造查询queryWrapper
            qw.lambda()
                    .eq(project_id != null, Bug::getProjectId, project_id)
                    .eq(id != null, Bug::getId, id)
                    .eq(status != null, Bug::getStatus, status)
                    .like(title != null, Bug::getBugTitle, title);

            String bugIntroducer = searchCondition.get("bugIntroducer");
            if (bugIntroducer != null) {
                List<Integer> bugIntroducerIds = iEmployeeBasicsService.list(new QueryWrapper<EmployeeBasics>()
                        .lambda().eq(EmployeeBasics::getName, bugIntroducer)).stream().map(EmployeeBasics::getId).collect(Collectors.toList());
                qw.lambda()
                        .in(bugIntroducerIds.size() > 0, Bug::getBugIntroducerId, bugIntroducerIds);
            }
        }

        Page<Bug> bugPage = iBugService.page(page, qw);
        // 从BugPage 转到 BugVOPage
        Page<BugVO> bugVOPage = new Page<>(bugPage.getCurrent(), bugPage.getSize(), bugPage.getTotal(), bugPage.isSearchCount());
        List<BugVO> bugVOS = new ArrayList<>();
        for (Bug b :
                bugPage.getRecords()) {
            bugVOS.add(bugToVO(b));
        }
        bugVOPage.setRecords(bugVOS);
        return bugVOPage;
    }

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

        if (!projectOngoing(projectId)) {
            throw new BussinessException("项目未在进行中，无法修改", null, "项目未在进行中，无法修改");
        }

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

    public boolean updateBugByProjectId(Bug bug) throws BussinessException {
        Bug old = new Bug();
        try {
            old = iBugService.getById(bug.getId());
        } catch (Exception e) {
            throw new BussinessException("没有这个bug", e.getCause());
        }
        bug.setEndTime(null);
        bug.setStartTime(old.getStartTime());
        bug.setProjectId(old.getProjectId());
        bug.setBugIntroducerId(old.getBugIntroducerId());
        if (bug.getStatus() == BugStatus.CLOSED.getStatus()) {
            bug.setEndTime(new Date(System.currentTimeMillis()));
        }
        return iBugService.updateById(bug);
    }


    public BugVO bugToVO(Bug bug) throws BussinessException {
        BugVO vo = new BugVO();
        vo.setId(bug.getId());
        vo.setProjectId(bug.getProjectId());
        vo.setBugTitle(bug.getBugTitle());
        vo.setBugIntroducer(
                iEmployeeBasicsService.getById(
                        bug.getBugIntroducerId()
                )
        );
        vo.setBugResponsible(
                iEmployeeBasicsService.getById(
                        bug.getBugResponsibleId()
                )
        );
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
