package com.smm.scrapMetal.chats.handler;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.scrapMetal.bo.IWebSocketBO;
import com.smm.scrapMetal.dao.IChatRecordsDao;
import com.smm.scrapMetal.dao.IChatsDao;
import com.smm.scrapMetal.dao.ITotalChatsDao;
import com.smm.scrapMetal.domain.ChatRecords;
import com.smm.scrapMetal.domain.Chats;
import com.smm.scrapMetal.domain.TotalChats;
import com.smm.scrapMetal.dto.ChatRecordsParamsDto;

/**
* @author  zhaoyutao
* @version 2016年2月19日 上午11:39:54
* 转发消息
*/

@Service
public class ChatMessageHandle {
	
	@Resource
	IChatsDao iChatDao;
	
	@Resource
	IChatRecordsDao iChatRecordsDao;
	
	@Resource
	ITotalChatsDao iTotalChatsDao;
	
	private ObjectMapper om = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
	
	private Logger logger = Logger.getLogger(ChatMessageHandle.class);
	
	/** 将message json串转换为对象
	 * @param message
	 * @return
	 * @throws JsonParseException, IOException 
	 */
	public ChatRecordsParamsDto convertStringMsgToObj(TextMessage message) throws JsonParseException, IOException {
		logger.info("发送信息为：" + message.getPayload());
		return om.readValue(message.getPayload(), ChatRecordsParamsDto.class);
	}
	
	/** 处理客户发送过来的普通消息
	 * @param session
	 * @param record
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public ChatRecordsParamsDto plainTextHandleForCustomer(WebSocketSession session, ChatRecordsParamsDto record) throws JsonProcessingException, IOException {
		Map<String, Object> map = session.getAttributes();
		record.setChatFromType("C");
		int chatId = (Integer) map.get("chatId");
		int customerId = (int) map.get("customerId");
		record.setChatId(chatId);
		record.setCustomerId(customerId);
		updateLastestMsg(chatId, record.getContent());
		
		ChatRecords rltRcd = new ChatRecords();
		rltRcd.setChatId(record.getChatId());
		rltRcd.setType(record.getType());
		rltRcd.setSubType(record.getSubType());
		rltRcd.setContent(record.getContent());
		
		TextMessage customerMessage = new TextMessage(om.writeValueAsString(rltRcd));
		boolean isRead = Boolean.FALSE;
		// 遍历在线并且处于当前chatId所对应的对话框的撮合员列表,如找到，则将消息推送过去并标记消息为已读，否则为未读
		WebSocketSession s = null;
		for(Entry<Integer, WebSocketSession> entry : IWebSocketBO.employeeSessionMap.entrySet()){
			s = entry.getValue();
			if(s!=null && s.isOpen()){
				Map<String, Object> mapS = s.getAttributes();
				int employeeCurrCustomerId = (int) mapS.get("currCustomerId");
				if(employeeCurrCustomerId == record.getCustomerId()){
					s.sendMessage(customerMessage);
					isRead = Boolean.TRUE;
					break;
				}
			}
		}
		
		record.setIsRead(isRead ? 1 : 0);
		record.setCreatedAt(new Date());
		iChatRecordsDao.insertSelective(record);
		// 如果发生新消息未读的情况，则刷新未读消息数量并推送给所有的在线撮合员
		if(!isRead) {
			broadcastNotReadMessage();
		}
		//无撮合员在线，给用户发送提示
		if(IWebSocketBO.employeeSessionMap.size() == 0) {
			rltRcd.setChatId(record.getChatId());
			rltRcd.setType(0);
			rltRcd.setSubType(0);
			rltRcd.setContent("当前不在线（交易时间为：8:30-17:30）");
			session.sendMessage(new TextMessage(om.writeValueAsString(rltRcd)));
		}
		
		return record;
	}
	
	public ChatRecordsParamsDto historyMessageHandleForCustomer(WebSocketSession session, ChatRecordsParamsDto record) throws JsonProcessingException, IOException{
		
		List<ChatRecords> list = iChatRecordsDao.getHistoryMsgForCustomer(record);
		session.sendMessage(new TextMessage(om.writeValueAsString(list)));
		int customerId = (int)session.getAttributes().get("customerId");
		iChatRecordsDao.setIsReadByCustomerId(customerId);
		return record;
	}
	
	public ChatRecordsParamsDto historyMessageHandleForEmployee(WebSocketSession session, ChatRecordsParamsDto record) throws JsonProcessingException, IOException{
		
		List<ChatRecords> list = iChatRecordsDao.getHistoryMsgForEmployee(record);
		session.sendMessage(new TextMessage(om.writeValueAsString(list)));
		return record;
	}
	
	public ChatRecordsParamsDto imgMessageHandleForEmployee(WebSocketSession session, ChatRecordsParamsDto record) throws JsonProcessingException, IOException {
		Map<String, Object> map = session.getAttributes();
		record.setChatFromType("U");
		
		int chatId = record.getChatId();
		
		updateLastestMsg(chatId, "[图片]");
		
		record.setEmployeeId((Integer)map.get("employeeId"));
		WebSocketSession customerSession = IWebSocketBO.customerSessionMap.get(map.get("currCustomerId"));
		
		ChatRecords rltRcd = new ChatRecords();
		rltRcd.setType(record.getType());
		rltRcd.setSubType(record.getSubType());
		rltRcd.setContent(record.getContent());
		rltRcd.setChatFromType(record.getChatFromType());
		
		if(customerSession != null && customerSession.isOpen()){
			customerSession.sendMessage(new TextMessage(om.writeValueAsString(rltRcd)));
		} else {
			record.setIsRead(0);
		}
		record.setCreatedAt(new Date());
		iChatRecordsDao.insertSelective(record);
		return record;
	}
	
	public ChatRecordsParamsDto plainTextHandleForEmployee(WebSocketSession session, ChatRecordsParamsDto record) throws JsonProcessingException, IOException{
		Map<String, Object> map = session.getAttributes();
		record.setChatFromType("U");
		int chatId = record.getChatId();
		
		updateLastestMsg(chatId, record.getContent());
		
		record.setEmployeeId((Integer)map.get("employeeId"));
		WebSocketSession customerSession = IWebSocketBO.customerSessionMap.get(map.get("currCustomerId"));
		
		ChatRecords rltRcd = new ChatRecords();
		rltRcd.setType(record.getType());
		rltRcd.setSubType(record.getSubType());
		rltRcd.setContent(record.getContent());
		rltRcd.setChatFromType(record.getChatFromType());
		
		if(customerSession != null && customerSession.isOpen()){
			customerSession.sendMessage(new TextMessage(om.writeValueAsString(rltRcd)));
		} else {
			record.setIsRead(0);
		}
		record.setCreatedAt(new Date());
		iChatRecordsDao.insertSelective(record);
		return record;
	}
	
	public ChatRecordsParamsDto employeeOpenChatWin(WebSocketSession session, ChatRecordsParamsDto record) {
		Map<String, Object> map = session.getAttributes();
		map.put("currChatId", record.getChatId());
		map.put("currCustomerId", record.getCustomerId());
		return record;
	}
	
	public void sendCurrentTimeMessage(WebSocketSession session) throws JsonProcessingException, IOException {
		ChatRecords currentTimeMessage = new ChatRecords();
		currentTimeMessage.setType(0);
		currentTimeMessage.setSubType(3);
		currentTimeMessage.setContent(String.valueOf(new Date().getTime()));
		session.sendMessage(new TextMessage(om.writeValueAsString(currentTimeMessage)));
	}
	
	public ChatRecordsParamsDto employeeCloseChatWin(WebSocketSession session, ChatRecordsParamsDto record) {
		Map<String, Object> map = session.getAttributes();
		map.put("currChatId", 0);
		map.put("currCustomerId", 0);
		return record;
	}
	
	public void broadcastNotReadMessage() throws JsonProcessingException, IOException  {
		IWebSocketBO.notReadVector.clear();
		IWebSocketBO.notReadVector.addAll(iChatRecordsDao.countIsNotReadGroupByChatId());
		ChatRecords notReadMsg = new ChatRecords();
		notReadMsg.setType(1);
		notReadMsg.setSubType(2);
		notReadMsg.setContent(om.writeValueAsString(IWebSocketBO.notReadVector));
		
		WebSocketSession s = null;
		for(Entry<Integer, WebSocketSession> entry : IWebSocketBO.employeeSessionMap.entrySet()){
			s = entry.getValue();
			if(s!=null && s.isOpen()){
				s.sendMessage(new TextMessage(om.writeValueAsString(notReadMsg)));
			}
		}
	}
	
	public void sendNotReadMessageToEmployee(WebSocketSession session) throws JsonProcessingException, IOException {
		IWebSocketBO.notReadVector.clear();
		IWebSocketBO.notReadVector.addAll(iChatRecordsDao.countIsNotReadGroupByChatId());
		ChatRecords notReadMsg = new ChatRecords();
		notReadMsg.setType(1);
		notReadMsg.setSubType(2);
		notReadMsg.setContent(om.writeValueAsString(IWebSocketBO.notReadVector));
		session.sendMessage(new TextMessage(om.writeValueAsString(notReadMsg)));
	}
	
	/**
	 * @param session
	 * @param record
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public ChatRecordsParamsDto sendOrderRemindMessageForEmployee(WebSocketSession session, ChatRecordsParamsDto record) throws JsonProcessingException, IOException {
		Map<String, Object> map = session.getAttributes();
		
		record.setChatFromType("U");
		record.setCreatedAt(new Date());
		
		WebSocketSession customerSession = IWebSocketBO.customerSessionMap.get(map.get("currCustomerId"));
		boolean isRead = Boolean.FALSE;
		if(customerSession != null && customerSession.isOpen()){
			customerSession.sendMessage(new TextMessage(om.writeValueAsString(record)));
			isRead = Boolean.TRUE;
		}
		record.setIsRead(isRead ? 1 : 0);
		iChatRecordsDao.insertSelective(record);
		
		updateLastestMsg(record.getChatId(), "订单已生成");
		return record;
	}
	
	/** 客户会话，最新内容和时间更新
	 * @param chatId
	 * @param content
	 */
	private void updateLastestMsg(Integer chatId, String content){
		
		Chats chatForUpdate = iChatDao.selectByPrimaryKey(chatId);
		chatForUpdate.setTitleMsg(content);
		chatForUpdate.setUpdatedAt(new Date());
		iChatDao.updateByPrimaryKeySelective(chatForUpdate);
		TotalChats totalChatForUpdate = iTotalChatsDao.selectByCustomerId(chatForUpdate.getCustomerId());
		totalChatForUpdate.setLastestMsg(content);
		totalChatForUpdate.setIsDel(0);
		totalChatForUpdate.setUpdatedAt(new Date());
		iTotalChatsDao.updateByPrimaryKeySelective(totalChatForUpdate);
	}
}
