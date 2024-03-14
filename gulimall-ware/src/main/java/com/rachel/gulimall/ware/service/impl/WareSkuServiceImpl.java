package com.rachel.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.to.SkuHasStockVo;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.common.utils.R;
import com.rachel.gulimall.ware.dao.WareSkuDao;
import com.rachel.gulimall.ware.entity.WareSkuEntity;
import com.rachel.gulimall.ware.feign.ProductFeignService;
import com.rachel.gulimall.ware.service.WareSkuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wareSkuEntityQueryWrapper = new QueryWrapper<>();
        // 库存id
        String wareId = (String) params.get("wareId");
        if (StringUtils.isNotEmpty(wareId)) {
            wareSkuEntityQueryWrapper.eq("ware_id", wareId);
        }
        // 商品id
        String skuId = (String) params.get("skuId");
        if (StringUtils.isNotEmpty(skuId)) {
            wareSkuEntityQueryWrapper.eq("sku_id", skuId);
        }
        IPage<WareSkuEntity> page = this.page(new Query<WareSkuEntity>().getPage(params),
                wareSkuEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        // 如果没有则新建
        int count = this.count(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (count > 0) {
            // 更新
            this.baseMapper.updateStock(skuId, wareId, skuNum);
        } else {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);
            wareSkuEntity.setWareId(wareId);
            // 远程调用商品服务 若失败 无需事物回滚
            // 1.try catch 试下其余方法使事物不会滚 TODO
            try {
                R r = productFeignService.getSkuinfo(skuId);
                if (r.getCode() == 0) {
                    Map<String, Object> skuInfo = (Map<String, Object>) r.get("skuInfo");
                    wareSkuEntity.setSkuName((String) skuInfo.get("skuName"));
                }
            } catch (Exception e) {
                log.error("远程调用商品服务失败，{}", e);
            }
            this.save(wareSkuEntity);
        }
    }

    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {

        List<SkuHasStockVo> skuHasStockVos = skuIds.stream().map(item -> {
            Long count = this.baseMapper.getSkuStock(item);
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            skuHasStockVo.setSkuId(item);
            skuHasStockVo.setHasStock(count == null?false:count > 0);
            return skuHasStockVo;
        }).collect(Collectors.toList());
        return skuHasStockVos;
    }

}