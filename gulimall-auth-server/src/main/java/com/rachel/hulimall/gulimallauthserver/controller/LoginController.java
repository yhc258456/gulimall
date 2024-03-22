package com.rachel.hulimall.gulimallauthserver.controller;


import com.alibaba.fastjson.TypeReference;
import com.rachel.common.constant.AuthServerConstant;
import com.rachel.common.exception.BizCodeEnum;
import com.rachel.common.utils.R;
import com.rachel.hulimall.gulimallauthserver.feign.MemberFeignService;
import com.rachel.hulimall.gulimallauthserver.feign.ThirdFeignService;
import com.rachel.hulimall.gulimallauthserver.vo.UserLoginVo;
import com.rachel.hulimall.gulimallauthserver.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {

    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private ThirdFeignService thirdFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/sms/sendcode")
    @ResponseBody
    public R sendCode(@RequestParam("phone") String phone) {
        // 1.接口防刷 TODO
        String key = AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone;
        String valueRedis = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(valueRedis)) {
            long time = Long.parseLong(valueRedis.split("_")[1]);
            // 在60s内
            if (System.currentTimeMillis() - time <= 60000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        // 2.验证码的再次校验
        String code = UUID.randomUUID().toString().substring(0, 6);
        String value = code + "_" + System.currentTimeMillis();
        stringRedisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
        return thirdFeignService.sendCode(phone, code);
    }

    @PostMapping("/regist")
    public String regist(@Valid UserRegisterVo registerVo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 1.参数校验
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            Map<String, String> errorsMap = new HashMap<>();
            fieldErrors.stream().forEach(fieldError -> {
                String key = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                if (errorsMap.containsKey(key)) {
                    errorsMap.put(key, errorsMap.get(key) + ";" + defaultMessage);
                } else {
                    errorsMap.put(key, defaultMessage);
                }
            });
            redirectAttributes.addFlashAttribute("errors", errorsMap);

            return "redirect:http://auth.gulimall.com/register.html";
        }
        // 2.验证码校验
        String key = AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone();
        String valueRedis = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(valueRedis)) {
            String code = valueRedis.split("_")[0];
            if (registerVo.getCode().equalsIgnoreCase(code)) {
                // 远程调用会员服务 注册用户
                R r = memberFeignService.registMember(registerVo);
                // 删除redis的key
                stringRedisTemplate.delete(key);
                if (r.getCode() == 0) {
                    return "redirect:http://auth.gulimall.com/login.html";
                } else {
                    redirectAttributes.addFlashAttribute("msg", r.getData("msg", new TypeReference<String>() {
                    }));
                    return "redirect:http://auth.gulimall.com/register.html";
                }
            } else {
                Map<String, String> errorsMap = new HashMap<>();
                errorsMap.put("code", "验证码错误！");
                redirectAttributes.addFlashAttribute("errors", errorsMap);
                return "redirect:http://auth.gulimall.com/register.html";
            }
        } else {
            Map<String, String> errorsMap = new HashMap<>();
            errorsMap.put("code", "验证码错误！");
            redirectAttributes.addFlashAttribute("errors", errorsMap);
            return "redirect:http://auth.gulimall.com/register.html";
        }
    }


    @PostMapping("/login")
    public String login(UserLoginVo userLoginVo, RedirectAttributes redirectAttributes) {
        R r = memberFeignService.loginMember(userLoginVo);
        if (r.getCode() == 0) {
            // 登录成功
            return "redirect:http://gulimall.com";
        }
        // 失败
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("msg", r.getData("msg", new TypeReference<String>() {
        }));
        redirectAttributes.addFlashAttribute("errors", hashMap);
        return "redirect:http://auth.gulimall.com/login.html";
    }

}
