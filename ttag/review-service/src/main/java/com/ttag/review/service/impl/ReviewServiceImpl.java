package com.ttag.review.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ttag.api.client.OrderClient;
import com.ttag.api.client.UserClient;
import com.ttag.api.domain.dto.LoginUser;
import com.ttag.api.domain.dto.Result;
import com.ttag.api.domain.po.OrderDetail;
import com.ttag.api.domain.po.Products;
import com.ttag.api.domain.po.User;
import com.ttag.api.domain.dto.GetUserDTO;
import com.ttag.api.exception.BusinessException;
import com.ttag.api.utils.MQConstants;
import com.ttag.review.domain.dto.AddProductReviewDTO;
import com.ttag.review.domain.po.Review;
import com.ttag.review.domain.vo.ListProductReviewDTO;
import com.ttag.review.mapper.ReviewMapper;
import com.ttag.review.service.IReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ttag.api.utils.Code.FAIL;
import static com.ttag.api.utils.RedisConstans.*;
import static com.ttag.api.utils.RedisConstans.CACHE_PRODUCT_TTL;

/**
 * 评论的业务层实现类
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements IReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final OrderClient orderClient;

    private final UserClient userClient;

    @Override
    public Result listProductReviewPage(Long productId, Integer current) {
        String key = CACHE_REVIEW_KEY + productId;
        //从redis中查询评论信息
        String reviewListJson = (String) stringRedisTemplate.opsForHash().get(key,current.toString());
        if (StrUtil.isNotBlank(reviewListJson)) {
            //查询到则更新缓存时间
            List<ListProductReviewDTO> listProductReviewDTOS = JSONUtil.toList(reviewListJson, ListProductReviewDTO.class);
            stringRedisTemplate.expire(key,CACHE_REVIEW_TTL,TimeUnit.MINUTES);
            return Result.ok(listProductReviewDTOS,listProductReviewDTOS.size());
        }
        //如果查询到是空集合，反回空集合
        if (reviewListJson != null) {
            return Result.ok(Collections.emptyList(),0);
        }
        //redis中没有信息则查询数据库
        Page page1 = new Page(current,8);
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at").eq("product_id",productId);
        IPage page = reviewMapper.selectPage(page1, queryWrapper);
        // 获取当前页数据
        List<Review> records = page.getRecords();
        //数据库没有消息，添加空集合缓存防止缓存穿透
        if (records.isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, current.toString(),"");
            return Result.ok(Collections.emptyList(),0);
        }
        List<ListProductReviewDTO> collect = records.stream().map(review -> {
            ListProductReviewDTO listProductReviewDTO = new ListProductReviewDTO();
            GetUserDTO getUserDTO = userClient.getUserById(review.getUserId());
            listProductReviewDTO.setReview(review);
            listProductReviewDTO.setUsername(getUserDTO.getUsername());
            listProductReviewDTO.setHead(getUserDTO.getHead());
            return listProductReviewDTO;
        }).collect(Collectors.toList());
        stringRedisTemplate.opsForHash().put(key,current.toString(), JSONUtil.toJsonStr(collect));
        stringRedisTemplate.expire(key,CACHE_REVIEW_TTL,TimeUnit.MINUTES);
        return Result.ok(collect,collect.size());
    }

    @Override
    public Result addProductReview(AddProductReviewDTO addProductReviewDTO) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Integer rating = addProductReviewDTO.getRating();
        //判断用户是否输入了不符合规范的评分
        if (!(rating<=5&&rating>=1)){
            return Result.fail("错误的评分");
        }
        Long orderId = addProductReviewDTO.getOrderId();
        //判断该订单的状态是否是完成
        if (!"completed".equals(orderClient.selectOrder(orderId).getStatus())){
            return Result.fail("订单尚未完成无法发表评论");
        }
        OrderDetail orderDetail = orderClient.getProduct(orderId);
        Long itemId = orderDetail.getItemId();
        Review review = new Review();
        review.setProductId(itemId).setRating(rating).setComment(addProductReviewDTO.getComment())
                .setUserId(user.getId()).setOrderId(orderId).setCreatedAt(LocalDateTime.now());
        stringRedisTemplate.delete(CACHE_REVIEW_KEY + itemId);
        try {
            reviewMapper.insert(review);
        } catch (Exception e) {
            throw new BusinessException(FAIL,"一个订单只能发表一条评论");
        }
        //向mq发送数据异步重新计算产品的分数
        rabbitTemplate.convertAndSend(MQConstants.SCORE_EXCHANGE,MQConstants.CALCULATE_SCORE_KEY, itemId);
        return Result.ok("评论成功");
    }
}
