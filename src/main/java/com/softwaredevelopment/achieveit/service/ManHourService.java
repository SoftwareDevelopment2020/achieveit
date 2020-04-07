package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.Activity;
import com.softwaredevelopment.achieveit.PO.entity.ManHour;
import com.softwaredevelopment.achieveit.PO.entity.ProjectEmployee;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.ActivityVO;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RainkQ
 * @date 2020/4/2 21:20
 */
@Service
public class ManHourService extends BaseService {

    public IPage<ManHour> getManHourSearchPage(PageSearchRequest<ManHour> request) {
        return iManHourService.page(new Page<>(request.getCurrent(), request.getSize()),
                new QueryWrapper<>(request.getSearchCondition()));
    }

    /**
     * 添加工时信息
     */
    public boolean addManHour(ManHour manHour) throws BussinessException {
        // 设置员工key
        manHour.setEmployeeId(currentUserDetail().getEmployeeId());
        // 设置审核状态
        manHour.setAuditingStatus(0);

        // TODO 检查工时冲突

        return iManHourService.save(manHour);
    }

    /**
     * 获取所有Activity
     */
    public List<ActivityVO> getAllActivities() {
        List<Activity> activities = iActivityService.list();

        // 转换为TREE
        // root
        Map<Integer, ActivityVO> map = new HashMap<>();
        for (Activity activity : activities) {
            if (activity.getSecondId() == 0) {
                ActivityVO activityVO = new ActivityVO();
                activityVO.setId(activity.getId());
                activityVO.setName(activity.getName());
                activityVO.setChildren(new ArrayList<>());
                map.put(activity.getFirstId(), activityVO);
            }
        }
        // children
        for (Activity activity : activities) {
            if (activity.getSecondId() != 0) {
                ActivityVO activityVO = new ActivityVO();
                activityVO.setId(activity.getId());
                activityVO.setName(activity.getName());
                if (map.containsKey(activity.getFirstId())) {
                    map.get(activity.getFirstId()).getChildren().add(activityVO);
                }
            }
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 审批工时
     *
     * @param manHourId
     * @return
     */
    public boolean auditManHour(Integer manHourId) {
        ManHour mh = iManHourService.getById(manHourId);
        if (mh == null) {
            return false;
        }
        return auditManHour(mh);
    }

    public Boolean auditManHour(ManHour manHour) {
        try {
            Integer employeeId = manHour.getEmployeeId();
            Integer projectId = manHour.getProjectId();
            ProjectEmployee projectEmployee = iProjectEmployeeService.getOne(new QueryWrapper<ProjectEmployee>()
                    .lambda().eq(ProjectEmployee::getProjectId, projectId).eq(ProjectEmployee::getEmployeeId, employeeId));
            // 如果项目中上级是我的话
            if (currentUserDetail().getEmployeeId().equals(projectEmployee.getEmployeeId())) {
                // 设置为审批成功
                manHour.setAuditingStatus(1);
                // 保存
                iManHourService.updateById(manHour);
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
