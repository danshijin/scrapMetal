package com.smm.scrapMetal.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smm.scrapMetal.bo.impl.AreasBO;
import com.smm.scrapMetal.bo.impl.CategoryBO;
import com.smm.scrapMetal.bo.impl.CustomerBo;
import com.smm.scrapMetal.bo.impl.ItemBo;
import com.smm.scrapMetal.bo.impl.UserBO;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.domain.Areas;
import com.smm.scrapMetal.domain.Item;
import com.smm.scrapMetal.dto.ResultData;

/**
 * @au thor duqiang
 */
@Controller
public class CustomerInterController {
    private Logger     logger = Logger.getLogger(CustomerInterController.class);
    @Resource
    private CustomerBo customerBo;
    @Resource
    private AreasBO    areasBO;
    @Resource
    private CategoryBO categoryBO;
    @Resource
    private UserBO     userBO;
    @Resource
    private ItemBo     itemBo;

    /**
     * 品种列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/showItemList")
    @ResponseBody
    public ResultData showItemList(HttpServletRequest request) {
        ResultData data = new ResultData();
        try {
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
            List<Item> itemList = itemBo.showItemList();
            List<Item> newItemList = new ArrayList<Item>();
            for (Iterator<Item> iterator = itemList.iterator(); iterator.hasNext();) {
                Item item = (Item) iterator.next();
                item.setImage(url + item.getImage());
                newItemList.add(item);
            }
            JSONArray jsonArray = JSONArray.fromObject(newItemList);
            data.setUnAuthorizedRequest(false);
            data.setSuccess(true);
            data.setResult(jsonArray.toString());
        } catch (Exception e) {
            logger.error("showItemList系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
//        logger.info(">>>data.toString()=" + data.toString());
        return data;
    }

    /**
     * 得到省接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/getProvAreas")
    @ResponseBody
    public ResultData getProvAreas(HttpServletRequest request) {
        ResultData data = new ResultData();
        try {
            List<Areas> areasList = areasBO.getParentAreas();
            JSONArray jsonArray = JSONArray.fromObject(areasList);
            data.setUnAuthorizedRequest(false);
            data.setSuccess(true);
            data.setResult(jsonArray.toString());
        } catch (Exception e) {
            logger.error("getProvAreas系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        //logger.info(">>>data.toString()=" + data.toString());
        return data;
    }

    /**
     * 根据省得到市接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/getCityAreas")
    @ResponseBody
    public ResultData getCityAreas(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {

            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("getCityAreas 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            data = areasBO.getCityAreas(paramsStr);
        } catch (Exception e) {
            logger.error("getCityAreas系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 用户注册接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/registerCustomer")
    @ResponseBody
    public ResultData registerCustomer(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("registerCustomer 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }

            data = customerBo.addCustomerInterface(paramsStr);
        } catch (Exception e) {
            logger.error("registerCustomer系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
    }

    /**
     * 用户更新接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/editCustomer")
    @ResponseBody
    public ResultData editCustomer(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");

        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("editCustomer 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            data = customerBo.updateCustomerInterface(paramsStr);
        } catch (Exception e) {
            logger.error("editCustomer系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
    }

    /**
     * 用户查看接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/showCustomerDetail")
    @ResponseBody
    public ResultData showCustomerDetail(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");

        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("showCustomerDetail 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            data = customerBo.showCustomerDetailInteface(paramsStr);
        } catch (Exception e) {
            logger.error("showCustomerDetail系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
    }

    /**
     * 用户登录接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/customerIsLogin")
    @ResponseBody
    public ResultData customerIsLogin(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        logger.info("##传入参数：" + paramsStr);
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("customerIsLogin 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            data = customerBo.customerIsLogin(paramsStr);
        } catch (Exception e) {
            logger.error("customerIsLogin系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
    }

    /**
     * 用户注销接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/customerCancel")
    @ResponseBody
    public ResultData customerCancel(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");

        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("customerCancel 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            data = customerBo.customerCancel(paramsStr);
        } catch (Exception e) {
            logger.error("customerCancel系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
    }
    
    /**
     * 用户未读消息列表接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customerInter/getNotReadMsgList")
    @ResponseBody
    public ResultData getNotReadMsgList(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");

        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("showNotReadMsgList 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            data = customerBo.getNotReadMsgInteface(paramsStr);
        } catch (Exception e) {
            logger.error("showCustomerDetail系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
    }
}
