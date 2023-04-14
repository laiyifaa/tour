package com.ischen.dao;

import com.ischen.pojo.Member;

/**
 * MemberDao
 *
 * @Author: ischen
 * @CreateTime: 2021-07-05
 * @Description:
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);
}

