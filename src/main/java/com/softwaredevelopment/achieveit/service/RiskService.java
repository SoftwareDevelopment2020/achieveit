package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.softwaredevelopment.achieveit.PO.entity.Risk;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
