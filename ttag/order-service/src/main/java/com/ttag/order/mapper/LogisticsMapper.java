package com.ttag.order.mapper;

import com.ttag.order.domain.po.Logistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 物流信息的数据层对象
 */
@Mapper
public interface LogisticsMapper extends BaseMapper<Logistics> {

}
