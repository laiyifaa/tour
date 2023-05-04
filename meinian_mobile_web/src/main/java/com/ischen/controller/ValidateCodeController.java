package com.ischen.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ischen.constant.MessageConstant;
import com.ischen.entity.Result;
import com.ischen.util.RedisMessageConstant;
import com.ischen.util.SMSUtils;
import com.ischen.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;



/**
 * ValidateCodeController
 *
 * @Author: ischen
 * @CreateTime: 2021-07-05
 * @Description:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        // 动态生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        System.out.println("发送阿里云短信：" + code);
        // 存入到redis
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN,
                5 * 60,code + "");
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * 发送短信
     * @return
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        // 生成短信验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        // 调用阿里云发送短信


        try {
            //发送短信
            SMSUtils.sendShortMessage(telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("code===" + code);
        // 验证码存入到redis 设置过期时间
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,
                5 * 60,code + "");
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}