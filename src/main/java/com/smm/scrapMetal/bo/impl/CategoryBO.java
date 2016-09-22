package com.smm.scrapMetal.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.ICategoryBO;
import com.smm.scrapMetal.dao.ICategoryDao;
import com.smm.scrapMetal.domain.Category;

/**
 * @author dongmiaonan
 */

@Service
public class CategoryBO implements ICategoryBO {

    @Resource
    ICategoryDao iCategoryDao;

    @Override
    public List<Category> getCategorys() {
        return iCategoryDao.getCategorys();
    }

}
