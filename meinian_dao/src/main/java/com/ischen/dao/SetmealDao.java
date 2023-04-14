package com.ischen.dao;

import com.ischen.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * SetmealDao
 *
 * @Author: ischen
 * @CreateTime: 2021-07-02
 * @Description:
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndTravelgroup(Map map);

    Page<Setmeal> findPage(String queryString);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);
}

