package com.rachel.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.gulimall.product.dao.SkuInfoDao;
import com.rachel.gulimall.product.entity.SkuInfoEntity;
import com.rachel.gulimall.product.service.SkuInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> skuInfoEntityQueryWrapper = new QueryWrapper<>();
        // 分类id
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.isNotEmpty(catelogId) && !"0".equals(catelogId)) {
            skuInfoEntityQueryWrapper.eq("catalog_id", catelogId);
        }
        // p品牌id
        String brandId = (String) params.get("brandId");
        if (StringUtils.isNotEmpty(brandId) && !"0".equals(brandId)) {
            skuInfoEntityQueryWrapper.eq("brand_id", brandId);
        }
        // 最小价格
        String min = (String) params.get("min");
        if (StringUtils.isNotEmpty(min) && new BigDecimal(min).compareTo(new BigDecimal("0")) > 0) {
            skuInfoEntityQueryWrapper.ge("price", min);
        }
        // 最大价格
        String max = (String) params.get("max");
        if (StringUtils.isNotEmpty(max) && new BigDecimal(max).compareTo(new BigDecimal("0")) > 0) {
            skuInfoEntityQueryWrapper.le("price", max);
        }

        // 关键字 id或者名字
        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)) {
            skuInfoEntityQueryWrapper.and(w -> w.eq("sku_id", key).or().like("sku_name", key));
        }

        IPage<SkuInfoEntity> page = this.page(new Query<SkuInfoEntity>().getPage(params), skuInfoEntityQueryWrapper);

        return new PageUtils(page);
    }

}