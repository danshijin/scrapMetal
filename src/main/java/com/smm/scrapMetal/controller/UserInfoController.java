package com.smm.scrapMetal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.smm.scrapMetal.bo.impl.UserBO;
import com.smm.scrapMetal.commom.LoginResult;
import com.smm.scrapMetal.domain.User;

/**
 * @author zengshihua 用户登录控制器 先简单匹配，后续采用Spring Security
 */
@Controller
public class UserInfoController {

    @Resource
    private UserBO userBO;

    /**
     * 登录页
     * 
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login() {

        ModelAndView view = new ModelAndView("login");

        return view;
    }

    /**
     * 实现用户登录
     * 
     * @param request
     * @param user
     * @param mode
     * @return
     */
    @RequestMapping("/user/userLogin")
    @ResponseBody
    public Map<String, Object> userLogin(HttpServletRequest request, User user, String vCode, Model mode) {

        String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        Map<String, Object> resultMap = new HashMap<>();
        LoginResult loginResult = LoginResult.FAILD;

        if (StringUtils.isNotBlank(vCode)) {
            if (code.equals(vCode)) {
                loginResult = userBO.login(user, request);
            } else {
                loginResult = LoginResult.VCODE_ERROR;
            }

        } else {
            loginResult = LoginResult.VCODE_NULL;
        }

        resultMap.put("code", loginResult.getCode());
        resultMap.put("msg", loginResult.getMessage());

        return resultMap;
    }

    @RequestMapping("/user/noPageLogin")
    public String noPageLogin(HttpServletRequest request, HttpServletResponse response, String uid, String pwd,
                              String destUrl) {
        User u = new User();
        u.setAccount(uid);
        u.setPwd(pwd);
        LoginResult loginResult = LoginResult.FAILD;
        loginResult = userBO.login(u, request);

        if (loginResult.equals(LoginResult.SUCC)) {

            return "redirect:" + destUrl;
        }

        return "redirect:/login.do";
    }

    /**
     * 登录成功
     */
    @RequestMapping("/user/loginSucc")
    public ModelAndView loginSucc(HttpServletRequest request, Model mode, String actionNameForMainPageFrame) {

        ModelAndView mav = new ModelAndView();
        // 查询当前品目要显示的界面组件
        // 获取当前品目id
        User user = (User) request.getSession().getAttribute("userInfo");

        //List<WebPartsPOJO> wlist = iwebpaPBO.getWebPartsPList(user.getId());
        String str = "";
        if (StringUtils.isNotBlank(str)) {
            str = str.substring(0, str.length() - 1);
        }
        if(StringUtils.isBlank(actionNameForMainPageFrame)){
        	actionNameForMainPageFrame = request.getContextPath() + "/user/showMainPage.do";
        }
        mav.addObject("actionNameForMainPageFrame", actionNameForMainPageFrame);
        mav.addObject("webParts", str);
        // 返回页面处理
        mav.setViewName("/home/index");
        return mav;
    }

    /**
     * 获取用户登录信息
     * 
     * @param request
     * @param mode
     * @return
     */
    @RequestMapping("/user/getUserLoginInfo")
    @ResponseBody
    public Map<String, Object> getUserLoginInfo(HttpServletRequest request, Model mode) {

        User user = (User) request.getSession().getAttribute("userInfo");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userName", user.getName());

        return resultMap;
    }

    /**
     * 实现用户退出登录
     */
    @RequestMapping("/user/loginOut")
    public String loginOut(HttpServletRequest request, Model mode) {

        // User user = (User) request.getSession().getAttribute("userInfo");
        // 清空session
        request.getSession().invalidate();

        return "redirect:/login.do";
    }

    /**
     * 洽谈请求提醒
     */
    @RequestMapping("/user/chatsRemind")
    @ResponseBody
    public Map<String, Object> chatsRemind(HttpServletRequest request, Model mode) {

        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();

        User user = (User) request.getSession().getAttribute("userInfo");
        // 品目ID
        paramMap.put("itemId", user.getItemId());
        // 订单状态
        paramMap.put("orderStatus", 1);
        //List<Chats> listChats = this.userInfoBO.queryChatsRemind(paramMap);

        resultMap.put("total", 0);

        return resultMap;
    }
    @RequestMapping(value="/user/showWebSocket")
    public ModelAndView showLogin(){
    	return new ModelAndView("/chats/webSocketFrame");
    }
    @RequestMapping(value="/user/showMainPage")
    public ModelAndView showMainPage(){
    	return new ModelAndView("/home/mainPage");
    }
}
