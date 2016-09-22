/**
 * 
 */
package com.smm.scrapMetal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.OrdersBo;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Orders;
import com.smm.scrapMetal.util.JSONUtil;

/**
 * OrdersBOTEST
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年2月18日
 */
@RunWith(SpringJUnit4ClassRunner.class)  //注明测试类运行者
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@Transactional  //测试结束后，所有数据库变更将自动回滚
public class OrdersBOTEST extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private OrdersBo ordersBo;
	
    /**
     * 订单列表分页
     */
	@Test
	public void getOrdersList(){
		Map<String, Object> map = new HashMap<String, Object>();
		
        int customerId = 1;
        int startNum = 0;
        int endNum = 20;
		map.put("customerId", customerId);
        map.put("startNum", startNum);
		map.put("endNum", endNum);
		
		try {
			List<Orders> ordersList = ordersBo.getOrderList(map);
			System.out.println(ordersList.size());
			System.out.println(ordersBo.getOrderCount(map));
            System.out.println(JSONUtil.doConvertObject2Json(ordersList).toString());
            Assert.assertNotNull(ordersList);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
     * 发布列表分页
     */
	@Test
	public void getReleaseList(){
		Map<String, Object> map = new HashMap<String, Object>();
		
        int customerId = 1;
        int startNum = 0;
        int endNum = 20;
		map.put("customerId", customerId);
        map.put("startNum", startNum);
		map.put("endNum", endNum);
		
		try {
			List<Orders> releaseList = ordersBo.getReleaseList(map);
			System.out.println(releaseList.size());
			System.out.println(ordersBo.getReleaseCount(map));
            System.out.println(JSONUtil.doConvertObject2Json(releaseList).toString());
            Assert.assertNotNull(releaseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
     * 收藏列表分页
     */
	@Test
	public void getFavorite(){
		Map<String, Object> map = new HashMap<String, Object>();
		
        int customerId = 1;
        int startNum = 0;
        int endNum = 20;
		map.put("customerId", customerId);
        map.put("startNum", startNum);
		map.put("endNum", endNum);
		
		try {
			List<Orders> favoriteList = ordersBo.getFavorite(map);
			System.out.println(favoriteList.size());
			System.out.println(ordersBo.getFavoriteCount(map));
            System.out.println(JSONUtil.doConvertObject2Json(favoriteList).toString());
            Assert.assertNotNull(favoriteList);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
     * 客户详情
     */
	@Test
	public void getCustomerDetail(){
        Integer customerId = 2;
        String phone = "12365689785";
        String createUser = "王晶晶";
        Customer customer = ordersBo.getCustomerDetail(customerId);
        Assert.assertEquals(customer.getPhone(),phone);
        Assert.assertEquals(customer.getCreateUser(),createUser);
	}
	
	/**
     * 删除客户
     */
	@Test
	public void delCustomer(){
        int customerId = 1;
        int count = ordersBo.delCustomer(customerId);
        Assert.assertEquals(count,1);
	}
	
	
	/*
	 * 
	 * 以下为微信接口
	 */
	
	/**
     * 订单列表
     */
	@Test
	public void myOrderList(){
        try {
            String phone = "18221396165";
            String orderStatus = "0";
            Map<String, Object> map = new HashMap<>();
            map.put("phone", phone);
            map.put("orderStatus", orderStatus);
            List<Orders> orderList = ordersBo.myOrderList(map);
            Orders orders = ordersBo.getOrderStatusCount(phone);
//          “wait”:1, ‘待确认数量’
//          “noDeal”:2, ‘未成交数量’
//          “dealed”:3  ‘已成交数量’
            System.out.println(orders.getWait());
            System.out.println(orders.getNoDeal());
            System.out.println(orders.getDealed());
            Assert.assertNotNull(orderList);
            } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
     * 确认订单
     */
	@Test
	public void submitOrder(){
        try {
            String orderId = "9";
            int count = ordersBo.submitOrder(orderId);
            Assert.assertEquals(count,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
     * 撤销订单
     */
	@Test
	public void closeOrder(){
        try {
            String orderId = "9";
            int count = ordersBo.closeOrder(orderId);
            Assert.assertEquals(count,1);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
     * 根据id查找订单
     */
	@Test
	public void getOrderById(){
        try {
            Integer orderId = 2;
            Orders order = ordersBo.getOrderById(orderId);
            Assert.assertEquals(order.getId(),2);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
     * 买家提交订单
     */
	@Test
	public void buyerSubmitOrder(){
        try {
        	String orderId = "2";
            int count = ordersBo.buyerSubmitOrder(orderId);
            Orders order = ordersBo.getOrderById(Integer.valueOf(orderId));
            Assert.assertEquals(count,1);
            Assert.assertEquals(order.getIsBuyerConfirmed(),1);
            Assert.assertNotNull(order.getBuyerConfirmedTime());
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
     * 卖家提交订单
     */
	@Test
	public void sellerSubmitOrder(){
        try {
        	String orderId = "2";
            int count = ordersBo.sellerSubmitOrder(orderId);
            Orders order = ordersBo.getOrderById(Integer.valueOf(orderId));
            Assert.assertEquals(count,1);
            Assert.assertEquals(order.getIsSellerConfirmed(),1);
            Assert.assertNotNull(order.getSellerConfirmedTime());
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
     * 买家关闭订单
     */
	@Test
	public void buyerCloseOrder(){
        try {
        	String orderId = "2";
            int count = ordersBo.buyerCloseOrder(orderId);
            Orders order = ordersBo.getOrderById(Integer.valueOf(orderId));
            Assert.assertEquals(count,1);
            Assert.assertEquals(order.getIsBuyerConfirmed(),2);
            Assert.assertNotNull(order.getBuyerConfirmedTime());
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
     * 卖家关闭订单
     */
	@Test
	public void sellerCloseOrder(){
        try {
        	String orderId = "2";
            int count = ordersBo.sellerCloseOrder(orderId);
            Orders order = ordersBo.getOrderById(Integer.valueOf(orderId));
            Assert.assertEquals(count,1);
            Assert.assertEquals(order.getIsSellerConfirmed(),2);
            Assert.assertNotNull(order.getSellerConfirmedTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
     * 收藏列表
     */
	@Test
	public void myFavoriteList(){
        try {
            String phone = "18221396165";
            List<Orders> favoriteList = ordersBo.myFavoriteList(phone);
            Assert.assertNotNull(favoriteList);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
     * 发布列表
     */
	@Test
	public void myReleaseList(){
        try {
            String phone = "18221396165";
            String infoStatus = "0";
            Map<String, Object> map = new HashMap<>();
            map.put("phone", phone);
            map.put("infoStatus", Integer.valueOf(infoStatus));
            List<Orders> releaseList = ordersBo.myReleaseList(map);
            Orders orders = ordersBo.getReleaseStatusCount(phone);
//          “published”:1,
//          “auditing”:2,
//          “notPass”:4
            System.out.println(orders.getPublished());
            System.out.println(orders.getAuditing());
            System.out.println(orders.getNotPass());
            Assert.assertNotNull(releaseList);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
    /**
     * 更新发布列表时间接口
     * 
     */
	@Test
    public void updateRelease() {
        try {
        	//更新供货单
        	String isAll = "0";
            String phone = "18221396165";
            String id = "1";
            String source = "1";//1供货2采购
            
            //更新全部
//          String isAll = "1";  
            
            //更新采购单
//          String isAll = "0";
//          String phone = "18221396165";
//          String id = "1";
//          String source = "2";//1供货2采购
            
            Map<String, Object> map = new HashMap<>();

            map.put("phone", phone);
            if (isAll.equals("0")) {
                map.put("id", Integer.valueOf(id));
                if (source.equals("1")) {
                    int i = ordersBo.updateSupply(map);
                    Assert.assertEquals(i,1);
                } else {
                	int i = ordersBo.updatePurchase(map);
                	Assert.assertEquals(i,1);
                }
            }else {
                map.put("id", -1); // id为-1时更新全部
                int i = ordersBo.updatePurchase(map);
                int j = ordersBo.updateSupply(map);
                Assert.assertEquals(i,1);
                Assert.assertEquals(j,1);
            }
        
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
    /**
     * 删除发布记录
     * 
     */
	@Test
    public void deleteRelease() {
        try {
        	//删除供货单
        	String id = "1";
            String source = "1";//1供货2采购
            
            //删除采购单
//          String id = "1";
//          String source = "2";//1供货2采购
            if (source.equals("1")) {
            	int i = ordersBo.deleteSupply(Integer.valueOf(id));
            	Assert.assertEquals(i,1);
            } else {
            	int i = ordersBo.deletePurchase(Integer.valueOf(id));
            	Assert.assertEquals(i,1);
            }
        
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/*
	 * 
	 * 删除收藏列表
	 */
	@Test
	public void deleteFavorite() {
		Map<String, Object> map = new HashMap<>();
		map.put("source", 2);
        map.put("id", 3);
        int i = ordersBo.deleteFavorite(map);
        Assert.assertEquals(i,1);
	}
	
    /**
     * 定时器任务测试
     * 
     */
	@Test
    public void updateOrders() {
		ordersBo.updateOrders();
	}
	
	@Test
    public void updateReleaseToBeOverdue() {
		ordersBo.updateRelease();
	}
	
}
