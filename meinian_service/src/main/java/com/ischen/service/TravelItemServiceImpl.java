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

/**
 * TravelItemServiceImpl
 *
 * @Author: 马伟奇
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
}