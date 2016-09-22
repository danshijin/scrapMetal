package com.smm.scrapMetal.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.PriceExplainBo;
import com.smm.scrapMetal.dao.PriceExplainDao;
import com.smm.scrapMetal.domain.PriceExplain;

@Service
public class PriceExplainImol  implements PriceExplainBo{
	@Resource
	private PriceExplainDao dao;
	@Override
	public List<PriceExplain> queryHomePriceExplain() {
		// TODO Auto-generated method stub
		return dao.queryHomePriceExplain();
	}

	@Override
	public List<PriceExplain> queryNotHomePriceExplain() {
		// TODO Auto-generated method stub
		return dao.queryNotHomePriceExplain();
	}

	@Override
	public PriceExplain queryPriceExplainById(Integer id) {
		// TODO Auto-generated method stub
		return dao.queryPriceExplainById(id);
	}

	@Override
	public int addPriceExplain(PriceExplain priceExplain) {
		// TODO Auto-generated method stub
		return dao.addPriceExplain(priceExplain);
	}

	@Override
	public int delPriceExplain(Integer id) {
		// TODO Auto-generated method stub
		return dao.delPriceExplain(id);
	}

	@Override
	public List<PriceExplain> queryAll() {
		return dao.queryAll();
	}

}
