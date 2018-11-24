package com.wisdom.mng.entity;

/***
 * 数据返回的对象
 * @param <T>
 */
public class Result<T> {

    /**
     * 状态码
     */
    private String Code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 返回的具体对象
     */
    private T data;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "Code='" + Code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
