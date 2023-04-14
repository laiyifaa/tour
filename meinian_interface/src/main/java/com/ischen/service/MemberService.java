package com.ischen.service;

import com.ischen.pojo.Member;

/**
 * MemberService
 *
 * @Author: ischen
 * @CreateTime: 2021-07-07
 * @Description:
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);
}

