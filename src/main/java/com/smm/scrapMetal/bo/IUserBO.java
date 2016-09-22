package com.smm.scrapMetal.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.smm.scrapMetal.commom.LoginResult;
import com.smm.scrapMetal.domain.User;

/**
 * @author dongmiaonan
 */
public interface IUserBO {
    /**
     * 获取企业类型
     * 
     * @return
     */
    public List<User> getUsers();

    public LoginResult login(User user, HttpServletRequest request);
}
