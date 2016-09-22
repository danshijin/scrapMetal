package com.smm.scrapMetal.chats;

import org.springframework.web.socket.WebSocketSession;

/**
* @author  zhaoyutao
* @version 2016年3月29日 下午3:30:43
* 类说明
*/

public class CustomerChatSession implements ChatSession {
	
	private WebSocketSession session;
	
	private int customerId;
	
	private int chatId;
	
	private int source;
	
	private int sourceId;
	
	public CustomerChatSession(WebSocketSession session, int customerId, int chatId){
		this.session = session;
		this.customerId = customerId;
		this.chatId = chatId;
	}

	@Override
	public WebSocketSession getSession() {
		return session;
	}

	public int getChatId() {
		return chatId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	
}
