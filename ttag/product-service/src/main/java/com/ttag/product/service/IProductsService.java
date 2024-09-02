package com.ttag.product.service;

import com.ttag.api.domain.dto.Result;
import com.ttag.product.domain.dto.AddProductDTO;
import com.ttag.api.domain.dto.UpdateProductDTO;
import com.ttag.api.domain.po.Products;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 产品业务层
 */
public interface IProductsService extends IService<Products> {

   

    Result addProduct(AddProductDTO addProductDTO);

    Result listMyProduct(Integer current);

    Result getProduct(Long id);

    Result updateProduct(UpdateProductDTO updateProductDTO);

    Result listProduct(Integer current);

    Result listVagueProduct(String context,Integer current);

    Result removeProduct(Long id);

    Result updateProductStock(Long productId,Integer num);

    Result permission(Long productId);

    Result reject(Long productId);

    Result getRequest(Integer current);
}
