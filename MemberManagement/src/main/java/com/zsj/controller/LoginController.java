package com.zsj.controller;


import com.zsj.DTO.AddressDetailDTO;
import com.zsj.entity.Member;
import com.zsj.entity.ResponseBo;
import com.zsj.service.MemberService;
import com.zsj.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Resource
    MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseBo login(AddressDetailDTO addressDetailDTO) {
        String password = MD5Utils.encrypt(addressDetailDTO.getBaseUsername(), addressDetailDTO.getBasePassword());
        System.out.println("login::"+password);
        UsernamePasswordToken token = new UsernamePasswordToken(addressDetailDTO.getBaseUsername(), password, addressDetailDTO.isRememberMe());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return ResponseBo.ok();
        } catch (UnknownAccountException e) {
            return ResponseBo.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return ResponseBo.error(e.getMessage());
        } catch (LockedAccountException e) {
            return ResponseBo.error(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseBo.error("认证失败！");
        }
    }

    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        Member member = (Member) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("member", member);
        return "index.html";
    }


    @PostMapping("/getUser")
    @ResponseBody
    private Map getUser(){
        Map map = new HashMap();
        Member member = (Member) SecurityUtils.getSubject().getPrincipal();
        map.put("username",member.getUserName());
//        map.put("user")
        return map;
    }

}
