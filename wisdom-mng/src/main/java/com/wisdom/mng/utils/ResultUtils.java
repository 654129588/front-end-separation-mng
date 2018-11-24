package com.wisdom.mng.utils;


import com.wisdom.mng.entity.Result;

public class ResultUtils {

    public static final String RESULT_SUCCESS_CODE="0";
    public static final String RESULT_ERROR_CODE="-1";

    /**
     * 返回成功
     * @return
     */
    public static Result SUCCESS(){
        Result result = new Result();
        result.setCode(RESULT_SUCCESS_CODE);
        result.setMsg("请求成功");
        result.setData(null);
        return result;
    }

    /**
     * 返回失败
     * @return
     */
    public static Result ERROR(){
        Result result = new Result();
        result.setCode(RESULT_ERROR_CODE);
        result.setMsg("请求途中发生错误");
        result.setData(null);
        return result;
    }

    /**
     * 返回数据
     * @return
     */
    public static Result DATA(String msg,String code,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}
