package com.rachel.gulimall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rachel.common.utils.CollectionUtils;
import com.rachel.common.utils.R;
import com.rachel.gulimall.cart.interceptor.CartInterceptor;
import com.rachel.gulimall.cart.service.CartService;
import com.rachel.gulimall.cart.to.UserInfoTo;
import com.rachel.gulimall.cart.vo.CartItemVo;
import com.rachel.gulimall.cart.vo.CartVo;
import com.rachel.gulimall.cart.feign.ProductFeignService;
import com.rachel.gulimall.cart.vo.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Component
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate customRedisTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;


    @Autowired
    private ProductFeignService productFeignService;

    private static final String CART_PREFIX = "gulimall:cart:";

    @Override
    public CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations cartOps = getCartOps();
        String json = (String) cartOps.get(skuId.toString());
        // 已有该商品
        if (!StringUtils.isEmpty(json)) {
            CartItemVo cartItemVo = JSON.parseObject(json, CartItemVo.class);
            cartItemVo.setCount(cartItemVo.getCount() + num);
            cartOps.put(skuId.toString(), JSON.toJSONString(cartItemVo));
            return cartItemVo;
        }

        // 没有有该商品
        CartItemVo cartItemVo = new CartItemVo();
        CompletableFuture<Void> skuInfoTask = CompletableFuture.runAsync(() -> {
            // 获取sku info
            R info = productFeignService.getSkuInfo(skuId);
            SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoVo>() {
            });

            cartItemVo.setSkuId(skuId);
            cartItemVo.setPrice(skuInfo.getPrice());
            cartItemVo.setCount(num);
            cartItemVo.setCheck(true);
            cartItemVo.setTitle(skuInfo.getSkuTitle());
            cartItemVo.setImage(skuInfo.getSkuDefaultImg());
        }, threadPoolExecutor);

        CompletableFuture<Void> saleAttrValuesTask = CompletableFuture.runAsync(() -> {
            List<String> skuSaleAttrValuesAsStringList = productFeignService.getSkuSaleAttrValuesAsStringList(skuId);
            cartItemVo.setSkuAttrValues(skuSaleAttrValuesAsStringList);
        }, threadPoolExecutor);

        // 阻塞等待所欲任务完成
        CompletableFuture.allOf(skuInfoTask, saleAttrValuesTask).get();

        cartOps.put(skuId.toString(), JSON.toJSONString(cartItemVo));
        return cartItemVo;
    }

    @Override
    public CartItemVo getCartBySkuId(Long skuId) {
        BoundHashOperations cartOps = getCartOps();
        String json = (String) cartOps.get(skuId.toString());
        // 无此商品
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, CartItemVo.class);
    }

    @Override
    public CartVo getCart() throws ExecutionException, InterruptedException {
        CartVo cartVo = new CartVo();
        UserInfoTo userInfoTo = CartInterceptor.toThreadLocal.get();
        if (userInfoTo.getUserId() != null) {
            // 已登录
            String cartTemKey = CART_PREFIX + userInfoTo.getUserKey();
            List<CartItemVo> cartItemVos = getCartItemByCartKey(cartTemKey);
            if (CollectionUtils.isNotEmpty(cartItemVos)) {
                for (CartItemVo cartItemVo : cartItemVos) {
                    addToCart(cartItemVo.getSkuId(), cartItemVo.getCount());
                }
            }
            // 删除临时购物车的数据
            ClearCart(cartTemKey);

            String cartKey = CART_PREFIX + userInfoTo.getUserId();
            List<CartItemVo> cartItemByCartKey = getCartItemByCartKey(cartKey);
            cartVo.setItems(cartItemByCartKey);

            return cartVo;
        } else {
            // 未登录
            String cartTemKey = CART_PREFIX + userInfoTo.getUserKey();
            List<CartItemVo> cartItemVos = getCartItemByCartKey(cartTemKey);
            cartVo.setItems(cartItemVos);
            return cartVo;
        }
    }

    @Override
    public void ClearCart(String cartKey) {
        customRedisTemplate.delete(cartKey);
    }

    private BoundHashOperations getCartOps() {
        UserInfoTo userInfoTo = CartInterceptor.toThreadLocal.get();
        String key = CART_PREFIX;
        // 登录用户
        if (userInfoTo.getUserId() != null) {
            key += userInfoTo.getUserId();
        } else {
            key += userInfoTo.getUserKey();
        }
        return customRedisTemplate.boundHashOps(key);
    }

    private List<CartItemVo> getCartItemByCartKey(String cartKey) {
        BoundHashOperations boundHashOperations = customRedisTemplate.boundHashOps(cartKey);
        List<Object> values = boundHashOperations.values();
        if (CollectionUtils.isEmpty(values)) {
            return new ArrayList<>();
        }
        List<CartItemVo> collect = values.stream().map(obj -> {
            String objJson = (String) obj;
            CartItemVo cartItemVo = JSON.parseObject(objJson, CartItemVo.class);
            return cartItemVo;
        }).collect(Collectors.toList());
        return collect;
    }


}
