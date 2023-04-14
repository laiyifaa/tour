package com.ischen.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ischen.constant.MessageConstant;
import com.ischen.entity.PageResult;
import com.ischen.entity.QueryPageBean;
import com.ischen.entity.Result;
import com.ischen.pojo.TravelGroup;
import com.ischen.service.TravelgroupService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TravelgroupController
 *
 * @Author: ischen
 * @CreateTime: 2021-06-30
 * @Description:
 */
@RestController
@RequestMapping("/travelgroup")
public class TravelgroupController {

    @Reference
    private TravelgroupService travelgroupService;

    @RequestMapping("/findAll")
    public Result findAll(){
       List<TravelGroup> lists =  travelgroupService.findAll();
       return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,lists);
    }

    @RequestMapping("/edit")
    public Result edit(Integer[] travelItemIds , @RequestBody TravelGroup travelGroup){
        travelgroupService.edit(travelItemIds,travelGroup);
        return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }


    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id){
        List<Integer> lists =  travelgroupService.findTravelItemIdByTravelgroupId(id);
        return lists;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
      TravelGroup travelGroup =   travelgroupService.findById(id);
      return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult  = travelgroupService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(Integer[] travelItemIds,@RequestBody TravelGroup travelGroup){
        travelgroupService.add(travelItemIds,travelGroup);
        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
    }
}