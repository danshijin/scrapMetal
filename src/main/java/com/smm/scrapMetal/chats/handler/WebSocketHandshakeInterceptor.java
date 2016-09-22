package com.smm.scrapMetal.chats.handler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dao.IChatsDao;

/**
* @author  zhaoyutao
* @version 2016年1月26日 下午7:30:12
* 通过http协议握手，升级协议为websocket
*/

public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	IChatsDao iChatsDao;
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		logger.info("Before Handshake");
		try{
			// 将创建会话时传入的参数写入session中
			String query = request.getURI().getQuery();
			if(StringUtils.isNotBlank(query)){
				MultiMap<String> values = new MultiMap<String>();
				UrlEncoded.decodeTo(query, values, "utf-8");
				for(Entry<String, List<String>> entry : values.entrySet()){
					attributes.put(entry.getKey(), entry.getValue().get(0));
				}
			}
			
			String chatInitiator = (String) attributes.get("chatInitiator");
			if("C".equalsIgnoreCase(chatInitiator)){ // 用户
				String openId = (String) attributes.get("openId");
				Integer customerId = iChatsDao.selectCustomerIdByOpenId(openId);
				if(customerId == null) {
					logger.info("握手时，传入openId：" + openId + "，不存在。");
					return false;
				}
				attributes.put("customerId", customerId);
			}
			
			logger.info("##拦截器获取到的参数为：" + attributes);
			return super.beforeHandshake(request, response, wsHandler, attributes);
		} catch (Exception ex){
			logger.error(ResErrorCode.PARAMS_NULL_ERROR_MSG, ex);
		}
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		logger.info("After Handshake");
		super.afterHandshake(request, response, wsHandler, ex);
	}
}
