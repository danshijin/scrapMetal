package com.smm.scrapMetal.dao;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.Order;
import com.smm.scrapMetal.domain.Supply;

public interface OrderDao {
	public List<Order> query(Map<String, Object> map);
	
	public Integer updateOrderStatus(Map<String, Object> map);
	
	public Order queryOrderInfo(Integer id);
	
	public Order purchaseToAddOrderInfoById(Integer id);
	
	public Order supplyToAddOrderInfoById(Integer id);
	
	public Integer addOrder(Order order);
	
	public Integer insOrderNoById(Order order);
	
	public Order purCustomerToAddOrderInfoById(Integer id);
	
	public Order SupCustomerToAddOrderInfoById(Integer id);
	
	public List<Order> ExporOrderList(Map<String, Object> map);
	
	public Integer updateFrozenQuantity(Supply supply);
	
	public Integer updateFrozenQuantityByOrderId(Map<String, Object> map);
}
