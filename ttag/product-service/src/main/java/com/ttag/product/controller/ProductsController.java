package com.ttag.product.controller;


import cn.hutool.core.bean.BeanUtil;
import com.ttag.api.domain.dto.LoginUser;
import com.ttag.api.domain.dto.Result;
import com.ttag.api.domain.po.Products;
import com.ttag.api.domain.po.User;
import com.ttag.api.exception.BusinessException;
import com.ttag.product.domain.dto.AddProductDTO;
import com.ttag.api.domain.dto.UpdateProductDTO;
import com.ttag.product.mapper.ProductsMapper;
import com.ttag.product.service.IProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.ttag.api.utils.Code.FAIL;

/**
 * 产品的表现层对象
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private IProductsService productsService;

    @Autowired
    private ProductsMapper productsMapper;

    /**
     * 农户可用，查看我的产品
     *
     * @param current 查询的页数
     * @return 我的产品的信息
     */
    @GetMapping("/me")
    public Result listMyProduct(@RequestParam Integer current){
        return productsService.listMyProduct(current);
    }

    /**
     * 农户可用，上架我的产品
     *
     * @param addProductDTO 上架产品信息的数据传输对象
     * @return 加入审核列表的成功或失败的信息
     */
    @PostMapping
    public Result addProduct(@RequestBody AddProductDTO addProductDTO){
        return productsService.addProduct(addProductDTO);
    }

    /**
     * 农户可用，更新我的产品（以废弃）
     *
     * @param updateProductDTO 更新我的产品的信息
     * @return 更新成功或失败的信息
     */
    @PutMapping()
    public Result updateProduct(@RequestBody UpdateProductDTO updateProductDTO){
        return productsService.updateProduct(updateProductDTO);
    }

    /**
     * 农户可用，更新我的产品的库存
     *
     * @param productId 要更新库存的产品的Id
     * @param num 要更新的库存的数量
     * @return 更新成功与否的信息
     */
    @PutMapping("/stock/{id}")
    public Result updateProductStock(@PathVariable("id")Long productId,@RequestParam("num")Integer num){
        return productsService.updateProductStock(productId,num);
    }

    /**
     * 农户可用，下架我的产品
     *
     * @param id 要下架的产品的Id
     * @return 下架成功与否的信息
     */
    @DeleteMapping("/{id}")
    public Result undercarriage(@PathVariable("id")Long id){
        return productsService.removeProduct(id);
    }


    /**
     * 获取商品的详细信息
     *
     * @param id 要获取详细信息的商品的Id
     * @return 商品的详细信息
     */
    @GetMapping("/detail/{id}")
    public Result getProduct(@PathVariable("id")Long id){
        return productsService.getProduct(id);
    }

    /**
     * 主页查询商品
     *
     * @param current 查询的页数
     * @return 商品信息的集合
     */
    @GetMapping("/list")
    public Result listProduct(@RequestParam Integer current){
        return productsService.listProduct(current);
    }

    /**
     * 搜索符合内容的商品
     *
     * @param context 搜索的内容
     * @param current 搜索的页数
     * @return 搜索到的商品的信息
     */
    @GetMapping("/vague")
    public Result listVagueProduct(@RequestParam(value = "context") String context,@RequestParam Integer current){
        return productsService.listVagueProduct(context,current);
    }

    /**
     * 管理员可用，查询农户的产品申请
     *
     * @param current 查询的页数
     * @return 农户产品申请的信息
     */
    @GetMapping("/admin/request")
    public Result getRequest(@RequestParam("current")Integer current){
        return productsService.getRequest(current);
    }

    /**
     * 管理员可用，允许农户商品的上线
     *
     * @param productId 允许的产品的Id
     * @return 是否成功操作
     */
    @PutMapping("/admin/permission/{id}")
    public Result permission(@PathVariable("id")Long productId){
        return productsService.permission(productId);
    }

    /**
     * 管理员可用，退回农户商品的上线
     *
     * @param productId 退回的产品的Id
     * @return 是否成功操作
     */
    @PutMapping("/admin/reject/{id}")
    public Result reject(@PathVariable("id")Long productId){
        return productsService.reject(productId);
    }

    /**
     * 内部服务，用于更改货物的库存数量
     *
     * @param productId 货物的Id
     * @param num 更改为多少
     */
    @PutMapping("/stock/client/{id}")
    public void updateProductStockClient(@PathVariable("id")Long productId,@RequestParam("num")Integer num){
        Products products = new Products();
        products.setStock(num).setId(productId);
        productsMapper.updateById(products);
    }
}
