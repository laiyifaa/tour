package com.ischen.job;

import com.ischen.constant.RedisConstant;
import com.ischen.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * ClearImgJob
 *
 * @Author: ischen
 * @CreateTime: 2021-07-02
 * @Description:
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;


    /**
     * 半夜凌晨2点删除图片，维护网站
     */
    public void clearImg(){
        // 集合多的值，放到前面
        Set<String> set = jedisPool.getResource().sdiff(
                RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES
        );
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            // 需要删除的图片
            String pic = iterator.next();
            System.out.println("删除的图片---" + pic);
            // 删除七牛云上面的图片
            QiniuUtils.deleteFileFromQiniu(pic);
            // 删除数据库里面的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
        }
    }
}