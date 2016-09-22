package com.smm.scrapMetal.bo;

import com.smm.scrapMetal.dto.ResultData;

/**
 * 类IFavoriteBO.java的实现描述：TODO 类实现描述
 * 
 * @author duqiang 2016年1月29日 下午4:04:32
 */
public interface IFavoriteBO {
    /**
     * 点赞收藏操作
     * 
     * @param paramsStr
     * @return
     * @throws Exception
     */
    public ResultData editFavorite(String paramsStr) throws Exception;
}
