package com.rachel.hulimall.gulimallauthserver.feign;

import com.rachel.common.utils.R;
import com.rachel.hulimall.gulimallauthserver.vo.SocialUser;
import com.rachel.hulimall.gulimallauthserver.vo.UserLoginVo;
import com.rachel.hulimall.gulimallauthserver.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-member")
public interface MemberFeignService {

    @PostMapping("/member/member/regist")
    public R registMember(@RequestBody UserRegisterVo memberRegisterVo);


    @PostMapping("/member/member/login")
    public R loginMember(@RequestBody UserLoginVo userLoginVo);

    @PostMapping(value = "/member/member/oauth2/login")
    R oauthLogin(@RequestBody SocialUser socialUser) throws Exception;
}
