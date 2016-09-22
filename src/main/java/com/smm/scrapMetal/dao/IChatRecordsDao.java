package com.smm.scrapMetal.dao;

import java.util.List;

import com.smm.scrapMetal.domain.ChatRecords;
import com.smm.scrapMetal.dto.ChatRecordsParamsDto;
import com.smm.scrapMetal.dto.ChatRemindDto;

public interface IChatRecordsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatRecords record);

    int insertSelective(ChatRecords record);

    ChatRecords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatRecords record);

    int updateByPrimaryKey(ChatRecords record);
    
    List<ChatRecords> getLatestMsgByCustomerId(int customerId);
    
    List<ChatRecords> getHistoryMsgForCustomer(ChatRecordsParamsDto record);
    
    List<ChatRecords> getHistoryMsgForEmployee(ChatRecordsParamsDto record);
    
    List<ChatRemindDto> countIsNotReadGroupByChatId();
    
    int setIsReadByChatId(int chatId);
    
    int setIsReadByCustomerId(int customerId);
    
    int getNotReadMsgNumByCustomerId(int customerId);
}