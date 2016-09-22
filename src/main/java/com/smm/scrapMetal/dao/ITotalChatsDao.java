package com.smm.scrapMetal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smm.scrapMetal.domain.TotalChats;
import com.smm.scrapMetal.dto.TotalChatsListView;

public interface ITotalChatsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TotalChats record);

    int insertSelective(TotalChats record);

    TotalChats selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TotalChats record);

    int updateByPrimaryKey(TotalChats record);
    
    TotalChats selectByCustomerId(int customerId);
    
    List<TotalChatsListView> getList(@Param("start") int start, @Param("len") int len);
    
    int countAll();
    
    List<TotalChatsListView> ExporChatList(@Param("ids")String ids);
    
    int deleteByIds(@Param("ids")String ids);
}