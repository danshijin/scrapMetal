package com.smm.scrapMetal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.scrapMetal.bo.ICustomerBo;
import com.smm.scrapMetal.bo.ITotalChatsBO;
import com.smm.scrapMetal.bo.IWebSocketBO;
import com.smm.scrapMetal.bo.impl.ChatsBO;
import com.smm.scrapMetal.bo.impl.CustomerBo;
import com.smm.scrapMetal.bo.impl.UserBO;
import com.smm.scrapMetal.chats.handler.ChatMessageHandle;
import com.smm.scrapMetal.domain.ChatRecords;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Order;
import com.smm.scrapMetal.domain.User;
import com.smm.scrapMetal.dto.TotalChatsListView;
import com.smm.scrapMetal.util.ExportUtil;
import com.smm.scrapMetal.util.JSONUtil;

/**
 * @author zhaoyutao
 * @version 2016年1月28日 下午3:11:33 类说明
 */

@Controller
@RequestMapping("chats")
public class ChatsController {
    private Logger     logger = Logger.getLogger(ChatsController.class);
    
    @Resource
    ChatsBO            chatsBO;

    @Resource
    IWebSocketBO       iWebSocketBO;

    @Resource
    ChatMessageHandle  chatMessageHandle;
    
    @Resource
    private CustomerBo customerBo;
    
    @Resource
    private ITotalChatsBO iTotalChatsBO;
    
    @Resource
    private ICustomerBo iCustomerBo;;

    @RequestMapping("index")
    public ModelAndView index() {
        return new ModelAndView("chats/chatsList");
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> getList(int start, int len) {
        return iTotalChatsBO.getChatsList(start, len);
    }

    @RequestMapping("showChatWin")
    public ModelAndView showChatWin(int chatId, HttpSession httpSession) {
        ModelAndView mv = new ModelAndView("chats/chatWin");
        mv.addObject("chatId", chatId);
        Customer customer = chatsBO.getCustomerInfoByChatId(chatId);
        mv.addObject("customerInfo", customer);

        List<ChatRecords> list = chatsBO.getLatestMsgByCustomerId(customer.getId());
        mv.addObject("lastestMsg", list);
        long lastestTime = list.size() > 0 ? list.get(0).getCreatedAt().getTime() : new Date().getTime();
        mv.addObject("lastestTime", lastestTime);
        iWebSocketBO.setIsReadByCustomerId(customer.getId());

        try {
        	User user = (User) httpSession.getAttribute("userInfo");
            chatMessageHandle.sendNotReadMessageToEmployee(IWebSocketBO.employeeSessionMap.get(user.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    @RequestMapping("showCustomerDetail")
    @ResponseBody
    public String showCustomerDetail(HttpServletRequest request) {
        String customerId = request.getParameter("customerId");
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(customerId)) {
                map.put("customerId", customerId + "");
                Customer customer = customerBo.getCustomerById(map);
                rtnMap.put("customer", customer);
                rtnMap.put("info", "success");
            } else {
                rtnMap.put("info", "failed");
                logger.error("showCustomerDetail 的params customerId is null");
            }

            return JSONUtil.map2json(rtnMap);
        } catch (Exception e) {
            logger.error("showCustomerDetail系统错误", e);
        }
        return null;
    }

    @RequestMapping("deleteByIds")
    @ResponseBody
    public Map<String, Object> deleteByIds(String ids) {
        Map<String, Object> obj = new HashMap<String, Object>();
        int count = iTotalChatsBO.deleteByIds(ids);
        if (count > 0) {
            obj.put("code", "succ");
            obj.put("msg", "操作成功");
        }
        return obj;
    }
    
    @RequestMapping("getChatIdOfBuyerAndSeller")
    @ResponseBody
    public Map<String, Object> getChatIdOfBuyerAndSeller(String paramsStr){
    	Order orderInfo = null;
		try {
			orderInfo = new ObjectMapper().readValue(paramsStr, Order.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return chatsBO.getChatIdOfBuyerAndSeller(orderInfo);
    }

    /**
     * 导出
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ExporChatList")
    public void ExporChatList(String ids, HttpServletResponse response) throws IOException {
        List<TotalChatsListView> list = iTotalChatsBO.ExporChatList(ids);
        if (list != null) {
            ExportUtil<TotalChatsListView> excel = new ExportUtil<TotalChatsListView>();
            String fileName = "消息中心";

            String[] headerNames = new String[] { "客户", "最新消息", "时间" };
            String[] header = new String[] { "nickName", "lastestMsg", "updatedAt" };
            String[] comments = new String[] { null, null, null };
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            excel.export("sheet1", headerNames, header, comments, list, os, "");
            byte[] contents = os.toByteArray();
            InputStream is = new ByteArrayInputStream(contents);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        }
    }
    
    @RequestMapping("getAllOnlineCustomer")
    @ResponseBody
    public Map<String, Object> getAllOnlineCustomer(){
    	Map<String, Object> map = new HashMap<>();
    	
    	for(Entry<Integer, WebSocketSession> entry : IWebSocketBO.customerSessionMap.entrySet()){
    		
    		Map<String, Object> sessionMap = entry.getValue().getAttributes();
    		int customerId = (int) sessionMap.get("customerId");
    		String nickName = iCustomerBo.selectNickNameById(customerId);
    		map.put("客户id为:" + customerId +", 昵称为：" + nickName,"所在会话为：" + sessionMap.get("chatId") + ", session为：" + entry.getValue());
    	}
    	return map;
    }
    
    @RequestMapping("getAllOnlineEmployee")
    @ResponseBody
    public Map<String, Object> getAllOnlineEmployee(){
    	Map<String, Object> map = new HashMap<>();
    	
    	for(Entry<Integer, WebSocketSession> entry : IWebSocketBO.employeeSessionMap.entrySet()){
    		
    		Map<String, Object> sessionMap = entry.getValue().getAttributes();
    		
    		map.put("撮合员id为：" + entry.getKey().toString(), "所在会话为：" + sessionMap.get("currChatId") + "， 当前客户为：" + sessionMap.get("currCustomerId") + ", session为：" + entry.getValue());
    	
    	}
    	map.put("notReadList", IWebSocketBO.notReadVector);
    	return map;
    }
    
}
