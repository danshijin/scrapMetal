package com.smm.scrapMetal.chats;

import org.springframework.web.socket.WebSocketSession;

/**
* @author  zhaoyutao
* @version 2016年4月6日 上午9:13:39
* 类说明
*/

public interface ChatSession {
	
	WebSocketSession getSession();
}
