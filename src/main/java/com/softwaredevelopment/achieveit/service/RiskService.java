package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.PO.entity.GlobalRisk;
import com.softwaredevelopment.achieveit.PO.entity.Risk;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.BugStatus;
import com.softwaredevelopment.achieveit.entity.MailBean;
import com.softwaredevelopment.achieveit.entity.RiskVO;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.utils.StringHelper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.softwaredevelopment.achieveit.entity.BugStatus.statusToString;

/**
 * @author RainkQ
 * @date 2020/3/30 15:06
 */
@Service
@CacheConfig(cacheNames = "risk")
public class RiskService extends BaseService {

    /**
     * 按照projectId获取risks
     *
     * @param projectId
     * @return
     */
    public List<RiskVO> getRisksByProjectId(String projectId) throws BussinessException {
        Integer id = projectIdToId(projectId);
        List<Risk> list = iRiskService.list(new QueryWrapper<Risk>().lambda().eq(Risk::getProjectId, id));
        List<RiskVO> voList = new ArrayList<>();
        for (Risk r :
                list) {
            voList.add(riskToVO(r));
        }
        return voList;
    }

    public boolean saveRiskByProjectId(Risk risk, String projectId) {
        Integer id = projectIdToId(projectId);
        risk.setProjectId(id);
        return iRiskService.save(risk);
    }

    /**
     * 发送邮件给风险相关的人
     */
    public void sendRiskMail() {
        List<Risk> allRisks = iRiskService.list();
        List<Risk> risksNotClosed = allRisks.stream()
                .filter((x) -> !x.getStatus().equals(BugStatus.CLOSED.getStatus()))
                .distinct()
                .collect(Collectors.toList());

        // 相关人
        List<String> relatedStringList = risksNotClosed.stream()
                .map(Risk::getRelated)
                .collect(Collectors.toList());

        Set<String> relatedStaff = new HashSet<>();
        for (String s : relatedStringList) {
            List<String> relateId = Arrays.asList(s.split(","));
            for (int i = 1; i < relateId.size(); i++) {
                relatedStaff.add(relateId.get(i));
            }
        }
        // 把负责人也加入
        relatedStaff.addAll(
                risksNotClosed.stream()
                        .map(Risk::getResponsible)
                        .collect(Collectors.toList()));

        // 取id对应的人的Email
        List<String> rsMailList = iEmployeeBasicsService.list(
                new QueryWrapper<EmployeeBasics>().lambda().in(EmployeeBasics::getId, relatedStaff))
                .stream().map(EmployeeBasics::getEmailAddress).collect(Collectors.toList());
        for (String rsMail :
                rsMailList) {
            mailUtil.sendSimpleMail(new MailBean(rsMail, "风险预警", "您有未处理的风险，请及时处理"));
        }
    }


    public RiskVO riskToVO(Risk risk) throws BussinessException {
        RiskVO vo = new RiskVO();

        vo.setId(risk.getId());
        vo.setProjectId(risk.getProjectId());
        vo.setType(risk.getType());
        vo.setDescription(risk.getDescription());
        String[] level = new String[]{
                "高", "中", "低"
        };
        try {
            vo.setLevel(level[risk.getLevel()]);
        } catch (Exception e) {
            throw new BussinessException("风险级别有问题", e.getCause());
        }

        String[] affect = new String[]{
                "严重影响", "较大影响", "中等影响", "较小影响", "可忽略影响"
        };
        try {
            vo.setAffect(affect[risk.getAffect()]);
        } catch (Exception e) {
            throw new BussinessException("影响范围有问题", e.getCause());
        }

        vo.setReact(risk.getReact());
        vo.setStrategy(risk.getStrategy());
        try {
            vo.setStatus(statusToString(risk.getStatus()));
        } catch (Exception e) {
            throw new BussinessException("状态有问题", e.getCause());
        }

        try {
            Integer employeeId = Integer.valueOf(risk.getResponsible());
            vo.setResponsible(iEmployeeBasicsService.getById(employeeId));
            vo.setTrackFreq(risk.getTrackFreq());
        } catch (Exception e) {
            throw new BussinessException("负责人有问题", e.getCause());
        }

        try {
            List<EmployeeBasics> related = iEmployeeBasicsService.listByIds(StringHelper.stringsDivideByCommaToList(risk.getRelated()));
            vo.setRelated(related);
        } catch (Exception e) {
            throw new BussinessException("相关人有问题", e.getCause());
        }
        return vo;
    }

    public IPage<RiskVO> getRisksByPage(String projectId, PageSearchRequest<Map<String, String>> pageSearchRequest) throws BussinessException {
        // 构建搜索的page
        Page<Risk> page = new Page<>(pageSearchRequest.getCurrent(), pageSearchRequest.getSize());
        Map<String, String> searchCondition = pageSearchRequest.getSearchCondition();
        // 先按照名字查到这些EmployeeBasics
        List<EmployeeBasics> listByNames = iEmployeeBasicsService.list(new QueryWrapper<EmployeeBasics>()
                .lambda().eq(EmployeeBasics::getName, searchCondition.get("name")));

        List<Integer> responsibleList = listByNames.stream().map(EmployeeBasics::getId).collect(Collectors.toList());

        Page<Risk> riskPage = iRiskService.page(page,
                new QueryWrapper<Risk>().lambda()
                        .like(searchCondition.get("type") != null, Risk::getType, searchCondition.get("type"))
                        .eq(projectIdToId(projectId) != null, Risk::getProjectId, projectIdToId(projectId))
                        .like(searchCondition.get("id") != null, Risk::getId, searchCondition.get("id"))
                        .eq(getIntOrNull(searchCondition.get("status")) != null, Risk::getStatus, searchCondition.get("status"))
                        // 按名字查到的employeeBasics的id们
                        .in(responsibleList.size() > 0, Risk::getResponsible, responsibleList));

        // 从riskPage转riskVOPage
        Page<RiskVO> riskVOPage = new Page<>(riskPage.getCurrent(), riskPage.getSize(), riskPage.getTotal(), riskPage.isSearchCount());
        List<RiskVO> records = new ArrayList<>();
        for (Risk risk : riskPage.getRecords()) {
            records.add(riskToVO(risk));
        }
        riskVOPage.setRecords(records);
        return riskVOPage;
    }

    @Cacheable(key = "#root.methodName")
    public List<GlobalRisk> getAllGlobalRisks() {
        return iGlobalRiskService.list();
    }

    /**
     * 更新risk
     *
     * @param risk
     * @param projectId
     * @return
     */
    public boolean updateRisk(Risk risk, String projectId) {
        Integer id = projectIdToId(projectId);
        risk.setProjectId(id);
        return iRiskService.updateById(risk);
    }
}
