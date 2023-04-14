package com.ischen.dao;

import com.ischen.pojo.Permission;

import java.util.Set;

/**
 * PermissionDao
 *
 * @Author: ischen
 * @CreateTime: 2021-07-07
 * @Description:
 */
public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}

