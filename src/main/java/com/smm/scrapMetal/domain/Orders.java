/**
 * 
 */
package com.smm.scrapMetal.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Orders
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年1月25日
 */
public class Orders implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id; // 主键id
	
	private int favoriteId;  //  '收藏ID'
	
	private int source; // '来源 1 供货 2 采购'
	
	private int sourceId; // '来源id'
	
	private String orderNo; // '订单编号'
	
	private String infoTitle; // '信息标题 '
	
	private int infoStatus; // '审核状态'
	
	private String metalName;  // '废旧金属名称'
	
	private String province;  // '省'
	
	private int priceNegotiable;  // '是否可面议'
	
	private BigDecimal expectPrice;  // '期望价格'

	private int expectPriceUnit;  // '货币类型'
	
	private int expiryType;  // '信息有效期'
	
	private Date collectionDate;  // '收藏日期'
	
	private int goodsProv; // '对应area的省id'
	
	private String goodsProvName; // '省名称'
	
	private int goodsCity; // '对应area的市id'
	
	private String goodsCityName; // '市名称'
	
	private BigDecimal quantity; // '数量'
	
	private int quantityUnit; // '单位 0 吨'
	
	private BigDecimal price; // '单价'
	
	private int priceUnit; // '0 人民币 1 美元'
	
	private int priceExplain; // '价格说明 对应 sp_priceExplain'
	
	private int delivery; // '交货方式 对应sp_delivery'
	
	private String buyerPhone; // '买方手机'
	
	private String buyerContacter; // '买方联系人'
	
	private String buyerCompanyName; // '买方公司名'
	
	private String buyerAddress; // '买方地址'
	
	private int isBuyerConfirmed; // '买家是否确认订单'0 未确认 1 已确认
	
	private Date buyerConfirmedTime; // '买家确认订单时间'
	
	private String sellerPhone; // '卖方手机'
	
	private String sellerContacter; // '卖方联系人'
	
	private String sellerCompanyName; // '卖方公司名'
	
	private String sellerAddress; // '卖方地址'
	
	private int isSellerConfirmed; // '卖家是否确认订单'0 未确认 1 已确认
	
	private Date sellerConfirmedTime; // '卖家确认订单时间'
	
	private int orderStatus; // '0 未成交 1 已成交 2 已撤销'
	
	private Date createdAt; // '创建时间'
	
	private int createdBy; // '创建人'
	
	private Date updatedAt; // '更新时间'
	
	private int updatedBy; // '更新人'
	
	private Date dealTime; // '成交时间'
	
	private String imgPath; // '图片地址'
	
	private int wait;	// '待确认数量'
	
	private int noDeal;	  // '未成交数量'
	
	private int dealed;	  // '已成交数量'
	
	private int published;	// '发布买卖单数量'
	
	private int auditing;	  // '审核中的买卖单数量'
	
	private int notPass;	  // '没有通过的买卖单数量'

	private String weChatAvatar;  // '微信头像'
	
	private String nickName;  // '微信昵称'
	
	private String auditedComment; //'审核备注 300字以内'
	
	private String orderTitle; // '订单标题'
	
	private String pTitle;//采购单标题
	
	
	
	public String getpTitle() {
		return pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}

	/**
	 * @return the orderTitle
	 */
	public String getOrderTitle() {
		return orderTitle;
	}

	/**
	 * @param orderTitle the orderTitle to set
	 */
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the published
	 */
	public int getPublished() {
		return published;
	}

	/**
	 * @param published the published to set
	 */
	public void setPublished(int published) {
		this.published = published;
	}

	/**
	 * @return the auditing
	 */
	public int getAuditing() {
		return auditing;
	}

	/**
	 * @param auditing the auditing to set
	 */
	public void setAuditing(int auditing) {
		this.auditing = auditing;
	}

	/**
	 * @return the notPass
	 */
	public int getNotPass() {
		return notPass;
	}

	/**
	 * @param notPass the notPass to set
	 */
	public void setNotPass(int notPass) {
		this.notPass = notPass;
	}

	/**
	 * @return the favoriteId
	 */
	public int getFavoriteId() {
		return favoriteId;
	}

	/**
	 * @param favoriteId the favoriteId to set
	 */
	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
	}

	/**
	 * @return the wait
	 */
	public int getWait() {
		return wait;
	}

	/**
	 * @param wait the wait to set
	 */
	public void setWait(int wait) {
		this.wait = wait;
	}

	/**
	 * @return the noDeal
	 */
	public int getNoDeal() {
		return noDeal;
	}

	/**
	 * @param noDeal the noDeal to set
	 */
	public void setNoDeal(int noDeal) {
		this.noDeal = noDeal;
	}

	
	/**
	 * @return the weChatAvatar
	 */
	public String getWeChatAvatar() {
		return weChatAvatar;
	}

	/**
	 * @param weChatAvatar the weChatAvatar to set
	 */
	public void setWeChatAvatar(String weChatAvatar) {
		this.weChatAvatar = weChatAvatar;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the dealed
	 */
	public int getDealed() {
		return dealed;
	}

	/**
	 * @param dealed the dealed to set
	 */
	public void setDealed(int dealed) {
		this.dealed = dealed;
	}

	/**
	 * @return the goodsProvName
	 */
	public String getGoodsProvName() {
		return goodsProvName;
	}

	/**
	 * @param goodsProvName the goodsProvName to set
	 */
	public void setGoodsProvName(String goodsProvName) {
		this.goodsProvName = goodsProvName;
	}

	/**
	 * @return the goodsCityName
	 */
	public String getGoodsCityName() {
		return goodsCityName;
	}

	/**
	 * @param goodsCityName the goodsCityName to set
	 */
	public void setGoodsCityName(String goodsCityName) {
		this.goodsCityName = goodsCityName;
	}

	/**
	 * @return the buyerAddress
	 */
	public String getBuyerAddress() {
		return buyerAddress;
	}

	/**
	 * @param buyerAddress the buyerAddress to set
	 */
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	/**
	 * @return the sellerAddress
	 */
	public String getSellerAddress() {
		return sellerAddress;
	}

	/**
	 * @param sellerAddress the sellerAddress to set
	 */
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the source
	 */
	public int getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(int source) {
		this.source = source;
	}

	/**
	 * @return the sourceId
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the infoTitle
	 */
	public String getInfoTitle() {
		return infoTitle;
	}

	/**
	 * @param infoTitle the infoTitle to set
	 */
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	/**
	 * @return the metalName
	 */
	public String getMetalName() {
		return metalName;
	}

	/**
	 * @param metalName the metalName to set
	 */
	public void setMetalName(String metalName) {
		this.metalName = metalName;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the priceNegotiable
	 */
	public int getPriceNegotiable() {
		return priceNegotiable;
	}

	/**
	 * @param priceNegotiable the priceNegotiable to set
	 */
	public void setPriceNegotiable(int priceNegotiable) {
		this.priceNegotiable = priceNegotiable;
	}

	/**
	 * @return the expectPrice
	 */
	public BigDecimal getExpectPrice() {
		return expectPrice;
	}

	/**
	 * @param expectPrice the expectPrice to set
	 */
	public void setExpectPrice(BigDecimal expectPrice) {
		this.expectPrice = expectPrice;
	}

	/**
	 * @return the expectPriceUnit
	 */
	public int getExpectPriceUnit() {
		return expectPriceUnit;
	}

	/**
	 * @param expectPriceUnit the expectPriceUnit to set
	 */
	public void setExpectPriceUnit(int expectPriceUnit) {
		this.expectPriceUnit = expectPriceUnit;
	}

	/**
	 * @return the expiryType
	 */
	public int getExpiryType() {
		return expiryType;
	}

	/**
	 * @param expiryType the expiryType to set
	 */
	public void setExpiryType(int expiryType) {
		this.expiryType = expiryType;
	}

	/**
	 * @return the collectionDate
	 */
	public Date getCollectionDate() {
		return collectionDate;
	}

	/**
	 * @param collectionDate the collectionDate to set
	 */
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	/**
	 * @return the goodsProv
	 */
	public int getGoodsProv() {
		return goodsProv;
	}

	/**
	 * @param goodsProv the goodsProv to set
	 */
	public void setGoodsProv(int goodsProv) {
		this.goodsProv = goodsProv;
	}

	/**
	 * @return the goodsCity
	 */
	public int getGoodsCity() {
		return goodsCity;
	}

	/**
	 * @param goodsCity the goodsCity to set
	 */
	public void setGoodsCity(int goodsCity) {
		this.goodsCity = goodsCity;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantityUnit
	 */
	public int getQuantityUnit() {
		return quantityUnit;
	}

	/**
	 * @param quantityUnit the quantityUnit to set
	 */
	public void setQuantityUnit(int quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the priceUnit
	 */
	public int getPriceUnit() {
		return priceUnit;
	}

	/**
	 * @param priceUnit the priceUnit to set
	 */
	public void setPriceUnit(int priceUnit) {
		this.priceUnit = priceUnit;
	}

	/**
	 * @return the priceExplain
	 */
	public int getPriceExplain() {
		return priceExplain;
	}

	/**
	 * @param priceExplain the priceExplain to set
	 */
	public void setPriceExplain(int priceExplain) {
		this.priceExplain = priceExplain;
	}

	/**
	 * @return the delivery
	 */
	public int getDelivery() {
		return delivery;
	}

	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}

	/**
	 * @return the buyerPhone
	 */
	public String getBuyerPhone() {
		return buyerPhone;
	}

	/**
	 * @param buyerPhone the buyerPhone to set
	 */
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	/**
	 * @return the buyerContacter
	 */
	public String getBuyerContacter() {
		return buyerContacter;
	}

	/**
	 * @param buyerContacter the buyerContacter to set
	 */
	public void setBuyerContacter(String buyerContacter) {
		this.buyerContacter = buyerContacter;
	}

	/**
	 * @return the buyerCompanyName
	 */
	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}

	/**
	 * @param buyerCompanyName the buyerCompanyName to set
	 */
	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}

	/**
	 * @return the sellerPhone
	 */
	public String getSellerPhone() {
		return sellerPhone;
	}

	/**
	 * @param sellerPhone the sellerPhone to set
	 */
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	/**
	 * @return the sellerContacter
	 */
	public String getSellerContacter() {
		return sellerContacter;
	}

	/**
	 * @param sellerContacter the sellerContacter to set
	 */
	public void setSellerContacter(String sellerContacter) {
		this.sellerContacter = sellerContacter;
	}

	/**
	 * @return the sellerCompanyName
	 */
	public String getSellerCompanyName() {
		return sellerCompanyName;
	}

	/**
	 * @param sellerCompanyName the sellerCompanyName to set
	 */
	public void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}

	/**
	 * @return the orderStatus
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the updatedBy
	 */
	public int getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the dealTime
	 */
	public Date getDealTime() {
		return dealTime;
	}

	/**
	 * @param dealTime the dealTime to set
	 */
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	/**
	 * @return the isBuyerConfirmed
	 */
	public int getIsBuyerConfirmed() {
		return isBuyerConfirmed;
	}

	/**
	 * @param isBuyerConfirmed the isBuyerConfirmed to set
	 */
	public void setIsBuyerConfirmed(int isBuyerConfirmed) {
		this.isBuyerConfirmed = isBuyerConfirmed;
	}

	/**
	 * @return the buyerConfirmedTime
	 */
	public Date getBuyerConfirmedTime() {
		return buyerConfirmedTime;
	}

	/**
	 * @param buyerConfirmedTime the buyerConfirmedTime to set
	 */
	public void setBuyerConfirmedTime(Date buyerConfirmedTime) {
		this.buyerConfirmedTime = buyerConfirmedTime;
	}

	/**
	 * @return the isSellerConfirmed
	 */
	public int getIsSellerConfirmed() {
		return isSellerConfirmed;
	}

	/**
	 * @param isSellerConfirmed the isSellerConfirmed to set
	 */
	public void setIsSellerConfirmed(int isSellerConfirmed) {
		this.isSellerConfirmed = isSellerConfirmed;
	}

	/**
	 * @return the sellerConfirmedTime
	 */
	public Date getSellerConfirmedTime() {
		return sellerConfirmedTime;
	}

	/**
	 * @param sellerConfirmedTime the sellerConfirmedTime to set
	 */
	public void setSellerConfirmedTime(Date sellerConfirmedTime) {
		this.sellerConfirmedTime = sellerConfirmedTime;
	}

	/**
	 * @return the auditedComment
	 */
	public String getAuditedComment() {
		return auditedComment;
	}

	/**
	 * @param auditedComment the auditedComment to set
	 */
	public void setAuditedComment(String auditedComment) {
		this.auditedComment = auditedComment;
	}

	/**
	 * @return the infoStatus
	 */
	public int getInfoStatus() {
		return infoStatus;
	}

	/**
	 * @param infoStatus the infoStatus to set
	 */
	public void setInfoStatus(int infoStatus) {
		this.infoStatus = infoStatus;
	}

	
}

