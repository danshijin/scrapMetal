package com.smm.scrapMetal.bo.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.SupplyBO;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dao.ICustomerDao;
import com.smm.scrapMetal.dao.PurchaseDao;
import com.smm.scrapMetal.dao.SupplyDAO;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Delivery;
import com.smm.scrapMetal.domain.SrchTimes;
import com.smm.scrapMetal.domain.Srchword;
import com.smm.scrapMetal.domain.Supply;
import com.smm.scrapMetal.domain.SupplyImages;
import com.smm.scrapMetal.domain.User;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.dto.SupplyListView;
import com.smm.scrapMetal.util.DateUtil;
import com.smm.scrapMetal.util.PageBean;
import com.smm.scrapMetal.util.PageBeans;

import freemarker.log.Logger;

/**
 * 
 * @author hece
 *
 */
@Service
public class SupplyBOImpl implements SupplyBO{
	
	@Resource
	private SupplyDAO supplyDao;
	@Resource
	private PurchaseDao purchaseDao;
	private static Logger logger = Logger.getLogger(SupplyBOImpl.class.getName());
	@Resource
	private ICustomerDao customerDao;
	@Value("#{address['imgServerAdd']}")
    private String imgServerAddress;
	
	/**
	 * 供货单列表
	 * */
	@Override
	public Map<String,Object> supplyList(Map<String,Object> map) {
		List<SupplyListView> slist = supplyDao.supplyList(map);
		PageBean page = new PageBean(10, map.get("pno") == null ? 1 : Integer.valueOf(map.get("pno").toString()), slist.size());
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		List<SupplyListView> supplylist = supplyDao.supplyList(map);
		Map<String,Object> resultmap = new HashMap<String, Object>();
		resultmap.put("supplylist", supplylist);
		resultmap.put("totalRecords", slist.size());//总条数
		resultmap.put("totalPage", page.getTotalPages());//总页数
		return resultmap;
	}

	/**
	 * 添加供货单
	 * */
	@Override
	public int addSupply(Map<String,Object> map,HttpServletRequest request) {
		Supply supp = new Supply();
		Customer cus = new Customer();
		if(map.get("itemid") != null && !map.get("itemid").equals("") 
				&& map.get("prov") != null && !map.get("prov").equals("")
				&& map.get("infoTitle") != null && !map.get("infoTitle").equals("")
				&& map.get("description") != null && !map.get("description").equals("")
				&& map.get("usableQuantity") != null && !map.get("usableQuantity").equals("")
				//&& map.get("price") != null && !map.get("price").equals("")
				//&& map.get("priceUnit") != null && !map.get("priceUnit").equals("")
				&& map.get("priceExplain") != null && !map.get("priceExplain").equals("")
				&& map.get("delivery") != null && !map.get("delivery").equals("")
				&& map.get("expiryType") != null && !map.get("expiryType").equals("")
				&& map.get("phone") != null && !map.get("phone").equals("")){
			User user = (User)request.getSession().getAttribute("userInfo");
			if(map.get("priceNegotiable") != null && !map.get("priceNegotiable").equals("")){
				if(map.get("price") != null && !map.get("price").equals("") && map.get("priceUnit") != null && !map.get("priceUnit").equals("")){
					supp.setPrice(new BigDecimal(map.get("price").toString()));
					supp.setPriceUnit(Integer.parseInt(map.get("priceUnit").toString()));
					String price = map.get("price").toString();
					if(price.length() > 9){
						return 4;
					}
				}
				
				String usableQuantity = map.get("usableQuantity").toString();
				if(usableQuantity.length() > 11){
					return 4;
				}
				
				
				//通过手机号码查询创建人
				cus = purchaseDao.queryCustomer(map.get("phone").toString());
				if(cus == null){
					return 2;
				}
				//图片
				if(map.get("himg") == null || map.get("himg").equals("")){
					return 3;
				}
				supp.setItemId(Integer.parseInt(map.get("itemid").toString()));
				supp.setCustomerId(cus.getId());
				supp.setIsCreatedByCustomer(0);
				supp.setIsUpdatedByCustomer(0);
				supp.setGoodsProv(Integer.parseInt(map.get("prov").toString()));
				if(map.get("city") == null){
					supp.setGoodsCity(Integer.parseInt(map.get("prov").toString()));
				}else{
					supp.setGoodsCity(Integer.parseInt(map.get("city").toString()));
				}
				supp.setInfoTitle(map.get("infoTitle").toString());
				supp.setDescription(map.get("description").toString());
				supp.setFrozenQuantity(new BigDecimal(0));
				supp.setUsableQuantity(new BigDecimal(map.get("usableQuantity").toString()));
				supp.setQuantityUnit(0);
				supp.setPriceNegotiable(Integer.parseInt(map.get("priceNegotiable").toString()));
				supp.setPriceExplain(Integer.parseInt(map.get("priceExplain").toString()));
				supp.setDelivery(Integer.parseInt(map.get("delivery").toString()));
				supp.setExpiryType(Integer.parseInt(map.get("expiryType").toString()));
				supp.setPhone(map.get("phone").toString());
				supp.setInfoStatus(1);
				supp.setUpdatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
				supp.setCreatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
				if(user != null){
					supp.setCreatedBy(user.getId());
					supp.setUpdatedBy(user.getId());
				}
				System.out.println("====================="+Timestamp.valueOf(new DateUtil().currentDate()));
				int i = supplyDao.addSupply(supp);
				
				String imgAddr = map.get("himg").toString();
				String str[] = imgAddr.split(",");
				for(int j=0;j<str.length;j++){
					System.out.println(str[j]);
					SupplyImages suImg = new SupplyImages();
					suImg.setSupplyId(supp.getId());
					suImg.setName(str[j]);
					suImg.setImgOrder(j);
					supplyDao.addSupplyImages(suImg);
				}
				
				return i;
			}else{
				if(map.get("price") == null || map.get("price").equals("") || map.get("priceUnit") == null){
					return 5;
				}
				String price = map.get("price").toString();
				if(price.length() > 9){
					return 4;
				}
				String usableQuantity = map.get("usableQuantity").toString();
				if(usableQuantity.length() > 11){
					return 4;
				}
				//通过手机号码查询创建人
				cus = purchaseDao.queryCustomer(map.get("phone").toString());
				if(cus == null){
					return 2;
				}
				//图片
				if(map.get("himg") == null || map.get("himg").equals("")){
					return 3;
				}
				supp.setItemId(Integer.parseInt(map.get("itemid").toString()));
				supp.setCustomerId(cus.getId());
				supp.setIsCreatedByCustomer(0);
				supp.setIsUpdatedByCustomer(0);
				supp.setGoodsProv(Integer.parseInt(map.get("prov").toString()));
				if(map.get("city") == null){
					supp.setGoodsCity(Integer.parseInt(map.get("prov").toString()));
				}else{
					supp.setGoodsCity(Integer.parseInt(map.get("city").toString()));
				}
				supp.setPrice(new BigDecimal(map.get("price").toString()));
				supp.setPriceUnit(Integer.parseInt(map.get("priceUnit").toString()));
				supp.setInfoTitle(map.get("infoTitle").toString());
				supp.setDescription(map.get("description").toString());
				supp.setFrozenQuantity(new BigDecimal(0));
				supp.setUsableQuantity(new BigDecimal(map.get("usableQuantity").toString()));
				supp.setQuantityUnit(0);
				//supp.setPriceNegotiable(Integer.parseInt(map.get("priceNegotiable").toString()));
				supp.setPriceExplain(Integer.parseInt(map.get("priceExplain").toString()));
				supp.setDelivery(Integer.parseInt(map.get("delivery").toString()));
				supp.setExpiryType(Integer.parseInt(map.get("expiryType").toString()));
				supp.setPhone(map.get("phone").toString());
				supp.setInfoStatus(1);
				supp.setUpdatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
				supp.setCreatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
				if(user != null){
					supp.setCreatedBy(user.getId());
					supp.setUpdatedBy(user.getId());
				}
				System.out.println("====================="+Timestamp.valueOf(new DateUtil().currentDate()));
				
				int i = supplyDao.addSupply(supp);
				
	    		String imgAddr = map.get("himg").toString();
				String str[] = imgAddr.split(",");
				for(int j=0;j<str.length;j++){
					System.out.println(str[j]);
					SupplyImages suImg = new SupplyImages();
					suImg.setSupplyId(supp.getId());
					suImg.setName(str[j]);
					suImg.setImgOrder(j);
					supplyDao.addSupplyImages(suImg);
				}
				return i;
			}
		}else{
			return 0;
		}
	}

	/**
	 * 通过ID查询详情
	 * */
	@Override
	public SupplyListView supplyDetailById(String suppid) {
		return supplyDao.supplyDetailById(suppid);
	}
	
	/**
	 * 修改供货单(后台)
	 * */
	@Override
	public int updateSupply(HttpServletRequest request) {
		Supply supp = new Supply();
		String itemId = "";
		String goodsProy = "";
		String goodsCity = "";
		String infoTitle = "";
		String description = "";
		String quantity = "";
		String priceNegotiable = "";
		String delivery = "";
		String expiryType = "";
		String priceUnit = "";
		String priceExplain = "";
		String phone = "";
		String price = "";
		String supplyId = "";
		String usableQuantity = "";
		Integer createdBy;
		Integer infoStatus;
		String auditedComment = "";
		if(request.getParameter("supplyId") != null && !request.getParameter("supplyId").equals("")){
			supplyId = request.getParameter("supplyId");
			SupplyListView supplist = supplyDao.supplyDetailById(supplyId);
			supp.setId(Integer.parseInt(supplyId));
			if(request.getParameter("itemid") != null && !request.getParameter("itemid").equals("")){
				itemId = request.getParameter("itemid");
				supp.setItemId(Integer.parseInt(itemId));
			}
			if(request.getParameter("goodsProv") != null && !request.getParameter("goodsProv").equals("")){
				goodsProy = request.getParameter("goodsProv");
				supp.setGoodsProv(Integer.parseInt(goodsProy));
			}
			if(request.getParameter("goodsCity") != null && !request.getParameter("goodsCity").equals("")){
				goodsCity = request.getParameter("goodsCity");
				supp.setGoodsCity(Integer.parseInt(goodsCity));
			}else{
				goodsCity = request.getParameter("goodsProv");
				supp.setGoodsCity(Integer.parseInt(goodsCity));
			}
			if(request.getParameter("infoTitle") != null && !request.getParameter("infoTitle").equals("")){
				infoTitle = request.getParameter("infoTitle");
				supp.setInfoTitle(infoTitle);
			}
			if(request.getParameter("description") != null && !request.getParameter("description").equals("")){
				description = request.getParameter("description");
				supp.setDescription(description);
			}
			if(request.getParameter("quantity") != null && !request.getParameter("quantity").equals("")){
				quantity = request.getParameter("quantity");
				supp.setQuantityUnit(Integer.parseInt(quantity));
			}
			if(request.getParameter("price") != null && !request.getParameter("price").equals("")){
				price = request.getParameter("price");
				supp.setPrice(new BigDecimal(price));
				supp.setPriceNegotiable(0);
				if(price.length() >= 10){
					return 2;
				}
			}else{
				System.out.println(">>"+request.getParameter("priceNegotiable"));
				if(request.getParameter("priceNegotiable") == null){
					return 4;
				}
			}
			if(request.getParameter("priceNegotiable") != null && !request.getParameter("priceNegotiable").equals(0)){
				priceNegotiable = request.getParameter("priceNegotiable");
				supp.setPriceNegotiable(Integer.parseInt(priceNegotiable));
				supp.setPrice(new BigDecimal(0));
			}
			if(request.getParameter("delivery") != null && !request.getParameter("delivery").equals("")){
				delivery = request.getParameter("delivery");
				supp.setDelivery(Integer.parseInt(delivery));
			}
			if(request.getParameter("expiryType") != null && !request.getParameter("expiryType").equals("")){
				expiryType = request.getParameter("expiryType");
				supp.setExpiryType(Integer.parseInt(expiryType));
			}
			if(request.getParameter("priceUnit") != null && !request.getParameter("priceUnit").equals("")){
				priceUnit = request.getParameter("priceUnit");
				supp.setPriceUnit(Integer.parseInt(priceUnit));
			}
			if(request.getParameter("priceExplain") != null && !request.getParameter("priceExplain").equals("")){
				priceExplain = request.getParameter("priceExplain");
				supp.setPriceExplain(Integer.parseInt(priceExplain));
			}
			if(request.getParameter("phone") != null && !request.getParameter("phone").equals("")){
				phone = request.getParameter("phone");
				supp.setPhone(phone);
			}
			
//			if(request.getParameter("createdBy") != null && !request.getParameter("createdBy").equals("")){
//				createdBy = Integer.valueOf(request.getParameter("createdBy"));
//				supp.setUpdatedBy(createdBy);
//			}
			supp.setUpdatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
			if(request.getParameter("infoStatus") != null && !request.getParameter("infoStatus").equals("")){
				infoStatus = Integer.valueOf(request.getParameter("infoStatus"));
				supp.setInfoStatus(infoStatus);
				if(infoStatus.equals(3)){
					if(supplist != null && supplist.getInfoStatus() != null){
						supp.setLastInfoStatus(supplist.getInfoStatus());
					}
				}
			}
			if(request.getParameter("auditedComment") != null && !request.getParameter("auditedComment").equals("")){
				auditedComment = request.getParameter("auditedComment");
				supp.setAuditedComment(auditedComment);
			}
			if(request.getParameter("usableQuantity") != null && !request.getParameter("usableQuantity").equals("")){
				usableQuantity = request.getParameter("usableQuantity");
				supp.setUsableQuantity(new BigDecimal(usableQuantity));
				if(usableQuantity.length() > 11){
					return 2;
				}
			}
			User user = (User)request.getSession().getAttribute("userInfo");
			if(user != null){
				supp.setIsUpdatedByCustomer(0);
				supp.setUpdatedBy(user.getId());
			}
			if(request.getParameter("hiddimg") != null && !request.getParameter("hiddimg").equals("")){
				supplyDao.deleteSupplyImages(supplyId);
				String imgAddr = request.getParameter("hiddimg");
				String str[] = imgAddr.split(",");
				for(int j=0;j<str.length;j++){
					System.out.println(str[j]);
					SupplyImages suImg = new SupplyImages();
					suImg.setSupplyId(Integer.valueOf(supplyId));
					suImg.setName(str[j]);
					suImg.setImgOrder(j);
					supplyDao.addSupplyImages(suImg);
				}
			}else{
				return 3;
			}
			supplyDao.updateSupplyAPI(supp);
			return 1;
		}
		return 0;
	}

	/**
	 * 批量删除
	 * */
	@Override
	public void deleteSupplyById(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		supplyDao.deleteSupplyById(map);
	}

	/**
	 * 批量更新
	 * */
	@Override
	public void updateSupplyById(String id,String status,String date) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("date",date);
		map.put("status",status);
		supplyDao.updateSupplyById(map);
	}

	/**
	 * 导出excel
	 */
	@Override
	public List<SupplyListView> newExcleSupplyList(Map<String, Object> map) {
		return supplyDao.supplyList(map);
	}

	/**
	 * 供货单列表(接口)
	 */
	@Override
	public ResultData supplyListAPI(String result) {
		ResultData data = new ResultData();
		JSONObject obj = JSONObject.fromObject(result);
		String infoTitle = "";
		String itemid = "";
		String goodsProy = "";
		String goodsCity = "";
		String sort = "";
		String sortType = "";
		int currentPage = 1;//当前页
		int pageNum = 10;//每页显示条数
		if(!obj.isEmpty()){
			if(obj.get("infoTitle") != null && !obj.get("infoTitle").equals("")){
				infoTitle = obj.get("infoTitle").toString();
			}
			if(obj.get("itemId") != null && !obj.get("itemId").equals("")){
				itemid = obj.get("itemId").toString();
			}
			if(obj.get("goodsProy") != null && !obj.get("goodsProy").equals("")){
				goodsProy = obj.get("goodsProy").toString();
			}
			if(obj.get("goodsCity") != null && !obj.get("goodsCity").equals("")){
				goodsCity = obj.get("goodsCity").toString();
			}
			if(obj.get("sort") != null && !obj.get("sort").equals("")){
				sort = obj.get("sort").toString();
			}
			if(obj.get("sortType") != null && !obj.get("sortType").equals("")){
				sortType = obj.get("sortType").toString();
			}
			if(obj.get("currentPage") != null && !obj.get("currentPage").equals("")){
				currentPage = Integer.valueOf(obj.get("currentPage").toString());
			}
			if(obj.get("pageNum") != null && !obj.get("pageNum").equals("")){
				pageNum = Integer.parseInt(obj.get("pageNum").toString());
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("infoTitle", infoTitle);
		map.put("itemid", itemid);
		map.put("goodsProy", goodsProy);
		map.put("goodsCity", goodsCity);
		map.put("sort", sort);
		map.put("sortType", sortType);
		
		List<SupplyListView> supplyListsize = supplyDao.supplyListAPI(map);
		PageBeans page = new PageBeans(pageNum, currentPage, supplyListsize.size());
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		System.out.println("///////////////////infoTitle="+infoTitle+"///goodsProy="+goodsProy+"//goodsCity="+goodsCity+"//itemid="+itemid+"//sort="+sort+"//sortType="+sortType);
		List<SupplyListView> supplyList = new ArrayList<SupplyListView>();
		supplyList = supplyDao.supplyListAPI(map);
		List<SupplyListView> slist = new ArrayList<SupplyListView>();
		for(int i=0;i<supplyList.size();i++){
			SupplyListView s = new SupplyListView();
			s = supplyList.get(i);
			SupplyImages simg = supplyDao.suppImg(s.getId().toString());
			if(simg != null){
				s.setSupplyImg(imgServerAddress+simg.getName());
			}else{
				s.setSupplyImg("");
			}
			slist.add(s);
		}
		Map<String,Object> resultmap = new HashMap<String, Object>();
		resultmap.put("supplyList", slist);
		resultmap.put("size", supplyListsize.size());
		JSONArray jsobj = JSONArray.fromObject(resultmap);
		if(supplyList.size()>0){
			data.setSuccess(true);
            data.setUnAuthorizedRequest(false);
            data.setResult(jsobj.toString());
		}else{
			data.setSuccess(false);
            data.setErrorcode(ResErrorCode.PURCHASE_ERR_EMPTY_CODE);
            data.setErrMsg(ResErrorCode.PURCHASE_ERR_EMPTY_MSG);
            data.setUnAuthorizedRequest(false);
		}
		return data;
	}

	/**
	 * 添加供货单(接口)
	 */
	@Override
	public ResultData addSupplyAPI(String paramsStr) {
		ResultData data = new ResultData();
	    JSONObject obj = JSONObject.fromObject(paramsStr);
		Supply supp = new Supply();
		int i = 0;
		//User user = (User)request.getSession().getAttribute("userInfo");
		String imageAddress = "";//图片
		if(!obj.isEmpty()){
			if(obj.get("itemId") != null && !obj.get("itemId").equals("")
					&& obj.get("goodsProy") != null && !obj.get("goodsProy").equals("")
					&& obj.get("goodsCity") != null && !obj.get("goodsCity").equals("")
					&& obj.get("infoTitle") != null && !obj.get("infoTitle").equals("")
					&& obj.get("description") != null && !obj.get("description").equals("")
					&& obj.get("quantity") != null && !obj.get("quantity").equals("")
					&& obj.get("delivery") != null && !obj.get("delivery").equals("")
					&& obj.get("expiryType") != null && !obj.get("expiryType").equals("")
					&& obj.get("priceExplain") != null && !obj.get("priceExplain").equals("")
					&& obj.get("phone") != null && !obj.get("phone").equals("")
					&& obj.get("imageAddress") != null && !obj.get("imageAddress").equals("")){
				if(obj.get("priceNegotiable") != null && !obj.get("priceNegotiable").equals("")) {
					if(obj.get("price") != null && !obj.get("price").equals("") && obj.get("priceUnit") != null && !obj.get("priceUnit").equals("")){
						supp.setPrice(new BigDecimal(obj.get("price").toString()));
						supp.setPriceUnit(Integer.parseInt(obj.get("priceUnit").toString()));
					}
					
					//通过手机号码查询创建人
					Customer cus = purchaseDao.queryCustomer(obj.get("phone").toString());
					supp.setItemId(Integer.parseInt(obj.get("itemId").toString()));
					supp.setCustomerId(cus.getId());
					supp.setIsCreatedByCustomer(1);
					supp.setIsUpdatedByCustomer(1);
					supp.setGoodsProv(Integer.parseInt(obj.get("goodsProy").toString()));
					supp.setGoodsCity(Integer.parseInt(obj.get("goodsCity").toString()));
					supp.setInfoTitle(obj.get("infoTitle").toString());
					supp.setDescription(obj.get("description").toString());
					supp.setUsableQuantity(new BigDecimal((String)obj.get("quantity")));
					supp.setQuantityUnit(0);
					supp.setPriceNegotiable(Integer.parseInt(obj.get("priceNegotiable").toString()));
					
					supp.setPriceExplain(Integer.parseInt(obj.get("priceExplain").toString()));
					supp.setDelivery(Integer.parseInt(obj.get("delivery").toString()));
					supp.setExpiryType(Integer.parseInt(obj.get("expiryType").toString()));
					supp.setPhone(obj.get("phone").toString());
					supp.setInfoStatus(0);
					//supp.setCreatedBy(1);
					supp.setCreatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
					supp.setUpdatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
					supp.setFrozenQuantity(new BigDecimal(0));
					
					i = supplyDao.addSupply(supp);
					if(i == 1){
						imageAddress = obj.get("imageAddress").toString();
						System.out.println("图片信息="+imageAddress);
						JSONArray array = JSONArray.fromObject(imageAddress);
						for (int j = 0; j < array.size(); j++) {
							System.out.println("图片名称="+array.get(j));
							SupplyImages images = new SupplyImages();
							images.setSupplyId(supp.getId());
							images.setName(array.get(j).toString());
							images.setImgOrder(j);
							supplyDao.addSupplyImages(images);
						}
						data.setSuccess(true);
						data.setUnAuthorizedRequest(false);
						data.setResult("");
					}else{
						data.setSuccess(false);
						data.setErrorcode(ResErrorCode.SUPPLYINSERT_ERROR_CODE);
						data.setErrMsg(ResErrorCode.SUPPLYINSERT_ERROR_MSG);
						data.setUnAuthorizedRequest(false);
					}
				}else {
					if(obj.get("price") == null && obj.get("price").equals("") && obj.get("priceUnit") == null && obj.get("priceUnit").equals("")){
						data.setSuccess(false);
			            data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
			            data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
			            data.setUnAuthorizedRequest(false);
			            return data;
					}
//					String price = obj.get("price").toString();
//					if(price.length() > 9){
//						data.setSuccess(false);
//						data.setErrMsg("价格超出限制");
//						data.setUnAuthorizedRequest(false);
//						return data;
//					}
//					String quantity = obj.get("quantity").toString();
//					if(quantity.length() > 11){
//						data.setSuccess(false);
//						data.setErrMsg("数量超出限制");
//						data.setUnAuthorizedRequest(false);
//						return data;
//					}
					//通过手机号码查询创建人
					Customer cus = purchaseDao.queryCustomer(obj.get("phone").toString());
					supp.setItemId(Integer.parseInt(obj.get("itemId").toString()));
					supp.setCustomerId(cus.getId());
					supp.setIsCreatedByCustomer(1);
					supp.setIsUpdatedByCustomer(1);
					supp.setGoodsProv(Integer.parseInt(obj.get("goodsProy").toString()));
					supp.setGoodsCity(Integer.parseInt(obj.get("goodsCity").toString()));
					supp.setInfoTitle(obj.get("infoTitle").toString());
					supp.setDescription(obj.get("description").toString());
					supp.setUsableQuantity(new BigDecimal(obj.get("quantity").toString()));
					supp.setQuantityUnit(0);
					//supp.setPriceNegotiable(Integer.parseInt(obj.get("priceNegotiable").toString()));
					supp.setPrice(new BigDecimal(obj.get("price").toString()));
					supp.setPriceUnit(Integer.parseInt(obj.get("priceUnit").toString()));
					supp.setPriceExplain(Integer.parseInt(obj.get("priceExplain").toString()));
					supp.setDelivery(Integer.parseInt(obj.get("delivery").toString()));
					supp.setExpiryType(Integer.parseInt(obj.get("expiryType").toString()));
					supp.setPhone(obj.get("phone").toString());
					supp.setInfoStatus(0);
					//supp.setCreatedBy(1);
					supp.setCreatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
					supp.setUpdatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
					supp.setFrozenQuantity(new BigDecimal(0));
					
					i = supplyDao.addSupply(supp);
					if(i == 1){
						imageAddress = obj.get("imageAddress").toString();
						System.out.println("图片信息="+imageAddress);
						JSONArray array = JSONArray.fromObject(imageAddress);
						for (int j = 0; j < array.size(); j++) {
							System.out.println("图片名称="+array.get(j));
							SupplyImages images = new SupplyImages();
							images.setSupplyId(supp.getId());
							images.setName(array.get(j).toString());
							images.setImgOrder(j + 1);
							supplyDao.addSupplyImages(images);
						}
						data.setSuccess(true);
						data.setUnAuthorizedRequest(false);
						data.setResult("");
					}else{
						data.setSuccess(false);
						data.setErrorcode(ResErrorCode.SUPPLYINSERT_ERROR_CODE);
						data.setErrMsg(ResErrorCode.SUPPLYINSERT_ERROR_MSG);
						data.setUnAuthorizedRequest(false);
					}
		        }
			}else {
	            data.setSuccess(false);
	            data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
	            data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
	            data.setUnAuthorizedRequest(false);
	        }
		}else {
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
		return data;
	}

	/**
	 * 通过ID查询详情(接口)
	 */
	@Override
	public ResultData supplyDetailByIdAPI(String paramsStr) {
		ResultData data = new ResultData();
	    JSONObject obj = JSONObject.fromObject(paramsStr);
		String supplyId = "";
		String openId = "";
		String customerid = "";
		SupplyListView suppDetail = new SupplyListView();
		if (obj.get("supplyId") != null && !obj.get("supplyId").equals("")) {
			supplyId = obj.get("supplyId").toString();
			suppDetail = supplyDao.supplyDetailById(supplyId);
            if (suppDetail != null) {
            	if(obj.get("openId") == null){
            		//有多少赞
                	int zan = supplyDao.queryUserZan(supplyId);
                	//是否点赞
                	suppDetail.setWhetherZan(2);
                	//是否收藏
                	suppDetail.setWhetherFavorite(2);
                	suppDetail.setSumZan(zan);
            	}else{
            		openId = obj.get("openId").toString();
            		Customer cus = customerDao.showClientCustomerDetail(openId);
            		if(cus != null){
            			customerid = cus.getId().toString();
            		}
            		Map<String,Object> map = new HashMap<String, Object>();
            		map.put("id", supplyId);
            		map.put("customerId", customerid);
            		//有多少赞
            		int zan = supplyDao.queryUserZan(supplyId);
            		//是否点赞
            		int whetherzan = supplyDao.queryUserWhetherZan(map);
            		if(whetherzan == 0){
            			suppDetail.setWhetherZan(1);
            		}else{
            			suppDetail.setWhetherZan(0);
            		}
            		//是否收藏
            		int shouCang = supplyDao.whetherShouCang(map);
            		if(shouCang == 0){
            			suppDetail.setWhetherFavorite(1);
            		}else{
            			suppDetail.setWhetherFavorite(0);
            		}
            		suppDetail.setSumZan(zan);
            	}
            	//查询图片
            	List<SupplyImages> img = supplyDao.querySupplyImages(supplyId);
            	List<SupplyImages> suppimg = new ArrayList<SupplyImages>();
            	for(int i=0;i<img.size();i++){
            		SupplyImages suImg = new SupplyImages();
            		suImg = img.get(i);
            		suImg.setName(imgServerAddress+suImg.getName());
            		suppimg.add(suImg);
            	}
            	
             	suppDetail.setSupplyImages(suppimg);
            	//suppDetail.setWhetherZan(whetherzan);
            	//suppDetail.setShouCang(shouCang);
                JSONObject suppObj = JSONObject.fromObject(suppDetail);
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
                data.setResult(suppObj.toString());
            } else {
                logger.error("没有数据");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PURCHASE_ERR_EMPTY_CODE);
                data.setErrMsg(ResErrorCode.PURCHASE_ERR_EMPTY_MSG);
                data.setUnAuthorizedRequest(false);
            }
        } else {
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
	}

	/**
	 * 修改供货单(接口)
	 */
	@Override
	public ResultData updateSupplyAPI(String paramsStr) {
		ResultData data = new ResultData();
	    JSONObject obj = JSONObject.fromObject(paramsStr);
		Supply supp = new Supply();
		String itemId = "";
		String goodsProy = "";
		String goodsCity = "";
		String infoTitle = "";
		String description = "";
		String quantity = "";
		String priceNegotiable = "";
		String delivery = "";
		String expiryType = "";
		String priceUnit = "";
		String priceExplain = "";
		String phone = "";
		String price = "";
		String supplyId = "";
		if(!obj.isEmpty()){
			if(obj.get("supplyId") != null && !obj.get("supplyId").equals("")){
				supplyId = obj.get("supplyId").toString();
				supp.setId(Integer.parseInt(supplyId));
				if(obj.get("itemId") != null && !obj.get("itemId").equals("")){
					itemId = obj.get("itemId").toString();
					supp.setItemId(Integer.parseInt(itemId));
				}
				if(obj.get("goodsProy") != null && !obj.get("goodsProy").equals("")){
					goodsProy = obj.get("goodsProy").toString();
					supp.setGoodsProv(Integer.parseInt(goodsProy));
				}
				if(obj.get("goodsCity") != null && !obj.get("goodsCity").equals("")){
					goodsCity = obj.get("goodsCity").toString();
					supp.setGoodsCity(Integer.parseInt(goodsCity));
				}
				if(obj.get("infoTitle") != null && !obj.get("infoTitle").equals("")){
					infoTitle = obj.get("infoTitle").toString();
					supp.setInfoTitle(infoTitle);
				}
				if(obj.get("description") != null && !obj.get("description").equals("")){
					description = obj.get("description").toString();
					supp.setDescription(description);
				}
				if(obj.get("quantity") != null && !obj.get("quantity").equals("")){
					quantity = obj.get("quantity").toString();
					supp.setUsableQuantity(new BigDecimal(quantity));
				}
				if(obj.get("price") != null && !obj.get("price").equals("")){
					price = obj.get("price").toString();
					supp.setPrice(new BigDecimal(price));
					supp.setPriceNegotiable(0);
				}
				if(obj.get("priceNegotiable") != null && !obj.get("priceNegotiable").equals(0)){
					priceNegotiable = obj.get("priceNegotiable").toString();
					supp.setPriceNegotiable(Integer.parseInt(priceNegotiable));
					supp.setPrice(new BigDecimal(0));
				}
				if(obj.get("delivery") != null && !obj.get("delivery").equals("")){
					delivery = obj.get("delivery").toString();
					supp.setDelivery(Integer.parseInt(delivery));
				}
				if(obj.get("expiryType") != null && !obj.get("expiryType").equals("")){
					expiryType = obj.get("expiryType").toString();
					supp.setExpiryType(Integer.parseInt(expiryType));
				}
				if(obj.get("priceUnit") != null && !obj.get("priceUnit").equals("")){
					priceUnit = obj.get("priceUnit").toString();
					supp.setPriceUnit(Integer.parseInt(priceUnit));
				}
				if(obj.get("priceExplain") != null && !obj.get("priceExplain").equals("")){
					priceExplain = obj.get("priceExplain").toString();
					supp.setPriceExplain(Integer.parseInt(priceExplain));
				}
				if(obj.get("phone") != null && !obj.get("phone").equals("")){
					phone = obj.get("phone").toString();
					supp.setPhone(phone);
				}
				
				supp.setUpdatedAt(Timestamp.valueOf(new DateUtil().currentDate()));
				supp.setInfoStatus(0);
				//supp.setPriceUnit(0);
				supplyDao.updateSupplyAPI(supp);
				
					if(obj.get("imageAddress") != null && !obj.get("imageAddress").equals("")){
						//删除供货单原有图片
						supplyDao.deleteSupplyImages(supplyId);
						String imageAddress = obj.get("imageAddress").toString();
						System.out.println("图片信息="+imageAddress);
						JSONArray array = JSONArray.fromObject(imageAddress);
						for (int j = 0; j < array.size(); j++) {
							System.out.println("图片名称="+array.get(j));
							SupplyImages images = new SupplyImages();
							images.setSupplyId(Integer.valueOf(supplyId));
							images.setName(array.get(j).toString());
							images.setImgOrder(j);
							supplyDao.addSupplyImages(images);
						}
					}
					data.setSuccess(true);
					data.setUnAuthorizedRequest(false);
					data.setResult("");
				}else {
					data.setSuccess(false);
					data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
					data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
					data.setUnAuthorizedRequest(false);
				}
			}else {
				data.setSuccess(false);
				data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
				data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
				data.setUnAuthorizedRequest(false);
		}
		return data;
	}

	/**
	 * 常用搜索列表
	 */
	@Override
	public ResultData commonSearchList() {
		ResultData data = new ResultData();
		List<Srchword> srch = new ArrayList<Srchword>();
		srch = supplyDao.commonSearchList();
        if (srch.size() > 0) {
            JSONArray suppObj = JSONArray.fromObject(srch);
            data.setSuccess(true);
            data.setUnAuthorizedRequest(false);
            data.setResult(suppObj.toString());
        } else {
            logger.error("没查数据");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.PURCHASE_ERR_EMPTY_CODE);
            data.setErrMsg(ResErrorCode.PURCHASE_ERR_EMPTY_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
	}

	/**
	 * 列表模糊查找
	 */
	@Override
	public ResultData searchSupplyList(String paramsStr) {
		ResultData data = new ResultData();
	    JSONObject obj = JSONObject.fromObject(paramsStr);
		List<SupplyListView> supp = new ArrayList<SupplyListView>();
		if (obj.get("name") != null && !obj.get("name").equals("")) {
			String name = obj.get("name").toString();
			supp = supplyDao.searchSupplyList(name);
            if (supp.size() > 0) {
            	JSONArray suppObj = JSONArray.fromObject(supp);
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
                data.setResult(suppObj.toString());
            } else {
                logger.error("没有数据!");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PURCHASE_ERR_EMPTY_CODE);
                data.setErrMsg(ResErrorCode.PURCHASE_ERR_EMPTY_MSG);
                data.setUnAuthorizedRequest(false);
            }
        } else {
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
	}

	/**
	 * 模糊查找通过title
	 */
	@Override
	public ResultData searchSupplyListByTitle(String paramsStr) {
		ResultData data = new ResultData();
	    JSONObject obj = JSONObject.fromObject(paramsStr);
		if (obj.get("name") != null && !obj.get("name").equals("")) {
			//通过名称查找是否存在
			SrchTimes stime = supplyDao.queryByName(obj.get("name").toString());
            if (stime == null) {
            	SrchTimes st = new SrchTimes();
            	st.setName(obj.get("name").toString());
            	st.setSrchTimes(1);
            	//添加
            	supplyDao.insertSrchTimes(st);
            } else {
                //修改
            	Map<String,Object> map = new HashMap<String, Object>();
            	map.put("id",stime.getId());
            	map.put("srchTimes", stime.getSrchTimes() + 1);
            	supplyDao.updateSrchTimes(map);
            }
            Map<String,Object> smap = new HashMap<String, Object>();
            smap.put("infoTitle", obj.get("name").toString());
            //通过标题查找供货单列表
            List<SupplyListView> supplyList = supplyDao.supplyListAPI(smap);
            Map<String,Object> resultmap = new HashMap<String, Object>();
    		resultmap.put("supplyList", supplyList);
    		resultmap.put("size", supplyList.size());
    		JSONArray jsobj = JSONArray.fromObject(resultmap);
            if (supplyList.size() > 0) {
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
                data.setResult(jsobj.toString());
            } else {
                logger.error("没有数据!");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PURCHASE_ERR_EMPTY_CODE);
                data.setErrMsg(ResErrorCode.PURCHASE_ERR_EMPTY_MSG);
                data.setUnAuthorizedRequest(false);
            }
        } else {
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
	}
	
	public static void main(String []args){
//		 String json = "[{\"a\":\"111\",\"b\":\"222\",\"c\":\"333\"},{\"a\":\"1000\",\"b\":\"2000\",\"c\":\"000\"},{\"a\":\"999\",\"b\":\"300\",\"c\":\"700\"}]";
//         String str = "[{\"a\":\"111\",\"b\":\"222\",\"c\":\"333\"}]";
//		 JSONArray jsonArr = JSONArray.fromObject(str);
//         String a[] = new String[jsonArr.size()];
//         //String b[] = new String[jsonArr.size()];
//         //String c[] = new String[jsonArr.size()];
//         for (int i = 0; i < jsonArr.size(); i++) {
//             a[i] = jsonArr.getJSONObject(i).getString("a");
//             //b[i] = jsonArr.getJSONObject(i).getString("b");
//             //c[i] = jsonArr.getJSONObject(i).getString("c");
//         }
//         for (int i = 0; i < a.length; i++) {
//            System.out.print(a[i]+" ");
//           // System.out.print(b[i]+" ");
//            //System.out.print(c[i]);
//            //System.out.println();
//         }
		//JSONArray json = JSONArray.fromObject("");
     }

	/**
	 * 国内的交货方式
	 */
	@Override
	public List<Delivery> queryDomestic() {
		return supplyDao.queryDomestic();
	}

	/**
	 * 国外的交货方式
	 */
	@Override
	public List<Delivery> queryAbroad() {
		return supplyDao.queryAbroad();
	}
}