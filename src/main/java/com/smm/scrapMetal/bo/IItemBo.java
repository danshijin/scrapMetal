package com.smm.scrapMetal.bo;

import java.util.List;

import com.smm.scrapMetal.domain.Item;

public interface IItemBo {
    /**
     * 品种
     * 
     * @return
     */
    public List<Item> showItemList();
    
    
    /**
     * 添加品种
     * 
     * @return
     */
    public int addItem(Item item);
    
    
    /**
     * 删除品种
     * 
     * @return
     */
    public int delItem(Integer id);

}
