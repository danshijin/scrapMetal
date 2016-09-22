package com.smm.scrapMetal.bo;

import java.util.List;

import com.smm.scrapMetal.domain.Category;

/**
 * @author dongmiaonan
 */
public interface ICategoryBO {
    /**
     * 获取企业类型
     * 
     * @return
     */
    public List<Category> getCategorys();

}
