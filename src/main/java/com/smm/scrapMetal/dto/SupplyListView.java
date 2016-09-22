package com.smm.scrapMetal.dto;

import java.util.List;

import com.smm.scrapMetal.domain.Supply;
import com.smm.scrapMetal.domain.SupplyImages;

public class SupplyListView extends Supply{

	private String name;//品类名称
	private String proyName;//所在省
	private String cityName;//所在市
	private String expiryTypeName;//信息有效期类型名称   一周/一个月
	private String delName;//交货方式名称
	private String customerName;//客户名称
	private String entTypes;//企业类型
	private Integer sumZan;//共多少赞
	private Integer whetherZan;//是否赞
	private Integer whetherFavorite;//是否收藏
	private List<SupplyImages> supplyImages;//供货单图片列表
	private String supplyImg;//供货单图片
	private String iname;//分类名称
	private String pname;//价格说明
	private String dname;//交货方式
	private String uname;//创建人
	private String nickName;//微信昵称
	private Integer priceExplainId;//价格说明ID
	private String priceExplainName;//价格说明名称
	private Integer deliveryId;//交货方式ID
	private String exportName;//导出名称
	private String exportPrice;//导出价格
	
	
	public String getExportPrice() {
		return exportPrice;
	}
	public void setExportPrice(String exportPrice) {
		this.exportPrice = exportPrice;
	}
	public String getExportName() {
		return exportName;
	}
	public void setExportName(String exportName) {
		this.exportName = exportName;
	}
	public Integer getPriceExplainId() {
		return priceExplainId;
	}
	public void setPriceExplainId(Integer priceExplainId) {
		this.priceExplainId = priceExplainId;
	}
	public String getPriceExplainName() {
		return priceExplainName;
	}
	public void setPriceExplainName(String priceExplainName) {
		this.priceExplainName = priceExplainName;
	}
	public Integer getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getIname() {
		return iname;
	}
	public void setIname(String iname) {
		this.iname = iname;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getSupplyImg() {
		return supplyImg;
	}
	public void setSupplyImg(String supplyImg) {
		this.supplyImg = supplyImg;
	}
	public List<SupplyImages> getSupplyImages() {
		return supplyImages;
	}
	public void setSupplyImages(List<SupplyImages> supplyImages) {
		this.supplyImages = supplyImages;
	}
	public Integer getWhetherFavorite() {
		return whetherFavorite;
	}
	public void setWhetherFavorite(Integer whetherFavorite) {
		this.whetherFavorite = whetherFavorite;
	}
	public Integer getSumZan() {
		return sumZan;
	}
	public void setSumZan(Integer sumZan) {
		this.sumZan = sumZan;
	}
	public Integer getWhetherZan() {
		return whetherZan;
	}
	public void setWhetherZan(Integer whetherZan) {
		this.whetherZan = whetherZan;
	}
	public String getEntTypes() {
		return entTypes;
	}
	public void setEntTypes(String entTypes) {
		this.entTypes = entTypes;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDelName() {
		return delName;
	}
	public void setDelName(String delName) {
		this.delName = delName;
	}
	public String getExpiryTypeName() {
		return expiryTypeName;
	}
	public void setExpiryTypeName(String expiryTypeName) {
		this.expiryTypeName = expiryTypeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProyName() {
		return proyName;
	}
	public void setProyName(String proyName) {
		this.proyName = proyName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
