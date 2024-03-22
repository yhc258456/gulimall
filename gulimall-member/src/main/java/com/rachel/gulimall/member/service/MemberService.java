package com.rachel.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.member.entity.MemberEntity;
import com.rachel.gulimall.member.exception.UserNameExistsException;
import com.rachel.gulimall.member.vo.MemberLoginVo;
import com.rachel.gulimall.member.vo.MemberRegisterVo;
import com.rachel.gulimall.member.vo.SocialUser;

import java.util.Map;

/**
 * @author rachel
 * @email 413843464@qq.com
 * @date 2024-03-08 13:35:11
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void registMember(MemberRegisterVo memberRegisterVo);

    void checkUserNameExist(String userName) throws UserNameExistsException;

    void checkPhoneExist(String phone) throws UserNameExistsException;

    MemberEntity loginMember(MemberLoginVo memberLoginVo);

    /**
     * 社交用户的登录
     * @param socialUser
     * @return
     */
    MemberEntity login(SocialUser socialUser) throws Exception;
}

