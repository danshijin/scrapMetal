package com.smm.scrapMetal.bo;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.Order;
import com.smm.scrapMetal.domain.Supply;

public interface IOrderBO {
		
		/**
		 * 查询订单列表
		 * @param map
		 * @return
		 */
		public List<Order> query(Map<String, Object> map);
		
		/**
		 * 修改订单状态
		 * @param map
		 * @return
		 */
		public Integer updateOrderStatus(Map<String, Object> map);
		
		/**
		 * 查看订单
		 * @param id
		 * @return
		 */
		public Order queryOrderInfo(Integer id);
		
		/**
		 * 采购单生成订单准备数据
		 * @param id
		 * @return
		 */
		public Order purchaseToAddOrderInfoById(Integer id);
		
		/**
		 * 供货单生成订单准备数据
		 * @param id
		 * @return
		 */
		public Order supplyToAddOrderInfoById(Integer id);
		
		/**
		 * 生成订单
		 * @param order
		 * @return
		 */
		public Integer addOrder(Order order);
		
		/**
		 * 生成订单编号
		 * @param order
		 * @return
		 */
		public Integer insOrderNoById(Order order);
		
		/**
		 * 采购商生成订单准备数据
		 * @param id
		 * @return
		 */
		public Order purCustomerToAddOrderInfoById(Integer id);
		
		/**
		 * 供货商生成订单准备数据
		 * @param id
		 * @return
		 */
		public Order SupCustomerToAddOrderInfoById(Integer id);
		
		/**
		 * 导出订单
		 * @param map
		 * @return
		 */
		public List<Order> ExporOrderList(Map<String, Object> map);
		
		/**
		 * 修改库存和冻结数量
		 * @param supply
		 * @return
		 */
		public Integer updateFrozenQuantity(Supply supply);
}
