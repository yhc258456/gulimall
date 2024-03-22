package com.rachel.hulimall.gulimallauthserver.feign;

import com.rachel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-third-service")
public interface ThirdFeignService {

    @GetMapping(value = "/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);

}
