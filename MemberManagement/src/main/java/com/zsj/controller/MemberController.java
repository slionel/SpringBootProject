package com.zsj.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsj.entity.Member;
import com.zsj.model.AjaxResponse;
import com.zsj.model.Cookies;
import com.zsj.service.MemberService;
import com.zsj.utils.GetCharAndNumber;
import com.zsj.utils.Keyutils;
import com.zsj.utils.MD5Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    public AjaxResponse register(@RequestParam String baseUsername, @RequestParam String baseEmail, @RequestParam String basePassword ){
        String inviteCode = GetCharAndNumber.getCharAndNumr(50);
        System.out.println("register，password:"+basePassword);
        String password = MD5Utils.encrypt(baseUsername,basePassword);
        System.out.println(baseUsername);
        System.out.println(password);
        String email = baseEmail;
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


    @PostMapping("getcookie")
    public String getCookie(HttpServletRequest request){
        String loginUserName = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for(int i = 0; i < cookies.length; i++){
                Cookie cookie = cookies[i];
                if("loginName".equals(cookie.getName())){
                    loginUserName = cookie.getValue();
                }
            }
        }
        Cookies cookies1 = new Cookies();
        cookies1.setLoginUserName(loginUserName);
        String s = JSONObject.toJSONString(cookies1);
        return s;
    }

    @GetMapping("getallbyname")
    public List<Member> findAllByName(HttpServletRequest request){
        String userName = request.getParameter("userName");
        List<Member> memberList = new ArrayList<Member>();
        memberList = memberService.findByName(userName);
        return memberList;
    }

    @RequestMapping("update")
    public Map update(HttpServletRequest request){
        Map map = new HashMap();
        String id = request.getParameter("id");
        String sex = request.getParameter("sex");
        String tel = request.getParameter("tel");
        String userName = request.getParameter("username");
        String email = request.getParameter("email");
        String invitecode = request.getParameter("invitecode");
        String registerdate = request.getParameter("registerdate");
        String password = request.getParameter("password");
        Member member = new Member();
        member.setUserName(userName);
        member.setId(id);
        member.setTel(tel);
        member.setSex(sex);
        member.setEmail(email);
        member.setInviteCode(invitecode);
        member.setRegisterDate(registerdate);
        member.setPassword(password);
        if(memberService.update(member) != null){
            map.put("rs","true");
        }
        else{
            map.put("rs","false");
        }
        return map;
    }

}
