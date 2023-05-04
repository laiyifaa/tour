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

    //根据id查询预约信息，包括人信息、套餐信息
    Map findById4Detail(Integer id);

}

