package com.ttag.order.mapper;

import com.ttag.order.domain.dto.PayOrderDTO;
import com.ttag.api.domain.po.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ttag.order.domain.vo.GetOrderVO;
import com.ttag.order.domain.vo.ListOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 订单的数据层对象
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<ListOrderVO> listMyOrderPending(Long userId,Integer current);

    List<ListOrderVO> listMyOrderOther(Long userId,Integer current);

    GetOrderVO getOrderById(Long orderId,Long userId);

    PayOrderDTO getPayOrder(Long orderId);

    @Update("update tb_order set status = 'paid' where id = #{orderId}")
    void updateStatusComplete(Long orderId);
}
