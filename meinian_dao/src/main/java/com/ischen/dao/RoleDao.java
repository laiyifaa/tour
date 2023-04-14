package com.ischen.dao;

import com.ischen.pojo.Role;

import java.util.Set;

/**
 * RoleDao
 *
 * @Author: ischen
 * @CreateTime: 2021-07-07
 * @Description:
 */
public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);
}

