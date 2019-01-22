package com.wisdom.mng.controller;

import com.wisdom.mng.entity.Category;
import com.wisdom.mng.entity.PageModel;
import com.wisdom.mng.entity.Result;
import com.wisdom.mng.entity.WisdomLife;
import com.wisdom.mng.service.WidomLifeService;
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
 * @create 2019-01-22 11:32
 * @desc
 */
@RestController
@RequestMapping("widomLife")
@Api(value="智慧生活文章管理接口")
public class WisdomLifeController {
    @Autowired
    private WidomLifeService widomLifeService;

    @PostMapping("/getList")
    @ApiOperation("智慧生活文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "列表当前数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "列表请求个数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "文章标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "startpostDate", value = "发布开始时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endpostDate", value = "发布结束时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "startcreateDate", value = "创建开始时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endcreateDate", value = "创建结束时间区间", required = true, dataType = "Date")
    })
    public Result getList(@Valid PageModel pageModel, WisdomLife wisdomLife){
        Page<WisdomLife> widomLifes = widomLifeService.findByAuto(wisdomLife, PageRequest.of(pageModel.getPageIndex(), pageModel.getPageSize(),new Sort(Sort.Direction.DESC, "id")));
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,widomLifes);
    }


    @PostMapping("/saveOrUpdate")
    @ApiOperation("保存文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "title", value = "文章标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "excerpt", value = "文章摘要", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "文章内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "栏目Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "banner", value = "文章缩略图", required = true, dataType = "File"),
            @ApiImplicitParam(name = "file", value = "文章附件", required = true, dataType = "File")
    })
    public Result saveOrUpdate(MultipartHttpServletRequest multipartRequest, WisdomLife wisdomLife, Long categoryId) throws Exception{
        if(categoryId == null){
            return ResultUtils.ERROR();
        }
        Category category = new Category();
        category.setId(categoryId);
        wisdomLife.setCategory(category);
        widomLifeService.saveOrUpdate(multipartRequest,wisdomLife);
        return ResultUtils.SUCCESS();
    }

    @PostMapping("/delete")
    @ApiOperation("删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wisdomLifeIds", value = "删除的Ids", required = true, dataType = "String,String..."),
    })
    public Result delete(String wisdomLifeIds){
        List<Long> ids = new ArrayList<>();
        for (String wisdomLifeId : wisdomLifeIds.split(",")){
            ids.add(Long.parseLong(wisdomLifeId));
        }
        widomLifeService.delete(ids);
        return ResultUtils.SUCCESS();
    }

}
