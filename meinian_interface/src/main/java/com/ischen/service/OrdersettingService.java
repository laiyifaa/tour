package com.ischen.service;

import com.ischen.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * OrdersettingService
 *
 * @Author: ischen
 * @CreateTime: 2021-07-03
 * @Description:
 */
public interface OrdersettingService {
    void add(List<OrderSetting> data);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);

}

