package com.smm.scrapMetal.dao;

import com.smm.scrapMetal.domain.Favorite;

/**
 * 类IFavoriteDao.java的实现描述：TODO 类实现描述
 * 
 * @author duqiang 2016年1月29日 下午4:04:02
 */
public interface IFavoriteDao {
    /**
     * 收藏
     * 
     * @param favorite
     */
    public void addFavorite(Favorite favorite);

    /**
     * 取消收藏
     * 
     * @param favorite
     * @return
     */
    public Integer delFavorite(Favorite favorite);

    /**
     * 点赞
     * 
     * @param favorite
     */
    public void addUpvote(Favorite favorite);

    /**
     * 取消点赞
     * 
     * @param favorite
     * @return
     */
    public Integer delUpvote(Favorite favorite);

}
