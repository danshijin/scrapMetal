package com.smm.scrapMetal.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 
 * @author hece
 *
 *	供货单
 */
public class Supply {

	private Integer id;//主键ID
	private Integer itemId;//废料ID
	private Integer customerId;//创建人
	private Integer isCreatedByCustomer;//0 撮合员创建  1  客户创建
	private Integer isUpdatedByCustomer;//0 撮合员修改  1  客户修改
	private Integer goodsProv;//对应省份ID
	private Integer goodsCity;//对应市ID
	private String infoTitle;//信息标题
	private String description;//供货信息
	private BigDecimal usableQuantity;//可用数量
	private BigDecimal frozenQuantity;//冻结数量
	private Integer quantityUnit;//单位 0吨
	private Integer priceNegotiable;//价格是否面议 0 不面议 1 面议
	private BigDecimal price;//单价
	private Integer priceUnit;//0 人民币  1 美元
	private Integer priceExplain;//价格说明    对应 sp_priceExplain
	private Integer delivery;//交货方式 对应sp_delivery
	private Integer expiryType;//信息有效期类型  0 一周 1 一个月
	private String phone;//联系人手机号码
	private Integer infoStatus;//0 待审核 1 通过 2 未通过 3 过期 4 删除
	private Integer lastInfoStatus;//过期前的状态
	private Timestamp createdAt;//创建时间
	private Integer createdBy;//创建人
	private Timestamp updatedAt;//更新时间
	private Integer updatedBy;//更新人
	private BigDecimal auditedAt;//审核时间
	private Integer auditedBy;//审核人
	private String auditedComment;//审核备注
	
	
	
	public Integer getLastInfoStatus() {
		return lastInfoStatus;
	}
	public void setLastInfoStatus(Integer lastInfoStatus) {
		this.lastInfoStatus = lastInfoStatus;
	}
	public Integer getIsUpdatedByCustomer() {
		return isUpdatedByCustomer;
	}
	public void setIsUpdatedByCustomer(Integer isUpdatedByCustomer) {
		this.isUpdatedByCustomer = isUpdatedByCustomer;
	}
	public Integer getIsCreatedByCustomer() {
		return isCreatedByCustomer;
	}
	public void setIsCreatedByCustomer(Integer isCreatedByCustomer) {
		this.isCreatedByCustomer = isCreatedByCustomer;
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
	public BigDecimal getUsableQuantity() {
		return usableQuantity;
	}
	public void setUsableQuantity(BigDecimal usableQuantity) {
		this.usableQuantity = usableQuantity;
	}
	public BigDecimal getFrozenQuantity() {
		return frozenQuantity;
	}
	public void setFrozenQuantity(BigDecimal frozenQuantity) {
		this.frozenQuantity = frozenQuantity;
	}
	public Integer getQuantityUnit() {
		return quantityUnit;
	}
	public void setQuantityUnit(Integer quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	public Integer getPriceNegotiable() {
		return priceNegotiable;
	}
	public void setPriceNegotiable(Integer priceNegotiable) {
		this.priceNegotiable = priceNegotiable;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}
	public Integer getPriceExplain() {
		return priceExplain;
	}
	public void setPriceExplain(Integer priceExplain) {
		this.priceExplain = priceExplain;
	}
	public Integer getDelivery() {
		return delivery;
	}
	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
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
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public BigDecimal getAuditedAt() {
		return auditedAt;
	}
	public void setAuditedAt(BigDecimal auditedAt) {
		this.auditedAt = auditedAt;
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
	
	
	
}
