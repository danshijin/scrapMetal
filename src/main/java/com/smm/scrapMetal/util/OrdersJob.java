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
public class OrdersJob {
	
//	Logger logger = Logger.getLogger(OrdersJob.class);
	
	@Resource
	private OrdersBo ordersBo;
	
	/**
	 * 处理定时更新订单任务
	 */
	@Transactional
	public void updateOrders() {
		//查询未确认订单
//		List<Orders> orders = iOrdersBo.getNotConfirmedOrders();
//		
//		String revokeIds = "";
//		String confirmedIds = "";
//		for (Orders order : orders) {
//			if (order.getIsBuyerConfirmed() == 0 && order.getIsSellerConfirmed() == 0) {
//				if (compareTime(order.getUpdatedAt()) > 7) { //超过7天自动更新
//					revokeIds = revokeIds + order.getId() + ",";
//				}
//			}else if (order.getIsBuyerConfirmed() != 0 && order.getIsSellerConfirmed() != 0) {
//				continue;
//			}else {
//				if (compareTime(order.getUpdatedAt()) > 7) {//超过7天自动更新
//					confirmedIds = confirmedIds + order.getId() + ",";
//				}
//			}
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (!revokeIds.equals("")) {
//			map.put("ids", revokeIds);
//			int i = iOrdersBo.RevokeOrders(map);
//			if (i > 0) {
//				logger.info("撤销订单成功，撤销数量："+i);
//			}
//		}
//		if (!confirmedIds.equals("")) {
//			map.put("ids", confirmedIds);
//			int i = iOrdersBo.ConfirmedOrders(map);
//			if (i > 0) {
//				logger.info("成交订单成功，成交数量："+i);
//			}
//		}
		ordersBo.updateOrders();
	}
	
//	public int compareTime(Date time){
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String updatedAt = DateUtil.doFormatDate(time, "yyyy-MM-dd");
//		String todayDate = DateUtil.getDateWidthFormat("yyyy-MM-dd");
//	    try {
//	    	calendar.setTime(sdf.parse(updatedAt));
//		    int updatedAtDay = calendar.get(Calendar.DAY_OF_YEAR);
//		    calendar.setTime(sdf.parse(todayDate));
//	        int today = calendar.get(Calendar.DAY_OF_YEAR);
//	        return today-updatedAtDay;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	    return 0;
//	}
}
