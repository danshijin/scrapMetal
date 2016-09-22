package com.smm.scrapMetal.bo.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.IChatsBO;
import com.smm.scrapMetal.dao.IChatRecordsDao;
import com.smm.scrapMetal.dao.IChatsDao;
import com.smm.scrapMetal.dao.ITotalChatsDao;
import com.smm.scrapMetal.dao.PurchaseDao;
import com.smm.scrapMetal.dao.SupplyDAO;
import com.smm.scrapMetal.domain.ChatRecords;
import com.smm.scrapMetal.domain.Chats;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Order;
import com.smm.scrapMetal.dto.ChatsListView;

/**
* @author  zhaoyutao
* @version 2016年2月18日 上午9:02:38
* 类说明
*/

@Service
public class ChatsBO implements IChatsBO {
	
	@Resource
	IChatsDao iChatsDao;
	
	@Resource
	IChatRecordsDao iChatRecordsDao;
	
	@Resource
	ITotalChatsDao iTotalChatsDao;
	
	@Resource
	PurchaseDao purchaseDao;
	
	@Resource
	SupplyDAO supplyDao;

	@Override
	public Map<String, Object> getChatsList(int start, int len) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		len = len == 0 ? 10 : len; 
		List<ChatsListView> list = iChatsDao.getList(start, len);
		
		int total = iChatsDao.countAll();
		
		map.put("rows", list);
		
		map.put("total", total);
		
		return map;
	}
	
	@Override
	public Map<String, Object> getChatIdOfBuyerAndSeller(Order orderInfo){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int buyerChatId = 0;
		int sellerChatId = 0;
		
		Chats chatTmp = new Chats();
		chatTmp.setSource(orderInfo.getSource());
		chatTmp.setSourceId(orderInfo.getSourceId());
		
		chatTmp.setCustomerId(orderInfo.getBuyerId());
		Integer buyerSourceChatId = iChatsDao.getChatIdByCustomerIdSourceAndSourceId(chatTmp);
		chatTmp.setCustomerId(orderInfo.getSellerId());
		Integer sellerSourceChatId = iChatsDao.getChatIdByCustomerIdSourceAndSourceId(chatTmp);
		
		if(buyerSourceChatId == null) {
			Chats chat = new Chats();
			String infoTitle = "";
			if(orderInfo.getSource() == 1){
				infoTitle = supplyDao.supplyDetailById(Integer.toString(orderInfo.getSourceId())).getInfoTitle();
			} else if(orderInfo.getSource() == 2) {
				infoTitle = purchaseDao.queryPurchaseById(orderInfo.getSourceId()).getInfoTitle();
			} else {
				map.put("buyerChatId", buyerChatId);
				map.put("sellerChatId", sellerChatId);
				return map;
			}
			chat.setInfoTitle(infoTitle);
			chat.setCustomerId(orderInfo.getBuyerId());
			chat.setCreatedAt(new Date());
			chat.setSource(orderInfo.getSource());
			chat.setSourceId(orderInfo.getSourceId());
			chat.setTitleMsg("订单已生成");
			chat.setUpdatedAt(new Date());
			iChatsDao.insertSelective(chat);
			buyerSourceChatId = chat.getId();
		}
		
		if(sellerSourceChatId == null) {
			Chats chat = new Chats();
			String infoTitle = "";
			if(orderInfo.getSource() == 1){
				infoTitle = supplyDao.supplyDetailById(Integer.toString(orderInfo.getSourceId())).getInfoTitle();
			} else if(orderInfo.getSource() == 2) {
				infoTitle = purchaseDao.queryPurchaseById(orderInfo.getSourceId()).getInfoTitle();
			} else {
				map.put("buyerChatId", buyerChatId);
				map.put("sellerChatId", sellerChatId);
				return map;
			}
			chat.setInfoTitle(infoTitle);
			chat.setInfoTitle(infoTitle);
			chat.setCustomerId(orderInfo.getSellerId());
			chat.setCreatedAt(new Date());
			chat.setSource(orderInfo.getSource());
			chat.setSourceId(orderInfo.getSourceId());
			chat.setTitleMsg("订单已生成");
			chat.setUpdatedAt(new Date());
			iChatsDao.insertSelective(chat);
			sellerSourceChatId = chat.getId();
			
		}
		map.put("buyerChatId", buyerSourceChatId);
		map.put("sellerChatId", sellerSourceChatId);
		return map;
	}
	
	@Override
	public Customer getCustomerInfoByChatId(int chatId){
		return iChatsDao.getCustomerInfoByChatId(chatId);
	}
	
	@Override
	public List<ChatRecords> getLatestMsgByCustomerId(int customerId){
		List<ChatRecords> list = iChatRecordsDao.getLatestMsgByCustomerId(customerId);
		Collections.reverse(list);
		return list;
	}

	@Override
	public Integer getChatIdByCustomerId(Chats chat) {
		return iChatsDao.getChatIdByCustomerIdSourceAndSourceId(chat);
	}
	
	@Override
	public int selectCustomerIdByOpenId(String openId){
		return iChatsDao.selectCustomerIdByOpenId(openId);
	}
	
	@Override
	public Chats insertSelective(Chats chat){
		iChatsDao.insertSelective(chat);
		return chat;
	}
	
	@Override
	public Chats selectByPrimaryKey(int chatId){
		return iChatsDao.selectByPrimaryKey(chatId);
	}
	
	@Override
	public int updateByPrimaryKeySelective(Chats chat){
		return iChatsDao.updateByPrimaryKeySelective(chat);
	}
	
	
	
}
