package com.smm.scrapMetal.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.ITotalChatsBO;
import com.smm.scrapMetal.dao.ITotalChatsDao;
import com.smm.scrapMetal.dto.TotalChatsListView;

/**
* @author  zhaoyutao
* @version 2016年3月2日 上午10:49:06
* 类说明
*/

@Service
public class TotalChatsBO implements ITotalChatsBO{
	
	@Resource
	ITotalChatsDao iTotalChatsDao;
	
	@Override
	public Map<String, Object> getChatsList(int start, int len) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		len = len == 0 ? 10 : len; 
		List<TotalChatsListView> list = iTotalChatsDao.getList(start, len);
		
		int total = iTotalChatsDao.countAll();
		
		map.put("rows", list);
		
		map.put("total", total);
		
		return map;
	}
	
	@Override
	public List<TotalChatsListView> ExporChatList(String ids) {
		return iTotalChatsDao.ExporChatList(ids);
	}

	@Override
	public int deleteByIds(String ids) {
		return iTotalChatsDao.deleteByIds(ids);
	}
}
