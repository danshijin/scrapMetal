/**
 * 
 */
package com.smm.scrapMetal.util;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.OrdersBo;

/**
 * OrdersJob
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年2月24日
 */
public class ReleaseJob {
	
	@Resource
	private OrdersBo ordersBo;
	
	/**
	 * 处理定时更新订单任务
	 */
	@Transactional
	public void updateOrders() {
		ordersBo.updateRelease();
	}
	
}
