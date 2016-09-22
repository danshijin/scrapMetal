package com.smm.scrapMetal.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smm.scrapMetal.bo.SupplyBO;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dto.ResultData;

import freemarker.log.Logger;

@Controller
@RequestMapping("/supplyInter")
public class SupplyInterController {

	private static Logger logger = Logger.getLogger(SupplyInterController.class.getName());
	@Resource
	private SupplyBO supplybo;
	
	/**
	 * 供货单列表(接口)
	 * */
	@RequestMapping(value="/orderList")
	@ResponseBody
	public ResultData orderList(HttpServletRequest request){
		logger.info("----------供货单列表(接口)-----------");
		String paramsStr = request.getParameter("paramsStr");
		ResultData data = new ResultData();
        try {
            data = supplybo.supplyListAPI(paramsStr);
        } catch (Exception e) {
            logger.error("系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
	}
	
	/**
	 * 供货单详情(接口)
	 * */
	@RequestMapping(value="/supplyDetail")
	@ResponseBody
	public ResultData supplyDetail(HttpServletRequest request){
		logger.info("-----------供货单详情(接口)-----------");
		ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            data = supplybo.supplyDetailByIdAPI(paramsStr);
        } catch (Exception e) {
            logger.error("系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
	}
	
	/**
	 * 新建供货单(接口)
	 * */
	@RequestMapping(value="/addSupply")
	@ResponseBody
	public ResultData addSupply(HttpServletRequest request){
		logger.info("-----------新建供货单(接口)-----------");
		ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        logger.info("新建供货单接口，参数：" + paramsStr);
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
           data = supplybo.addSupplyAPI(paramsStr);
        } catch (Exception e) {
            logger.error("发布供货单出错", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.SUPPLY_API_ADD_ERROR);
        }
        return data;
	}
	
	/**
	 * 修改供货单(接口)
	 * */
	@RequestMapping(value="/updateSupply")
	@ResponseBody
	public ResultData updateSupply(HttpServletRequest request){
		logger.info("-----------修改供货单(接口)-----------");
		ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
        	//String str = new String(paramsStr.getBytes("gbk"),"gbk");
            if (!StringUtils.isNotBlank(paramsStr)) {
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
           data = supplybo.updateSupplyAPI(paramsStr);
        } catch (Exception e) {
            logger.error("系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.SUPPLY_API_UPD_ERROR);
        }
        return data;
	}
	
	/**
	 * 常用搜索列表
	 * */
	@RequestMapping(value="/commonSearch")
	@ResponseBody
	public ResultData commonSearch(HttpServletRequest request){
		logger.info("-----------常用搜索列表-----------");
		ResultData data = new ResultData();
        try {
           data = supplybo.commonSearchList();
        } catch (Exception e) {
            logger.error("系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
	}
	
	/**
	 * 列表模糊查找
	 */
	@RequestMapping(value="/searchSupplyList")
	@ResponseBody
	public ResultData searchSupplyList(HttpServletRequest request){
		logger.info("-----------列表模糊查找-----------");
		ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
           data = supplybo.searchSupplyList(paramsStr);
        } catch (Exception e) {
            logger.error("系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
	}
	
	/**
	 * 模糊查找通过title
	 */
	@RequestMapping(value="/searchSupplyListByTitle")
	@ResponseBody
	public ResultData searchSupplyListByTitle(HttpServletRequest request){
		logger.info("-----------模糊查找通过title-----------");
		ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
           data = supplybo.searchSupplyListByTitle(paramsStr);
        } catch (Exception e) {
            logger.error("系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
	}
}
