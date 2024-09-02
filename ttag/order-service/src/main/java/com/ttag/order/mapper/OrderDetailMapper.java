package com.ttag.order.mapper;

import com.ttag.api.domain.po.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单详情的数据层对象
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    void updateStock(Integer num,Long orderId);
}
