package com.ischen.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ischen.dao.OrdersettingDao;
import com.ischen.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrdersettingServiceImpl
 *
 * @Author: ischen
 * @CreateTime: 2021-07-03
 * @Description:
 */
@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService{

    @Autowired
    private OrdersettingDao ordersettingDao;


    @Override
    public void add(List<OrderSetting> data) {
        for (OrderSetting orderSetting : data) {

            // 查询数据库
            long count =  ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());

            // 判断是否设置了预约日期
            if (count>0){
                // 设置了日期，更新数据
                ordersettingDao.editNumberByOrderDate(orderSetting);
            }else {
                // 如果没有设置日期，添加数据
                ordersettingDao.add(orderSetting);
            }


        }

    }

    /**
     * 根据年和月，查询数据库里面一个月的数据
     * @param date
     * @return
     * 第一种查询方案：select * from 表名字 where orderDate like '2021-07-%'
     * 第二种查询方案：select * from 表名字 where orderDate between '2021-07-01' and '2021-07-31'
     */
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        //date = 2021-07-01 --- 2021-07-31
        // 只能使用在mysql8.0以下
        String dateBegin = date + "-01";
        String dateEnd = date + "-31";
        Map<String, Object> map = new HashMap<>();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> lists =  ordersettingDao.getOrderSettingByMonth(map);
// this.leftobj = [
        //     { date: 1, number: 120, reservations: 1 },
        //     { date: 3, number: 120, reservations: 1 },
        //     { date: 4, number: 120, reservations: 120 },
        //     { date: 6, number: 120, reservations: 1 },
        //     { date: 8, number: 120, reservations: 1 }
        // ];
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : lists) {
            Map dataMap = new HashMap<>();
            dataMap.put("date",orderSetting.getOrderDate().getDate());
            dataMap.put("number",orderSetting.getNumber());
            dataMap.put("reservations",orderSetting.getReservations());
            data.add(dataMap);
        }
        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0){
            ordersettingDao.editNumberByOrderDate(orderSetting);
        }else {
            ordersettingDao.add(orderSetting);
        }
    }
}