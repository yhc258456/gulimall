package com.rachel.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;

import com.rachel.gulimall.product.dao.BrandDao;
import com.rachel.gulimall.product.entity.BrandEntity;
import com.rachel.gulimall.product.service.BrandService;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BrandEntity> brandEntityQueryWrapper = new QueryWrapper<>();
        Object name = params.get("name");
        if (name != null) {
            brandEntityQueryWrapper.like("name", name);
        }
        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), brandEntityQueryWrapper);

        return new PageUtils(page);
    }

}