package com.smm.scrapMetal;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.IAreasBO;
import com.smm.scrapMetal.bo.IItemBo;
import com.smm.scrapMetal.bo.IOrderBO;
import com.smm.scrapMetal.bo.PriceExplainBo;
import com.smm.scrapMetal.bo.PurchaseBo;
import com.smm.scrapMetal.bo.SupplyBO;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Order;


@RunWith(SpringJUnit4ClassRunner.class)
//注明测试类运行者
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
//加载spring配置文件
@Transactional
//测试结束后，所有数据库变更将自动回滚
public class OrderTest extends AbstractTransactionalJUnit4SpringContextTests  {

    @Resource
	private IOrderBO orderBO;
	@Resource
	private IAreasBO areasBO;
	@Resource
	private PriceExplainBo priceExplainBo;
	@Resource
	private IItemBo itemBo;
	@Resource
	private PurchaseBo purchaseBo;
	@Resource
	private SupplyBO supplyBO;
	
	private Logger     logger = Logger.getLogger(OrderTest.class);
	
	//查询订单列表
	@Test
	public void query(){
		logger.info("-------------开始订单列表测试: ---------");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("attribute","1");
		map.put("content","");
		map.put("goodsProv","-1");
		map.put("goodsCity","-1");
		map.put("dates","1");
		map.put("statDate","");
		map.put("endDate","");
		map.put("itemId","-1");
		map.put("orderStatus", "-1");
		
		map.put("startNum", "0");
		map.put("endNum", "10");
		List<Order> list = orderBO.query(map);
		assert(list!=null):"返回结果集为null";
		assert(list.size()>=0):"返回长度小于0";
//		logger.info("-------------查询结束  共"+list.size()+"条记录 -------------------" );

	}
	
	//修改订单状态
	@Test
	public void updateOrderStatus(){
		logger.info("-------------开始修改订单状态测试: ---------");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderId",2);
		map.put("orderStatus", 2);
		Integer count =orderBO.updateOrderStatus(map);
		assert(count==1):"受影响行数不为1";
//		logger.info("-------------修改订单状态测试结束: 受影响行数 "+count);
	}
	
	//查看订单详情
	@Test
	public void queryOrderInfo(){
		logger.info("-------------开始查看订单详情测试: ---------");
		Order order = orderBO.queryOrderInfo(2);
		assert(order!=null):"订单编号为null";
//		logger.info("-------------查看订单详情测试结束:  查询结果  订单编号："+order.getOrderNo());
	}
	
	//从采购单进入生成订单
	@Test
	public void toAddOrderByPurchase(){
		logger.info("-------------开始从买方进入生成订单测试: ---------");
	 	Order mol =  orderBO.purchaseToAddOrderInfoById(2);
	 	assert(mol !=null):"查询结果为空";
//	 	logger.info("-------------从买方进入生成订单 测试结束:  查询结果 买方id："+mol.getBuyerId());
	}
	
	//从供应单进入生成订单
	@Test
	public void toAddOrderBySupply(){
		logger.info("-------------开始从卖方进入生成订单测试: ---------");
		Order mol =  orderBO.supplyToAddOrderInfoById(5);
		assert(mol!=null):"查询结果为空";
//		logger.info("-------------从卖方进入生成订单 测试结束:  查询结果 卖方id："+mol.getSellerId());
	}
	
	//从采购客户生成订单
	@Test
	public void toAddOrderByPurCustomer(){
		logger.info("-------------开始从采购客户生成订单测试: ---------");
		Order mol =  orderBO.purCustomerToAddOrderInfoById(1);
		assert(mol!=null):"查询结果为空";
//		logger.info("-------------从采购客户生成订单 测试结束:  查询结果 采购客户id："+mol.getBuyerId());
	}
	
	//从供应客户生成订单
	@Test
	public void toAddOrderBySupCustomer(){
		logger.info("-------------开始从供应客户生成订单测试: ---------");
		Order mol =  orderBO.SupCustomerToAddOrderInfoById(2);
		assert(mol!=null):"查询结果为空";
//		logger.info("-------------从供应客户生成订单 测试结束:  查询结果 供应客户id："+mol.getSellerId());
	}
	
	
	//生成订单
	@Test
	public void addOrder(){
		logger.info("-------------开始生成订单测试: ---------");
		Order o = new Order();
		o.setGoodsProv(1);
		o.setGoodsCity(11);
		o.setQuantity(1.1);
		o.setPrice(1.1);
		o.setSource(1);
		o.setSourceId(1);
		o.setSellerPhone("1234567");
		o.setSellerContacter("aaa");
		o.setSellerCompanyName("123aaa");
		o.setBuyerPhone("1234567");
		o.setBuyerContacter("bbb");
		o.setBuyerCompanyName("123bbb");
		o.setPriceExplain(1);
		o.setDelivery(1);
		o.setQuantityUnit(0);
		o.setPriceUnit(0);
		o.setCreatedAt(new Date());
		o.setCreatedBy(1);
		o.setUpdatedAt(new Date());
		o.setUpdatedBy(1);
		o.setSellerId(1);
		o.setBuyerId(1);
		
		orderBO.addOrder(o);
		assert(o.getId()>0):"生成订单失败";
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");
		o.setOrderNo(sdf.format(new Date())+o.getId());
		Integer stat = orderBO.insOrderNoById(o);
		assert(stat==1):"生成订单编号失败";
//		logger.info("-------------生成订单 测试结束: 受影响行数："+stat +" 订单编号："+o.getOrderNo());
	}
	
	//查询手机号
	@Test
	public void queryCustomerByPhone(){
		logger.info("-------------开始查询手机号测试: ---------");
		Customer cus =  purchaseBo.queryCustomer("13585824525");
		assert(cus!=null):"查询结果为空";
//		logger.info("-------------查询手机号 测试结束:  查询结果 客户名称："+cus.getName());
	}
}
