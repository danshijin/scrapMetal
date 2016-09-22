package com.smm.scrapMetal.bo.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.PurchaseBo;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.controller.CustomerController;
import com.smm.scrapMetal.dao.PurchaseDao;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.PurchaseDomain;
import com.smm.scrapMetal.domain.PurchaseInter;
import com.smm.scrapMetal.domain.PurchaseInterDetail;
import com.smm.scrapMetal.dto.ResultData;

import net.sf.json.JSONObject;

@Service
public class PurchaseImpl implements PurchaseBo {
	private Logger logger = Logger.getLogger(CustomerController.class);

	@Resource
	private PurchaseDao dao;

	@Override
	public List<PurchaseDomain> queryPurchase(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.queryPurchase(map);
	}

	@Override
	public PurchaseDomain queryPurchaseById(Integer id) {
		// TODO Auto-generated method stub
		return dao.queryPurchaseById(id);
	}

	@Override
	public Integer addPurchase(PurchaseDomain Purchase) {
		// TODO Auto-generated method stub
		return dao.addPurchase(Purchase);
	}

	@Override
	public Integer updatePurchase(PurchaseDomain Purchase) {
		// TODO Auto-generated method stub
		return dao.updatePurchase(Purchase);
	}

	@Override
	public Integer delPurchase(String[] array) {
		// TODO Auto-generated method stub
		return dao.delPurchase(array);
	}

	@Override
	public List<PurchaseDomain> ExporQueryPurchaseByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.ExporQueryPurchaseByIds(map);
	}

	@Override
	public Integer batchUpdatePurchase(Map<String ,Object> map) {
		// TODO Auto-generated method stub
		return dao.batchUpdatePurchase(map);
	}

	@Override
	public Customer queryCustomer(String phone) {
		// TODO Auto-generated method stub
		return dao.queryCustomer(phone);
	}

	@Override
	public List<PurchaseInter> qeuryPurchaseInter(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.qeuryPurchaseInter(map);
	}

	@Override
	public PurchaseInterDetail PurchaseInterDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.PurchaseInterDetail(map);
	}

	/**
	 * 新建采购单方法
	 * 
	 * @param paramsStr
	 * @return
	 */
	public ResultData addpPurchase(String paramsStr) {
		ResultData data = new ResultData();
		PurchaseDomain purchase = new PurchaseDomain();
		JSONObject obj = JSONObject.fromObject(paramsStr);
		if (obj.get("itemId") != null && !"".equals(obj.get("itemId"))) {
			purchase.setItemId((Integer)obj.get("itemId"));
		} else {
			logger.error("添加采购单 接口 ,品目itemId 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("itemId, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("goodsProv") != null && !"".equals(obj.get("goodsProv"))) {
			purchase.setGoodsProv((Integer)obj.get("goodsProv"));
			
		} else {
			logger.error("添加采购单 接口 ,省份ID goodsProv 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("goodsProv, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("goodsCity") != null && !"".equals(obj.get("goodsCity"))) {
			purchase.setGoodsCity((Integer)obj.get("goodsCity"));
		} else {
			logger.error("添加采购单 接口 ,市ID goodsCity 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("goodsCity, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("infoTitle") != null && !"".equals(obj.get("infoTitle"))) {
			purchase.setInfoTitle((String)obj.get("infoTitle"));
		} else {
			logger.error("添加采购单 接口 ,采购标题 infoTitle 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("infoTitle, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("unit") != null && !"".equals(obj.get("unit"))) {
			purchase.setUnit((Integer)obj.get("unit"));
		} else {
			logger.error("添加采购单 接口 ,单位 unit 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("unit, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("description") != null && !"".equals(obj.get("description"))) {
			purchase.setDescription((String)obj.get("description"));
		} else {
			logger.error("添加采购单 接口 ,采购详情 description 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("description, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("quantity") != null && !"".equals(obj.get("quantity"))) {
			purchase.setQuantity( new BigDecimal((String)obj.get("quantity")));
		} else {
			logger.error("添加采购单 接口 ,采购数量 quantity 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("quantity, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("priceNegotiable") != null && !"".equals(obj.get("priceNegotiable"))) {
			purchase.setPriceNegotiable((Integer)obj.get("priceNegotiable"));
		} else {
			logger.error("采购单 接口 , 价格是否面议 priceNegotiable 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("priceNegotiable, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (purchase.getPriceNegotiable() != null && purchase.getPriceNegotiable() == 0
				&& obj.get("expectPrice") != null && !"".equals(obj.get("expectPrice"))) {
			purchase.setExpectPrice(new BigDecimal((String)obj.get("expectPrice")));
		} else if ((purchase.getPriceNegotiable() != null && purchase.getPriceNegotiable() == 0)
				&& (obj.get("expectPrice") == null || "".equals(obj.get("expectPrice")))) {
			logger.error("采购单 接口 , 价格是否面议 priceNegotiable 为0时 expectPrice 为空 》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("expectPrice, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (purchase.getPriceNegotiable() != null && purchase.getPriceNegotiable() == 0
				&& obj.get("expectPriceUnit") != null && !"".equals(obj.get("expectPriceUnit"))) {
			purchase.setExpectPriceUnit((Integer)(obj.get("expectPriceUnit")));
		} else if ((purchase.getPriceNegotiable() != null && purchase.getPriceNegotiable() == 0)
				&& (obj.get("expectPriceUnit") == null || "".equals(obj.get("expectPriceUnit")))) {
			logger.error("采购单 接口 , 价格是否面议 priceNegotiable 为0时 expectPriceUnit 为空 》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("expectPriceUnit, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("priceExplain") != null && !"".equals(obj.get("priceExplain"))) {
			purchase.setPriceExplain((Integer)(obj.get("priceExplain")));
		} else {
			logger.error("采购单 接口 , 价格说明 priceExplain 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("priceExplain, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("expiryType") != null && !"".equals(obj.get("expiryType"))) {
			purchase.setExpiryType((Integer)(obj.get("expiryType")));
		} else {
			logger.error("采购单 接口 , 采购周期 expiryType 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("expiryType, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("phone") != null && !"".equals(obj.get("phone"))) {
			purchase.setPhone((String)obj.get("phone"));
		} else {
			logger.error("采购单 接口 ,手机 phone 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("phone, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		Customer customer = queryCustomer(purchase.getPhone());
		if (customer == null) {
			logger.error("采购单 接口 ,手机 " + purchase.getPhone() + " 的客户不存在》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PURCHASE_ERR_PHONE_ISNO_CODE);
			data.setErrMsg("phone, " + ResErrorCode.PURCHASE_ERR_PHONE_ISNO_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		purchase.setCustomerId(customer.getId());
		purchase.setCreatedBy(1);
		purchase.setUpdatedBy(1);
		purchase.setInfoStatus(0);
		purchase.setIsCreatedByCustomer(1);
		purchase.setIsUpdatedByCustomer(1);
		int a = addPurchase(purchase);
		if (a > 0) {
			logger.error("采购单 接口 ,手机 " + purchase.getPhone() + " 的采购单添加成功");
			data.setSuccess(true);
			data.setUnAuthorizedRequest(false);
			return data;
		} else {
			logger.error("采购单 接口 ,手机 " + purchase.getPhone() + " 采购单添加失败 》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PURCHASE_ERR_PURCHASE_ADD_CODE);
			data.setErrMsg(ResErrorCode.PURCHASE_ERR_PURCHASE_ADD_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}

	}

	/**
	 * 修改采购单方法
	 * 
	 * @param paramsStr
	 * @return
	 */
	public ResultData updatePurchaseInter(String paramsStr) {
		ResultData data = new ResultData();

		JSONObject obj = JSONObject.fromObject(paramsStr);
		PurchaseDomain purchase = null;
		if (obj.get("id") != null && !"".equals(obj.get("id"))) {
			purchase = queryPurchaseById((Integer)(obj.get("id")));
		} else {
			logger.error("修改采购单 接口 ,采购ID id 为空》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("id, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if(purchase==null){
			logger.error("采购单不存在》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			data.setErrMsg("采购单不存在, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}
		if (obj.get("itemId") != null && !"".equals(obj.get("itemId"))) {
			purchase.setItemId((Integer)(obj.get("itemId")));
		}
		if (obj.get("goodsProv") != null && !"".equals(obj.get("goodsProv"))) {
			purchase.setGoodsProv((Integer)(obj.get("goodsProv")));
		}
		if (obj.get("goodsCity") != null && !"".equals(obj.get("goodsCity"))) {
			purchase.setGoodsCity((Integer)(obj.get("goodsCity")));
		}
		if (obj.get("infoTitle") != null && !"".equals(obj.get("infoTitle"))) {
			purchase.setInfoTitle((String)obj.get("infoTitle"));
		}
		if (obj.get("description") != null && !"".equals(obj.get("description"))) {
			purchase.setDescription((String)obj.get("description"));
		}
		if (obj.get("quantity") != null && !"".equals(obj.get("quantity"))) {
			purchase.setQuantity(new BigDecimal((String)obj.get("quantity")));
		}
		if (obj.get("unit") != null && !"".equals(obj.get("unit"))) {
			purchase.setUnit((Integer)obj.get("unit"));
		} 
		
		if(obj.get("priceNegotiable") != null && !obj.get("priceNegotiable").equals(0)){
			purchase.setPriceNegotiable((Integer)(obj.get("priceNegotiable")));
			purchase.setExpectPrice(new BigDecimal(0));
		}else{
			purchase.setPriceNegotiable((Integer)(obj.get("priceNegotiable")));
			purchase.setExpectPrice(new BigDecimal((String)obj.get("expectPrice")));
		}
		if (obj.get("expectPriceUnit") != null && !"".equals(obj.get("expectPriceUnit"))) {
			purchase.setExpectPriceUnit((Integer)(obj.get("expectPriceUnit")));
		}
		
//		if (purchase.getPriceNegotiable() != null && purchase.getPriceNegotiable() == 0
//				&& obj.get("expectPrice") != null && !"".equals(obj.get("expectPrice"))) {
//			purchase.setExpectPrice(new BigDecimal((String)obj.get("expectPrice")));
//		} else if ((purchase.getPriceNegotiable() == 1
//				&& obj.get("priceNegotiable") != null && !"".equals(obj.get("priceNegotiable"))
//				&& Integer.valueOf((Integer)obj.get("priceNegotiable")) == 0)
//				&& (obj.get("expectPriceUnit") == null || "".equals(obj.get("expectPriceUnit")))) {
//			logger.error("修改采购单 接口 , 价格是否面议 priceNegotiable 为0时 期望 expectPrice 为空 》》》");
//			data.setSuccess(false);
//			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
//			data.setErrMsg("expectPrice, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
//			data.setUnAuthorizedRequest(false);
//			return data;
//		}	
//		if (obj.get("expectPriceUnit") != null && !"".equals(obj.get("expectPriceUnit"))) {
//			purchase.setExpectPriceUnit((Integer)(obj.get("expectPriceUnit")));
//		} else if ((purchase.getPriceNegotiable() == 1
//				&& obj.get("priceNegotiable") != null && !"".equals(obj.get("priceNegotiable"))
//				&& (Integer)(obj.get("priceNegotiable")) == 0)
//				&& (obj.get("expectPriceUnit") == null || "".equals(obj.get("expectPriceUnit")))) {
//			logger.error("修改采购单 接口 , 价格是否面议 priceNegotiable 为0时  价格单位 expectPriceUnit 为空 》》》");
//			data.setSuccess(false);
//			data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
//			data.setErrMsg("expectPriceUnit, " + ResErrorCode.PARAMS_NULL_ERROR_MSG);
//			data.setUnAuthorizedRequest(false);
//			return data;
//		}
//		if (obj.get("priceNegotiable") != null && !"".equals(obj.get("priceNegotiable"))) {
//			purchase.setPriceNegotiable((Integer)(obj.get("priceNegotiable")));
//		}
		if (obj.get("priceExplain") != null && !"".equals(obj.get("priceExplain"))) {
			purchase.setPriceExplain((Integer)(obj.get("priceExplain")));
		}
		if (obj.get("expiryType") != null && !"".equals(obj.get("expiryType"))) {
			purchase.setExpiryType((Integer)(obj.get("expiryType")));
		}
		if (obj.get("phone") != null && !"".equals(obj.get("phone"))) {
			purchase.setPhone((String)obj.get("phone"));
			Customer customer = queryCustomer(purchase.getPhone());
			if (customer == null) {
				logger.error("修改采购单 接口 ,手机 " + purchase.getPhone() + " 的客户不存在》》》");
				data.setSuccess(false);
				data.setErrorcode(ResErrorCode.PURCHASE_ERR_PHONE_ISNO_CODE);
				data.setErrMsg("phone, " + ResErrorCode.PURCHASE_ERR_PHONE_ISNO_MSG);
				data.setUnAuthorizedRequest(false);
				return data;
			}
			purchase.setCustomerId(customer.getId());
		}
		purchase.setIsUpdatedByCustomer(1);
//		purchase.setCreatedBy(25);
//		purchase.setUpdatedBy(25);
		purchase.setUpdatedAt(new Date());
		purchase.setInfoStatus(0);
		int a = updatePurchase(purchase);
		if (a > 0) {
			logger.error("修改采购单 接口 ,手机 " + purchase.getPhone() + " 的采购单修改成功");
			data.setSuccess(true);
			data.setUnAuthorizedRequest(false);
			return data;
		} else {
			logger.error("修改采购单 接口 ,手机 " + purchase.getPhone() + " 采购单修改失败 》》》");
			data.setSuccess(false);
			data.setErrorcode(ResErrorCode.PURCHASE_ERR_PURCHASE_UPDATE_CODE);
			data.setErrMsg(ResErrorCode.PURCHASE_ERR_PURCHASE_UPDATE_MSG);
			data.setUnAuthorizedRequest(false);
			return data;
		}

	}

	@Override
	public String queryInfoTitle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.queryInfoTitle(map);
	}
}
