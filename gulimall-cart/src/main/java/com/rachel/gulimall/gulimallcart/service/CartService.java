package com.rachel.gulimall.gulimallcart.service;

import com.rachel.gulimall.gulimallcart.vo.CartItemVo;
import com.rachel.gulimall.gulimallcart.vo.CartVo;

import java.util.concurrent.ExecutionException;

public interface CartService {
    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItemVo getCartBySkuId(Long skuId);

    CartVo getCart() throws ExecutionException, InterruptedException;

    void ClearCart(String cartKey);
}
