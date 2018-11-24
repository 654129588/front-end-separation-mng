package com.wisdom.mng.utils;

import com.wisdom.mng.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/***
 * @author CHENWEICONG
 * @create 2018-11-23 16:02
 * @desc chenweicong
 */
public class UserUtils {

    public static SysUser getSysUser(){
        return SecurityContextHolder.getContext().getAuthentication() .getPrincipal() instanceof String?null:(SysUser) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
    }
}
