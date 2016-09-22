package com.smm.scrapMetal.bo.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.IFavoriteBO;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dao.ICustomerDao;
import com.smm.scrapMetal.dao.IFavoriteDao;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Favorite;
import com.smm.scrapMetal.dto.ResultData;

@Service
public class FavoriteBo implements IFavoriteBO {
    private Logger logger = Logger.getLogger(FavoriteBo.class);
    @Resource
    IFavoriteDao   iFavoriteDao;
    @Resource
    ICustomerDao   iCustomerDao;

    public ResultData editFavorite(String paramsStr) throws Exception {
        ResultData data = new ResultData();
        JSONObject obj = JSONObject.fromObject(paramsStr);
        String source = obj.getString("source");
        String sourceId = obj.getString("sourceId");
        String openId = obj.getString("openId");
        String type = obj.getString("type");
        String customerId = null;
        if (!StringUtils.isNotBlank(source)) {
            logger.error("source is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.SOURCE_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.SOURCE_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
            return data;
        }
        if (!StringUtils.isNotBlank(sourceId)) {
            logger.error("sourceId is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.SOURCEID_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.SOURCEID_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
            return data;
        }
        if (!StringUtils.isNotBlank(openId)) {
            logger.error(" openId is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CUSTOMERID_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CUSTOMERID_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
            return data;
        } else {
            Customer cus = iCustomerDao.showClientCustomerDetail(openId);
            if (null != cus) {
                customerId = cus.getId() + "";
            }

        }
        if (!StringUtils.isNotBlank(type)) {
            logger.error(" type is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.TYPE_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.TYPE_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
            return data;
        }
        if (!StringUtils.isNotBlank(customerId)) {
            logger.error(" customerId is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CUSTOMERID_NULL_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CUSTOMERID_NULL_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
            return data;
        }
        Favorite favorite = new Favorite();
        favorite.setCustomerId(Integer.parseInt(customerId));
        favorite.setSourceId(Integer.parseInt(sourceId));
        favorite.setSource(Integer.parseInt(source));
        if (type.equals("1")) {//收藏
            iFavoriteDao.addFavorite(favorite);
        } else if (type.equals("3")) {//点赞
            iFavoriteDao.addUpvote(favorite);
        } else if (type.equals("2")) {//取消收藏
            iFavoriteDao.delFavorite(favorite);
        } else if (type.equals("4")) {//取消点赞
            iFavoriteDao.delUpvote(favorite);
        } else {
            logger.error("不存在这个类型 type=" + type);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.TYPE_NO_EXIST_ERROR_CODE);
            data.setErrMsg(ResErrorCode.TYPE_NO_EXIST_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
            return data;
        }
        data.setSuccess(true);
        data.setUnAuthorizedRequest(false);
        return data;
    }

}
