package com.smm.scrapMetal.bo;

import java.util.List;

import com.smm.scrapMetal.domain.ChatRecords;

/**
* @author  zhaoyutao
* @version 2016年2月18日 上午9:47:37
* 类说明
*/

public interface IChatRecordsBO {

	List<ChatRecords> getLatestMsgByCustomerId(int customerId);

	ChatRecords insertSelective(ChatRecords record);
	
}
