package com.wisdom.mng.controller;

import com.wisdom.mng.entity.Category;
import com.wisdom.mng.entity.Result;
import com.wisdom.mng.utils.IOUtils;
import com.wisdom.mng.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-24 8:57
 * @desc 文件处理
 */
@RestController
@RequestMapping("file")
@Api(value="文件处理接口")
public class FileController {
    @Autowired
    private Environment env;

    @PostMapping("/uploadFile")
    @ApiOperation("上传文件")
    public Result uploadFile(MultipartFile file){
        try {
            if(file != null){
                String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String filename = System.currentTimeMillis()+suffix;
                IOUtils.saveFileFromInputStream(file.getInputStream(),env.getProperty("upload.file.path"),filename,null);
                return ResultUtils.DATA("上传文件成功",ResultUtils.RESULT_SUCCESS_CODE,env.getProperty("upload.url")+filename);
            }
            return ResultUtils.DATA("未检索到文件",ResultUtils.RESULT_ERROR_CODE,null);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.DATA("上传文件途中发生错误",ResultUtils.RESULT_ERROR_CODE,null);
        }
    }
}
