package com.smm.scrapMetal.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.IChatRecordsBO;
import com.smm.scrapMetal.dao.IChatRecordsDao;
import com.smm.scrapMetal.domain.ChatRecords;

/**
* @author  zhaoyutao
* @version 2016年2月18日 上午9:47:47
* 类说明
*/

@Service
public class ChatRecordsBO implements IChatRecordsBO{
	
	@Resource
	IChatRecordsDao iChatRecordsDao;
	
	@Override
	public List<ChatRecords> getLatestMsgByCustomerId(int customerId){
		return iChatRecordsDao.getLatestMsgByCustomerId(customerId);
	}
	
	@Override
	public ChatRecords insertSelective(ChatRecords record){
		iChatRecordsDao.insert(record);
		return record;
	}
}
