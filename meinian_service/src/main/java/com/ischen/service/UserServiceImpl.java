package com.ischen.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ischen.dao.UserDao;
import com.ischen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl
 *
 * @Author: ischen
 * @CreateTime: 2021-07-07
 * @Description:
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}