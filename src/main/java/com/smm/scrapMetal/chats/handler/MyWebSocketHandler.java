package com.smm.scrapMetal.chats.handler;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.smm.scrapMetal.bo.IWebSocketBO;
import com.smm.scrapMetal.chats.ChatCloseStatus;

/**
* @author  zhaoyutao
* @version 2016年1月26日 下午5:38:11
* 
* 用于处理websocket连接建立成功、关闭，消息接收
*/
public class MyWebSocketHandler extends TextWebSocketHandler {
	
	private Logger logger = Logger.getLogger(MyWebSocketHandler.class);
	
	@Resource
	IWebSocketBO iWebSocketBO;
	
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
		
		try {
			iWebSocketBO.handleTextMessage(session, message);
		} catch (JsonParseException e) {
			e.printStackTrace();
			logger.error("json转换出错", e);
		} catch (Exception e) {
			try {
				session.close(ChatCloseStatus.ERROR_ON_SEND_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("转发消息出错", e);
		}
    }
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		iWebSocketBO.afterConnectionEstablished(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		iWebSocketBO.afterConnectionClosed(session, status);
	}
}
