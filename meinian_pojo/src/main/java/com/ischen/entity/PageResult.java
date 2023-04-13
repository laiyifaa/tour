package com.ischen.entity;

import java.io.Serializable;
import java.util.List;

/**
 * PageResult
 *
 * @Author: 马伟奇
 * @CreateTime: 2021-06-29
 * @Description:
 *
 * PageResult:分页结果集,返回分页数据
 */
public class PageResult implements Serializable {
    // 返回总记录数
    private Long total;
    // 当前页面返回的结果
    private List rows;

    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }


}