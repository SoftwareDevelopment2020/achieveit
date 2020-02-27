package com.softwaredevelopment.achieveit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.softwaredevelopment.achieveit.entity.RoleBasics;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author RainkQ
 * @since 2020-02-27
 */
@Repository
@Transactional
public interface RoleBasicsMapper extends BaseMapper<RoleBasics> {

}
