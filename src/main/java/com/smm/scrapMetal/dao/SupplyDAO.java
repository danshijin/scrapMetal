package com.smm.scrapMetal.dao;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.Delivery;
import com.smm.scrapMetal.domain.SrchTimes;
import com.smm.scrapMetal.domain.Srchword;
import com.smm.scrapMetal.domain.Supply;
import com.smm.scrapMetal.domain.SupplyImages;
import com.smm.scrapMetal.dto.SupplyListView;

/**
 * 
 * @author hece
 *
 */
public interface SupplyDAO {

	//供货单列表
	List<SupplyListView> supplyList(Map<String,Object> map);
	//添加供货单
	int addSupply(Supply supp);
	//添加供货单
	void addSupplyImages(SupplyImages images);
	//通过ID查询详情
	SupplyListView supplyDetailById(String suppid);
	//批量删除
	void deleteSupplyById(Map<String,Object> map);
	//批量更新
	void updateSupplyById(Map<String,Object> map);
	//导出excel
	//List<SupplyListView> newExcleSupplyList(Map<String,Object> map);
	
	//共有多少赞
	int queryUserZan(String id);
	//是否点赞
	int queryUserWhetherZan(Map<String,Object> map);
	//是否收藏
	int whetherShouCang(Map<String,Object> map);
	//供货单图片
	List<SupplyImages> querySupplyImages(String supplyId);
	
	//供货单列表(接口)
	List<SupplyListView> supplyListAPI(Map<String,Object> map);
	//根据ID查询供货单图片
	SupplyImages suppImg(String supplyId);
	//删除供货单图片
	void deleteSupplyImages(String suppId);
	
	//修改供货单
	void updateSupplyAPI(Supply supp);
	//常用搜索列表
	List<Srchword> commonSearchList();
	//列表模糊查找
	List<SupplyListView> searchSupplyList(String name);
	//通过名称查找
	SrchTimes queryByName(String name);
	void insertSrchTimes(SrchTimes sc);
	void updateSrchTimes(Map<String,Object> map);
	
	/**
	 * 处理定时任务
	 */
	//清空搜索关键字表
	void cleanSrchWord();
	//查询热门搜索前八条数据
	List<SrchTimes> querySrchTimesList();
	//插入搜索关键字表
	void insertSrchWord(Srchword word);
	
	//查询国内的交货方式
	List<Delivery> queryDomestic();
	//查询国外的交货方式
	List<Delivery> queryAbroad();
}
