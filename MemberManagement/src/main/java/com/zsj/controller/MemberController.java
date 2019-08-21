package com.zsj.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsj.entity.Member;
import com.zsj.model.AjaxResponse;
import com.zsj.service.MemberService;
import com.zsj.utils.Keyutils;
import com.zsj.utils.MD5Util;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zsj55
 */
@RestController
@RequestMapping("mc")
public class MemberController {
    @Resource
    MemberService memberService;

    @RequestMapping("register")
    public AjaxResponse register(@RequestParam String baseUsername, @RequestParam String baseEmail, @RequestParam String basePassword, @RequestParam String inviteCode ){
        String password = MD5Util.string2MD5(basePassword);
        String email = MD5Util.string2MD5(baseEmail);
        String registerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Member member = new Member();
        member.setId(Keyutils.genUniqueKey());
        member.setEmail(email);
        member.setInviteCode(inviteCode);
        member.setRegisterDate(registerDate);
        member.setUserName(baseUsername);
        member.setPassword(password);
        memberService.register(member);
        return AjaxResponse.success(member);
    }

    @RequestMapping("find/{userName}")
    public Map findByName(@PathVariable String userName){
        Map map = new HashMap();
        List<Member> memberList = new ArrayList<Member>();
        memberList = memberService.findByName(userName);
        if (memberList.size() != 0){
            map.put("rs", "true");
        }
        else{
            map.put("rs", "false");
        }
        return map;
    }

    @RequestMapping("login")
    public Map login(@RequestParam String baseUsername, @RequestParam String basePassword, HttpServletResponse response){
        Map map = new HashMap();
        Cookie cookie = new Cookie("loginName", baseUsername);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        String password = MD5Util.string2MD5(basePassword);
        if(memberService.findByNameAndPwd(baseUsername,password).size() != 0){
            map.put("rs","true");
        }
        else{
            map.put("rs","false");
        }
        return map;
    }
}
