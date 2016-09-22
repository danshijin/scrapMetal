package com.smm.scrapMetal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.smm.scrapMetal.bo.IWebSocketBO;
import com.smm.scrapMetal.chats.ChatCloseStatus;

/**
* @author  zhaoyutao
* @version 2016年1月25日 下午8:49:37
* 类说明
*/
@Controller
@RequestMapping("myWebSocket")
public class WebsocketController {
	
	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("chats/customerTest");
	}
	
	@RequestMapping("employeeTest")
	public ModelAndView employeeTest(){
		return new ModelAndView("chats/employeeTest");
	}
	
	@RequestMapping("sendMessage")
	@ResponseBody
	public Map<String, Object> sendMessage() throws Exception{
		for(Entry<Integer,WebSocketSession> entry : IWebSocketBO.employeeSessionMap.entrySet()){
			WebSocketSession session = entry.getValue();
			try{
				session.sendMessage(new TextMessage("你好啊, " + entry.getKey() + "   "));
			} catch(IOException ex) {
				session.close(ChatCloseStatus.ERROR_ON_SEND_MESSAGE);
			}
		}
		return new HashMap<String, Object>();
	}
}
