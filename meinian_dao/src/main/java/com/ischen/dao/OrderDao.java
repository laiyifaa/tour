package com.ischen.dao;

import com.ischen.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * OrderDao
 *
 * @Author: ischen
 * @CreateTime: 2021-07-05
 * @Description:
 */
public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);

    Map findById(Integer id);
}

