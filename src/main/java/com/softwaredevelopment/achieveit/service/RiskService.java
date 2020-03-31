package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.PO.entity.Risk;
import com.softwaredevelopment.achieveit.entity.BugStatus;
import com.softwaredevelopment.achieveit.entity.MailBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author RainkQ
 * @date 2020/3/30 15:06
 */
@Service
public class RiskService extends BaseService {

    /**
     * 按照projectId获取risks
     *
     * @param projectId
     * @return
     */
    public List<Risk> getRisksByProjectId(String projectId) {
        Integer id = projectIdToId(projectId);
        return iRiskService.list(new QueryWrapper<Risk>().lambda().eq(Risk::getProjectId, id));
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


}
