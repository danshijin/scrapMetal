package com.smm.scrapMetal.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购单 接口使用
 * @author tantaigen
 *
 */
public class PurchaseInterDetail {
	private  Integer id;  			     //主键         
	
	private  Integer itemId;	     	 //废料id
	private  String itemName;//品目名称
	   	
	private String customerName;//客户名称
	
	private  Integer goodsProv;     	 //对应AREA的省id 
	private  String goodsProvName;		//省份名称
	
	private  Integer goodsCity;    		 //对应AREA的市id   
	
	private  String goodsCityName;		//市名称
	
	private  String infoTitle;  		 //信息标题         
	
	private  String description; 		 //采购详情         
	
	private  BigDecimal quantity;			 //采购数量           
	
	private  Integer unit;				 //单位 0 吨          
	
	private  Integer priceNegotiable;	 //价格是否面议             
	
	private  BigDecimal expectPrice;     //期望价格 
	
	
	private  Integer expectPriceUnit; 	 //0 人民币 1 美元                                
    
	private  Integer priceExplain; 		//价格说明  对应 sp_priceExplain                
	                                                                                                                                                                               
	private  Integer expiryType; 		//信息有效期类型  0 一周 1 一个月      
	                                       	                                                                                                                                                                               
	private  Date updatedAt; 		 	//更新时间  
	private String whetherFavorite;      //是否收藏	
	private String entTypes;      //买家类型
	private String  entTypesName;//买家类型名称
	private String sumZan;      //共多少赞
	private String whetherZan;      //是否点赞
	
	private String weChatAvatar;//微信头像地址
	
	private String auditedComment;//审核未通过原因
	private String nickName;
	private String priceExplainName;
	private String phone;

	public String getEntTypes() {
		return entTypes;
	}

	public void setEntTypes(String entTypes) {
		this.entTypes = entTypes;
	}

	public String getSumZan() {
		return sumZan;
	}

	public void setSumZan(String sumZan) {
		this.sumZan = sumZan;
	}

	public String getWhetherZan() {
		return whetherZan;
	}

	public void setWhetherZan(String whetherZan) {
		this.whetherZan = whetherZan;
	}

	public String getWhetherFavorite() {
		return whetherFavorite;
	}

	public void setWhetherFavorite(String whetherFavorite) {
		this.whetherFavorite = whetherFavorite;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getGoodsProv() {
		return goodsProv;
	}

	public void setGoodsProv(Integer goodsProv) {
		this.goodsProv = goodsProv;
	}

	public Integer getGoodsCity() {
		return goodsCity;
	}

	public void setGoodsCity(Integer goodsCity) {
		this.goodsCity = goodsCity;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getPriceNegotiable() {
		return priceNegotiable;
	}

	public void setPriceNegotiable(Integer priceNegotiable) {
		this.priceNegotiable = priceNegotiable;
	}

	public BigDecimal getExpectPrice() {
		return expectPrice;
	}

	public void setExpectPrice(BigDecimal expectPrice) {
		this.expectPrice = expectPrice;
	}

	public Integer getExpectPriceUnit() {
		return expectPriceUnit;
	}

	public void setExpectPriceUnit(Integer expectPriceUnit) {
		this.expectPriceUnit = expectPriceUnit;
	}

	public Integer getPriceExplain() {
		return priceExplain;
	}

	public void setPriceExplain(Integer priceExplain) {
		this.priceExplain = priceExplain;
	}

	public Integer getExpiryType() {
		return expiryType;
	}

	public void setExpiryType(Integer expiryType) {
		this.expiryType = expiryType;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getEntTypesName() {
		return entTypesName;
	}

	public void setEntTypesName(String entTypesName) {
		this.entTypesName = entTypesName;
	}

	public String getGoodsProvName() {
		return goodsProvName;
	}

	public void setGoodsProvName(String goodsProvName) {
		this.goodsProvName = goodsProvName;
	}

	public String getGoodsCityName() {
		return goodsCityName;
	}

	public void setGoodsCityName(String goodsCityName) {
		this.goodsCityName = goodsCityName;
	}

	public String getWeChatAvatar() {
		return weChatAvatar;
	}

	public void setWeChatAvatar(String weChatAvatar) {
		this.weChatAvatar = weChatAvatar;
	}

	public String getAuditedComment() {
		return auditedComment;
	}

	public void setAuditedComment(String auditedComment) {
		this.auditedComment = auditedComment;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPriceExplainName() {
		return priceExplainName;
	}

	public void setPriceExplainName(String priceExplainName) {
		this.priceExplainName = priceExplainName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	 
	
	
	
	
}
