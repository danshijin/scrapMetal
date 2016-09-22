/**
 * 
 */
package com.smm.scrapMetal.controller;

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

import com.smm.scrapMetal.bo.PriceExplainBo;
import com.smm.scrapMetal.domain.Item;
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
@RequestMapping("/price")
public class priceExplainController {

	private Logger logger = Logger.getLogger(priceExplainController.class);
	
	@Autowired
	private PriceExplainBo priceExplainBo;
	
	@RequestMapping("/list")
	public ModelAndView getPriceList(){
		ModelAndView model = new ModelAndView("/setting/pricelist");
		logger.info("开始得到价格说明列表");
		
		List<PriceExplain> priceExplains = priceExplainBo.queryAll();
		
		model.addObject("priceExplains",priceExplains);
		return model;
	}
	
	@RequestMapping("/addPriceExplain")
	@ResponseBody
	public Map<String, String> getList(HttpServletRequest request){
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
		
		PriceExplain priceExplain = new PriceExplain();
		priceExplain.setName(name);
		priceExplain.setIsDomestic(Integer.valueOf(isDomestic));
		priceExplain.setIsDefault(0);
		priceExplain.setIsDel(0);
		priceExplain.setCreatedAt(DateUtil.getDateWidthFormat(null));
		priceExplain.setCreatedBy(user.getId());
		int count = priceExplainBo.addPriceExplain(priceExplain);
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
	
	@RequestMapping("/delPriceExplain")
	@ResponseBody
	public Map<String, String> delItem(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, String> result = new HashMap<>();
		int count = priceExplainBo.delPriceExplain(Integer.valueOf(id));
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
