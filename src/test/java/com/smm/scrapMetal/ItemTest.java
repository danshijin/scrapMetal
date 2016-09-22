/**
 * 
 */
package com.smm.scrapMetal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.IItemBo;
import com.smm.scrapMetal.domain.Item;

/**
 * ItemTest
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年2月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)  //注明测试类运行者
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@Transactional  //测试结束后，所有数据库变更将自动回滚
public class ItemTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IItemBo iItemBo;
	
    /**
     * 添加品类
     */
	@Test
	public void addItem(){
		Item item = new Item();
		item.setName("Y/N");
        int count = iItemBo.addItem(item);
        Assert.assertEquals(count,1);
	}
	
	/**
     * 删除品类
     */
	@Test
	public void delItem(){
        int id = 1;
        int count = iItemBo.delItem(id);
        Assert.assertEquals(count,1);
	}
	
}
