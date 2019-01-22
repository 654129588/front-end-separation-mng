package com.wisdom.mng.config;

import com.wisdom.mng.dao.SysFunctionDao;
import com.wisdom.mng.dao.SysRoleDao;
import com.wisdom.mng.entity.SysFunction;
import com.wisdom.mng.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2018-11-16 14:18
 * @desc chenweicong
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    SysFunctionDao sysFunctionDao;

    @Autowired
    SysRoleDao sysRoleDao;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        if ("/login_p".equals(requestUrl)) {
            return null;
        }
        List<SysFunction> allMenu = sysFunctionDao.findAll();
        for (SysFunction menu : allMenu) {
            menu.setSysRoles(sysRoleDao.findSysRoleByFunctionId(menu.getId()));
            if (antPathMatcher.match(menu.getFunctionUrl(), requestUrl)
                    &&menu.getSysRoles().size()>0) {
                List<SysRole> roles = menu.getSysRoles();
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(values);
            }
        }
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
