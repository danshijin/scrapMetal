package com.smm.scrapMetal.domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

	/**
	 * @author haokang
	 */
	private static final long serialVersionUID = 6279357193224736024L;
	
	private Integer id;
	private Integer source;//来源 1 供货 2 采购
	private Integer sourceId;//来源id
	private Integer goodsProv;//对应area的省id
	private Integer goodsCity;//对应area的市id
	private Double quantity;//数量
	private Integer quantityUnit;//单位 0 吨
	private Double price;//单价
	private Integer priceUnit;//0 人民币 1 美元
	private Integer priceExplain;//价格说明 对应 sp_priceExplain
	private Integer delivery;//交货方式 对应sp_delivery
	private String buyerPhone;//买方手机
	private String buyerContacter;//买方联系人
	private String buyerCompanyName;//买方公司名
	private String sellerPhone;//卖方手机
	private String sellerContacter;//卖方联系人
	private String sellerCompanyName;//卖方公司名
	private Integer orderStatus;//0 未成交 1 已成交 2 已撤销
	private Date createdAt;//创建时间
	private Integer createdBy;//创建人
	private Date updatedAt;//更新时间
	private Integer updatedBy;//更新人
	private Date dealTime;//成交时间
	private String orderNo;//订单编号
	private Integer buyerId;//买方id
	private Integer sellerId;//卖方id
	
	private String infoTitle;//信息标题
	private String itemName;//分类名称
	private String pcname;//省市名称
	private String name;//负责人
	private String strPriceExplain;//价格说明
	private String deliveryName;//交货方式
	private String buyAddress;//买方地址
	private String sellAddress;//卖方地址
	
	private String quantityStr;//数量 (用于导出)
	private String sumPrice;//总金额 (用于导出)
	
	private Integer isBuyerConfirmed;//0 未确认 1 已确认(成交) 2已确认(未成交)
	private Date buyerConfirmedTime;//买家确认订单时间
	private Integer isSellerConfirmed;//0 未确认 1 已确认(成交) 2已确认(未成交)
	private Date sellerConfirmedTime;//卖家确认订单时间
	
	
	
	public Integer getIsBuyerConfirmed() {
		return isBuyerConfirmed;
	}
	public void setIsBuyerConfirmed(Integer isBuyerConfirmed) {
		this.isBuyerConfirmed = isBuyerConfirmed;
	}
	public Date getBuyerConfirmedTime() {
		return buyerConfirmedTime;
	}
	public void setBuyerConfirmedTime(Date buyerConfirmedTime) {
		this.buyerConfirmedTime = buyerConfirmedTime;
	}
	public Integer getIsSellerConfirmed() {
		return isSellerConfirmed;
	}
	public void setIsSellerConfirmed(Integer isSellerConfirmed) {
		this.isSellerConfirmed = isSellerConfirmed;
	}
	public Date getSellerConfirmedTime() {
		return sellerConfirmedTime;
	}
	public void setSellerConfirmedTime(Date sellerConfirmedTime) {
		this.sellerConfirmedTime = sellerConfirmedTime;
	}
	public String getQuantityStr() {
		return quantityStr;
	}
	public void setQuantityStr(String quantityStr) {
		this.quantityStr = quantityStr;
	}
	public String getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getBuyAddress() {
		return buyAddress;
	}
	public void setBuyAddress(String buyAddress) {
		this.buyAddress = buyAddress;
	}
	public String getSellAddress() {
		return sellAddress;
	}
	public void setSellAddress(String sellAddress) {
		this.sellAddress = sellAddress;
	}
	public String getStrPriceExplain() {
		return strPriceExplain;
	}
	public void setStrPriceExplain(String strPriceExplain) {
		this.strPriceExplain = strPriceExplain;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getPcname() {
		return pcname;
	}
	public void setPcname(String pcname) {
		this.pcname = pcname;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
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
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Integer getQuantityUnit() {
		return quantityUnit;
	}
	public void setQuantityUnit(Integer quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
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
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public String getBuyerContacter() {
		return buyerContacter;
	}
	public void setBuyerContacter(String buyerContacter) {
		this.buyerContacter = buyerContacter;
	}
	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}
	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}
	public String getSellerPhone() {
		return sellerPhone;
	}
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}
	public String getSellerContacter() {
		return sellerContacter;
	}
	public void setSellerContacter(String sellerContacter) {
		this.sellerContacter = sellerContacter;
	}
	public String getSellerCompanyName() {
		return sellerCompanyName;
	}
	public void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	
	
	
	
	
	

}
