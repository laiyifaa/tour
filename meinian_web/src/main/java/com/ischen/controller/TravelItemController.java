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

import java.util.List;

/**
 * TravelItemController
 *
 * @Author: ischen
 * @CreateTime: 2021-06-29
 * @Description:
 */
@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    /**
     * 查询所有
     */
    @RequestMapping("/findAll")
    public Result findAll(){
      List<TravelItem> lists =  travelItemService.findAll();
      return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,lists);
    }



    /**
     *
     * @param id :参数在提交的时候，必须前后端的参数名字必须一致
     * @return
     */
    @RequestMapping("/delete")
    public Result deleteById(Integer id){
        try {
            travelItemService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_TRAVELITEM_FAIL);
        }


    }

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