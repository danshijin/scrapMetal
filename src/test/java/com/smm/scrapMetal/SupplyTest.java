package com.smm.scrapMetal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.SupplyBO;
import com.smm.scrapMetal.domain.Delivery;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.dto.SupplyListView;
import com.smm.scrapMetal.util.DateUtil;

/**
 * 
 * @author hece
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//注明测试类运行者
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
//加载spring配置文件
@Transactional
//测试结束后，所有数据库变更将自动回滚
public class SupplyTest {

	private Logger logger = Logger.getLogger(SupplyTest.class);
	@Resource
	private SupplyBO suppBo;
	
	/**
	 * 供货单列表(后台)
	 */
	public void supplyListTest(){
		logger.info("供货单列表(后台)------------------开始");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("status", "");
			map.put("type", "");
			map.put("recommend", "");
			map.put("prov", "");
			map.put("city", "");
			map.put("attribute", "");
			map.put("startTime", "");
			map.put("endTime", "");
			map.put("itemid", "");
			map.put("pno", 1);
			Map<String,Object> suppList = suppBo.supplyList(map);
			System.out.println("供货单列表="+suppList);
		} catch (Exception e) {
			logger.info("供货单列表(后台)------------------异常");
			e.printStackTrace();
		}
		logger.info("供货单列表(后台)------------------成功");
	}
	
	/**
	 * 添加供货单(后台)
	 */
	public void addSupplyTest(){
		logger.info("添加供货单(后台)------------------开始");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("itemid", 1);
			map.put("prov", 18);
			map.put("city", 204);
			map.put("infoTitle", "测试666");
			map.put("description", "测试999");
			map.put("usableQuantity", 1);
			//map.put("priceNegotiable", 0);
			map.put("delivery", 1);
			map.put("expiryType", 0);
			map.put("priceUnit", 0);
			map.put("priceExplain", 1);
			map.put("phone", "15604455783");
			map.put("price", 100);
			//map.put("imageAddress", "测试007");
	        int suppAdd = suppBo.addSupply(map, null);
			System.out.println("添加供货单(后台)="+suppAdd);
		} catch (Exception e) {
			logger.info("添加供货单(后台)------------------异常");
			e.printStackTrace();
		}
		logger.info("添加供货单(后台)------------------成功");
	}
	
	/**
	 * 通过ID查询详情(后台)
	 */
	public void supplyDetailByIdTest(){
		logger.info("通过ID查询详情(后台)------------------开始");
		try {
			SupplyListView suppDetail = suppBo.supplyDetailById("52");
			System.out.println("通过ID查询详情="+suppDetail);
		} catch (Exception e) {
			logger.info("通过ID查询详情(后台)------------------异常");
			e.printStackTrace();
		}
		logger.info("通过ID查询详情(后台)------------------成功");
	}
	
	/**
	 * 查询国内的交货方式(后台)
	 */
	public void queryDomesticTest(){
		logger.info("查询国内的交货方式(后台)------------------开始");
		try {
			List<Delivery> del = suppBo.queryDomestic();
			System.out.println("查询国内的交货方式="+del);
		} catch (Exception e) {
			logger.info("查询国内的交货方式(后台)------------------异常");
			e.printStackTrace();
		}
		logger.info("查询国内的交货方式(后台)------------------成功");
	}
	
	/**
	 * 查询国外的交货方式(后台)
	 */
	public void queryAbroadTest(){
		logger.info("查询国外的交货方式(后台)------------------开始");
		try {
			List<Delivery> del = suppBo.queryAbroad();
			System.out.println("查询国外的交货方式="+del);
		} catch (Exception e) {
			logger.info("查询国外的交货方式(后台)------------------异常");
			e.printStackTrace();
		}
		logger.info("查询国外的交货方式(后台)------------------成功");
	}
	
	/**
	 * 批量删除(后台)
	 */
	public void deleteSupplyByIdTest(){
		logger.info("批量删除(后台)------------------开始");
		try {
			suppBo.deleteSupplyById("52");
		} catch (Exception e) {
			logger.info("批量删除(后台)------------------异常");
			e.printStackTrace();
		}
		logger.info("批量删除(后台)------------------成功");
	}
	
	/**
	 * 批量更新(后台)
	 */
	public void updateSupplyByIdTest(){
		logger.info("批量更新(后台)------------------开始");
		try {
			suppBo.updateSupplyById("52", "", new DateUtil().currentDate());
		} catch (Exception e) {
			logger.info("批量更新(后台)------------------异常");
			e.printStackTrace();
		}
		logger.info("批量更新(后台)------------------成功");
	}
	
	/**
	 * 导出excel查询(后台)
	 */
	public void newExcleSupplyListTest(){
		logger.info("导出excel查询(后台)------------------开始");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("currentPage", 1);
			map.put("pageNum", 10);
			List<SupplyListView> suppList = suppBo.newExcleSupplyList(map);
			System.out.println("导出excel查询="+suppList);
		} catch (Exception e) {
			logger.info("导出excel查询(后台)------------------异常");
			e.printStackTrace();
		}
		logger.info("导出excel查询(后台)------------------成功");
	}
	
	/**
	 * 供货单列表(接口)
	 */
	public void supplyListAPITest(){
		logger.info("供货单列表(接口)------------------开始");
		try {
			JSONObject obj = new JSONObject();
	        obj.put("currentPage", 1);
	        obj.put("pageNum", 10);
	        ResultData suppList = suppBo.supplyListAPI(obj.toString());
			System.out.println("供货单列表(接口)="+suppList);
		} catch (Exception e) {
			logger.info("供货单列表(接口)------------------异常");
			e.printStackTrace();
		}
		logger.info("供货单列表(接口)------------------成功");
	}
	
	/**
	 * 通过ID查询详情(接口)
	 */
	public void supplyDetailByIdAPITest(){
		logger.info("通过ID查询详情(接口)------------------开始");
		try {
			JSONObject obj = new JSONObject();
	        obj.put("supplyId", 52);
	        ResultData suppDetail = suppBo.supplyDetailByIdAPI(obj.toString());
			System.out.println("通过ID查询详情(接口)="+suppDetail);
		} catch (Exception e) {
			logger.info("通过ID查询详情(接口)------------------异常");
			e.printStackTrace();
		}
		logger.info("通过ID查询详情(接口)------------------成功");
	}
	
	/**
	 * 添加供货单(接口)
	 */
	public void addSupplyAPITest(){
		logger.info("添加供货单(接口)------------------开始");
		try {
			JSONObject obj = new JSONObject();
	        obj.put("itemId", 1);
	        obj.put("goodsProy", 18);
	        obj.put("goodsCity", 204);
	        obj.put("infoTitle", "测试666");
	        obj.put("description", "测试999");
	        obj.put("quantity", 1);
	        obj.put("priceNegotiable", 0);
	        obj.put("delivery", 1);
	        obj.put("expiryType", 0);
	        obj.put("priceUnit", 0);
	        obj.put("priceExplain", 1);
	        obj.put("phone", "15604455783");
	        obj.put("price", 100);
	        obj.put("imageAddress", "测试007");
	        ResultData suppAdd = suppBo.addSupplyAPI(obj.toString());
			System.out.println("添加供货单(接口)="+suppAdd);
		} catch (Exception e) {
			logger.info("添加供货单(接口)------------------异常");
			e.printStackTrace();
		}
		logger.info("添加供货单(接口)------------------成功");
	}
	
	/**
	 * 修改供货单(接口)
	 */
	public void updateSupplyAPITest(){
		logger.info("修改供货单(接口)------------------开始");
		try {
			JSONObject obj = new JSONObject();
	        obj.put("supplyId", 52);
	        obj.put("infoTitle", "测试007");
	        ResultData suppUpdte = suppBo.updateSupplyAPI(obj.toString());
			System.out.println("修改供货单(接口)="+suppUpdte);
		} catch (Exception e) {
			logger.info("修改供货单(接口)------------------异常");
			e.printStackTrace();
		}
		logger.info("修改供货单(接口)------------------成功");
	}
	
	/**
	 * 常用搜索列表(接口)
	 */
	public void commonSearchListTest(){
		logger.info("常用搜索列表(接口)------------------开始");
		try {
	        ResultData result = suppBo.commonSearchList();
			System.out.println("常用搜索列表(接口)="+result);
		} catch (Exception e) {
			logger.info("常用搜索列表(接口)------------------异常");
			e.printStackTrace();
		}
		logger.info("常用搜索列表(接口)------------------成功");
	}
	
	/**
	 * 列表模糊查找(接口)
	 */
	public void searchSupplyListTest(){
		logger.info("列表模糊查找(接口)------------------开始");
		try {
	        ResultData result = suppBo.searchSupplyList("测试");
			System.out.println("列表模糊查找(接口)="+result);
		} catch (Exception e) {
			logger.info("列表模糊查找(接口)------------------异常");
			e.printStackTrace();
		}
		logger.info("列表模糊查找(接口)------------------成功");
	}
	
	/**
	 * 模糊查找通过title(接口)
	 */
	public void searchSupplyListByTitleTest(){
		logger.info("模糊查找通过title(接口)------------------开始");
		try {
	        ResultData result = suppBo.searchSupplyListByTitle("测试");
			System.out.println("模糊查找通过title(接口)="+result);
		} catch (Exception e) {
			logger.info("模糊查找通过title(接口)------------------异常");
			e.printStackTrace();
		}
		logger.info("模糊查找通过title(接口)------------------成功");
	}
}