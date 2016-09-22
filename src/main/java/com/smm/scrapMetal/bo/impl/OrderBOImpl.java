package com.smm.scrapMetal.bo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.IOrderBO;
import com.smm.scrapMetal.dao.OrderDao;
import com.smm.scrapMetal.domain.Order;
import com.smm.scrapMetal.domain.Supply;

@Service
public class OrderBOImpl implements IOrderBO{
	
	@Resource
	private OrderDao orderDao;
	
	/**
	 * 查询订单列表
	 */
	public List<Order> query(Map<String, Object> map) {
		List<Order> list = new ArrayList<Order>();
		try {
			list = orderDao.query(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 修改订单状态
	 */
	public Integer updateOrderStatus(Map<String, Object> map) {
		Integer count = -1;
		try {
			orderDao.updateFrozenQuantityByOrderId(map);//修改供货单库存和冻结数量
			count = orderDao.updateOrderStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 查看订单
	 */
	public Order queryOrderInfo(Integer id) {
		Order o = new Order();
		try {
			o = orderDao.queryOrderInfo(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 采购单生成订单准备数据
	 */
	public Order purchaseToAddOrderInfoById(Integer id) {
		Order o = new Order();
		try {
			o = orderDao.purchaseToAddOrderInfoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 供货单生成订单准备数据
	 */
	public Order supplyToAddOrderInfoById(Integer id) {
		Order o = new Order();
		try {
			o = orderDao.supplyToAddOrderInfoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 生成订单
	 */
	public Integer addOrder(Order order) {
		Integer status =-1;
		try {
			status = orderDao.addOrder(order);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}

	/**
	 * 生成订单编号
	 */
	public Integer insOrderNoById(Order order) {
		Integer status =-1;
		try {
			status = orderDao.insOrderNoById(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * 采购商生成订单准备数据
	 */
	public Order purCustomerToAddOrderInfoById(Integer id) {
		Order o = new Order();
		try {
			o = orderDao.purCustomerToAddOrderInfoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 供货商生成订单准备数据
	 */
	public Order SupCustomerToAddOrderInfoById(Integer id) {
		Order o = new Order();
		try {
			o = orderDao.SupCustomerToAddOrderInfoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 导出订单
	 */
	public List<Order> ExporOrderList(Map<String, Object> map) {
		List<Order> list = null;
		try {
			list = orderDao.ExporOrderList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer updateFrozenQuantity(Supply supply) {
		Integer count = -1;
		try {			
			count = orderDao.updateFrozenQuantity(supply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
