package com.smm.scrapMetal.chats;

import org.springframework.web.socket.WebSocketSession;

/**
* @author  zhaoyutao
* @version 2016年3月29日 下午3:30:43
* 撮合员聊天实体
*/

public class EmployeeChatSession implements ChatSession {
	
	private WebSocketSession session;
	
	private int employeeId;
	
	private int currChatId;
	
	private int currCustomerId;
	
	public EmployeeChatSession(WebSocketSession session) {
		this.session = session;
		currChatId = 0;
		currCustomerId = 0;
	}
	
	@Override
	public WebSocketSession getSession() {
		return session;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getCurrChatId() {
		return currChatId;
	}

	public void setCurrChatId(int currChatId) {
		this.currChatId = currChatId;
	}

	public int getCurrCustomerId() {
		return currCustomerId;
	}

	public void setCurrCustomerId(int currCustomerId) {
		this.currCustomerId = currCustomerId;
	}
}
