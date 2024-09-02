package com.ttag.order.controller;


import com.ttag.api.domain.dto.Result;
import com.ttag.api.domain.po.OrderDetail;
import com.ttag.api.domain.po.Order;
import com.ttag.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单表现层
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 用户创建订单
     *
     * @param num 购买产品的数量
     * @param productId 购买的产品的Id
     * @return 创建订单成功或失败的信息
     */
    @PostMapping("/{id}")
    public Result saveOrder(@RequestParam(value = "num")Integer num,@PathVariable("id")Long productId){
        return orderService.saveOrder(num,productId);
    }

    /**
     * 删除已经完成或超时的订单
     *
     * @param orderId 订单号
     * @return 删除订单成功或失败的信息
     */
    @DeleteMapping("/{id}")
    public Result removeOrder(@PathVariable("id")Long orderId){
        return orderService.removeOrder(orderId);
    }

    /**
     * 查看我尚未支付的订单
     *
     * @param current 查询的页数
     * @return 我尚未支付的订单的集合
     */
    @GetMapping("/me/pending")
    public Result listMyOrderPending(@RequestParam(value = "current")Integer current){
        return orderService.listMyOrderPending(current);
    }

    /**
     * 查询除尚未完成订单外的其他订单
     *
     * @param current 查询的页数
     * @return 尚未完成订单外的其他订单的集合
     */
    @GetMapping("/me/other")
    public Result listMyOrderOther(@RequestParam(value = "current")Integer current){
        return orderService.listMyOrderOther(current);
    }

    /**
     * 支付某一订单
     *
     * @param pw 用户的密码
     * @param orderId 要支付的订单的Id
     * @return 支付成功或失败的结果
     */
    @PutMapping("/{id}")
    public Result payOrder(@RequestParam("pw")String pw,@PathVariable("id")Long orderId){
        return orderService.payOrder(pw,orderId);
    }

    /**
     * 内部服务调用的获取orderDetail的方法
     *
     * @param id 订单的Id
     * @return 订单的详细信息
     */
    @GetMapping("/product/detail/{Id}")
    public OrderDetail getOrderDetailByOrderId(@PathVariable("Id")Long id){
        return orderService.getOrderDetailByOrderId(id);
    }

    /**
     * 查询我的某一个订单
     *
     * @param orderId 订单Id
     * @return 订单的详细信息
     */
    @GetMapping("/{id}")
    public Result getOrder(@PathVariable("id")Long orderId){
        return orderService.getOrder(orderId);
    }

    /**
     * 内部调用查询某一个订单
     *
     * @param orderId 订单Id
     * @return 订单的信息
     */
    @GetMapping("client/{id}")
    public Order selectOrder(@PathVariable("id")Long orderId){
        return orderService.getById(orderId);
    }
}
