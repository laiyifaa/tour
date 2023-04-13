package com.ischen.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ischen.constant.MessageConstant;
import com.ischen.entity.PageResult;
import com.ischen.entity.QueryPageBean;
import com.ischen.entity.Result;
import com.ischen.pojo.TravelItem;
import com.ischen.service.TravelItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TravelItemController
 *
 * @Author: 马伟奇
 * @CreateTime: 2021-06-29
 * @Description:
 */
@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelItem travelItem){
        travelItemService.edit(travelItem);
        return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
       TravelItem travelItem =  travelItemService.findById(id);
       return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
    }


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult pageResult =  travelItemService.findPage(queryPageBean);
       return pageResult;
    }

    // .do可以省略
    // @RequestBody:表示对象和json数据进行互转
    // 因为前段传递过来的数据是json数据，需要把json封装到对象里面
    @RequestMapping("/add")
    public Result add(@RequestBody TravelItem travelItem){
        try {
            travelItemService.add(travelItem);
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }

}