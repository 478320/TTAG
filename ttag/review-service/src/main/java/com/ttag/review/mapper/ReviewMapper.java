package com.ttag.review.mapper;

import com.ttag.review.domain.po.Review;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论的数据层对象
 */
@Mapper
public interface ReviewMapper extends BaseMapper<Review> {

}
