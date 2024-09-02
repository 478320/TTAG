package com.ttag.user.mapper;

import com.ttag.api.domain.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 用户数据层
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update tb_user set money = money - ${totalFee} where id = #{userId}")
    void deductMoney(Long userId,Integer totalFee);

    @Update("update tb_user set money = money + ${totalFee} where id = #{userId}")
    void addMoney(Long userId,Integer totalFee);

}
