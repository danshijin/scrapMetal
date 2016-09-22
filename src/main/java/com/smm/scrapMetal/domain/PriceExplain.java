package com.smm.scrapMetal.domain;

public class PriceExplain {
	private Integer id;//                                         
	private String name;// 名称                       
	private Integer isDomestic;//是否国内 0 否 1 是     
	private Integer isDefault;//是否默认显示 0 否 1是
	private Integer isDel;//是否被删除 0 否 1 是  
	private String createdAt;//创建时间                 
	private Integer createdBy;//创建人                    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsDomestic() {
		return isDomestic;
	}
	public void setIsDomestic(Integer isDomestic) {
		this.isDomestic = isDomestic;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}


}
