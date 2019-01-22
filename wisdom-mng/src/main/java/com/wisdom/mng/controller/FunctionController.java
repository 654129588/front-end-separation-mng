package com.wisdom.mng.controller;


import com.wisdom.mng.dao.SysFunctionDao;
import com.wisdom.mng.entity.*;
import com.wisdom.mng.service.SysFunctionService;
import com.wisdom.mng.service.SysRoleService;
import com.wisdom.mng.utils.ResultUtils;
import com.wisdom.mng.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author CHENWEICONG
 * @create 2018-06-26 17:05
 * @desc 小区Controller
 */
@RestController
@RequestMapping("system/function")
@Api(value="菜单权限接口")
public class FunctionController {

    @Autowired
    SysFunctionService sysFunctionService;

    @Autowired
    SysRoleService sysRoleService;

    @PostMapping("/getAllSysFunction")
    @ApiOperation("菜单权限树列表")
    public Result getList(){
        List<SysFunction> functions = sysFunctionService.getAllMenu();
        return ResultUtils.DATA("获取菜单权限列表成功",ResultUtils.RESULT_SUCCESS_CODE,functions);
    }

    @PostMapping("/getFunctionInfo")
    @ApiOperation("获取当前登录用户权限")
    public Result function(){
        List<SysFunction> functions = sysFunctionService.getMenu(UserUtils.getSysUser().getId());
        return ResultUtils.DATA("获取当前登录用户权限成功",ResultUtils.RESULT_SUCCESS_CODE,functions);
    }



}
