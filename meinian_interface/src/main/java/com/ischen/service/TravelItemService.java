package com.ischen.service;

import com.ischen.entity.PageResult;
import com.ischen.entity.QueryPageBean;
import com.ischen.pojo.TravelItem;

/**
 * TravelItemService
 *
 * @Author: 马伟奇
 * @CreateTime: 2021-06-29
 * @Description:
 */
public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);
}

