package com.ischen.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ischen.constant.MessageConstant;
import com.ischen.dao.MemberDao;
import com.ischen.dao.OrderDao;
import com.ischen.dao.OrdersettingDao;
import com.ischen.entity.Result;
import com.ischen.pojo.Member;
import com.ischen.pojo.Order;
import com.ischen.pojo.OrderSetting;
import com.ischen.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrderServiceImpl
 *
 * @Author: ischen
 * @CreateTime: 2021-07-05
 * @Description:
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersettingDao ordersettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

//1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
    // 查询当天是否有旅游团，是否开团
//
//2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
//
//3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
//
//4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
//
//5、预约成功，更新当日的已预约人数


    @Override
    public Result order(Map map) {
        try {
            // 查询当天是否有旅游团，是否开团
            // 获取旅游日期
            String orderDate = (String) map.get("orderDate");
            // 把字符串类型转换成日期类型
            Date date = DateUtils.parseString2Date(orderDate);
            // 查询当天是否开团
            OrderSetting orderSetting =  ordersettingDao.findByOrderDate(date);
            // 判断
            if (orderSetting == null){
                // 当天没有旅游团，如果没有团，就不能进行下单
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }else {
                // 说明当前有旅游团
                // 检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
                // 获取旅游团的大小
                int number = orderSetting.getNumber();
                // 获取有多少人已经预约
                int reservations = orderSetting.getReservations();
                // 判断可预约的人数是否比团的大小要大
                if (reservations>=number){
                    // 说明这个旅游团已经约满
                    return new Result(false,MessageConstant.ORDER_FULL);
                }

            }
            // 获取用户输入的手机号码
            String telephone = (String) map.get("telephone");
            // 根据手机号码， 查询用户是否已经注册
            Member member =  memberDao.findByTelephone(telephone);
            // 判断当前用户对象是否为null
            if (member!=null){
                // 说明用户已经注册，已经注册可以直接下单
                // 检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
                // 获取会员id
                Integer memberId = member.getId();
                //  获取套餐id
                Integer setmealId = Integer.parseInt((String) map.get("setmealId")) ;
                // 创建订单对象
                String orderType = (String) map.get("orderType");
                Order order = new Order(
                        memberId,date,orderType,Order.ORDERSTATUS_NO,setmealId);
                // 查询订单，判断用户是否已经下单
                List<Order> lists =  orderDao.findByCondition(order);
                // 判断订单是否有值
                if (lists!=null &&lists.size()>0){
                    // 说明用户已经下单，如果已经下单，不能重复下单
                    return new Result(false,MessageConstant.HAS_ORDERED);
                }


            }else {
                // 说明用户没有注册，没有注册，不能下单，必须进行注册，才能下单
//                如果不是会员则自动完成注册并进行预约
                member = new Member();
                member.setName((String) map.get("name"));
                member.setSex((String) map.get("sex"));
                member.setIdCard((String) map.get("idCard"));
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberDao.add(member);
            }

            // 5、更新当日的已预约人数
            // 下单成功之后，可以预约人数，进行加1
            orderSetting.setReservations(orderSetting.getReservations() + 1);
            // 进行持久化操作，调用数据库进行更新
            ordersettingDao.editReservationsByOrderDate(orderSetting);
            // 实现下订单
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setOrderType((String) map.get("orderType"));
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
            // 订单创建成功之后，需要添加到数据库进行持久化操作
            orderDao.add(order);
            return new Result(true,MessageConstant.ORDER_SUCCESS,order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDER_FAIL);
        }


    }

    @Override
    public Map findById(Integer id) {
        Map map =  orderDao.findById(id);
        return map;
    }

    @Override
    public Map findById4Detail(Integer id) {
        return orderDao.findById4Detail(id);
    }
}