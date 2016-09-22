package com.smm.scrapMetal.util;

import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import com.smm.scrapMetal.dao.SupplyDAO;
import com.smm.scrapMetal.domain.SrchTimes;
import com.smm.scrapMetal.domain.Srchword;

/**
 * 
 * @author hece
 *	
 */

public class MarketJob {
	
	Logger logger = Logger.getLogger(MarketJob.class);
	@Resource
	private SupplyDAO supplyDao;
	
	/**
	 * 处理定时任务
	 */
	public void handleSearch() {
		//清空搜索关键字表
		supplyDao.cleanSrchWord();
		//查询热门搜索前八条数据
		List<SrchTimes> srch = supplyDao.querySrchTimesList();
		for(int i = 0;i < srch.size();i++){
			SrchTimes sc = srch.get(i);
			if(sc != null){
				Srchword word = new Srchword();
				word.setName(sc.getName());
				word.setSrchOrder(i);
				//插入搜索关键字表
				supplyDao.insertSrchWord(word);
			}
		}
	}
}