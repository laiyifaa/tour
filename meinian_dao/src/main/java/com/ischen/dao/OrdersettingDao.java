package com.ischen.dao;

import com.ischen.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrdersettingDao
 *
 * @Author: ischen
 * @CreateTime: 2021-07-03
 * @Description:
 */
public interface OrdersettingDao {
    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map<String, Object> map);

    OrderSetting findByOrderDate(Date date);

    void editReservationsByOrderDate(OrderSetting orderSetting);

}

