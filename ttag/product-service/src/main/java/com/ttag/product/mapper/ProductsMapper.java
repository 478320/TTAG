package com.ttag.product.mapper;

import com.ttag.api.domain.po.Products;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ttag.product.domain.vo.QuerryProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 产品的数据层对象
 */
@Mapper
public interface ProductsMapper extends BaseMapper<Products> {

    List<QuerryProductVO> selectProductListVague(String context, Integer current);

    List<QuerryProductVO> selectProductList(Integer current);

}
