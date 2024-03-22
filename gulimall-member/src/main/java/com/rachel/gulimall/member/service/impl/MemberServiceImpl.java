package com.rachel.gulimall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.HttpUtils;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.gulimall.member.dao.MemberDao;
import com.rachel.gulimall.member.dao.MemberLevelDao;
import com.rachel.gulimall.member.entity.MemberEntity;
import com.rachel.gulimall.member.entity.MemberLevelEntity;
import com.rachel.gulimall.member.exception.UserNameExistsException;
import com.rachel.gulimall.member.service.MemberService;
import com.rachel.gulimall.member.vo.MemberLoginVo;
import com.rachel.gulimall.member.vo.MemberRegisterVo;
import com.rachel.gulimall.member.vo.SocialUser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void registMember(MemberRegisterVo memberRegisterVo) {
        String userName = memberRegisterVo.getUserName();
        // 用户名唯一检查
        checkUserNameExist(userName);
        String phone = memberRegisterVo.getPhone();
        // 手机号唯一检查
        checkUserNameExist(phone);

        String password = memberRegisterVo.getPassword();
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setCreateTime(new Date());

        //设置默认等级
        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(levelEntity.getId());

        // 密码
        // 盐值MD5加密
        // Md5Crypt.md5Crypt("123456".getBytes(), "$123$3212$");
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = cryptPasswordEncoder.encode(password);
        memberEntity.setPassword(encodePassword);
        memberEntity.setUsername(userName);
        memberEntity.setMobile(phone);

        memberEntity.setGender(0);

        this.save(memberEntity);
    }

    @Override
    public void checkUserNameExist(String userName) throws UserNameExistsException {
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", userName));
        if (memberEntity != null) {
            throw new UserNameExistsException();
        }
    }

    @Override
    public void checkPhoneExist(String phone) throws UserNameExistsException {
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (memberEntity != null) {
            throw new UserNameExistsException();
        }
    }

    @Override
    public MemberEntity loginMember(MemberLoginVo memberLoginVo) {
        String loginacct = memberLoginVo.getLoginacct();
        String password = memberLoginVo.getPassword();

        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginacct).or().eq("mobile", loginacct));
        if (memberEntity == null) {
            return null;
        }

        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        String memberEntityPasswordDB = memberEntity.getPassword();
        boolean matches = cryptPasswordEncoder.matches(password, memberEntityPasswordDB);
        if (matches) {
            return memberEntity;
        }

        return null;
    }

    @Override
    public MemberEntity login(SocialUser socialUser) throws Exception {

        //具有登录和注册逻辑
        String uid = socialUser.getUid();

        //1、判断当前社交用户是否已经登录过系统
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));

        if (memberEntity != null) {
            //这个用户已经注册过
            //更新用户的访问令牌的时间和access_token
            MemberEntity update = new MemberEntity();
            update.setId(memberEntity.getId());
//            update.setAccessToken(socialUser.getAccess_token());
//            update.setExpiresIn(socialUser.getExpires_in());
            this.baseMapper.updateById(update);

//            memberEntity.setAccessToken(socialUser.getAccess_token());
//            memberEntity.setExpiresIn(socialUser.getExpires_in());
            return memberEntity;
        } else {
            //2、没有查到当前社交用户对应的记录我们就需要注册一个
            MemberEntity register = new MemberEntity();
            //3、查询当前社交用户的社交账号信息（昵称、性别等）
            Map<String,String> query = new HashMap<>();
            query.put("access_token",socialUser.getAccess_token());
            query.put("uid",socialUser.getUid());
            HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<String, String>(), query);

            if (response.getStatusLine().getStatusCode() == 200) {
                //查询成功
                String json = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(json);
                String name = jsonObject.getString("name");
                String gender = jsonObject.getString("gender");
                String profileImageUrl = jsonObject.getString("profile_image_url");

                register.setNickname(name);
                register.setGender("m".equals(gender)?1:0);
                register.setHeader(profileImageUrl);
                register.setCreateTime(new Date());
//                register.setSocialUid(socialUser.getUid());
//                register.setAccessToken(socialUser.getAccess_token());
//                register.setExpiresIn(socialUser.getExpires_in());

                //把用户信息插入到数据库中
                this.baseMapper.insert(register);

            }
            return register;
        }

    }

}