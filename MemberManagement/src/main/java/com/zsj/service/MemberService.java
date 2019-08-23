package com.zsj.service;

import com.zsj.repository.MemberRepository;
import com.zsj.entity.Member;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

    public void addCookie(String userName, HttpServletResponse response){
        if(!"".equals(userName)){
            Cookie cookie = new Cookie("loginName", userName);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
        }
    }

    public Member update(Member member){
        return memberRepository.save(member);
    }
}
