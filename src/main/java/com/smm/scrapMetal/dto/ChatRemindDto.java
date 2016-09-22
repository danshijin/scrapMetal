package com.smm.scrapMetal.dto;

import java.io.Serializable;

/**
* @author  zhaoyutao
* @version 2016年2月26日 上午11:35:10
* 类说明
*/

public class ChatRemindDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5088346142527170601L;
	
	private Integer chatId;
	
	private Integer num;

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}
