/**
 * 
 */
package com.smm.scrapMetal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.scrapMetal.bo.impl.IOrdersBo;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Orders;
import com.smm.scrapMetal.domain.User;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.util.JSONUtil;
import com.smm.scrapMetal.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * OrdersController
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年1月25日
 */
@Controller
public class OrdersController {
	private Logger logger = Logger.getLogger(OrdersController.class);
	@Resource
	private IOrdersBo iOrdersBo;
	
	@RequestMapping("/orders/orderList")
	public ModelAndView getBuyOrderList(Integer pno,Integer customerId){
		ModelAndView model = new ModelAndView("/orders/orderlist");
		logger.info("得到id为: "+customerId+" 的订单列表!");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerId", customerId);  // 客户id 需要得到
		//查询记录总条数
		int countnum = iOrdersBo.getOrderCount(map);
		if (pno == null) { 
			pno = 1;
		}
		PageBean page = new PageBean(10, pno, countnum);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		map.put("startNum", startNum);
		map.put("endNum", endNum);	
		model.addObject("totalRecords",countnum);//总条数
		model.addObject("totalPage",page.getTotalPages());//总页数
		
		model.addObject("customerId",customerId);
		List<Orders> ordersList = iOrdersBo.getOrderList(map);
		model.addObject("ordersList", ordersList);
		return model;
	}
	
	@RequestMapping("/orders/releaselist")
	public ModelAndView getSellOrderList(Integer pno,Integer customerId){
		ModelAndView model = new ModelAndView("/orders/releaselist");
		
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("得到id为:  "+customerId+" 的发布列表!");
		map.put("customerId", customerId); // 客户id 需要得到
		//查询记录总条数
		int countnum = iOrdersBo.getReleaseCount(map);
		if (pno == null) { 
			pno = 1;
		}
		PageBean page = new PageBean(10, pno, countnum);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		map.put("startNum", startNum);
		map.put("endNum", endNum);	
		model.addObject("totalRecords",countnum);//总条数
		model.addObject("totalPage",page.getTotalPages());//总页数
		
		model.addObject("customerId",customerId);
		List<Orders> releaseList = iOrdersBo.getReleaseList(map);
		model.addObject("releaseList", releaseList);
		return model;
	}
	
	@RequestMapping("/orders/favorite")
	public ModelAndView getFavoriteList(Integer pno,Integer customerId){
		ModelAndView model = new ModelAndView("/orders/favorite");
		
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("得到id为:  "+customerId+" 的收藏列表!");
		map.put("customerId", customerId);  // 客户id 需要得到
		//查询记录总条数
		int countnum = iOrdersBo.getFavoriteCount(map);
		if (pno == null) { 
			pno = 1;
		}
		PageBean page = new PageBean(10, pno, countnum);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		map.put("startNum", startNum);
		map.put("endNum", endNum);	
		model.addObject("totalRecords",countnum);//总条数
		model.addObject("totalPage",page.getTotalPages());//总页数
		
		model.addObject("customerId",customerId);
		List<Orders> favoriteList = iOrdersBo.getFavorite(map);
		
		model.addObject("favoriteList", favoriteList);
		return model;
	}
	
	@RequestMapping("/orders/customerDetail")
	public ModelAndView getCustomerDetail(Integer customerId){
		ModelAndView model = new ModelAndView("/orders/customerdetail");
		Customer customer = iOrdersBo.getCustomerDetail(customerId);
		List<String> entTypesList = new ArrayList<>();
		if (customer.getEntTypes() != null) {
			String[] entTypes = customer.getEntTypes().split(",");
			for (String string : entTypes) {
				if (string.equals("29")) {
					entTypesList.add("贸易商");
				}
				if (string.equals("30")) {
					entTypesList.add("冶炼");				
				}
				if (string.equals("31")) {
					entTypesList.add("加工制作");
				}
				if (string.equals("32")) {
					entTypesList.add("其他");
				}
			}
		}
		model.addObject("customer", customer);
		model.addObject("customerId", customerId);
		model.addObject("entTypesList", entTypesList);
		return model;
	}
	
	@RequestMapping("/orders/delCustomer")
	@ResponseBody
	public Map<String, String> deleteCustomer(HttpServletRequest request){
		Integer customerId = Integer.valueOf(request.getParameter("id"));
		Map<String, String> result = new HashMap<>();
		int count = iOrdersBo.delCustomer(customerId);
		if (count<1) {
			result.put("code", "defeat");
			result.put("info", "删除失败!");
		}else{
			result.put("code", "succ");
			result.put("info", "删除成功!");
		}
		return result;
	}
	
	@RequestMapping("/orders/toupdate")
	public ModelAndView toUpdateCustomer(Integer customerId){
		ModelAndView model = new ModelAndView("/orders/updateCustomer");
		
		Customer customer = iOrdersBo.getCustomerDetail(customerId); // 需要把1换成customerId 1是测试数据
		model.addObject("customer", customer);
		model.addObject("customerId", customerId);
		return model;
	}
	
	@RequestMapping("/test")
	public ModelAndView testModel(){
		ModelAndView model = new ModelAndView("/login");
		return model;
	}
	
}
