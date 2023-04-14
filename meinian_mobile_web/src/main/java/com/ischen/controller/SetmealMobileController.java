package com.ischen.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ischen.constant.MessageConstant;
import com.ischen.entity.Result;
import com.ischen.pojo.Setmeal;
import com.ischen.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SetmealMobileController
 *
 * @Author: ischen
 * @CreateTime: 2021-07-05
 * @Description:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {


    @Reference
    private SetmealService setmealService;

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal =  setmealService.findById(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }


    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
      List<Setmeal> lists =   setmealService.getSetmeal();
      return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,lists);
    }
}