package com.smm.scrapMetal.bo.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.scrapMetal.bo.IWebSocketBO;
import com.smm.scrapMetal.chats.ChatCloseStatus;
import com.smm.scrapMetal.chats.handler.ChatMessageHandle;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dao.IChatRecordsDao;
import com.smm.scrapMetal.dao.IChatsDao;
import com.smm.scrapMetal.dao.ITotalChatsDao;
import com.smm.scrapMetal.dao.PurchaseDao;
import com.smm.scrapMetal.dao.SupplyDAO;
import com.smm.scrapMetal.domain.ChatRecords;
import com.smm.scrapMetal.domain.Chats;
import com.smm.scrapMetal.domain.TotalChats;
import com.smm.scrapMetal.dto.ChatRecordsParamsDto;

/**
* @author  zhaoyutao
* @version 2016年2月18日 上午9:25:08
* 类说明
*/

@Service
public class WebSocketBO implements IWebSocketBO{
	
	@Resource
	IChatsDao iChatDao;
	
	@Resource
	IChatRecordsDao iChatRecordsDao;
	
	@Resource
	ITotalChatsDao iTotalChatsDao;
	
	@Resource
	ChatMessageHandle chatMessageHandle;
	
	@Resource
	PurchaseDao purchaseDao;
	
	@Resource
	SupplyDAO supplyDao;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private ObjectMapper om = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonParseException, IOException {
		Map<String, Object> map = session.getAttributes();
		ChatRecordsParamsDto recordDto = chatMessageHandle.convertStringMsgToObj(message);
		int type = recordDto.getType();
		int subType = recordDto.getSubType();
		String chatInitiator = (String) map.get("chatInitiator");
		if("C".equalsIgnoreCase(chatInitiator)) { // 用户
			if(type ==0 && subType == 0){
				chatMessageHandle.plainTextHandleForCustomer(session, recordDto);
			} else if (type == 0 && subType ==1 ){
				recordDto.setChatId((Integer) map.get("chatId"));
				chatMessageHandle.historyMessageHandleForCustomer(session, recordDto);
			}
			logger.info("##客户，发送：" + message.getPayload());
		} else if("U".equalsIgnoreCase(chatInitiator)) { // 撮合员
			if(type == 0 && subType == 0){ //普通消息
				chatMessageHandle.plainTextHandleForEmployee(session, recordDto);
			} else if (type == 0 && subType == 1){  //历史消息
				chatMessageHandle.historyMessageHandleForEmployee(session, recordDto);
			} else if (type == 0 && subType == 2){  //撮合员发送图片
				chatMessageHandle.imgMessageHandleForEmployee(session, recordDto);
			} else if (type == 1 && subType == 0) { //撮合员打开聊天窗口
				chatMessageHandle.employeeOpenChatWin(session, recordDto);
			} else if (type == 1 && subType == 1) { //撮合员关闭聊天窗口
				chatMessageHandle.employeeCloseChatWin(session, recordDto);
			} else if (type == 1 && subType == 3){ // 生成订单消息
				chatMessageHandle.sendOrderRemindMessageForEmployee(session, recordDto);
			}
			logger.info("##撮合员，发送：" + message.getPayload());
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session){
		try {
			Map<String, Object> map = session.getAttributes();
			String chatInitiator = (String) map.get("chatInitiator");
			if("C".equalsIgnoreCase(chatInitiator)){ // 用户
				Chats chat = addOrUpdateChat(map, session);
				addOrUpdateTotalChat(chat);
				addRecordForChangeSourceAndSendMsgToEmployee(chat);
				if(customerSessionMap.containsKey(chat.getCustomerId())) {
					customerSessionMap.get(chat.getCustomerId()).close(ChatCloseStatus.USELESS_CONNECT);
				}
				customerSessionMap.put(chat.getCustomerId(), session);
				// 将客户发起会话产生的chatId和分配的撮合员id放入session中
				map.put("chatId", chat.getId());
				map.put("customerId", chat.getCustomerId());
			} else if("U".equalsIgnoreCase(chatInitiator)) { // 撮合员
				int employeeId = Integer.parseInt((String) map.get("employeeId"));
				//存入撮合员id、初始化正在聊天的chatId和客户id
				map.put("employeeId", employeeId);
				map.put("currChatId", 0);
				map.put("currCustomerId", 0);
				employeeSessionMap.put(employeeId, session);
			} else {
				throw new IllegalArgumentException(ResErrorCode.UNKNOWN_CHAT_INITIATOR);
			}
			logger.info("Connection Establied! session为：" + session);
		} catch (Exception e) {
			logger.error("连接建立出错", e);
			try {
				if(session.isOpen()){
					session.close(ChatCloseStatus.ERROR_AFTER_CONNECTION_ESTABLISHED);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
		Map<String, Object> map = session.getAttributes();
		String chatInitiator = (String) map.get("chatInitiator");
		
		try {
			if("C".equalsIgnoreCase(chatInitiator)){
				customerSessionMap.remove(map.get("customerId"));
				logger.info("##客户所在的Connection Closed！客户的Id为：" + map.get("customerId") + ", 客户所在的chatId为：" + map.get("chatId") + ", 关闭原因：" + status + ", session:" + session);
			} else {
				employeeSessionMap.remove(map.get("employeeId"));
				logger.info("##撮合员所在的Connection Closed！撮合员的id为：" + map.get("employeeId") + ", 关闭原因：" + status + ", session:" + session);
			}
		} catch (Exception e) {
			String name = chatInitiator.equals("C") ? "客户" : "撮合员";
			logger.error("##" + name + "连接关闭过程中出错", e);
		}
	}
	
	@Override
	public int setIsReadByCustomerId(int customerId){
		return iChatRecordsDao.setIsReadByCustomerId(customerId);
	}
	
	/** 为切换聊天的供货单或采购单添加记录
	 * @param customerId
	 * @param chatId
	 * @param source
	 * @param sourceId
	 * @param infoTitle
	 * @throws IOException
	 */
	private void addRecordForChangeSourceAndSendMsgToEmployee(Chats chat) throws IOException{
		logger.info("##传入参数" + chat);
		
		ChatRecords record = new ChatRecords();
		record.setType(2);
		record.setSubType(0);
		record.setChatId(chat.getId());
		record.setCustomerId(chat.getCustomerId());
		record.setChatFromType("C");
		record.setCreatedAt(new Date());
		record.setContent(om.writeValueAsString(chat));
		iChatRecordsDao.insertSelective(record);
		WebSocketSession s = null;
		for(Entry<Integer, WebSocketSession> entry : IWebSocketBO.employeeSessionMap.entrySet()){
			s = entry.getValue();
			if(s!=null && s.isOpen()){
				Map<String, Object> mapS = s.getAttributes();
				int employeeCurrCustomerId = (int) mapS.get("currCustomerId");
				if(employeeCurrCustomerId == record.getCustomerId()){
					s.sendMessage(new TextMessage(om.writeValueAsString(record)));
				}
			}
		}
	}
	
	private String getInfoTitleBySourceAndSourceId(int source, int sourceId) {
		return source == 1 
				? supplyDao.supplyDetailById(Integer.toString(sourceId)).getInfoTitle() 
				: purchaseDao.queryPurchaseById(sourceId).getInfoTitle();
	}
	
	private Chats addOrUpdateChat(Map<String, Object> map, WebSocketSession session) throws JsonProcessingException, IOException, IllegalAccessException, InvocationTargetException{
		Chats chat = new Chats();
		BeanUtils.populate(chat, map);
		chat.setInfoTitle(getInfoTitleBySourceAndSourceId(chat.getSource(), chat.getSourceId()));
		Integer tempChatId = iChatDao.getChatIdByCustomerIdSourceAndSourceId(chat);
		if(tempChatId == null){ // 新建会话
			Date now = new Date();
			chat.setCreatedAt(now);
			chat.setUpdatedAt(now);
			iChatDao.insertSelective(chat);
		} else { //更新会话
			chat = iChatDao.selectByPrimaryKey(tempChatId);
			BeanUtils.populate(chat, map);
			chat.setIsDel(0);
			chat.setUpdatedAt(new Date());
			iChatDao.updateByPrimaryKeySelective(chat);
			chatMessageHandle.sendCurrentTimeMessage(session);
		}
		return chat;
	}
	
	private void addOrUpdateTotalChat(Chats chat){
		TotalChats totalChat = iTotalChatsDao.selectByCustomerId(chat.getCustomerId());
		
		if(totalChat ==null || totalChat.getId() == null){
			totalChat = new TotalChats();
			totalChat.setCustomerId(chat.getCustomerId());
			totalChat.setLastChatId(chat.getId());
			totalChat.setUpdatedAt(new Date());
			iTotalChatsDao.insertSelective(totalChat);
		} else {
			totalChat.setLastChatId(chat.getId());
			totalChat.setUpdatedAt(new Date());
			totalChat.setIsDel(0);
			iTotalChatsDao.updateByPrimaryKeySelective(totalChat);
		}
	}
}
