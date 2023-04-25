package com.ischen.entity;

import java.io.Serializable;

/**
 * QueryPageBean
 *
 * @Author: ischen
 * @CreateTime: 2021-06-29
 * @Description:
 *
 * QueryPageBean:作为请求参数
 */
public class QueryPageBean implements Serializable {
    // 当前页码
    private Integer currentPage;
    // 每个页面展示多少条数据
    private Integer pageSize;
    // 查询条件
    private String queryString;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}