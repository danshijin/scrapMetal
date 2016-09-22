package com.smm.scrapMetal.afterContextRefreshed;

import java.io.IOException;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.web.socket.WebSocketSession;

import com.smm.scrapMetal.bo.IWebSocketBO;
import com.smm.scrapMetal.chats.ChatCloseStatus;
import com.smm.scrapMetal.domain.User;

/**
* @author  zhaoyutao
* @version 2016年3月15日 上午11:22:42
* 类说明
*/

public class MyHttpSessionListener implements HttpSessionListener{

	private Logger logger = Logger.getLogger(MyHttpSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		User user = (User) se.getSession().getAttribute("userInfo");
		WebSocketSession session = IWebSocketBO.employeeSessionMap.get(user.getId());
		try {
			session.close(ChatCloseStatus.HTTP_SESSION_EXPIRED);
		} catch (IOException e) {
			logger.error("关闭过期http session中的websocket连接出错", e);
			e.printStackTrace();
		}
	}

}
