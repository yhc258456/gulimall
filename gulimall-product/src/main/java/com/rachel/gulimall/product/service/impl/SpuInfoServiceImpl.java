package com.rachel.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.to.SkuReductionTo;
import com.rachel.common.to.SpuBoundTo;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.common.utils.R;
import com.rachel.gulimall.product.dao.SpuInfoDao;
import com.rachel.gulimall.product.entity.*;
import com.rachel.gulimall.product.feign.CouponFeignService;
import com.rachel.gulimall.product.service.*;
import com.rachel.gulimall.product.vo.AttrRespVo;
import com.rachel.gulimall.product.vo.spu.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * TODO 高级部分来完善（事物回滚、远程调用缓慢）
     * @param spuSaveVo
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        // 1.保存spu的基本信息；pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);
        Long spuId = spuInfoEntity.getId();

        // 2.保存spu的描述图片；pms_spu_info_desc
        List<String> decript = spuSaveVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.save(spuInfoDescEntity);
        // 3.保存spu的图片集；pms_spu_images
        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveSpuImages(spuInfoEntity.getId(), images);
        // 4.保存spu的所有属性信息；pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(baseAttr -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(baseAttr.getAttrId());
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            AttrRespVo attrInfo = attrService.getAttrInfo(baseAttr.getAttrId());
            productAttrValueEntity.setAttrName(attrInfo.getAttrName());
            productAttrValueEntity.setAttrValue(baseAttr.getAttrValues());
            productAttrValueEntity.setQuickShow(baseAttr.getShowDesc());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(productAttrValueEntities);

        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        spuBoundTo.setSpuId(spuId);
        // 5.远程调用积分服务，保存spu的积分信息；gulimall_sms > sms_spu_bounds
        R boundsSaveR = couponFeignService.saveBounds(spuBoundTo);
        if (boundsSaveR.getCode() != 0) {
            log.error("远程调用spu的积分信息失败！");
        }

        // 6.保存spu对应的所有sku信息
        List<Skus> skus = spuSaveVo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(sku -> {
                String defaultImgUrl = "";
                List<Image> skuImages = sku.getImages();
                for (Image image : skuImages) {
                    Integer defaultImg = image.getDefaultImg();
                    if (defaultImg != null && image.getDefaultImg() == 1) {
                        defaultImgUrl = image.getImgUrl();
                        break;
                    }
                }

                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku, skuInfoEntity);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                // 6.1) 保存sku的基本信息；pms_sku_info
                skuInfoEntity.setSkuDefaultImg(defaultImgUrl);
                skuInfoService.save(skuInfoEntity);

                // 新增的skuId
                Long skuId = skuInfoEntity.getSkuId();

                List<SkuImagesEntity> skuImagesEntities = skuImages.stream().map(img -> {
                            SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                            skuImagesEntity.setSkuId(skuId);
                            skuImagesEntity.setDefaultImg(img.getDefaultImg());
                            skuImagesEntity.setImgUrl(img.getImgUrl());
                            return skuImagesEntity;
                        }).filter(skuImagesEntity -> !StringUtils.isEmpty(skuImagesEntity.getImgUrl()))
                        .collect(Collectors.toList());

                // 6.2) 保存sku的图片信息；pms_sku_images
                skuImagesService.saveBatch(skuImagesEntities);

                List<Attr> attrs = sku.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attrs.stream().map(attr -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                // 6.3) 保存sku的基本销售属性；pms_sku_sale_attr_value
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                // 5.4) 保存sku的优惠、满减信息；gulimall_sms > sms_sku_ladder\sms_sku_full_reduction\sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(sku, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                List<MemberPrice> memberPrices = sku.getMemberPrice();
                List<com.rachel.common.to.MemberPrice> memberPriceList = memberPrices.stream().map(memberPrice -> {
                    com.rachel.common.to.MemberPrice memberPriceTo = new com.rachel.common.to.MemberPrice();
                    BeanUtils.copyProperties(memberPrices, memberPriceTo);
                    return memberPriceTo;
                }).collect(Collectors.toList());
                skuReductionTo.setMemberPrice(memberPriceList);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) > 0) {
                    R r = couponFeignService.saveSkuReduction(skuReductionTo);
                    if (r.getCode() != 0) {
                        log.error("远程调用保存优惠信息失败！");
                    }
                }
            });
        }


    }

}