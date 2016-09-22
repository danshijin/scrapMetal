/**
 * 
 */
package com.smm.scrapMetal.dao;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.OrderDetail;
import com.smm.scrapMetal.domain.Orders;

/**
 * IOrdersDAO
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年1月25日
 */
public interface IOrdersDAO {

	public List<Orders> getOrderList(Map<String, Object> map);
	
	public int getOrderCount(Map<String, Object> map);
	
	public List<Orders> getReleaseList(Map<String, Object> map);
	
	public int getReleaseCount(Map<String, Object> map);
	
	public List<Orders> getFavorite(Map<String, Object> map);
	
	public int getFavoriteCount(Map<String, Object> map);
	
	public Customer getCustomerDetail(int customerId);
	
	public int delCustomer(int customerId);
	
	public List<String> getItemName(Map<String, Object> map);
	
	/*
	 * 
	 * 以下为定时器job
	 */
	public List<Orders> getNotConfirmedOrders();

	public int ConfirmedOrders(Map<String, Object> map);
	
	public int RevokeOrders(Map<String, Object> map);
	
	public List<Orders> getAuditReleaseList();
	
	public int supplyIsOverdue(Map<String, Object> map);
	
	public int purchaseIsOverdue(Map<String, Object> map);
	
	public int supplyLastInfoStatus(Map<String, Object> map);
	
	public int purchaseLastInfoStatus(Map<String, Object> map);
	//买方
	public int updateOrderBuyerTime(Map<String,Object> map);
	//卖方
	public int updateOrderSellerTime(Map<String,Object> map);
	
	/*
	 * 
	 * 以下为微信接口
	 */
	public List<Orders> myOrderList(Map<String, Object> map);

	public Orders getOrderStatusCount(String phone);
	
	public int submitOrder(String orderId);
	
	public int submitOrderUpdateSupply(Map<String, Object> map);
	
	public int closeOrder(String orderId);
	
	public int closeOrderUpdateSupply(Map<String, Object> map);
	
	public Orders getOrderById(Integer id);
	
	public int sellerSubmitOrder(String orderId);
	
	public int buyerSubmitOrder(String orderId);
	
	public int sellerCloseOrder(String orderId);
	
	public int buyerCloseOrder(String orderId);

	public List<Orders> myFavoriteList(String phone);
	
	public List<Orders> myReleaseList(Map<String, Object> map);
	
	public Orders getReleaseStatusCount(String phone);
	
	public int updatePurchase(Map<String, Object> map);
	
	public int updateSupply(Map<String, Object> map);
	
	public int restorePurchaseInfostatus(Map<String, Object> map);
	
	public int restoreSupplyInfostatus(Map<String, Object> map);
	
	public int deletePurchase(int id);
	
	public int deleteSupply(int id);
	
	public int deleteFavorite(Map<String, Object> map);
	
	/**
	 * 采购单详情
	 * @param map
	 * @return
	 */
	public OrderDetail queryOrderDetail(Map<String, Object> map);
}
