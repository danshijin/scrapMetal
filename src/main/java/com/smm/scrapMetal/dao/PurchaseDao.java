package com.smm.scrapMetal.dao;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.PurchaseDomain;
import com.smm.scrapMetal.domain.PurchaseInter;
import com.smm.scrapMetal.domain.PurchaseInterDetail;

/**
 * 采购单
 * @author tantaigen
 *
 */
public interface PurchaseDao {
	/**
	 * 查询采购单列表
	 * @param Purchase
	 * @return
	 */
	public  List<PurchaseDomain> queryPurchase(Map<String, Object> map);
	
	/**
	 * 根据Id查询采购单
	 * @param id
	 * @return
	 */
	public  PurchaseDomain queryPurchaseById(Integer id);
	
	/**
	 * 添加采购单
	 * @param Purchase
	 * @return
	 */
	public Integer addPurchase(PurchaseDomain Purchase);
	
	/**
	 * 修改采购单
	 * @param Purchase
	 * @return
	 */
	public Integer updatePurchase(PurchaseDomain Purchase);
	
	/**
	 * 批量删除
	 * @param array
	 * @return
	 */
	public Integer delPurchase(String[] array );
	
	/**
	 * 批量更新
	 * @param array
	 * @return
	 */
	public Integer batchUpdatePurchase(Map<String ,Object> map);
	
	
	/**
	 * 导出查询
	 * @param array
	 * @return
	 */
	public List<PurchaseDomain>  ExporQueryPurchaseByIds(Map<String, Object> map);
	
	/**
	 * 根据电话查询用户
	 * @param phone
	 * @return
	 */
	public  Customer  queryCustomer(String phone);
	
	/**
	 * 接口列表方法
	 * @param map
	 * @return
	 */
	public  List<PurchaseInter> qeuryPurchaseInter(Map<String, Object> map);
	
	
	/**
	 * 接口查看详情方法
	 * @param map
	 * @return
	 */
	public PurchaseInterDetail  PurchaseInterDetail(Map<String, Object> map);
	
	/**
	 * 模糊搜索最近10个标题
	 * @param infoTitle
	 * @return
	 */
	public String queryInfoTitle(Map<String, Object> map);
	
	

}
