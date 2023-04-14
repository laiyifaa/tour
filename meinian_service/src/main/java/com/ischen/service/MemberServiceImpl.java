package com.ischen.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ischen.dao.MemberDao;
import com.ischen.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberServiceImpl
 *
 * @Author: ischen
 * @CreateTime: 2021-07-07
 * @Description:
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}