package com.ischen.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ischen.constant.MessageConstant;
import com.ischen.entity.Result;
import com.ischen.pojo.Member;
import com.ischen.service.MemberService;
import com.ischen.util.RedisMessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * LoginController
 *
 * @Author: ischen
 * @CreateTime: 2021-07-07
 * @Description:
 */
@RequestMapping("/login")
@RestController
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;


    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map){
        // 获取用户输入的手机号码
        String telephone = (String) map.get("telephone");
        // 获取用户输入的验证码
        String validateCode = (String) map.get("validateCode");
        // 从redis获取真正的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        // 判断验证码是否一致
        if (codeInRedis == null ||!validateCode.equals(codeInRedis)){
            return new Result(false, MessageConstant.SEND_SMS_ERROR);
        }else {
            // 成功
            // 判断是否是会员
           Member member =  memberService.findByTelephone(telephone);
           // 判断用户是否为null
           if (member == null){
               // 不是用户，就需要进行注册
               member =  new Member();
               member.setPhoneNumber(telephone);
               member.setRegTime(new Date());
               memberService.add(member);
           }
           // 登录成功，写入到cookie
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");
            // 保存一个月
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }

    }
}