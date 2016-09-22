/**
 * 
 */
package com.smm.scrapMetal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.scrapMetal.bo.IDeliveryBo;
import com.smm.scrapMetal.domain.Delivery;
import com.smm.scrapMetal.domain.PriceExplain;
import com.smm.scrapMetal.domain.User;
import com.smm.scrapMetal.util.DateUtil;


/**
 * priceExplainController
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年2月22日
 */
@Controller
@RequestMapping("/delivery")
public class DeliveryController {

	private Logger logger = Logger.getLogger(DeliveryController.class);
	
	@Autowired
	private IDeliveryBo iDeliveryBo;
	
	@RequestMapping("/list")
	public ModelAndView getDeliveryList(){
		ModelAndView model = new ModelAndView("/setting/deliverylist");
		logger.info("开始得到交货方式列表");
		
		List<Delivery> deliverys = iDeliveryBo.queryAll();
		
		model.addObject("deliverys",deliverys);
		return model;
	}
	
	@RequestMapping("/addDelivery")
	@ResponseBody
	public Map<String, String> addDelivery(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("userInfo");
		String name = request.getParameter("name");
		String isDomestic = request.getParameter("isDomestic");
		logger.info("获取参数数据：name:"+name+" isDomestic:"+isDomestic);
		Map<String, String> result = new HashMap<>();
		
		if (name.equals("") || name==null) {
			result.put("code", "defeat");
			result.put("info", "请输入品类名称!");		
			return result;
		}
		
		Delivery delivery = new Delivery();
		delivery.setName(name);
		delivery.setIsDomestic(Integer.valueOf(isDomestic));
		delivery.setIsDefault(0);
		delivery.setIsDel(0);
		delivery.setCreatedAt(new Date());
		delivery.setCreatedBy(user.getId());
		int count = iDeliveryBo.addDelivery(delivery);
		if (count<1) {
			result.put("code", "defeat");
			result.put("info", "添加失败!");
		}else{
			logger.info("添加价格说明成功！");
			result.put("code", "succ");
			result.put("info", "添加成功!");
		}
		return result;
	}
	
	@RequestMapping("/delDelivery")
	@ResponseBody
	public Map<String, String> delItem(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, String> result = new HashMap<>();
		int count = iDeliveryBo.delDelivery(Integer.valueOf(id));
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
