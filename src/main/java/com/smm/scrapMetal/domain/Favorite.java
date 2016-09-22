package com.smm.scrapMetal.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 类Favorite.java的实现描述：TODO 类实现描述
 * 
 * @author duqiang 2016年1月29日 下午3:49:54
 */
public class Favorite implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 303676871648546049L;

    private Integer           id;

    private Integer           sourceId;

    private Integer           source;

    private Integer           customerId;

    private Date              createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
