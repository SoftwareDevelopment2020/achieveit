package com.softwaredevelopment.achieveit.mapper;

import com.softwaredevelopment.achieveit.entity.ProjectEmployeeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/30 10:56
 */
@Repository
public interface ProjectEmployeeVOMapper {
    List<ProjectEmployeeVO> selectProjectEmployeesByProjectId(String projectId);

    List<Integer> selectProjectEmployeeIds(@Param("projectId") Integer projectId,
                                           @Param("employeeName") String employeeName,
                                           @Param("roles") List<String> roles);

    List<ProjectEmployeeVO> selectProjectEmployeeVO(@Param("projectEmployeeIds") List<Integer> projectEmployeeIds);
}
