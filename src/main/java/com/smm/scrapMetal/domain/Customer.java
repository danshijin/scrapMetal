package com.smm.scrapMetal.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongmiaonan
 */
public class Customer implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Integer           id;
    private Integer           pic;
    private String            picUser;
    private String            nickName;
    private String            phone;
    private String            scrapItemIds;
    private String            name;
    private String            address;
    private String            companyName;
    private String            entTypes;
    private String            categorybusiness;
    private String            email;
    private Integer           infoStatus;
    private Date              createdAt;
    private Integer           createdBy;
    private String            createUser;
    private Date              updatedAt;
    private Integer           updatedBy;
    private Integer           goodsProv;
    private Integer           goodsCity;
    private String            goodsProvName;
    private String            goodsCityName;
    private Date              dealTime;
    private Integer           itemId;
    private String            itemName;
    private String            userName;
    private String            weChatID;
    private String            weChatAvatar;
    private String            areas;

    private Integer           orderCount;
    private Integer           favoriteCount;
    private Integer           supplyCount;
    private Integer           purchaseCount;
    private Integer           publishCount;
    private String            openId;
    private Integer           prov;
    private Integer           city;

    private String            provName;
    private String            cityName;
    private Integer           isSpread;
    private String            isSpreadName;

    public String getIsSpreadName() {
        return isSpreadName;
    }

    public void setIsSpreadName(String isSpreadName) {
        this.isSpreadName = isSpreadName;
    }

    public Integer getIsSpread() {
        return isSpread;
    }

    public void setIsSpread(Integer isSpread) {
        this.isSpread = isSpread;
    }

    public Integer getProv() {
        return prov;
    }

    public void setProv(Integer prov) {
        this.prov = prov;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getPublishCount() {
        return publishCount;
    }

    public void setPublishCount(Integer publishCount) {
        this.publishCount = publishCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getSupplyCount() {
        return supplyCount;
    }

    public void setSupplyCount(Integer supplyCount) {
        this.supplyCount = supplyCount;
    }

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getWeChatID() {
        return weChatID;
    }

    public void setWeChatID(String weChatID) {
        this.weChatID = weChatID;
    }

    public String getWeChatAvatar() {
        return weChatAvatar;
    }

    public void setWeChatAvatar(String weChatAvatar) {
        this.weChatAvatar = weChatAvatar;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPicUser() {
        return picUser;
    }

    public void setPicUser(String picUser) {
        this.picUser = picUser;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsProvName() {
        return goodsProvName;
    }

    public void setGoodsProvName(String goodsProvName) {
        this.goodsProvName = goodsProvName;
    }

    public String getGoodsCityName() {
        return goodsCityName;
    }

    public void setGoodsCityName(String goodsCityName) {
        this.goodsCityName = goodsCityName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPic() {
        return pic;
    }

    public void setPic(Integer pic) {
        this.pic = pic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScrapItemIds() {
        return scrapItemIds;
    }

    public void setScrapItemIds(String scrapItemIds) {
        this.scrapItemIds = scrapItemIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEntTypes() {
        return entTypes;
    }

    public void setEntTypes(String entTypes) {
        this.entTypes = entTypes;
    }

    public String getCategorybusiness() {
        return categorybusiness;
    }

    public void setCategorybusiness(String categorybusiness) {
        this.categorybusiness = categorybusiness;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(Integer infoStatus) {
        this.infoStatus = infoStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getGoodsProv() {
        return goodsProv;
    }

    public void setGoodsProv(Integer goodsProv) {
        this.goodsProv = goodsProv;
    }

    public Integer getGoodsCity() {
        return goodsCity;
    }

    public void setGoodsCity(Integer goodsCity) {
        this.goodsCity = goodsCity;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
