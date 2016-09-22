package com.smm.scrapMetal.dao;

import java.util.List;

import com.smm.scrapMetal.domain.PriceExplain;

public interface PriceExplainDao {
	
	/**
	 * 价格说明 国内
	 * @return
	 */
	public List<PriceExplain> queryHomePriceExplain();
	
	/**
	 * 价格说明 国外
	 * @return
	 */
	public List<PriceExplain> queryNotHomePriceExplain();
	
	/**
	 * 价格说明 根据ID查询
	 * @param id
	 * @return
	 */
	public PriceExplain queryPriceExplainById(Integer id);
	
	public List<PriceExplain> queryAll();
	
	public int addPriceExplain(PriceExplain priceExplain);
	
	public int delPriceExplain(Integer id);

}
