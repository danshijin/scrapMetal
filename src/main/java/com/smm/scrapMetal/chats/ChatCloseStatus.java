package com.smm.scrapMetal.chats;

import org.springframework.web.socket.CloseStatus;

/**
* CloseStatus中的code : 4000-4999范围内的状态码保留用于私有使用且因此不能被注册。这些状态码可以被在WebSocket应用之间的先前的协议使用。本规范未定义这些状态码的解释。
* @author  zhaoyutao
* @version 2016年4月15日 下午3:25:04
*/

public final class ChatCloseStatus {
	public static final CloseStatus HTTP_SESSION_EXPIRED = new CloseStatus(4000, "httpSession 过期");
	public static final CloseStatus ERROR_ON_SEND_MESSAGE = new CloseStatus(4001, "转发消息是出错");
	public static final CloseStatus USELESS_CONNECT = new CloseStatus(4002, "客户发起连接时，已有的连接未正常关闭");
	public static final CloseStatus ERROR_AFTER_CONNECTION_ESTABLISHED = new CloseStatus(4003, "连接建立成功后出现异常");
	
}
