package com.ischen.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ischen.constant.MessageConstant;
import com.ischen.entity.Result;
import com.ischen.pojo.OrderSetting;
import com.ischen.service.OrdersettingService;
import com.ischen.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrdersettingController
 *
 * @Author: ischen
 * @CreateTime: 2021-07-03
 * @Description:
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;


    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        ordersettingService.editNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }

    // this.leftobj = [
    //     { date: 1, number: 120, reservations: 1 },
    //     { date: 3, number: 120, reservations: 1 },
    //     { date: 4, number: 120, reservations: 120 },
    //     { date: 6, number: 120, reservations: 1 },
    //     { date: 8, number: 120, reservations: 1 }
    // ];

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
      List<Map> list =   ordersettingService.getOrderSettingByMonth(date);
      return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
    }



    /**
     * 上传excel表格，导入数据
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            // 读取excel里面的数据
            List<String[]> lists = POIUtils.readExcel(excelFile);

            List<OrderSetting> data = new ArrayList<>();

            // String[0]:日期 String[1]：可预约的数量
            for (String[] str : lists) {

                OrderSetting orderSetting =
                        new OrderSetting(new Date(str[0]),Integer.parseInt(str[1]));


                data.add(orderSetting);
            }
            ordersettingService.add(data);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }
}