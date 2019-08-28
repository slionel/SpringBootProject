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
    public AjaxResponse register(@RequestParam String baseUsername, @RequestParam String baseEmail, @RequestParam String basePassword, @RequestParam int grade, @RequestParam String inviterId ){
        String inviteCode = GetCharAndNumber.getCharAndNumr(50);
        String password = MD5Utils.encrypt(baseUsername,basePassword);
        String email = baseEmail;
        String registerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Member member = new Member();
        member.setId(Keyutils.genUniqueKey());
        member.setEmail(email);
        member.setInviteCode(inviteCode);
        member.setRegisterDate(registerDate);
        member.setUserName(baseUsername);
        member.setPassword(password);
        member.setGrade(grade);
        member.setInviterId(inviterId);
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
        int num = memberService.update(tel, sex, id);
        map.put("rs",num);
        return map;
    }

    @GetMapping("findbyinvitecodeval")
    public Member findByInviteCodeVal (@RequestParam("invitecodeval") String inviteCodeVal){
        return memberService.findByInviteCode(inviteCodeVal);
    }

    @GetMapping("updatemembergrade")
    public void updateMemberGrade(@RequestParam("id") String id, @RequestParam("grade") int grade){
        memberService.updateMemberGrade(grade, id);
    }

    @GetMapping("updatememberinviterid")
    public Map updateMemberInviterId(@RequestParam("id") String id, @RequestParam("inviterId") String inviterId){
        Map map = new HashMap();
        int num = memberService.updateMemberInviterId(inviterId, id);
        map.put("rs",num);
        return map;
    }
}
