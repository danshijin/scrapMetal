package com.smm.scrapMetal.bo;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.ChatRecords;
import com.smm.scrapMetal.domain.Chats;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Order;

/**
* @author  zhaoyutao
* @version 2016年2月18日 上午9:03:04
* 类说明
*/

public interface IChatsBO {
	Map<String, Object> getChatsList(int start, int len);
	
	Customer getCustomerInfoByChatId(int chatId);
	
	List<ChatRecords> getLatestMsgByCustomerId(int customerId);
	
	Integer getChatIdByCustomerId(Chats chat);

	int selectCustomerIdByOpenId(String openId);

	Chats insertSelective(Chats chat);

	Chats selectByPrimaryKey(int chatId);

	int updateByPrimaryKeySelective(Chats chat);

	Map<String, Object> getChatIdOfBuyerAndSeller(Order orderInfo);

}
