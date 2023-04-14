package com.ischen.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ischen.constant.MessageConstant;
import com.ischen.constant.RedisConstant;
import com.ischen.entity.PageResult;
import com.ischen.entity.QueryPageBean;
import com.ischen.entity.Result;
import com.ischen.pojo.Setmeal;
import com.ischen.service.SetmealService;
import com.ischen.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import java.util.UUID;

/**
 * SetmealController
 *
 * @Author: ischen
 * @CreateTime: 2021-07-02
 * @Description:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult pageResult =  setmealService.findPage(queryPageBean);
       return pageResult;
    }


    @RequestMapping("/add")
    public Result add(Integer[] travelgroupIds, @RequestBody Setmeal setmeal){
        setmealService.add(travelgroupIds,setmeal);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }



    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
            // 获取文件（图片）的名字
            String originalFilename = imgFile.getOriginalFilename();

            //  abc.jpg
            // 先找到图片当中的.
            int lastIndexOf = originalFilename.lastIndexOf(".");
            // 截取图片当中.jpg 获取后缀
            String substring = originalFilename.substring(lastIndexOf);

            // 随机生成图片的名字 UUID
            String fileName =  UUID.randomUUID().toString() + substring;



            // 上传图片
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);

            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);

            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }
}