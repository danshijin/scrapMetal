/**
 * 
 */
package com.smm.scrapMetal.bo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.impl.IOrdersBo;
import com.smm.scrapMetal.dao.IOrdersDAO;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.OrderDetail;
import com.smm.scrapMetal.domain.Orders;
import com.smm.scrapMetal.util.DateUtil;

/**
 * OrdersBo
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年1月25日
 */

@Service
public class OrdersBo implements IOrdersBo{

	Logger logger = Logger.getLogger(OrdersBo.class);
	
	@Resource
	private IOrdersDAO iOrdersDAO;

	@Override
	public List<Orders> getOrderList(Map<String, Object> map) {
		return iOrdersDAO.getOrderList(map);
	}

	@Override
	public int getOrderCount(Map<String, Object> map) {
		return iOrdersDAO.getOrderCount(map);
	}

	@Override
	public List<Orders> getReleaseList(Map<String, Object> map) {
		return iOrdersDAO.getReleaseList(map);
	}

	@Override
	public int getReleaseCount(Map<String, Object> map) {
		return iOrdersDAO.getReleaseCount(map);
	}

	@Override
	public List<Orders> getFavorite(Map<String, Object> map) {
		return iOrdersDAO.getFavorite(map);
	}

	@Override
	public Customer getCustomerDetail(int customerId) {
		Customer customer =  iOrdersDAO.getCustomerDetail(customerId);
		if (customer != null && customer.getScrapItemIds() != null) {
			String[] itemIds = customer.getScrapItemIds().split(",");
			Map<String, Object> map = new HashMap<>();
			map.put("itemIds", itemIds);
			List<String> itemName = iOrdersDAO.getItemName(map);
			customer.setScrapItemIds(getItemName(itemName));
		}
		return customer;
	}

	@Override
	public int getFavoriteCount(Map<String, Object> map) {
		return iOrdersDAO.getFavoriteCount(map);
	}

	@Override
	public int delCustomer(int customerId) {
		return iOrdersDAO.delCustomer(customerId);
	}

	@Override
	public List<String> getItemName(Map<String, Object> map) {
		return iOrdersDAO.getItemName(map);
	}
	
	public String getItemName(List<String> itemName){
		String str = "";
		for (String string : itemName) {
			str=str+string+",";
		}
		if (str.length()>0) {
			return str.substring(0,str.length()-1);
		}
		return str;
	}

	
	/*
	 * 
	 * 以下为微信接口
	 */
	@Override
	public List<Orders> myOrderList(Map<String, Object> map) {
		return iOrdersDAO.myOrderList(map);
	}

	@Override
	public Orders getOrderStatusCount(String phone) {
		return iOrdersDAO.getOrderStatusCount(phone);
	}

	@Override
	public List<Orders> myFavoriteList(String phone) {
		return iOrdersDAO.myFavoriteList(phone);
	}

	@Override
	public List<Orders> myReleaseList(Map<String, Object> map) {
		return iOrdersDAO.myReleaseList(map);
	}

	@Override
	public Orders getReleaseStatusCount(String phone) {
		return iOrdersDAO.getReleaseStatusCount(phone);
	}

	@Override
	public int updatePurchase(Map<String, Object> map) {
		return iOrdersDAO.updatePurchase(map);
	}

	@Override
	public int updateSupply(Map<String, Object> map) {
		return iOrdersDAO.updateSupply(map);
	}
	
	@Override
	public int restorePurchaseInfostatus(Map<String, Object> map){
		return iOrdersDAO.restorePurchaseInfostatus(map);
	}
	
	@Override
	public int restoreSupplyInfostatus(Map<String, Object> map){
		return iOrdersDAO.restoreSupplyInfostatus(map);
	}

	@Override
	public int submitOrder(String orderId) {
		return iOrdersDAO.submitOrder(orderId);
	}
	
	@Override
	public int submitOrderUpdateSupply(Map<String,Object> map) {
		return iOrdersDAO.submitOrderUpdateSupply(map);
	}
	
	@Override
	public int closeOrder(String orderId) {
		return iOrdersDAO.closeOrder(orderId);
	}
	
	@Override
	public int closeOrderUpdateSupply(Map<String,Object> map) {
		return iOrdersDAO.closeOrderUpdateSupply(map);
	}
	
	@Override
	public Orders getOrderById(Integer id) {
		return iOrdersDAO.getOrderById(id);
	}

	@Override
	public int sellerSubmitOrder(String orderId) {
		return iOrdersDAO.sellerSubmitOrder(orderId);
	}

	@Override
	public int buyerSubmitOrder(String orderId) {
		return iOrdersDAO.buyerSubmitOrder(orderId);
	}

	@Override
	public int sellerCloseOrder(String orderId) {
		return iOrdersDAO.sellerCloseOrder(orderId);
	}

	@Override
	public int buyerCloseOrder(String orderId) {
		return iOrdersDAO.buyerCloseOrder(orderId);
	}

	@Override
	public int deletePurchase(int id) {
		return iOrdersDAO.deletePurchase(id);
	}

	@Override
	public int deleteSupply(int id) {
		return iOrdersDAO.deleteSupply(id);
	}
	
	@Override
	public int deleteFavorite(Map<String, Object> map) {
		return iOrdersDAO.deleteFavorite(map);
	}
	
	/*
	 * 
	 * 以下为定时器job
	 */
	@Override
	public List<Orders> getNotConfirmedOrders() {
		return iOrdersDAO.getNotConfirmedOrders();
	}

	@Override
	public int ConfirmedOrders(Map<String, Object> map) {
		return iOrdersDAO.ConfirmedOrders(map);
	}

	@Override
	public int RevokeOrders(Map<String, Object> map) {
		return iOrdersDAO.RevokeOrders(map);
	}
	
	/**
	 * 处理定时更新订单任务
	 */
	public void updateOrders() {
		//查询未确认订单
		List<Orders> orders = getNotConfirmedOrders();
		
		String revokeIds = "";
		String confirmedIds = "";
		Map<String, Object> map = new HashMap<String, Object>();
		for (Orders order : orders) {
			if (order.getIsBuyerConfirmed() == 0 && order.getIsSellerConfirmed() == 0) {
				if (compareTime(order.getUpdatedAt()) >= 14) { //超过14天自动未成交
					if(order.getIsBuyerConfirmed() == 0){
						Map<String,Object> omap = new HashMap<>();
						omap.put("isBuyerConfirmed", 1);
						omap.put("buyerConfirmedTime", new DateUtil().currentDate());
						iOrdersDAO.updateOrderBuyerTime(omap);
					}
					if(order.getIsSellerConfirmed() == 0){
						Map<String,Object> omap = new HashMap<>();
						omap.put("isSellerConfirmed", 1);
						omap.put("sellerConfirmedTime", new DateUtil().currentDate());
						iOrdersDAO.updateOrderSellerTime(omap);
					}
					
					revokeIds = revokeIds + order.getId() + ",";
					if (order.getSource() == 1) {// 减掉对应的供货单里冻结的数量
						map.clear();
						map.put("id", order.getSourceId());
            			map.put("quantity", order.getQuantity());
            			closeOrderUpdateSupply(map);
					}
				}
			}else if ( (order.getIsBuyerConfirmed() == 0 && order.getIsSellerConfirmed() == 1) || (order.getIsBuyerConfirmed() == 1 && order.getIsSellerConfirmed() == 0) ) {
				if (compareTime(order.getUpdatedAt()) >= 7) {//超过7天自动提交
					confirmedIds = confirmedIds + order.getId() + ",";
					if(order.getIsBuyerConfirmed() == 0){
						Map<String,Object> omap = new HashMap<>();
						omap.put("isBuyerConfirmed", 1);
						omap.put("buyerConfirmedTime", new DateUtil().currentDate());
						iOrdersDAO.updateOrderBuyerTime(omap);
					}
					if(order.getIsSellerConfirmed() == 0){
						Map<String,Object> omap = new HashMap<>();
						omap.put("isSellerConfirmed", 1);
						omap.put("sellerConfirmedTime", new DateUtil().currentDate());
						iOrdersDAO.updateOrderSellerTime(omap);
					}
					if (order.getSource() == 1) { // 减掉对应的供货单里冻结的数量
						map.clear();
        				map.put("id", order.getSourceId());
            			map.put("quantity", order.getQuantity());
            			submitOrderUpdateSupply(map);
					}
				}
			}
		}
		map.clear();
		if (!revokeIds.equals("")) {
			map.put("ids", revokeIds.split(","));
			int i = RevokeOrders(map);
			if (i > 0) {
				logger.info("撤销订单成功，撤销数量："+i);
			}
		}
		if (!confirmedIds.equals("")) {
			map.put("ids", confirmedIds.split(","));
			int i = ConfirmedOrders(map);
			if (i > 0) {
				logger.info("成交订单成功，成交数量："+i);
			}
		}
		
	}
	
	@Override
	public List<Orders> getAuditReleaseList() {
		return iOrdersDAO.getAuditReleaseList();
	}

	@Override
	public int supplyIsOverdue(Map<String, Object> map) {
		return iOrdersDAO.supplyIsOverdue(map);
	}

	@Override
	public int purchaseIsOverdue(Map<String, Object> map) {
		return iOrdersDAO.purchaseIsOverdue(map);
	}
	
	@Override
	public int supplyLastInfoStatus(Map<String, Object> map){
		return iOrdersDAO.supplyLastInfoStatus(map);
	}
	
	@Override
	public int purchaseLastInfoStatus(Map<String, Object> map){
		return iOrdersDAO.purchaseLastInfoStatus(map);
	}
	
	public void updateRelease(){
		List<Orders> orders = getAuditReleaseList();
		String supplyIds = "";
		String purchaseIds = "";
		for (Orders order : orders) {
			int outDay = order.getExpiryType() == 0 ? 7 : 30; //信息有效期类型 0 一周 1 一个月
			if (order.getSource() == 1) { //1 供货 2 采购
				if (compareTime(order.getUpdatedAt()) >= outDay) {
					supplyIds = supplyIds + order.getId() + ",";
				}
			}else {
				if (compareTime(order.getUpdatedAt()) >= outDay) {
					purchaseIds = purchaseIds + order.getId() + ",";
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (!supplyIds.equals("")) {
			map.put("ids", supplyIds.split(","));
			supplyLastInfoStatus(map);
			int i = supplyIsOverdue(map);
			if (i > 0) {
				logger.info("有"+i+"条供货单过期");
			}
		}
		if (!purchaseIds.equals("")) {
			map.put("ids", purchaseIds.split(","));
			purchaseLastInfoStatus(map);
			int i = purchaseIsOverdue(map);
			if (i > 0) {
				logger.info("有"+i+"条采购单过期");
			}
		}
	}
	
	public int compareTime(Date time){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updatedAt = DateUtil.doFormatDate(time, "yyyy-MM-dd");
		String todayDate = DateUtil.getDateWidthFormat("yyyy-MM-dd");
	    try {
	    	calendar.setTime(sdf.parse(updatedAt));
		    int updatedAtDay = calendar.get(Calendar.DAY_OF_YEAR);
		    calendar.setTime(sdf.parse(todayDate));
	        int today = calendar.get(Calendar.DAY_OF_YEAR);
	        return today-updatedAtDay;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return 0;
	}

	@Override
	public OrderDetail queryOrderDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return iOrdersDAO.queryOrderDetail(map);
	}

}
