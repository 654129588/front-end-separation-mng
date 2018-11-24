package com.wisdom.mng.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wisdom.mng.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestEntity {

    private final Logger log = LoggerFactory.getLogger(RequestEntity.class);

    //private final String token = "5dddd9af91dc7f2d1015f8e699a1fda0";

    private String sign;         //签名结果
    private String timestamp;   //时间戳
    private String jsonString;  //请求参数json字符串

    public Result checkSign(){
        try {
            //获取请求参数
            LinkedHashMap<String, String> jsonMap = JSON.parseObject(this.jsonString, new TypeReference<LinkedHashMap<String, String>>() {});
            //token加密方式：MD5(caller + wisdom)
            String token = DigestUtils.md5DigestAsHex(new String(jsonMap.get("caller").toString()+"wisdom").getBytes());
            String param = token + this.timestamp;
            if(jsonMap != null && jsonMap.size() > 0) {
                for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                    log.info("key:{},value:{}", entry.getKey(), entry.getValue());
                    if(!entry.getKey().equals("caller")) {
                        param += entry.getValue();
                    }
                }
            }
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(param.getBytes());
            System.out.println(md5DigestAsHex);
            if(md5DigestAsHex.equals(this.sign)){
                return ResultUtils.SUCCESS();
            }
        }catch (Exception e){
            log.error("检查参数错误:{}",e.getMessage());
            return ResultUtils.DATA("检查参数错误",ResultUtils.RESULT_ERROR_CODE,null);
        }
        return ResultUtils.DATA("签名不通过",ResultUtils.RESULT_ERROR_CODE,null);
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public static void main(String[] args) {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(new String("wisdom_").getBytes());
        System.out.println(md5DigestAsHex);
    }

}
