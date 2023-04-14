package com.ischen.service;

import com.ischen.entity.PageResult;
import com.ischen.entity.QueryPageBean;
import com.ischen.pojo.TravelGroup;

import java.util.List;

/**
 * TravelgroupService
 *
 * @Author: ischen
 * @CreateTime: 2021-06-30
 * @Description:
 */
public interface TravelgroupService {
    void add(Integer[] travelItemIds, TravelGroup travelGroup);

    PageResult findPage(QueryPageBean queryPageBean);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(Integer[] travelItemIds, TravelGroup travelGroup);

    List<TravelGroup> findAll();


}

