package com.smm.scrapMetal.domain;

import java.util.Date;

/**
 * 订单详情（接口）
 * @author tantaigen
 *
 */
public class OrderDetail {
	private Integer Id;  //ID
	private String orderNo;//订单标编号
	private String itemName;//品目名称
	private String itemId;//品目ID
	private String infoTitle;//标题
	private String price;//价格
	private String quantity;//数量
	private String quantityUnit;//数量单位 o 吨
	private String priceUnit;//价格单位
	private String goodscname;//地区名称
	private String goodsProv;//省ID
	private String goodsCity;//市Id
	private String strPriceExplain;//价格说明名称
	private String sellerPhone;//卖方电话
	private String buyerPhone;//买方电话
	private Date createdAt;//创建时间
	private Date dealTime;//成交时间
	private String customerName;//客户名称
	private String source;// 来源 0 供货单  1 采购单  3 客户池
	private String sourceId;// 来源ID
	private String orderStatus;//状态 0 待确认 1 未成交 2 已成交 3 已撤销
	private String buyerContacter;//买方联系人
	private String buyerCompanyName;//买方企业名称
	private String sellerContacter;//卖方联系人
	private String sellerCompanyName;//卖方企业名称
	private String isBuyerConfirmed;//卖方 0 未确认  1 已确认(成交) 2已确认(未成交)
	private String isSellerConfirmed;// 买方  0 未确认  1 已确认(成交) 2已确认(未成交)
	private Date buyDealTime; //买家确认时间
	private Date sellDealTime; //卖家确认时间
	private String sumPrice;//交易总额
	private String deliveryName;//交付方式
	
	private Date buyerConfirmedTime;//买方确认订单时间
	private Date sellerConfirmedTime;//买方确认订单时间
	private Date updatedAt;//撮合员确认时间(更新时间)
	private String address;
	private String imageName;
	
	private String buyerAddress; //买家地址
	private String sellerAddress; //卖家地址
	
	
	
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
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getQuantityUnit() {
		return quantityUnit;
	}
	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public String getGoodscname() {
		return goodscname;
	}
	public void setGoodscname(String goodscname) {
		this.goodscname = goodscname;
	}
	public String getGoodsProv() {
		return goodsProv;
	}
	public void setGoodsProv(String goodsProv) {
		this.goodsProv = goodsProv;
	}
	public String getGoodsCity() {
		return goodsCity;
	}
	public void setGoodsCity(String goodsCity) {
		this.goodsCity = goodsCity;
	}
	public String getStrPriceExplain() {
		return strPriceExplain;
	}
	public void setStrPriceExplain(String strPriceExplain) {
		this.strPriceExplain = strPriceExplain;
	}
	public String getSellerPhone() {
		return sellerPhone;
	}
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getIsBuyerConfirmed() {
		return isBuyerConfirmed;
	}
	public void setIsBuyerConfirmed(String isBuyerConfirmed) {
		this.isBuyerConfirmed = isBuyerConfirmed;
	}
	public String getIsSellerConfirmed() {
		return isSellerConfirmed;
	}
	public void setIsSellerConfirmed(String isSellerConfirmed) {
		this.isSellerConfirmed = isSellerConfirmed;
	}
	public Date getBuyDealTime() {
		return buyDealTime;
	}
	public void setBuyDealTime(Date buyDealTime) {
		this.buyDealTime = buyDealTime;
	}
	public Date getSellDealTime() {
		return sellDealTime;
	}
	public void setSellDealTime(Date sellDealTime) {
		this.sellDealTime = sellDealTime;
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
	public Date getBuyerConfirmedTime() {
		return buyerConfirmedTime;
	}
	public void setBuyerConfirmedTime(Date buyerConfirmedTime) {
		this.buyerConfirmedTime = buyerConfirmedTime;
	}
	public Date getSellerConfirmedTime() {
		return sellerConfirmedTime;
	}
	public void setSellerConfirmedTime(Date sellerConfirmedTime) {
		this.sellerConfirmedTime = sellerConfirmedTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	

	
	

}
