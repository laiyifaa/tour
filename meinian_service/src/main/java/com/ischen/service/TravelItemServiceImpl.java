package com.ischen.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ischen.dao.TravelItemDao;
import com.ischen.entity.PageResult;
import com.ischen.entity.QueryPageBean;
import com.ischen.pojo.TravelItem;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TravelItemServiceImpl
 *
 * @Author: ischen
 * @CreateTime: 2021-06-29
 * @Description:
 */
@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 分页插件的代码必须写到第一行
        PageHelper.startPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize());

        Page<TravelItem> page =
                travelItemDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelItem findById(Integer id) {
        return travelItemDao.findById(id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }


    /**
     * 删除分为两种：
     * ① 物理删除 表示真的从数据库里面删除 DELETE FROM t_travelgroup_travelitem WHERE travelitem_id = 30
     * ② 逻辑删除：删库跑路
     *
     *
     *
     * @param id
     */

    @Override
    public void deleteById(Integer id) {
        // 在删除之前，需要先进行查询，查询中间表是否有数据，如果中间表有数据，就不能删除
        long count =  travelItemDao.findCountByTravelgroupTravelitem(id);
        if (count > 0){
            // 说明中间表有数据
            throw new RuntimeException("没有删除权限,不允许删除");
        }

        travelItemDao.deleteById(id);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }
}