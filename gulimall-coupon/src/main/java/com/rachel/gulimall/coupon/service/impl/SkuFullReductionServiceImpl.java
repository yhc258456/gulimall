package com.rachel.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.to.MemberPrice;
import com.rachel.common.to.SkuReductionTo;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.gulimall.coupon.dao.SkuFullReductionDao;
import com.rachel.gulimall.coupon.entity.MemberPriceEntity;
import com.rachel.gulimall.coupon.entity.SkuFullReductionEntity;
import com.rachel.gulimall.coupon.entity.SkuLadderEntity;
import com.rachel.gulimall.coupon.service.MemberPriceService;
import com.rachel.gulimall.coupon.service.SkuFullReductionService;
import com.rachel.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    private MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveInfo(SkuReductionTo reductionTo) {
        Long skuId = reductionTo.getSkuId();
        // 保存sku的优惠、满减信息；gulimall_sms > sms_sku_ladder\sms_sku_full_reduction\sms_member_price
        if (reductionTo.getFullCount() > 0) {
            SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
            skuLadderEntity.setSkuId(skuId);
            skuLadderEntity.setDiscount(reductionTo.getDiscount());
            skuLadderEntity.setFullCount(reductionTo.getFullCount());
            skuLadderEntity.setAddOther(reductionTo.getCountStatus());
            skuLadderService.save(skuLadderEntity);
        }

        if (reductionTo.getFullPrice().compareTo(new BigDecimal("0")) > 0) {
            SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
            BeanUtils.copyProperties(reductionTo, skuFullReductionEntity);
            this.save(skuFullReductionEntity);
        }


        List<MemberPrice> memberPrices = reductionTo.getMemberPrice();
        if (memberPrices != null && memberPrices.size() > 0) {
            List<MemberPriceEntity> memberPriceEntities = memberPrices.stream().map(memberPrice -> {
                MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                memberPriceEntity.setSkuId(skuId);
                memberPriceEntity.setMemberPrice(memberPrice.getPrice());
                memberPriceEntity.setMemberLevelId(memberPrice.getId());
                memberPriceEntity.setMemberLevelName(memberPrice.getName());
                memberPriceEntity.setAddOther(1);
                return memberPriceEntity;
            }).filter(memberPriceEntity -> memberPriceEntity.getMemberPrice().compareTo(new BigDecimal("0")) > 0).collect(Collectors.toList());
            memberPriceService.saveBatch(memberPriceEntities);
        }
    }

}