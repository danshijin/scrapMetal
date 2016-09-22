package com.smm.scrapMetal.domain;

/**
 * 
 * @author hece
 *
 */
public class SrchTimes {

	private Integer id;
	private String name;//关键字
	private Integer srchTimes;//搜索次数
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
	public Integer getSrchTimes() {
		return srchTimes;
	}
	public void setSrchTimes(Integer srchTimes) {
		this.srchTimes = srchTimes;
	}
	
	
}
