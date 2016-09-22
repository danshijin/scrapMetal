/**
 * 
 */
package com.smm.scrapMetal;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.IDeliveryBo;
import com.smm.scrapMetal.domain.Delivery;
import com.smm.scrapMetal.util.JSONUtil;

/**
 * DeliveryTest
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年2月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)  //注明测试类运行者
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@Transactional  //测试结束后，所有数据库变更将自动回滚
public class DeliveryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IDeliveryBo iDeliveryBo;
	
    /**
     * 交货方式列表
     */
	@Test
	public void getDeliveryList(){
		try {
			List<Delivery> deliveryList = iDeliveryBo.queryAll();
			System.out.println(deliveryList.size());
            System.out.println(JSONUtil.doConvertObject2Json(deliveryList).toString());
            Assert.assertNotNull(deliveryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
     * 添加交货方式
     */
	@Test
	public void addDelivery(){
		Delivery delivery = new Delivery();
		delivery.setName("abc");
		delivery.setIsDomestic(0);
        int count = iDeliveryBo.addDelivery(delivery);
        Assert.assertEquals(count,1);
	}
	
	/**
     * 删除交货方式
     */
	@Test
	public void delDelivery(){
        int id = 1;
        int count = iDeliveryBo.delDelivery(id);
        Assert.assertEquals(count,1);
	}
	
}
