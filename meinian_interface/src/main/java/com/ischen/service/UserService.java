package com.ischen.service;

import com.ischen.pojo.User;

/**
 * UserService
 *
 * @Author: ischen
 * @CreateTime: 2021-07-07
 * @Description:
 */
public interface UserService {
    User findUserByUsername(String username);
}

