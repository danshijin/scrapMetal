package com.smm.scrapMetal.bo;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.smm.scrapMetal.domain.Delivery;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.dto.SupplyListView;

/**
 * 
 * @author hece
 *
 */
public interface SupplyBO {

	//供货单列表
	Map<String,Object> supplyList(Map<String,Object> map);
	//添加供货单
	int addSupply(Map<String,Object> map,HttpServletRequest request);
	//通过ID查询详情
	SupplyListView supplyDetailById(String suppid);
	//查询国内的交货方式
	List<Delivery> queryDomestic();
	//查询国外的交货方式
	List<Delivery> queryAbroad();
	
	//修改供货单
	int updateSupply(HttpServletRequest request);
	//批量删除
	void deleteSupplyById(String id);
	//批量更新
	void updateSupplyById(String id,String status,String date);
	//导出excel
	List<SupplyListView> newExcleSupplyList(Map<String,Object> map);
	
	//供货单列表(接口)
	ResultData supplyListAPI(String result);
	//通过ID查询详情(接口)
	ResultData supplyDetailByIdAPI(String paramsStr);
	//添加供货单
	ResultData addSupplyAPI(String paramsStr);
	//修改供货单
	ResultData updateSupplyAPI(String paramsStr);
	//常用搜索列表
	ResultData commonSearchList();
	//列表模糊查找
	ResultData searchSupplyList(String paramsStr);
	//模糊查找通过title
	ResultData searchSupplyListByTitle(String paramsStr);
}
