package com.ttag.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ttag.api.client.UserClient;
import com.ttag.api.domain.po.User;
import com.ttag.api.domain.dto.GetUserDTO;
import com.ttag.api.domain.vo.MyUserVO;
import com.ttag.api.domain.dto.LoginUser;
import com.ttag.api.domain.dto.Result;
import com.ttag.api.exception.BusinessException;
import com.ttag.product.domain.dto.AddProductDTO;
import com.ttag.api.domain.dto.UpdateProductDTO;
import com.ttag.api.domain.po.Products;
import com.ttag.product.domain.vo.MyProductVO;
import com.ttag.product.domain.vo.QuerryProductVO;
import com.ttag.product.domain.vo.RequestProductVO;
import com.ttag.product.mapper.ProductsMapper;
import com.ttag.product.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
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

/**
 * 产品业务层实现类
 */
@Service
@RequiredArgsConstructor
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final UserClient userClient;

    @Override
    public Result addProduct(AddProductDTO addProductDTO) {
        Products products = BeanUtil.copyProperties(addProductDTO, Products.class);
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        String limitUser = "farmer";
        //获取到我的信息
        Result me = userClient.getMe();
        MyUserVO myUserVO = BeanUtil.toBean(me.getData(), MyUserVO.class);
        //判断我是否是农户
        if (!myUserVO.getRole().equals(limitUser)){
            return Result.fail("上线产品前请先成为农户");
        }
        //设置产品初始评分
        products.setFarmerId(userId).setScore(Double.valueOf(0));
        try {
            productsMapper.insert(products);
        } catch (Exception e) {
            throw new BusinessException(FAIL,"请填写完整商品的信息");
        }
        return Result.ok("新增产品成功");
    }

    @Override
    public Result listMyProduct(Integer current) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        IPage page1 = new Page(current,8);
        QueryWrapper<Products> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("farmer_id",userId).orderByDesc("id");
        IPage page = productsMapper.selectPage(page1, queryWrapper);
        // 获取当前页数据
        List<Products> records = page.getRecords();
        List<MyProductVO> collect = records.stream().map(products -> {
            MyProductVO myProductVO = BeanUtil.copyProperties(products, MyProductVO.class);
            return myProductVO;
        }).collect(Collectors.toList());
        return Result.ok(collect,collect.size());
    }

    @Override
    public Result getProduct(Long id) {
        String key = CACHE_PRODUCT_KEY + id;
        //从redis中获取信息
        String productJson = stringRedisTemplate.opsForValue().get(key);
        //判断获取到信息
        if (StrUtil.isNotBlank(productJson)) {
            //重新设置缓存时间
            Products products = JSONUtil.toBean(productJson, Products.class);
            stringRedisTemplate.opsForValue().set(key, productJson, CACHE_PRODUCT_TTL + new Random().nextLong(100), TimeUnit.SECONDS);
            return Result.ok(products);
        }
        //如果查询到是空，反回空
        if (productJson != null) {
            return Result.ok();
        }
        //redis中没有信息则查询数据库
        Products products = productsMapper.selectById(id);
        //数据库没有消息，添加空集合缓存防止缓存穿透
        if (products==null) {
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return Result.ok();
        }
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(products), CACHE_PRODUCT_TTL + new Random().nextLong(100), TimeUnit.SECONDS);
        return Result.ok(products);
    }

    @Override
    public Result updateProduct(UpdateProductDTO updateProductDTO) {
        //该方法已经废弃
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        Long farmerId = productsMapper.selectById(updateProductDTO.getId()).getFarmerId();
        if (!userId.equals(farmerId)){
            return Result.fail("你没有权限进行此操作");
        }
        Products products = BeanUtil.toBean(updateProductDTO, Products.class);
        products.setStatus("pending");
        try {
            productsMapper.updateById(products);
        } catch (Exception e) {
            throw new BusinessException(FAIL,"物品库存不足");
        }
        return Result.ok("更改货物信息成功，请等待重新审核");
    }

    @Override
    public Result listProduct(Integer current) {
        List<QuerryProductVO> collect = productsMapper.selectProductList(current*12);
        return Result.ok(collect,collect.size());
    }

    @Override
    public Result listVagueProduct(String context,Integer current) {
        List<QuerryProductVO> listProduct = productsMapper.selectProductListVague("*"+context+"*", current*12);
        return Result.ok(listProduct,listProduct.size());
    }

    @Override
    public Result removeProduct(Long id) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        Long farmerId = productsMapper.selectById(id).getFarmerId();
        //判断下架商品是否是农户本人
        if (!userId.equals(farmerId)){
            return Result.fail("你没有权限进行此操作");
        }
        //主动更新redis信息
        stringRedisTemplate.delete(CACHE_PRODUCT_KEY+id);
        productsMapper.deleteById(id);
        return Result.ok("货物下架成功");
    }

    @Override
    public Result updateProductStock(Long productId, Integer num) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        Long farmerId = productsMapper.selectById(productId).getFarmerId();
        //判断更改库存的是否是农户本人
        if (!userId.equals(farmerId)){
            return Result.fail("你没有权限进行此操作");
        }
        Products products = productsMapper.selectById(productId);
        products.setStock(num);
        stringRedisTemplate.delete(CACHE_PRODUCT_KEY + productId);
        try {
            productsMapper.updateById(products);
        } catch (Exception e) {
            throw new BusinessException(FAIL,"物品库存不足");
        }
        return Result.ok("更改成功");
    }

    @Override
    public Result permission(Long productId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        String accessRole = userClient.getUserById(user.getId()).getRole();
        String limitUser = "admin";
        //判断用户的权限是否是管理员
        if (!limitUser.equals(accessRole)){
            return Result.fail("你没有权限进行此操作");
        }
        Products products = new Products();
        products.setStatus("accept").setId(productId);
        productsMapper.updateById(products);
        return Result.ok("您同意了该产品的上线申请");
    }

    @Override
    public Result reject(Long productId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        String accessRole = userClient.getUserById(user.getId()).getRole();
        String limitUser = "admin";
        //判断用户的权限是否是管理员
        if (!limitUser.equals(accessRole)){
            return Result.fail("你没有权限进行此操作");
        }
        Products products = new Products();
        products.setStatus("reject").setId(productId);
        productsMapper.updateById(products);
        return Result.ok("您退回了该产品的上线申请，请及时联系农户指导产品的上线");
    }

    @Override
    public Result getRequest(Integer current) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        String accessRole = userClient.getUserById(user.getId()).getRole();
        //判断用户的权限是否是管理员
        String limitUser = "admin";
        if (!limitUser.equals(accessRole)){
            return Result.fail("你没有权限进行此操作");
        }
        IPage page1 = new Page(current,12);
        QueryWrapper<Products> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").eq("status","pending");
        IPage page = productsMapper.selectPage(page1, queryWrapper);
        // 获取当前页数据
        List<Products> records = page.getRecords();
        List<RequestProductVO> collect = records.stream().map(products -> {
            GetUserDTO getUserDTO = userClient.getUserById(products.getFarmerId());
            RequestProductVO requestProductVO = new RequestProductVO();
            requestProductVO.setProducts(products);
            requestProductVO.setEmail(getUserDTO.getEmail());
            requestProductVO.setUsername(getUserDTO.getUsername());
            requestProductVO.setPicture(getUserDTO.getHead());
            return requestProductVO;
        }).collect(Collectors.toList());
        return Result.ok(collect,collect.size());
    }
}
