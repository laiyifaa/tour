package com.ischen.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.ischen.constant.MessageConstant;
import com.ischen.entity.Result;
import com.ischen.pojo.Order;
import com.ischen.service.OrderService;
import com.ischen.util.RedisMessageConstant;
import com.ischen.util.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * OrderMobileController
 *
 * @Author: ischen
 * @CreateTime: 2021-07-05
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;


    @RequestMapping("/findById")
    public Result findById(Integer id){

        Map map =null;
        try{
            map = orderService.findById4Detail(id);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }


    }



    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        // 获取用户输入的验证码
        String validateCode = (String) map.get("validateCode");
        // 获取用户输入的手机号码
        String telephone = (String) map.get("telephone");
        // 从redis获取真实的验证码
        String codeInRedis =  jedisPool.getResource()
                .get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        // 判断用输入的验证码是否正确
        if (codeInRedis == null || !validateCode.equals(codeInRedis)){
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        // 提交订单
        Result result = null;

        try{
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            // 断点调试，查看map里面封装了哪些数据
            result = orderService.order(map);
        }catch (Exception e){
            e.printStackTrace();
            //预约失败
            return null;
        }
        if(result.isFlag()){
            //预约成功，发送短信通知，短信通知内容可以是“预约时间”，“预约人”，“预约地点”，“预约事项”等信息。
            String orderDate = (String) map.get("orderDate");
            try {
                SMSUtils.sendShortMessage(telephone,orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
}