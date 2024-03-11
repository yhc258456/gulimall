package com.rachel.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.constant.WareConstant;
import com.rachel.common.utils.CollectionUtils;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.gulimall.ware.dao.PurchaseDao;
import com.rachel.gulimall.ware.entity.PurchaseDetailEntity;
import com.rachel.gulimall.ware.entity.PurchaseEntity;
import com.rachel.gulimall.ware.service.PurchaseDetailService;
import com.rachel.gulimall.ware.service.PurchaseService;
import com.rachel.gulimall.ware.vo.MergeVo;
import com.rachel.gulimall.ware.vo.PurchaseDoneVo;
import com.rachel.gulimall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;


    @Autowired
    private WareSkuServiceImpl wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnReceiveList(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0).or().eq("status", 1);

        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params), wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void merge(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.NEW_CREATED.getCode());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }

        // 商品状态为新建或已分配
        PurchaseEntity purchaseEntity = this.getById(purchaseId);
        Integer status = purchaseEntity.getStatus();
        if (WareConstant.PurchaseStatusEnum.NEW_CREATED.getCode() != status &&
                WareConstant.PurchaseStatusEnum.ASSIGNED.getCode() != status) {
            return;
        }

        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> purchaseDetailEntities = items.stream().map(item -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(item);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());

        purchaseDetailService.updateBatchById(purchaseDetailEntities);

        PurchaseEntity newPurchaseEntity = new PurchaseEntity();
        newPurchaseEntity.setId(purchaseId);
        newPurchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }

    /**
     * 采购单的id集合
     *
     * @param ids
     */
    @Override
    public void received(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            // 采购单都是新建或者已分配的
            List<PurchaseEntity> collect = ids.stream().map(id -> {
                PurchaseEntity purchase = this.getById(id);
                return purchase;
            }).filter(purchase -> {
                Integer status = purchase.getStatus();
                if (WareConstant.PurchaseStatusEnum.NEW_CREATED.getCode() == status || WareConstant.PurchaseStatusEnum.ASSIGNED.getCode() == status) {
                    return true;
                }
                return false;
            }).map(purchaseEntity -> {
                purchaseEntity.setUpdateTime(new Date());
                purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.RECEIVED.getCode());
                return purchaseEntity;
            }).collect(Collectors.toList());

            // 改变采购单的状态
            this.updateBatchById(collect);

            // 改变采购项的状态
            collect.forEach(purchaseEntity -> {
                Long id = purchaseEntity.getId();
                List<PurchaseDetailEntity> purchaseDetails = purchaseDetailService.listDetailByPurchaseId(id);
                List<PurchaseDetailEntity> newDetailEntities = purchaseDetails.stream().map(purchaseDetailEntity -> {
                    PurchaseDetailEntity newPurchaseDetailEntity = new PurchaseDetailEntity();
                    newPurchaseDetailEntity.setId(purchaseDetailEntity.getId());
                    newPurchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
                    return newPurchaseDetailEntity;
                }).collect(Collectors.toList());

                purchaseDetailService.updateBatchById(newDetailEntities);
            });
        }
    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo purchaseDoneVo) {
        Long purchaseId = purchaseDoneVo.getId();

        // 修改采购项的状态
        List<PurchaseItemDoneVo> items = purchaseDoneVo.getItems();
        boolean flag = true;
        List<PurchaseDetailEntity> purchaseDetailEntities = new ArrayList<PurchaseDetailEntity>();
        for (PurchaseItemDoneVo purchaseItemDoneVo : items) {
            Long itemId = purchaseItemDoneVo.getItemId();
            Integer status = purchaseItemDoneVo.getStatus();
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(itemId);
            if (status == WareConstant.PurchaseDetailStatusEnum.HAS_ERROR.getCode()) {
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.HAS_ERROR.getCode());
                flag = false;
            } else {
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getCode());
                PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(itemId);
                // 添加库存
                wareSkuService.addStock(purchaseDetail.getSkuId(), purchaseDetail.getWareId(), purchaseDetail.getSkuNum());
            }
            purchaseDetailEntities.add(purchaseDetailEntity);
        }

        purchaseDetailService.updateBatchById(purchaseDetailEntities);

        // 修改采购单的状态
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setStatus(flag ? WareConstant.PurchaseStatusEnum.FINISHED.getCode() : WareConstant.PurchaseStatusEnum.HAS_ERROR.getCode());
        purchaseEntity.setUpdateTime(new Date());
        purchaseEntity.setId(purchaseId);
        this.updateById(purchaseEntity);

    }

}