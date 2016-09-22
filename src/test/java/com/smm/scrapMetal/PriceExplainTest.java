package com.smm.scrapMetal;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.PriceExplainBo;
import com.smm.scrapMetal.domain.PriceExplain;
import com.smm.scrapMetal.util.JSONUtil;

import net.sf.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
//注明测试类运行者
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
//加载spring配置文件
@Transactional
//测试结束后，所有数据库变更将自动回滚
public class PriceExplainTest {
	@Resource
	private PriceExplainBo priceExplainBo;
	private Logger logger = Logger.getLogger(customerTest.class);

	
	/**
	 * 价格说明 国内
	 * @return
	 */
	@Test
	public void queryHomePriceExplain(){
		logger.info("价格说明国内查询测试开始》》》》》》》》》");
		try {
		List<PriceExplain>	 list=priceExplainBo.queryHomePriceExplain();
		System.out.println(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		logger.info("价格说明国内查询测试结束》》》》》》》》》");
	};
	
	/**
	 * 价格说明 国外
	 * @return
	 */
	@Test
	public void queryNotHomePriceExplain(){
		
		logger.info("价格说明国外查询测试开始》》》》》》》》》");
		try {
			List<PriceExplain>	 list=priceExplainBo.queryNotHomePriceExplain();
			System.out.println(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		logger.info("价格说明国外查询测试结束》》》》》》》》》");
	};
	
	/**
	 * 价格说明 根据ID查询
	 * @param id
	 * @return
	 */
	@Test
	public void queryPriceExplainById(){
		logger.info("价格说明 根据ID查询测试开始》》》》》》》》》");
		try {
			PriceExplain priceExplain=priceExplainBo.queryPriceExplainById(1);
			System.out.println(JSONArray.fromObject(priceExplain).toString());
			Assert.assertEquals(priceExplain.getName(),"不含税");
	
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		logger.info("价格说明 根据ID查询测试结束》》》》》》》》》");
	};

	
	//-----------------------以下为 袁猛 的测试 -----------------------------
	
	/**
     * 价格说明列表
     */
	@Test
	public void getPriceExplainList(){
		try {
			List<PriceExplain> priceExplainList = priceExplainBo.queryAll();
			System.out.println(priceExplainList.size());
            System.out.println(JSONUtil.doConvertObject2Json(priceExplainList).toString());
            Assert.assertNotNull(priceExplainList);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
     * 添加价格说明
     */
	@Test
	public void addPriceExplain(){
		PriceExplain priceExplain = new PriceExplain();
		priceExplain.setName("abc");
		priceExplain.setIsDomestic(0);
        int count = priceExplainBo.addPriceExplain(priceExplain);
        Assert.assertEquals(count,1);
	}
	
	/**
     * 删除价格说明
     */
	@Test
	public void delPriceExplain(){
        int id = 1;
        int count = priceExplainBo.delPriceExplain(id);
        Assert.assertEquals(count,1);
	}
}
