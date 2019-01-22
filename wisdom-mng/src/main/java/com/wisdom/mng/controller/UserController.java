package com.wisdom.mng.controller;

import com.wisdom.mng.entity.*;
import com.wisdom.mng.service.SysUserService;
import com.wisdom.mng.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-18 10:59
 * @desc chenweicong
 */
@RestController
@RequestMapping("system/user")
@Api(value="系统用户接口")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/getList")
    @ApiOperation("用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "列表当前数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "列表请求个数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
    })
    public Result getList(@Valid PageModel pageModel, SysUser sysUser){
        Page<SysUser> sysUsers = sysUserService.findByAuto(sysUser, PageRequest.of(pageModel.getPageIndex(), pageModel.getPageSize(), new Sort(Sort.Direction.DESC, "id")));
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,sysUsers);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation("保存用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "roleIds", value = "关联角色Ids", required = true, dataType = "String,String..."),
    })
    public Result saveOrUpdate(SysUser sysUser,String roleIds){
        List<SysRole> roles = new ArrayList<>();
        for (String roleId : roleIds.split(",")){
            SysRole sysRole = new SysRole();
            sysRole.setId(Long.parseLong(roleId));
            roles.add(sysRole);
        }
        sysUser.setRoles(roles);
        sysUserService.saveOrUpdate(sysUser);
        return ResultUtils.SUCCESS();
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "删除的Ids", required = true, dataType = "String,String..."),
    })
    public Result delete(String userIds){
        List<Long> ids = new ArrayList<>();
        for (String userId : userIds.split(",")){
            ids.add(Long.parseLong(userId));
        }
        sysUserService.delete(ids);
        return ResultUtils.SUCCESS();
    }
}
