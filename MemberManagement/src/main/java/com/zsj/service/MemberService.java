package com.zsj.service;

import com.zsj.repository.MemberRepository;
import com.zsj.entity.Member;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zsj55
 */
@Service
public class MemberService {
    @Resource
    MemberRepository memberRepository;

    /**
     * 注册
     * 将登陆信息存入数据库
     * @param member
     * @return
     */
    public Member register(Member member){
        return memberRepository.save(member);
    }

    public List<Member> findByName(String userName){
        return memberRepository.findByUserName(userName);
    }

    public List<Member> findByNameAndPwd(String userName, String pwd){
        return memberRepository.findByUserNameAndPassword(userName, pwd);
    }
}
