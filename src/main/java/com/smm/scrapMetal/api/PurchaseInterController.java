package com.smm.scrapMetal.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.scrapMetal.bo.PurchaseBo;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.controller.CustomerController;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.PurchaseInter;
import com.smm.scrapMetal.domain.PurchaseInterDetail;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.util.PageBeans;

import net.sf.json.JSONObject;

/**
 * 采购单
 * 
 * @author tantaigen
 *
 */
@Controller
@RequestMapping(value = "purchaseInter")
public class PurchaseInterController {
	private Logger logger = Logger.getLogger(CustomerController.class);
	@Resource
	private PurchaseBo purchaseBo;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 采购单列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "purchaseList")
	@ResponseBody
	public ResultData queryPurchaseList(HttpServletRequest request) {
		logger.info("进入采购单列表查询借口》》》》》》》》》》》》》》》》");
		ResultData data = new ResultData();
		String paramsStr = request.getParameter("paramsStr");
		Map<String, Object> map = new HashMap<>();
		JSONObject obj = null;
		try {
			int currentPage = 1;// 当前页
			int pageNum = 10;// 每页显示条数
			// 查询条件
			if (!StringUtils.isNotBlank(paramsStr)) {
				logger.error("查询采购单列表 的paramsStr  is null");
			} else {

				obj = JSONObject.fromObject(paramsStr);
				String infoTitle = (String) obj.get("infoTitle");// 采购标题
				String itemId = (String)obj.get("itemId");// 采购标题
				Integer goodsProv = (Integer)obj.get("goodsProv");// 省
				Integer goodsCity = (Integer)obj.get("goodsCity");// 市
				Integer sort = (Integer)obj.get("sort");// 是否排序
				Integer sortType = (Integer)obj.get("sortType");// 排序方式
				logger.info("查询条件：infoTitle:" + infoTitle + " itemId:" + itemId + " itemId:" + itemId + " goodsProv:"
						+ goodsProv + " goodsCity:" + goodsCity + " sort:" + sort + " sortType:" + sortType);

				map.put("infoTitle", infoTitle);
				map.put("itemId", itemId);
				map.put("goodsProv", goodsProv);
				map.put("goodsCity", goodsCity);
				map.put("sort", sort);
				map.put("sortType", sortType);
			}

			List<PurchaseInter> purchaselist = purchaseBo.qeuryPurchaseInter(map);
			if (obj != null) {
				if (obj.get("pageNum") != null && !"".equals(obj.get("pageNum"))) {
					pageNum=(Integer) obj.get("pageNum");
				}
				if (obj.get("currentPage") != null && !"".equals(obj.get("currentPage"))) {
					currentPage= (Integer)obj.get("currentPage");
				}
			}

			PageBeans page = new PageBeans(pageNum, currentPage, purchaselist.size());

			int startNum = page.getStartNum();
			int endNum = page.getEndNum();
			map.put("startNum", startNum);
			map.put("endNum", endNum);
			List<PurchaseInter> list = purchaseBo.qeuryPurchaseInter(map);
			if (list != null && list.size() > 0) {
				data.setSuccess(true);
				ObjectMapper om = new ObjectMapper();
				om.setDateFormat(df);
				Map<String, Object> mapJ = new HashMap<>();
				mapJ.put("list", list);
				mapJ.put("total", purchaselist.size());
				String json = om.writeValueAsString(mapJ);
				logger.error("返回数据》》》》》》" + json);
				data.setResult(json);
			} else {
				logger.error("没有数据》》》》》》》");
				data.setSuccess(false);
				data.setErrorcode(ResErrorCode.PURCHASE_ERR_EMPTY_CODE);
				data.setErrMsg(ResErrorCode.PURCHASE_ERR_EMPTY_MSG);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统错误", e);
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.ERROR_CODE);
			data.setErrMsg(ResErrorCode.ERROR_MSG);
			return data;
		}

		return data;

	}

	/**
	 * 查看采购单详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "purchaseById")
	@ResponseBody
	public ResultData purchaseById(HttpServletRequest request) {
		ResultData data = new ResultData();
		logger.info("进入入查看订单详情接口》》》》》》》");
		String paramsStr = request.getParameter("paramsStr");
		try {
			if (!StringUtils.isNotBlank(paramsStr)) {
				logger.error("查看采购单详情 的paramsStr  is null");
				data.setSuccess(false);
				data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
				data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
				data.setUnAuthorizedRequest(false);
				return data;
			}
			Map<String, Object> map = new HashMap<>();

			JSONObject obj = JSONObject.fromObject(paramsStr);
			Integer id = (Integer)obj.get("id");
			
			String phone=null;
			if(obj.containsKey("phone")){
			phone= (String)obj.get("phone");
			}
			if (id == null || "".equals(id)) {
				logger.error("参数错误采购单ID不能为空！》》》》》》》》》》》》");
				data.setSuccess(false);
				data.setErrorcode(ResErrorCode.PURCHASE_ERR_ID_EMPTY_CODE);
				data.setErrMsg(ResErrorCode.PURCHASE_ERR_ID_EMPTY_MSG);
				return data;

			}
			Customer customer = null;
			Boolean b=false;
			if (phone == null || "".equals(phone)) {
				map.put("customerId", 0);
				b=true;
				// 检验手机号
//				logger.error("参数错误 手机号不能为空！》》》》》》》》》》》》");
//				data.setSuccess(false);
//				data.setErrorcode(ResErrorCode.PURCHASE_ERR_PHONE_EMPTY_CODE);
//				data.setErrMsg(ResErrorCode.PURCHASE_ERR_PHONE_EMPTY_MSG);
//				return data;
				} else {
				customer = purchaseBo.queryCustomer(phone);
				if (customer == null || "".equals(customer)) {
					logger.error("参数错误 该手机号用户不存在！》》》》》》》》》》》》");
					data.setSuccess(false);
					data.setErrorcode(ResErrorCode.PURCHASE_ERR_PHONE_ISNO_CODE);
					data.setErrMsg(ResErrorCode.PURCHASE_ERR_PHONE_ISNO_MSG);
					return data;

				}else{
					map.put("customerId", customer.getId());

				}

			}

			map.put("id", id);
			logger.error("接受数据》》》》》》 id" + id + " phone" + phone);
			PurchaseInterDetail pInter = purchaseBo.PurchaseInterDetail(map);
			if (pInter == null) {
				logger.error("参数错误 采购单不存在！》》》》》》》》》》》》");
				data.setSuccess(false);
				data.setErrorcode(ResErrorCode.PURCHASE_ERR_PURCHASE_ISNO_CODE);
				data.setErrMsg(ResErrorCode.PURCHASE_ERR_PURCHASE_ISNO_MSG);
				return data;

			} else {
				if(b){
					
					pInter.setWhetherFavorite("2");
					pInter.setWhetherZan("2");
				}
				data.setSuccess(true);
				ObjectMapper om = new ObjectMapper();
				om.setDateFormat(df);
				String json = om.writeValueAsString(pInter);
				logger.error("返回数据》》》》》》" + json);
				data.setResult(json);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统错误", e);
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.ERROR_CODE);
			data.setErrMsg(ResErrorCode.ERROR_MSG);
			return data;
		}

		return data;

	}

	/**
	 * 新建采购单
	 * 
	 * @param purchase
	 * @return
	 */
	@RequestMapping(value = "addPurchase")
	@ResponseBody
	public ResultData savePurchase(HttpServletRequest request) {

		ResultData data = new ResultData();
		logger.info("进入查看添加采购单》》》》》》》");
		String paramsStr = request.getParameter("paramsStr");
		try {
			if (!StringUtils.isNotBlank(paramsStr)) {
				logger.error("新建采购单 的paramsStr  is null");
				data.setSuccess(false);
				data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
				data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
				data.setUnAuthorizedRequest(false);
				return data;
			}
			data = purchaseBo.addpPurchase(paramsStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统错误", e);
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.ERROR_CODE);
			data.setErrMsg(ResErrorCode.PURCHASE_API_ADD_ERROR);
			return data;
		}

		return data;
	}

	/**
	 * 修改采购单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updatePurchase")
	@ResponseBody
	public ResultData updatePurchase(HttpServletRequest request) {
		ResultData data = new ResultData();
		logger.info("进入查看修改采购单》》》》》》》");
		String paramsStr = request.getParameter("paramsStr");
		try {
			if (!StringUtils.isNotBlank(paramsStr)) {
				logger.error("修改采购单 的paramsStr  is null");
				data.setSuccess(false);
				data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
				data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
				data.setUnAuthorizedRequest(false);
				return data;
			}
			data = purchaseBo.updatePurchaseInter(paramsStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统错误", e);
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.ERROR_CODE);
			data.setErrMsg(ResErrorCode.PURCHASE_API_UPD_ERROR);
			return data;
		}

		return data;

	}
	
	
	/**
	 * 查询采购单标题
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryInfoTitle")
	@ResponseBody
	public ResultData queryInfoTitle(HttpServletRequest request) {
		ResultData data = new ResultData();
		logger.info("进入查询采购单标题》》》》》》》");
		String paramsStr = request.getParameter("paramsStr");
		String str="";
		if (!StringUtils.isNotBlank(paramsStr)) {
			logger.error("进入查询采购单标题 的paramsStr  is null");
			str=purchaseBo.queryInfoTitle(null);
		}else{
			JSONObject obj = JSONObject.fromObject(paramsStr);

			if(obj.containsKey("infoTitle")){
				String infoTitle= (String)obj.get("infoTitle");
				Map<String, Object> map=new HashMap<>();
				map.put("infoTitle", infoTitle);
				str=purchaseBo.queryInfoTitle(map);
				}else{
					str=purchaseBo.queryInfoTitle(null);
				}
		}
		data.setSuccess(true);		
		logger.error("返回数据》》》》》》" + str);
		data.setResult(str);
		
		return data;
	}

}
