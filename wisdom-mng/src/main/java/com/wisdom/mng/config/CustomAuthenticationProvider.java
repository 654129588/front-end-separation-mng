package com.wisdom.mng.config;

import com.alibaba.fastjson.JSON;
import com.wisdom.mng.dao.SysUserDao;
import com.wisdom.mng.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/***
 * @author CHENWEICONG
 * @create 2018-11-22 10:51
 * @desc chenweicong
 * 自定义登录验证
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(JSON.toJSONString(authentication));
        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();


        if(StringUtils.isBlank(details.getUsername()))
            throw new UsernameNotFoundException("用户名为空");

        if(StringUtils.isBlank(details.getPassword()))
            throw new BadCredentialsException("密码为空");

        SysUser user = sysUserDao.findByUsername(details.getUsername());

        if(user == null)
            throw new UsernameNotFoundException("用户名不存在");

        if(!details.getPassword().equals(user.getPassword()))
            throw new BadCredentialsException("用户名密码不匹配");

        //throw new DisabledException("帐号被禁用");
        return new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
