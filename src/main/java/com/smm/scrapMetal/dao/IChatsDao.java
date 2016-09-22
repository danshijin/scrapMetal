package com.smm.scrapMetal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smm.scrapMetal.domain.Chats;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.dto.ChatsListView;

public interface IChatsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Chats record);

    int insertSelective(Chats record);

    Chats selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Chats record);

    int updateByPrimaryKey(Chats record);
    
    List<ChatsListView> getList(@Param("start") int start, @Param("len") int len);
    
    int countAll();
    
    Integer getChatIdByCustomerIdSourceAndSourceId(Chats chat);
    
    Customer getCustomerInfoByChatId(int chatId);
    
    Integer selectCustomerIdByOpenId(String openId);
    
    int countCustomerByOpenId(String openId);
    
    List<ChatsListView> getNotReadMsgListByCustomerId(@Param("customerId") int customerId);
    
}