package com.smm.scrapMetal.domain;

/**
 * 
 * @author hece
 *	图片信息
 */
public class SupplyImages {

	private Integer id;
	private Integer supplyId;//供应单Id
	private String name;//图片名
	private Integer imgOrder;//图片顺序
	
	private String imgName;//图片名称(不拼接)
	
	
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getImgOrder() {
		return imgOrder;
	}
	public void setImgOrder(Integer imgOrder) {
		this.imgOrder = imgOrder;
	}

	
}
