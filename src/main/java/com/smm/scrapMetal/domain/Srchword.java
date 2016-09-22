package com.smm.scrapMetal.domain;

/**
 * 
 * @author hece
 *	关键字搜索
 */
public class Srchword {

	private Integer id;
	private String name;//关键字
	private Integer srchOrder;//显示顺序
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
	public Integer getSrchOrder() {
		return srchOrder;
	}
	public void setSrchOrder(Integer srchOrder) {
		this.srchOrder = srchOrder;
	}
}