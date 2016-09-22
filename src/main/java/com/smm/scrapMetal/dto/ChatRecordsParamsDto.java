package com.smm.scrapMetal.dto;

import java.util.Date;

import com.smm.scrapMetal.domain.ChatRecords;

/**
* @author  zhaoyutao
* @version 2016年2月19日 下午2:19:32
* 类说明
*/

public class ChatRecordsParamsDto extends ChatRecords {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1114283294333588231L;

	private Date lastestTime;
	
	private Integer len;

	public Date getLastestTime() {
		return lastestTime;
	}

	public void setLastestTime(Date lastestTime) {
		this.lastestTime = lastestTime;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	
	
}
