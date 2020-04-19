package com.softwaredevelopment.achieveit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.softwaredevelopment.achieveit.PO.entity.Activity;
import com.softwaredevelopment.achieveit.PO.entity.EmployeeBasics;
import com.softwaredevelopment.achieveit.PO.entity.ManHour;
import com.softwaredevelopment.achieveit.PO.entity.ProjectEmployee;
import com.softwaredevelopment.achieveit.controller.BussinessException;
import com.softwaredevelopment.achieveit.entity.ActivityVO;
import com.softwaredevelopment.achieveit.entity.request.PageSearchRequest;
import com.softwaredevelopment.achieveit.utils.ObjectHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author RainkQ
 * @date 2020/4/2 21:20
 */
@Service
public class ManHourService extends BaseService {

    /**
     * 获取工时信息
     */
    public IPage<ManHour> getManHourSearchPage(PageSearchRequest<ManHour> request) throws BussinessException {
        Page<ManHour> page = new Page<>(request.getCurrent(), request.getSize());

        // 将空字符串设置为NULL
        ManHour searchCondition = request.getSearchCondition();
        if (searchCondition == null) {
            searchCondition = new ManHour();
        } else {
            ObjectHelper.setObjectEmptyToNull(searchCondition);
        }

        // 查询条件
        QueryWrapper<ManHour> queryWrapper = new QueryWrapper<>();
        // employeeId
        switch (searchCondition.getType()) {
            case 1: // 我的
                searchCondition.setEmployeeId(currentUserDetail().getEmployeeId());
                break;
            case 2: // 下属的
                if (searchCondition.getEmployeeId() == null) {
                    // 查询所有下属ID
                    List<ProjectEmployee> employeeBasics = iProjectEmployeeService.list(
                            new QueryWrapper<ProjectEmployee>()
                                    .eq("superior_id", currentUserDetail().getEmployeeId())
                                    .isNull("exit_time")
                    );
                    if (CollectionUtils.isEmpty(employeeBasics)) {
                        // 没有下属，直接返回
                        return page;
                    }
                    queryWrapper.in("employee_id", employeeBasics.stream().map(ProjectEmployee::getEmployeeId).collect(Collectors.toList()));
                }
                // 无法看到下属已撤回的信息
                queryWrapper.ne("auditing_status", 3);
                break;
            case 3: // 所有的
                // 无法看到已撤回的信息
                queryWrapper.ne("auditing_status", 3);
                break;
        }

        // 日期
        if (searchCondition.getStartTime() != null) {
            queryWrapper.eq("DATE_FORMAT(start_time, '%Y%m%d')", searchCondition.getStartTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            searchCondition.setStartTime(null);
        }
        // 其他条件
        queryWrapper.setEntity(searchCondition);
        // 排序
        queryWrapper.orderByDesc("start_time").orderByAsc("auditing_status");

        // 查询
        IPage<ManHour> result = iManHourService.page(new Page<>(request.getCurrent(), request.getSize()),queryWrapper);

        // 设置employeeBasics
        List<ManHour> manHours = result.getRecords();
        if (!CollectionUtils.isEmpty(manHours)) {
            List<EmployeeBasics> employeeBasics = iEmployeeBasicsService.listByIds(manHours.stream().map(ManHour::getEmployeeId).collect(Collectors.toList()));
            // to map
            Map<Integer, EmployeeBasics> employeeBasicsMap = employeeBasics.stream().collect(Collectors.toMap(EmployeeBasics::getId, Function.identity()));
            // 设置employeeBasics
            for (ManHour manHour : manHours) {
                manHour.setEmployeeBasics(employeeBasicsMap.get(manHour.getEmployeeId()));
            }
            // 设置record
            result.setRecords(manHours);
        }

        return result;
    }

    /**
     * 添加工时信息
     */
    public boolean addManHour(ManHour manHour) throws BussinessException {
        if (!projectOngoing(manHour.getProjectId())) {
            throw new BussinessException("项目未在进行中，无法修改", null, "项目未在进行中，无法修改");
        }
        boolean hasPermission = false;
        for (String s :
                currentUserDetail().getPermissionsMap().get(manHour.getProjectId())) {
            if (s.equals("manhour")) {
                hasPermission = true;
                break;
            }
        }
        if (!hasPermission) {
            throw new BussinessException("没有工时权限", null, "没有工时权限");
        }

        // 设置员工key
        manHour.setEmployeeId(currentUserDetail().getEmployeeId());
        // 设置审核状态
        manHour.setAuditingStatus(0);

        // region 检查工时冲突
        // 获取当天已提交的工时
        List<ManHour> manHours = iManHourService.list(
                new QueryWrapper<ManHour>()
                        .eq("project_id", manHour.getProjectId())
                        .eq("employee_id", manHour.getEmployeeId())
                        .eq("DATE_FORMAT(start_time, '%Y%m%d')", manHour.getStartTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                        .in("auditing_status", 0, 1)
                        .orderByAsc("start_time")
        );
        if (!checkManHourTime(manHours, manHour)) {
            throw new BussinessException("添加工时失败", new Exception(), "当前时段与其他工时冲突");
        }
        // endregion

        return iManHourService.save(manHour);
    }

    /**
     * 修改工时信息
     */
    public boolean updateManHour(ManHour manHour) throws BussinessException {
        // 如果修改基本信息，需要判断时间冲突
        if (manHour.getAuditingStatus() == null) {
            // 原始信息
            ManHour original = iManHourService.getById(manHour.getId());
            if (!manHour.getStartTime().equals(original.getStartTime()) || !manHour.getEndTime().equals(original.getEndTime())) {
                // 时间修改，重新判定
                List<ManHour> existManHours = iManHourService.list(
                        new QueryWrapper<ManHour>()
                                .ne("id", original.getId())
                                .eq("project_id", original.getProjectId())
                                .eq("employee_id", original.getEmployeeId())
                                .eq("DATE_FORMAT(start_time, '%Y%m%d')", manHour.getStartTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                                .in("auditing_status", 0, 1)
                                .orderByAsc("start_time")
                );
                if (!checkManHourTime(existManHours, manHour)) {
                    throw new BussinessException("修改工时失败", new Exception(), "当前时段与其他工时冲突");
                }
            }
        }

        return iManHourService.updateById(manHour);
    }

    /**
     * 判断区间
     */
    private boolean checkManHourTime(List<ManHour> existManHour, ManHour checkManHour) {
        if (checkManHour.getStartTime().isEqual(checkManHour.getEndTime())) {
            return false;
        }

        if (CollectionUtils.isEmpty(existManHour)) {
            return true;
        }

        LocalDateTime left = checkManHour.getStartTime().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime right;
        for (ManHour manHour : existManHour) {
            // 右区间是下一项的start_time
            right = manHour.getStartTime();
            // left <= start_time < end_time <= right满足条件
            if (!checkManHour.getStartTime().isBefore(left) && !checkManHour.getEndTime().isAfter(right)) {
                return true;
            }
            // 左区间是上一项的end_time
            left = manHour.getEndTime();
        }
        // 最后一个区间，left<=start_time即可
        return !checkManHour.getStartTime().isBefore(left);
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
}
