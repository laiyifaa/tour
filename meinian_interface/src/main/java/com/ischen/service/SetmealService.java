package com.ischen.service;

import com.ischen.entity.PageResult;
import com.ischen.entity.QueryPageBean;
import com.ischen.pojo.Setmeal;

import java.util.List;

/**
 * SetmealService
 *
 * @Author: ischen
 * @CreateTime: 2021-07-02
 * @Description:
 */
public interface SetmealService {
    void add(Integer[] travelgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);
}

