package com.wisdom.mng.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wisdom.mng.dao.SysFunctionDao;
import com.wisdom.mng.entity.SysFunction;
import com.wisdom.mng.entity.SysRole;
import com.wisdom.mng.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2018-11-16 14:17
 * @desc chenweicong
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, AuthenticationException {
        Iterator<ConfigAttribute> iterator = collection.iterator();
        String requestUrl = StringUtils.substringAfterLast(StringUtils.deleteWhitespace(o.toString()),"URL:");
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            //当前请求需要的权限
            String needRole = ca.getAttribute();
            System.out.println(needRole);
            if ("ROLE_LOGIN".equals(needRole) || authentication.getPrincipal().equals("anonymousUser")) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                }
            }
            //当前用户所具有的超级权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("系统管理员")) {
                    return;
                }
            }
            SysUser sysUser = (SysUser) authentication.getPrincipal();

            List<SysRole> roles = sysUser.getRoles();
            //登陆后指定可访问路径过滤
            if(requestUrl.equals("/system/function/getFunctionInfo")){
                return;
            }
            //判断是否存在访问权限
            for (SysRole role: roles) {
                for (SysFunction function: role.getFunctions()) {
                    if(StringUtils.isNotBlank(function.getFunctionUrl()) && requestUrl.equals(function.getFunctionUrl())){
                        return;
                    }
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
