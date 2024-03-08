package com.rachel.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 
 *
 * @author rachel
 * @email 413843464@qq.com
 * @date 2024-03-08 13:35:10
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

