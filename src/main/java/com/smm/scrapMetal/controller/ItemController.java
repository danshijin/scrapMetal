/**
 * 
 */
package com.smm.scrapMetal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.scrapMetal.bo.impl.ItemBo;
import com.smm.scrapMetal.domain.Item;
import com.smm.scrapMetal.domain.User;

/**
 * ItemController
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年2月22日
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	private Logger logger = Logger.getLogger(ItemController.class);
	@Resource
	private ItemBo itemBo;
	
	@RequestMapping("/list")
	public ModelAndView getItemList(){
		ModelAndView model = new ModelAndView("/setting/itemlist");
		logger.info("开始得到品种列表");
		
		List<Item> items = itemBo.showItemList();
		
		model.addObject("items",items);
		return model;
	}
	
	@RequestMapping("/addItem")
	@ResponseBody
	public Map<String, String> addItem(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("userInfo");
		String name = request.getParameter("name");
		
		Map<String, String> result = new HashMap<>();
		
		if (name.equals("") || name==null) {
			result.put("code", "defeat");
			result.put("info", "请输入品类名称!");		
			return result;
		}
		
		Item item = new Item();
		item.setName(name);
		item.setIsDefault(0);
		item.setCreatedAt(new Date());
		item.setCreatedBy(user.getId());
		int count = itemBo.addItem(item);
		if (count<1) {
			result.put("code", "defeat");
			result.put("info", "添加失败!");
		}else{
			result.put("code", "succ");
			result.put("info", "添加成功!");
		}
		return result;
	}
	
	@RequestMapping("/delItem")
	@ResponseBody
	public Map<String, String> delItem(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, String> result = new HashMap<>();
		int count = itemBo.delItem(Integer.valueOf(id));
		if (count<1) {
			result.put("code", "defeat");
			result.put("info", "删除失败!");
		}else{
			result.put("code", "succ");
			result.put("info", "删除成功!");
		}
		return result;
	}
}
