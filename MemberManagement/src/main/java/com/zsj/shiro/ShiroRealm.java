package com.zsj.shiro;

import com.zsj.entity.Member;
import com.zsj.repository.MemberRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 获取用户角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        return null;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        System.out.println("用户" + userName + "认证-----ShiroRealm.doGetAuthenticationInfo");
        Member member = memberRepository.findByUserName(userName).get(0);

        if (member == null) {
            throw new UnknownAccountException("用户名错误！");
        }
        if (!password.equals(member.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
//        if (user.getStatus().equals("0")) {
//            throw new LockedAccountException("账号已被锁定,请联系管理员！");
//        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(member, password, getName());
        return info;
    }

}
