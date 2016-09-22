package com.smm.scrapMetal.dao;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.User;

/**
 * 类ICategoryDao.java的实现描述：TODO 类实现描述
 * 
 * @author duqiang 2016年1月26日 下午2:19:50
 */
public interface IUserDao {
    public List<User> getUsers();

    public List<User> userLogin(Map<String, String> paramMap);
}
