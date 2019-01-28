package com.wisdom.mng.controller;

import com.wisdom.mng.entity.Category;
import com.wisdom.mng.entity.PageModel;
import com.wisdom.mng.entity.Result;
import com.wisdom.mng.service.CategoryService;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-21 10:24
 * @desc
 */
@RestController
@RequestMapping("category")
@Api(value="栏目管理接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/getAllCategory")
    @ApiOperation("栏目树列表")
    public Result getList(){
        List<Category> categorys = categoryService.getCategorys();
        return ResultUtils.DATA("栏目列表树请求成功",ResultUtils.RESULT_SUCCESS_CODE,categorys);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation("保存栏目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "栏目名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryLevel", value = "栏目级别", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "parentId", value = "父ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "uploadLogo", value = "图片", required = true, dataType = "File"),
            @ApiImplicitParam(name = "identification", value = "标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orders", value = "排序", required = true, dataType = "Integer")
    })
    public Result saveOrUpdate(MultipartHttpServletRequest multipartRequest, Category category){
        categoryService.saveOrUpdate(multipartRequest,category);
        return ResultUtils.SUCCESS();
    }

    @PostMapping("/delete")
    @ApiOperation("删除栏目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryIds", value = "删除的Ids", required = true, dataType = "String,String..."),
    })
    public Result delete(String categoryIds){
        List<Long> ids = new ArrayList<>();
        for (String categoryId : categoryIds.split(",")){
            ids.add(Long.parseLong(categoryId));
        }
        categoryService.delete(ids);
        return ResultUtils.SUCCESS();
    }

    @PostMapping("/getList")
    @ApiOperation("栏目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "列表当前数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "列表请求个数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "栏目名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryLevel", value = "栏目级别", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "parentId", value = "父ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "identification", value = "标识", required = true, dataType = "String")
    })
    public Result findCategory(Category category,@Valid PageModel pageModel){
        Page<Category> categorys = categoryService.findByAuto(category,PageRequest.of(pageModel.getPageIndex(), pageModel.getPageSize(),new Sort(Sort.Direction.ASC, "orders")));
        return ResultUtils.DATA("栏目列表请求成功",ResultUtils.RESULT_SUCCESS_CODE,categorys);
    }

    @PostMapping("/getChildCategory")
    @ApiOperation("标识获取子栏目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "identification", value = "标识", required = true, dataType = "String")
    })
    public Result getChildCategory(String identification){
        List<Category> categorys = categoryService.findAllByIdentificationInOrderByOrdersAsc(identification);
        return ResultUtils.DATA("标识获取子栏目请求成功",ResultUtils.RESULT_SUCCESS_CODE,categorys);
    }
}
