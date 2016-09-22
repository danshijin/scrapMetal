package com.smm.scrapMetal.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
* @author  zhaoyutao
* @version 2016年4月12日 上午10:05:50
* 错误页面跳转
*/

@Controller
@RequestMapping("errorPage")
public class ErrorPageController {
	
	@RequestMapping("404")
	public ModelAndView badRequest(HttpServletRequest request){
		return new ModelAndView("error/404");
	}
	
	@RequestMapping("500")
	public ModelAndView internalServerError(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("error/500");
		/*Enumeration<String> strArr = request.getAttributeNames();
		int index = 0;
		while(strArr.hasMoreElements()) {
			String element = strArr.nextElement();
			if((index = element.indexOf(prefix)) > -1) {
				index += prefix.length();
				mv.addObject(element.substring(index), request.getAttribute(element));
			}
		}*/
		Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
		mv.addObject("exception", ex);
		StringWriter sw = new StringWriter();  
        PrintWriter pw = new PrintWriter(sw);  
        ex.printStackTrace(pw);
        mv.addObject("stackTrace", sw.toString());
		return mv;
	}
}
