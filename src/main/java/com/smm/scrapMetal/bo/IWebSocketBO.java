package com.smm.scrapMetal.bo;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.smm.scrapMetal.dto.ChatRemindDto;

/**
* @author  zhaoyutao
* @version 2016年2月18日 上午9:26:00
* 类说明
*/

public interface IWebSocketBO {

	/**
	 * 客户队列 key：客户id
	 */
	public final static ConcurrentMap<Integer, WebSocketSession> customerSessionMap = new ConcurrentHashMap<>();
	/**
	 * 撮合员队列 key：撮合员id
	 */
	public final static ConcurrentMap<Integer, WebSocketSession> employeeSessionMap = new ConcurrentHashMap<>();
	
	/**
	 * 未读消息列表
	 */
	public final static Vector<ChatRemindDto> notReadVector = new Vector<>();
	
	void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception;

	void afterConnectionEstablished(WebSocketSession session);

	void afterConnectionClosed(WebSocketSession session, CloseStatus status);

	int setIsReadByCustomerId(int customerId);
	
}
