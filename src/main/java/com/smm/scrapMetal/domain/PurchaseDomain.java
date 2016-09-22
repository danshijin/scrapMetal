package com.smm.scrapMetal.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购单
 * @author tantaigen
 *
 */
public class PurchaseDomain {
	private  Integer id;  			     //主键         
	
	private  Integer itemId;	     	 //废料id 
	
	
	
	private  Integer customerId;    	 //创建人    
	
	private  Integer goodsProv;     	 //对应AREA的省id 
	private String goodsProvName;
	
	private  Integer goodsCity;    		 //对应AREA的市id   
	private String goodsCityName;
	
	private  String infoTitle;  		 //信息标题         
	
	private  String description; 		 //采购详情         
	
	private  BigDecimal quantity;			 //采购数量           
	
	private  Integer unit;				 //单位 0 吨       
	private String quantityName;
	
	private  Integer priceNegotiable;	 //价格是否面议             
	
	private  BigDecimal expectPrice;     //期望价格 
	private  String expectPriceName;
	
	private  Integer expectPriceUnit; 	 //0 人民币 1 美元                                
    
	private  Integer priceExplain; 		//价格说明  对应 sp_priceExplain                
	private  String priceExplainName;                                                                                                                                                                              
	private  Integer expiryType; 		//信息有效期类型  0 一周 1 一个月      
	private String expiryTypeName;
	
	private Integer isCreatedByCustomer;// 0 后台创建  1 前台创建
	private Integer isUpdatedByCustomer;// 0 后台修改 1 前台修改
	                                                                                                                                                                               

	private  String  phone; 		    //联系人手机号                                  
	                                                                                                                                                                               
	 private  Integer infoStatus; 		//0 待审核 1 通过 2 未通过 3 过期 4 删除  
	                                                                                                                                                                               
	 private  Date createdAt; 		//创建时间                                        
	                                                                                                                                                                               
	private  Integer createdBy; 		//创建人                                           
	                                                                                                                                                                               
	private  Date updatedAt; 		 	//更新时间                                        
	                                                                                                                                                                               
	private  Integer updatedBy; 		//更新人                                           
	                                                                                                                                                                               
	private  Date auditedAt; 		 	//审核时间                                        
	                                                                                                                                                                               
	private  Integer auditedBy; 		//审核人                                           
	                                                                                                                                                                               
	private  String auditedComment;   	//审核备注     
	
	private String itemName;            //分类名称
	
	private String customerName;        //客户名称
	
	private String createdByName;       //创建人名称
	
	private String updatedByName;       //修改人名称
	
	private String  cityName;			//市地区名称
	private String  provName;			//省份地区名称
	
	private Integer lastInfoStatus;//过期前的状态
	
	
	
	public Integer getLastInfoStatus() {
		return lastInfoStatus;
	}

	public void setLastInfoStatus(Integer lastInfoStatus) {
		this.lastInfoStatus = lastInfoStatus;
	}

	public String getQuantityName() {
		return quantityName;
	}

	public void setQuantityName(String quantityName) {
		this.quantityName = quantityName;
	}

	public String getExpectPriceName() {
		return expectPriceName;
	}

	public void setExpectPriceName(String expectPriceName) {
		this.expectPriceName = expectPriceName;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public Integer getIsUpdatedByCustomer() {
		return isUpdatedByCustomer;
	}

	public void setIsUpdatedByCustomer(Integer isUpdatedByCustomer) {
		this.isUpdatedByCustomer = isUpdatedByCustomer;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(Integer infoStatus) {
		this.infoStatus = infoStatus;
	}


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getAuditedAt() {
		return auditedAt;
	}

	public void setAuditedAt(Date auditedAt) {
		this.auditedAt = auditedAt;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}


	public Integer getAuditedBy() {
		return auditedBy;
	}

	public void setAuditedBy(Integer auditedBy) {
		this.auditedBy = auditedBy;
	}

	public String getAuditedComment() {
		return auditedComment;
	}

	public void setAuditedComment(String auditedComment) {
		this.auditedComment = auditedComment;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getPriceExplainName() {
		return priceExplainName;
	}

	public void setPriceExplainName(String priceExplainName) {
		this.priceExplainName = priceExplainName;
	}

	public String getExpiryTypeName() {
		return expiryTypeName;
	}

	public void setExpiryTypeName(String expiryTypeName) {
		this.expiryTypeName = expiryTypeName;
	}

	public Integer getIsCreatedByCustomer() {
		return isCreatedByCustomer;
	}

	public void setIsCreatedByCustomer(Integer isCreatedByCustomer) {
		this.isCreatedByCustomer = isCreatedByCustomer;
	}
	                                                                                                                                                                               

	

}
