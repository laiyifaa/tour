package com.ischen.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ischen.constant.MessageConstant;
import com.ischen.entity.Result;
import com.ischen.pojo.Order;
import com.ischen.service.OrderService;
import com.ischen.util.RedisMessageConstant;
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
      Map map =   orderService.findById(id);
      return new Result(true,MessageConstant.ORDER_SUCCESS,map);
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

        // 设置移动端下单
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        // 提交订单
        Result result =  orderService.order(map);
        return result;

    }
}