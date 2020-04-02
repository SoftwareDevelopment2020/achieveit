package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.Activity;
import com.softwaredevelopment.achieveit.PO.entity.ManHour;
import com.softwaredevelopment.achieveit.PO.entity.ProjectEmployee;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean saveManHour(ManHour manHour) {
        return iManHourService.save(manHour);
    }

    /**
     * 获取所有Activity
     *
     * @return
     */
    public List<Activity> getAllActivities() {
        return iActivityService.list();
    }

    /**
     * 审批工时
     *
     * @param manHourId
     * @return
     */
    public boolean auditManHour(Integer manHourId) {
        ManHour mh = iManHourService.getById(manHourId);
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
