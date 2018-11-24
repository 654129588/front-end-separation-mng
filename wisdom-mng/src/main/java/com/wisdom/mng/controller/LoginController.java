package com.wisdom.mng.controller;

import com.wisdom.mng.entity.Result;
import com.wisdom.mng.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author CHENWEICONG
 * @create 2018-11-16 14:05
 * @desc chenweicong
 */
@RestController
@CrossOrigin(origins = "*")
@Api(value="登录接口")
public class LoginController {

    /**
     * 拦截未登录请求
     * @return
     */
    @RequestMapping("/login_p")
    public Result loginp(){
        return ResultUtils.DATA("请登录后重试",ResultUtils.RESULT_ERROR_CODE,null);
    }


    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    public void login(){
        //登录接口，交给spring security完成
    }
}
