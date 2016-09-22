package com.smm.scrapMetal.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.IItemBo;
import com.smm.scrapMetal.dao.ItemDao;
import com.smm.scrapMetal.domain.Item;

@Service
public class ItemBo implements IItemBo {
    @Resource
    ItemDao itemDao;

    /**
     * 查询所有品类
     */
    public List<Item> showItemList() {
        return itemDao.showItemList();
    }

    /**
     * 添加品类
     */
	@Override
	public int addItem(Item item) {
		return itemDao.addItem(item);
	}

	/**
     * 删除品类
     */
	@Override
	public int delItem(Integer id) {
		return itemDao.delItem(id);
	}
    
    
}
