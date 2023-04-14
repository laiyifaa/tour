package com.ischen.dao;

import com.ischen.pojo.User;

/**
 * UserDao
 *
 * @Author: 马伟奇
 * @CreateTime: 2021-07-07
 * @Description:
 */
public interface UserDao {
    User findUserByUsername(String username);
}

