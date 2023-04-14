package com.ischen.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ischen.dao.TravelgroupDao;
import com.ischen.entity.PageResult;
import com.ischen.entity.QueryPageBean;
import com.ischen.pojo.TravelGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TravelgroupServiceImpl
 *
 * @Author: ischen
 * @CreateTime: 2021-06-30
 * @Description:
 */
@Service(interfaceClass = TravelgroupService.class)
@Transactional
public class TravelgroupServiceImpl implements TravelgroupService{

    @Autowired
    private TravelgroupDao travelgroupDao;


    /**
     * 需要往两张里面添加数据，一张表是跟团，中间表
     * @param travelItemIds
     * @param travelGroup
     */
    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        // 先插入跟团表
        travelgroupDao.add(travelGroup);
        // 插入到中间表，传入两个参数
        setTravelgroupAndTravelitem(travelGroup.getId(),travelItemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 实现分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<TravelGroup> page =  travelgroupDao.findPage(queryPageBean.getQueryString());


        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelGroup findById(Integer id) {

        return travelgroupDao.findById(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        return travelgroupDao.findTravelItemIdByTravelgroupId(id);
    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        // 编辑跟团游表
        travelgroupDao.edit(travelGroup);
        // 先删除中间表数据
        travelgroupDao.deleteTravelgroupAndTravelitem(travelGroup.getId());
        // 在添加中间表数据
        setTravelgroupAndTravelitem(travelGroup.getId(),travelItemIds);




    }

    @Override
    public List<TravelGroup> findAll() {
        return travelgroupDao.findAll();
    }

    private void setTravelgroupAndTravelitem(Integer travelGroupId, Integer[] travelItemIds) {
        for (Integer travelItemId : travelItemIds) {
            Map map = new HashMap<>();
            map.put("travelGroupId",travelGroupId);
            map.put("travelItemIds",travelItemId);
            travelgroupDao.setTravelgroupAndTravelitem(map);
        }

    }
}