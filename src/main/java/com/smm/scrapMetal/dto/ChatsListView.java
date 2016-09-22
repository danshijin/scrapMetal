package com.smm.scrapMetal.dto;

import com.smm.scrapMetal.domain.Chats;

public class ChatsListView extends Chats{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4123008431490518828L;
	
	private String name;
	
	private String nickName;
	
	private Integer notReadNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getNotReadNum() {
		return notReadNum;
	}

	public void setNotReadNum(Integer notReadNum) {
		this.notReadNum = notReadNum;
	}

}
