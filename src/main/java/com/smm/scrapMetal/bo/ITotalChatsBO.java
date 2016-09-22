package com.smm.scrapMetal.bo;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.dto.ChatsListView;
import com.smm.scrapMetal.dto.TotalChatsListView;

/**
* @author  zhaoyutao
* @version 2016年3月2日 上午10:48:46
* 类说明
*/

public interface ITotalChatsBO {

	Map<String, Object> getChatsList(int start, int len);

	List<TotalChatsListView> ExporChatList(String ids);

	int deleteByIds(String ids);

}
