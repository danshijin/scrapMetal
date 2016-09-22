package com.smm.scrapMetal.dao;

import java.util.List;

import com.smm.scrapMetal.domain.Item;

/**
 * 类ICustomerDao.java的实现描述：TODO 类实现描述
 * 
 * @author duqiang 2016年1月25日 下午5:07:34
 */
public interface ItemDao {
    public List<Item> showItemList();
    
    public int addItem(Item item);
    
    public int delItem(Integer id);

}
