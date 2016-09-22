package com.smm.scrapMetal.dto;

import java.util.List;

/**
 * @author zhaoyutao
 * @version 2016年1月26日 下午2:39:42 类说明
 */

public class PageData<T> {
    /**
     * 总页数
     */
    private int     count;

    /**
     * 当前第几页
     */
    private int     page;

    /**
     * 一页多少条
     */
    private int     pageSize;

    /**
     * 返回列表数据
     */
    private List<T> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
