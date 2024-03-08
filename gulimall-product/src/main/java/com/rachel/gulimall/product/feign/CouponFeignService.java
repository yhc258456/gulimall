package com.rachel.gulimall.product.feign;


import com.rachel.common.to.SkuReductionTo;
import com.rachel.common.to.SpuBoundTo;
import com.rachel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "gulimall-coupon")
public interface CouponFeignService {

    /**
     * 保存
     */
    @PostMapping("/coupon/spubounds/save")
    R saveBounds(@RequestBody SpuBoundTo spuBoundTo);


    @PostMapping("coupon/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody  SkuReductionTo skuReductionTo);
}
