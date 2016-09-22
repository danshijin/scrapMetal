package com.smm.scrapMetal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.PurchaseBo;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.PurchaseDomain;
import com.smm.scrapMetal.domain.PurchaseInter;
import com.smm.scrapMetal.domain.PurchaseInterDetail;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.util.JSONUtil;

import net.sf.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
// 注明测试类运行者
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
// 加载spring配置文件
@Transactional
// 测试结束后，所有数据库变更将自动回滚
public class PurchaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	private Logger logger = Logger.getLogger(customerTest.class);

	@Resource
	private PurchaseBo purchaseBo;
	/**
	 * 采购单列表
	 * 
	 */
	@Test
	public void queryPurchase() {
		logger.info("采购单列表测试开始》》》》》》》》》》》》》》》》");
		Map<String, Object> map = new HashMap<>();
		map.put("nameType", null);
		map.put("liekname", null);
		map.put("goodsProv", null);
		map.put("goodsCity", null);
		map.put("dateType", null);
		map.put("startDate", null);
		map.put("endDate", null);
		map.put("infoStatus", null);
		map.put("startNum", 0);
		map.put("endNum", 10);
		try {
			List<PurchaseDomain> list = purchaseBo.queryPurchase(map);
			
			if(list!=null){
			System.out.println(JSONArray.fromObject(list).toString());
			Assert.assertNotNull(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("采购单列表测试异常》》》》》》》》》》》》》》》》");
		}
		logger.info("采购单列表测试完成》》》》》》》》》》》》》》》》");
	}

	/**
	 * 采购单详情
	 */
	@Test
	public void queryPurchaseById() {
		logger.info("采购单详情测试开始》》》》》》》》》》》");
		try {
			PurchaseDomain purchase = purchaseBo.queryPurchaseById(2);
			if(purchase!=null){
			System.out.println(JSONArray.fromObject(purchase).toString());
			Assert.assertNotNull(purchase);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("采购单详情测试异常》》》》》》》》》》》》》》》》");
		}
		logger.info("采购单详情测试完成》》》》》》》》》》》》》》》》");
	};

	/**
	 * 添加采购单
	 */
	@Test
	public void addPurchase() {
		logger.info("添加采购单测试开始》》》》》》》》");
		PurchaseDomain purchase = new PurchaseDomain();
		try {

			purchase.setInfoTitle("测试采购单添加");
			purchase.setItemId(1);
			purchase.setDescription("测试采购单添加");
			purchase.setGoodsProv(1);
			purchase.setGoodsCity(1);
			purchase.setCreatedAt(new Date());
			purchase.setUpdatedAt(new Date());
			purchase.setCreatedBy(1);
			purchase.setUpdatedBy(1);
			purchase.setCustomerId(1);
			purchase.setExpectPrice(new BigDecimal("12.22"));
			purchase.setUnit(0);
			purchase.setQuantity(new BigDecimal("15.22"));
			purchase.setPhone("18221396165");
			purchase.setExpectPriceUnit(1);
			purchase.setExpiryType(1);
			purchase.setPriceExplain(1);
			purchase.setInfoStatus(0);
			purchase.setPriceNegotiable(1);
			purchaseBo.addPurchase(purchase);
			//Assert.assertEquals(a+"",1+"");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("添加采购单测试异常》》》》》》》》");
		}

		logger.info("添加采购单测试结束》》》》》》》》");
	};

	/**
	 * 修改采购单
	 */
	@Test
	public void updatePurchase() {
		logger.info("修改采购单测试开始》》》》》》》》");
		PurchaseDomain purchase = new PurchaseDomain();
		try {
			purchase.setId(3);
			purchase.setInfoTitle("测试采购单修改");
			purchase.setItemId(1);
			purchase.setDescription("测试采购单修改");
			purchase.setGoodsProv(1);
			purchase.setGoodsCity(1);
			purchase.setCreatedAt(new Date());
			purchase.setUpdatedAt(new Date());
			purchase.setCreatedBy(1);
			purchase.setUpdatedBy(1);
			purchase.setCustomerId(1);
			purchase.setExpectPrice(new BigDecimal("12.22"));
			purchase.setUnit(0);
			purchase.setQuantity(new BigDecimal("15.22"));
			purchase.setPhone("18221396165");
			purchase.setExpectPriceUnit(1);
			purchase.setExpiryType(1);
			purchase.setPriceExplain(1);
			purchase.setInfoStatus(0);
			purchase.setPriceNegotiable(1);
			purchaseBo.updatePurchase(purchase);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("修改采购单测试异常》》》》》》》》");
		}

		logger.info("修改采购单测试结束》》》》》》》》");
	};

	/**
	 * 删除采购单测试
	 */
	@Test
	public void delPurchase() {
		logger.info("批量删除采购单测试开始》》》》》》》》");
		String[] array = { "5", "6", "7" };
		try {
			int a = purchaseBo.delPurchase(array);
			System.out.println(a + "》》》》》》》》》》》》》》");
			//Assert.assertEquals(a+"",3+"");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("批量删除采购单测试异常》》》》》》》》");
		}
		logger.info("批量删除采购单测试结束》》》》》》》》");
	}

	/**
	 * 更新创建时间采购单测试
	 */
	@Test
	public void batchUpdatePurchase() {
		logger.info("更新创建时间采购单测试开始》》》》》》》》");
		String[] array = { "5", "6", "7" };
		try {
			Map<String ,Object> map1 =new HashMap<>();

			map1.put("array", array);
			map1.put("updatedAt", new Date());
			purchaseBo.batchUpdatePurchase(map1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("更新创建时间采购单测试异常》》》》》》》》");
		}
		logger.info("更新创建时间采购单测试结束》》》》》》》》");
	}

	/**
	 * 导出
	 */
	@Test
	public void ExporQueryPurchaseByIds() {
		logger.info("导出采购单测试开始》》》》》》》》");
		String[] array = { "5", "6", "7" };
		
		try {
			Map<String, Object> map=new HashMap<>();
			map.put("array", array);
			purchaseBo.ExporQueryPurchaseByIds(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("导出采购单测试异常》》》》》》》》");
		}
		logger.info("导出采购单测试结束》》》》》》》》");

	}

	/**
	 * 根据电话查询公司
	 */
	@Test
	public void queryCustomer() {
		logger.info("根据电话查询公司测试开始》》》》》》》》》》》");
		try {
			Customer customer = purchaseBo.queryCustomer("18221396165");
			if(customer!=null){
			System.out.println(JSONArray.fromObject(customer).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("根据电话查询公司测试异常》》》》》》》》》》》》》》》》");
		}
		logger.info("根据电话查询公司测试完成》》》》》》》》》》》》》》》》");
	}

	/**
	 * 采购单列表（接口）
	 * 
	 */
	@Test
	public void qeuryPurchaseInter() {
		logger.info("采购单列表(接口)测试开始》》》》》》》》》》》》》》》》");
		Map<String, Object> map = new HashMap<>();
		map.put("nameType", null);
		map.put("liekname", null);
		map.put("goodsProv", null);
		map.put("goodsCity", null);
		map.put("dateType", null);
		map.put("startDate", null);
		map.put("endDate", null);
		map.put("infoStatus", null);
		map.put("startNum", 0);
		map.put("endNum", 10);

		try {
			List<PurchaseInter> list = purchaseBo.qeuryPurchaseInter(map);
			if(list!=null){
			System.out.println(JSONArray.fromObject(list).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("采购单列表(接口)表测试异常》》》》》》》》》》》》》》》》");
		}
		logger.info("采购单列表(接口)测试完成》》》》》》》》》》》》》》》》");
	}

	/**
	 * 采购单详情(接口)
	 */
	@Test
	public void PurchaseInterDetail() {
		logger.info("采购单详情(接口)测试开始》》》》》》》》》》》");
		try {
			Map<String, Object> map = new HashMap<>();
			PurchaseInterDetail purchase = purchaseBo.PurchaseInterDetail(map);
			if(purchase!=null){
			System.out.println(JSONArray.fromObject(purchase).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("采购单详情(接口)测试异常》》》》》》》》》》》》》》》》");
		}
		logger.info("采购单详情(接口)测试完成》》》》》》》》》》》》》》》》");
	};

	/**
	 * 采购添加(接口)
	 */
	@Test
	public void addpPurchase() {
		logger.info("添加采购(接口)测试开始》》》》》》》》");
		Map<String, Object> map = new HashMap<>();
		map.put("itemId", 2);
		map.put("goodsProv", 2);
		map.put("goodsCity", 2);
		map.put("goodsCity", 2);
		map.put("description", "测试测试测试");
		map.put("quantity", "22022.00");
		map.put("infoTitle", "测试添加接口");
		map.put("unit", 2);
		map.put("expectPrice", "2235.00");
		map.put("expectPriceUnit", 2);
		map.put("priceNegotiable", 2);
		map.put("priceExplain", 2);
		map.put("phone", "18717864960");
		map.put("expectPrice", 2);
		map.put("expectPrice", 2);
		map.put("expiryType", 1);
		try {
			ResultData dto = purchaseBo.addpPurchase(JSONUtil.map2json(map).toString());
			if(dto!=null){
			System.out.println(dto.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("添加采购(接口)单测试异常》》》》》》》》");
		}

		logger.info("添加采购(接口)测试结束》》》》》》》》");

	}

	/**
	 * 修改采购单(接口)
	 */
	@Test
	public void updatePurchaseInter() {
		logger.info("修改采购单(接口)测试开始》》》》》》》》");
		Map<String, Object> map = new HashMap<>();
		map.put("id", 2);
		map.put("itemId", 2);
		map.put("goodsProv", 2);
		map.put("goodsCity", 2);
		map.put("goodsCity", 2);
		map.put("description", "测试测试测试");
		map.put("quantity", "22022.00");
		map.put("infoTitle", "修改采购单(接口)");
		map.put("unit", 2);
		map.put("expectPrice", "2235.00");
		map.put("expectPriceUnit", 2);
		map.put("priceNegotiable", 2);
		map.put("priceExplain", 2);
		map.put("phone", "18717864960");
		map.put("expiryType", 1);
		try {
			ResultData dto = purchaseBo.updatePurchaseInter(JSONUtil.map2json(map).toString());
			if(dto!=null){
			System.out.println(dto.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("修改采购单(接口)单测试异常》》》》》》》》");
		}

		logger.info("修改采购单(接口)测试结束》》》》》》》》");

	}
}
