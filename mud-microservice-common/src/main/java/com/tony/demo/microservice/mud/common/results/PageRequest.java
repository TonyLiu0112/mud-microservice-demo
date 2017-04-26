package com.tony.demo.microservice.mud.common.results;

/**
 * Created by Tony on 1/27/16.
 */
public class PageRequest {
    /**
     * 排序字段
     */
    private String orderColumn;

    /**
     * 排序类型
     */
    private String orderType;

    /**
     * 每页长度
     */
    private int pageSize;

    /**
     * 当前所在页
     */
    private int pageNum;

    private int draw;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
