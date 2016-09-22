/**
 * 
 */
package com.smm.scrapMetal.api;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.scrapMetal.bo.impl.IOrdersBo;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.domain.OrderDetail;
import com.smm.scrapMetal.domain.Orders;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.util.JSONUtil;

/**
 * OrdersController Copyright 2016 SMM, Inc. All Rights Reserved.
 * 
 * @author Yuanmeng at 2016年1月25日
 */
@Controller
public class OrdersInterController {
	
    private Logger logger = Logger.getLogger(OrdersInterController.class);
    
    @Resource
    private IOrdersBo iOrdersBo;

    /**
     * 得到订单列表接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ordersInter/myOrderList")
    @ResponseBody
    public ResultData getMyOrderList(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {

            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("myOrderList 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }

            JSONObject obj = JSONObject.fromObject(paramsStr);
            String phone = obj.getString("phone");
            String orderStatus = obj.getString("orderStatus");
            if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(orderStatus)) {
                Map<String, Object> map = new HashMap<>();
                map.put("phone", phone);
                map.put("orderStatus", orderStatus);
                List<Orders> orderList = iOrdersBo.myOrderList(map);
                Orders orders = iOrdersBo.getOrderStatusCount(phone);
                Map<String, Object> result = new HashMap<>();
                result.put("currentList", orderList);
                result.put("statusCount", orders);
                data.setUnAuthorizedRequest(false);
                data.setSuccess(true);
                data.setResult(JSONUtil.doConvertObject2Json(result));
            } else {
                logger.error("myOrderList 的params phone or orderStatus is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }

        } catch (Exception e) {
            logger.error("myOrderList系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 提交（确认）订单接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ordersInter/submitOrder")
    @Transactional
    @ResponseBody
    public ResultData submitOrder(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {

            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("submitOrder 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }

            JSONObject obj = JSONObject.fromObject(paramsStr);
            String orderId = obj.getString("orderId");
            String phone = obj.getString("phone");
            if (StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(phone)) {
            	Orders order =  iOrdersBo.getOrderById(Integer.valueOf(orderId));
            	if(order != null){
            		String orderStatus = String.valueOf(order.getOrderStatus());
            		if(!orderStatus.equals("") && orderStatus.equals("1")){
            			logger.error("closeOrder 对方已确认未成交");
                        data.setSuccess(false);
                        data.setErrMsg("对方已确认未成交");
                        data.setUnAuthorizedRequest(false);
                        return data;
            		}
            	}
            	int i = 0;
            	int j = 0;
            	Map<String, Object> map = new HashMap<>();
            	if (order.getSellerPhone().equals(phone)) {
            		i = iOrdersBo.sellerSubmitOrder(orderId);
            		if (order.getIsBuyerConfirmed() == 1) { // 双方都确认的话 提交订单
            			j = iOrdersBo.submitOrder(orderId);
            			if (order.getSource() == 1) { // 减掉对应的供货单里冻结的数量
            				map.put("id", order.getSourceId());
                			map.put("quantity", order.getQuantity());
                			iOrdersBo.submitOrderUpdateSupply(map);
						}
					}
            		
				}else if (order.getBuyerPhone().equals(phone)) {
					i = iOrdersBo.buyerSubmitOrder(orderId);
					if (order.getIsSellerConfirmed() == 1) { // 双方都确认的话 提交订单
            			j = iOrdersBo.submitOrder(orderId);
            			if (order.getSource() == 1) { // 减掉对应的供货单里冻结的数量
            				map.put("id", order.getSourceId());
                			map.put("quantity", order.getQuantity());
                			iOrdersBo.submitOrderUpdateSupply(map);
						}
					}
				}else {
					logger.error("参数phone跟买/卖家不匹配");
	                data.setSuccess(false);
	                data.setErrorcode(ResErrorCode.SUBMITORDER_ERROR_CODE1);
	                data.setErrMsg(ResErrorCode.SUBMITORDER_ERROR_MSG1);
	                data.setUnAuthorizedRequest(false);
	                return data;
				}
            	data.setUnAuthorizedRequest(false);
                data.setSuccess(true);
                if (i < 0 && j < 0) {
                    logger.error("id为:" + orderId + "的订单提交失败");
                    data.setSuccess(false);
                    data.setErrorcode(ResErrorCode.SUBMITORDER_ERROR_CODE);
                    data.setErrMsg(ResErrorCode.SUBMITORDER_ERROR_MSG);
                    data.setUnAuthorizedRequest(false);
                }
            } else {
				logger.error("submitOrder 的params phone or orderId is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }

        } catch (Exception e) {
            logger.error("submitOrder系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }
    
    /**
     * 未确认订单接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ordersInter/closeOrder")
    @ResponseBody
    public ResultData closeOrder(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {

            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("closeOrder 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }

            JSONObject obj = JSONObject.fromObject(paramsStr);
            String orderId = obj.getString("orderId");
            String phone = obj.getString("phone");
            if (StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(phone)) {
            	Orders order =  iOrdersBo.getOrderById(Integer.valueOf(orderId));
            	if(order != null){
            		String orderStatus = String.valueOf(order.getOrderStatus());
            		if(!orderStatus.equals("") && orderStatus.equals("1")){
            			logger.error("closeOrder 对方已确认未成交");
                        data.setSuccess(false);
                        data.setErrMsg("对方已确认未成交");
                        data.setUnAuthorizedRequest(false);
                        return data;
            		}
            	}
            	int i = 0;
            	int j = 0;
            	Map<String, Object> map = new HashMap<>();
            	if (order.getSellerPhone().equals(phone)) {
            		i = iOrdersBo.sellerCloseOrder(orderId);
            		if (order.getIsBuyerConfirmed() != 1) { // 如若买方没有确认订单 则订单直接关闭
            			j = iOrdersBo.closeOrder(orderId);
            			if (order.getSource() == 1) { // 减掉对应的供货单里冻结的数量
            				map.put("id", order.getSourceId());
                			map.put("quantity", order.getQuantity());
                			iOrdersBo.closeOrderUpdateSupply(map);
						}
					}
            		
				}else if (order.getBuyerPhone().equals(phone)) {
					i = iOrdersBo.buyerCloseOrder(orderId);
					if (order.getIsSellerConfirmed() != 1) { // 如若卖方没有确认订单 则订单直接关闭
            			j = iOrdersBo.closeOrder(orderId);
            			if (order.getSource() == 1) { // 减掉对应的供货单里冻结的数量
            				map.put("id", order.getSourceId());
                			map.put("quantity", order.getQuantity());
                			iOrdersBo.closeOrderUpdateSupply(map);
						}
					}
				}else {
					logger.error("参数phone跟买/卖家不匹配");
	                data.setSuccess(false);
	                data.setErrorcode(ResErrorCode.SUBMITORDER_ERROR_CODE1);
	                data.setErrMsg(ResErrorCode.SUBMITORDER_ERROR_MSG1);
	                data.setUnAuthorizedRequest(false);
	                return data;
				}
            	data.setUnAuthorizedRequest(false);
                data.setSuccess(true);
                if (i < 0 && j < 0) {
                    logger.error("id为:" + orderId + "的订单取消失败");
                    data.setSuccess(false);
                    data.setErrorcode(ResErrorCode.SUBMITORDER_ERROR_CODE);
                    data.setErrMsg(ResErrorCode.SUBMITORDER_ERROR_MSG);
                    data.setUnAuthorizedRequest(false);
                }
                
            } else {
                logger.error("closeOrder 的params phone or orderId is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }

        } catch (Exception e) {
            logger.error("closeOrder系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 得到收藏列表接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ordersInter/favoriteList")
    @ResponseBody
    public ResultData getFavoriteList(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("favoriteList 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }

            JSONObject obj = JSONObject.fromObject(paramsStr);
            String phone = obj.getString("phone");
            if (StringUtils.isNotBlank(phone)) {
                List<Orders> favoriteList = iOrdersBo.myFavoriteList(phone);
                JSONArray jsonArray = JSONArray.fromObject(favoriteList);
                data.setUnAuthorizedRequest(false);
                data.setSuccess(true);
                data.setResult(jsonArray.toString());
            } else {
                logger.error("favoriteList 的params phone is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }
        } catch (Exception e) {
            logger.error("favoriteList系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 得到发布列表接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ordersInter/myReleaseList")
    @ResponseBody
    public ResultData getMyReleaseList(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("myReleaseList 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            JSONObject obj = JSONObject.fromObject(paramsStr);
            String phone = obj.getString("phone");
            String infoStatus = obj.getString("infoStatus");
            if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(infoStatus)) {
                Map<String, Object> map = new HashMap<>();
                map.put("phone", phone);
                map.put("infoStatus", Integer.valueOf(infoStatus));
                List<Orders> releaseList = iOrdersBo.myReleaseList(map);
                Orders orders = iOrdersBo.getReleaseStatusCount(phone);
                Map<String, Object> result = new HashMap<>();
                result.put("currentList", releaseList);
                result.put("statusCount", orders);
                data.setUnAuthorizedRequest(false);
                data.setSuccess(true);
                data.setResult(JSONUtil.doConvertObject2Json(result));
            } else {
                logger.error("myReleaseList 的params phone or infoStatus is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }
        } catch (Exception e) {
            logger.error("myReleaseList系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 更新发布列表时间接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ordersInter/updateRelease")
    @ResponseBody
    @Transactional
    public ResultData updateRelease(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("myReleaseList 的paramsStr is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            JSONObject obj = JSONObject.fromObject(paramsStr);
            String isAll = obj.getString("isAll");
            String phone = obj.getString("phone");
            String id = obj.getString("id");
            String source = obj.getString("source");//1供货2采购
            Map<String, Object> map = new HashMap<>();
            if (StringUtils.isNotBlank(isAll) && StringUtils.isNotBlank(phone)) {
                map.put("phone", phone);
                if (isAll.equals("0")) {
                    if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(source)) {
                        map.put("id", Integer.valueOf(id));
                        if (source.equals("1")) {
                            iOrdersBo.updateSupply(map);
                            iOrdersBo.restoreSupplyInfostatus(map); // 还原之前的状态
                        } else {
                            iOrdersBo.updatePurchase(map);
                            iOrdersBo.restorePurchaseInfostatus(map); // 还原之前的状态
                        }
                        succInfo(data);
                        return data;
                    } else {
                        errorInfo(data, "myReleaseList", "source", "id");
                    }
                } else {
                    map.put("id", -1); // id为-1时更新全部
                    iOrdersBo.updatePurchase(map);
                    iOrdersBo.updateSupply(map);
                    iOrdersBo.restoreSupplyInfostatus(map); // 还原之前的状态
                    iOrdersBo.restorePurchaseInfostatus(map); // 还原之前的状态
                }
            } else {
                errorInfo(data, "myReleaseList", "isAll", "phone");
            }
        } catch (Exception e) {
            logger.error("myReleaseList系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 删除发布记录
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ordersInter/deleteRelease")
    @ResponseBody
    @Transactional
    public ResultData deleteRelease(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("myReleaseList 的paramsStr is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            JSONObject obj = JSONObject.fromObject(paramsStr);
            String id = obj.getString("id");
            String source = obj.getString("source");//1供货2采购
            
            Map<String, Object> map = new HashMap<>();
            map.put("source", source);
            map.put("id", id);
            
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(source)) {
                if (source.equals("1")) {
                    iOrdersBo.deleteSupply(Integer.valueOf(id));
                } else {
                    iOrdersBo.deletePurchase(Integer.valueOf(id));
                }
                
                iOrdersBo.deleteFavorite(map); // 删除 对应的收藏列表
                
                succInfo(data);
                return data;
            } else {
                errorInfo(data, "myReleaseList", "source", "id");
            }
        } catch (Exception e) {
            logger.error("myReleaseList系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    public void errorInfo(ResultData data, String... parms) {
        logger.error(parms[0] + "的params" + parms[1] + " " + parms[2] + " is null");
        data.setSuccess(false);
        data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
        data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
        data.setUnAuthorizedRequest(false);
    }

    public void succInfo(ResultData data) {
        data.setUnAuthorizedRequest(false);
        data.setSuccess(true);
    }
    
    
    @RequestMapping("/ordersInter/queryOrderDetail")
    @ResponseBody
    @Transactional
    public ResultData queryOrderDetail(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
        	 if (!StringUtils.isNotBlank(paramsStr)) {
                 logger.error("queryOrderDetail 的paramsStr is null");
                 data.setSuccess(false);
                 data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                 data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                 data.setUnAuthorizedRequest(false);
                 return data;
             }
        	  JSONObject obj = JSONObject.fromObject(paramsStr);
              String orderNo = obj.getString("orderNo");
              if(orderNo==null || "".equals(orderNo)){
            	  logger.info("订单编号不能为nul");
            	  data.setSuccess(false);
                  data.setErrorcode(ResErrorCode.SUBMITORDER_orderNo_code);
                  data.setErrMsg(ResErrorCode.SUBMITORDER_orderNo_MSG);
                  data.setUnAuthorizedRequest(false);
                  return data;
              }
              Map<String, Object> map =new HashMap<>();
              map.put("orderNo", orderNo);
              	OrderDetail orderDetail=iOrdersBo.queryOrderDetail(map);
              	if(orderDetail==null){
              		logger.info("订单编号"+orderNo+" 订单不存在！");
              		data.setSuccess(false);
                    data.setErrorcode(ResErrorCode.SUBMITORDER_order_code);
                    data.setErrMsg(ResErrorCode.SUBMITORDER_order_MSG);
                    data.setUnAuthorizedRequest(false);
                    return data;
              		
              	}
              	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
              	data.setSuccess(true);
				ObjectMapper om = new ObjectMapper();
				
				Calendar calendar=Calendar.getInstance(); 
				
				if(orderDetail.getIsBuyerConfirmed()!=null&&orderDetail.getIsSellerConfirmed()!=null&&
						Integer.valueOf(orderDetail.getIsBuyerConfirmed())==0&&Integer.valueOf(orderDetail.getIsSellerConfirmed())==0){
					calendar.setTime(orderDetail.getCreatedAt());
					calendar.add(Calendar.DAY_OF_YEAR, 14);//让日期加7 
	    			Date remindTime=calendar.getTime();
	    			orderDetail.setBuyDealTime(remindTime);
	    			orderDetail.setSellDealTime(remindTime);
				}else if(orderDetail.getIsBuyerConfirmed()!=null&&orderDetail.getIsSellerConfirmed()!=null&&
						Integer.valueOf(orderDetail.getIsBuyerConfirmed())==0&&Integer.valueOf(orderDetail.getIsSellerConfirmed())==1){
						calendar.setTime(orderDetail.getSellerConfirmedTime());
						calendar.add(Calendar.DAY_OF_YEAR, 7);//让日期加7 
		    			Date remindTime=calendar.getTime();
		    			orderDetail.setBuyDealTime(remindTime);
				}else if(orderDetail.getIsBuyerConfirmed()!=null&&orderDetail.getIsSellerConfirmed()!=null&&
						Integer.valueOf(orderDetail.getIsBuyerConfirmed())==1&&Integer.valueOf(orderDetail.getIsSellerConfirmed())==0){
						calendar.setTime(orderDetail.getBuyerConfirmedTime());
						calendar.add(Calendar.DAY_OF_YEAR, 7);//让日期加7 
		    			Date remindTime=calendar.getTime();		    			
		    			orderDetail.setSellDealTime(remindTime);
				}
				BigDecimal price=new BigDecimal(orderDetail.getPrice());
				BigDecimal quetity=new BigDecimal(orderDetail.getQuantity());
				orderDetail.setSumPrice(price.multiply(quetity).toString());
				om.setDateFormat(df);
				String json = om.writeValueAsString(orderDetail);
				logger.error("返回数据》》》》》》" + json);
				data.setResult(json);
				return data;
        	
		} catch (Exception e) {
			  logger.error("queryOrderDetail系统错误", e);
	            data.setSuccess(false);
	            data.setErrorcode(ResErrorCode.ERROR_CODE);
	            data.setErrMsg(ResErrorCode.ERROR_MSG);
	            data.setUnAuthorizedRequest(false);
	            return data;
		}
        
        
        
       
        
    }
}
