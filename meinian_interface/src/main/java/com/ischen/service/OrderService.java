package com.ischen.service;

import com.ischen.entity.Result;

import java.util.Map;

/**
 * OrderService
 *
 * @Author: ischen
 * @CreateTime: 2021-07-05
 * @Description:
 */
public interface OrderService {
    Result order(Map map);

    Map findById(Integer id);

}

