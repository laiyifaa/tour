package com.ischen.dao;

import com.ischen.pojo.TravelItem;
import com.github.pagehelper.Page;

/**
 * TravelItemDao
 *
 * @Author: 马伟奇
 * @CreateTime: 2021-06-29
 * @Description:
 */
public interface TravelItemDao {
    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

}
