package com.smm.scrapMetal.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.smm.scrapMetal.bo.IUserBO;
import com.smm.scrapMetal.bo.IWebSocketBO;
import com.smm.scrapMetal.commom.LoginResult;
import com.smm.scrapMetal.dao.IUserDao;
import com.smm.scrapMetal.domain.User;

/**
 * @author dongmiaonan
 */

@Service
public class UserBO implements IUserBO {
    private Logger logger = Logger.getLogger(UserBO.class);
    @Resource
    IUserDao       iUserDao;

    @Override
    public List<User> getUsers() {
        return iUserDao.getUsers();
    }

    @Override
    public LoginResult login(User user, HttpServletRequest request) {

        LoginResult loginResult = LoginResult.FAILD;
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("account", user.getAccount());
            paramMap.put("pwd", user.getPwd());
            List<User> listUser = iUserDao.userLogin(paramMap);
            if (listUser != null && listUser.size() > 0) {
                if (listUser.size() > 1) {
                    loginResult = LoginResult.EXE;
                    logger.info("用户登录失败，数据库存在相同用户.");
                } else {

                    User userInfo = listUser.get(0);
                    
                    for(int sessionCustomerId : IWebSocketBO.employeeSessionMap.keySet()) {
                    	if(userInfo.getId() == sessionCustomerId) {
                    		return LoginResult.REPEAT_LOGIN;
                    	}
                    }
                    
                    //修改用户在线状态
                    //                  this.userInfoDAO.updateOnline(1,userInfo.getId());
                    //加载对应品目
                    //List<Items> items = this.userInfoDAO.getItemsById(userInfo.getItemId());
                    //userInfo.setItems(items.get(0));
                    //将用户信息放进Session
                    request.getSession().setAttribute("userInfo", userInfo);
                    request.getSession().setAttribute("notReadList", IWebSocketBO.notReadVector);
                    loginResult = LoginResult.SUCC;

                    logger.info("用户登录成功，用户信息：" + listUser.get(0).toString());
                }
            } else {
                loginResult = LoginResult.FAILD;
                logger.info("用户登录失败，请查看具体原因.");
            }
        } catch (Exception e) {
            loginResult = LoginResult.EXE;
            logger.info("用户登录失败，程序发生异常." + e.getMessage());
        }
        return loginResult;
    }
}
