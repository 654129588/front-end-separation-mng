package com.wisdom.mng.controller;


import com.wisdom.mng.entity.Article;
import com.wisdom.mng.entity.Category;
import com.wisdom.mng.entity.PageModel;
import com.wisdom.mng.entity.Result;
import com.wisdom.mng.service.ArticleService;
import com.wisdom.mng.utils.ResultUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-17 11:38
 * @desc chenweicong
 */
@RestController
@RequestMapping("article")
@Api(value="新闻管理接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/getList")
    @ApiOperation("新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "列表当前数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "列表请求个数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "新闻标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "startpostDate", value = "发布开始时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endpostDate", value = "发布结束时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "startcreateDate", value = "创建开始时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endcreateDate", value = "创建结束时间区间", required = true, dataType = "Date")
    })
    public Result getList(@Valid PageModel pageModel, Article article){
       Page<Article> articles = articleService.findByAuto(article, PageRequest.of(pageModel.getPageIndex(), pageModel.getPageSize(),new Sort(Sort.Direction.DESC, "id")));
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,articles);
    }


    @PostMapping("/saveOrUpdate")
    @ApiOperation("保存新闻")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "title", value = "新闻标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "excerpt", value = "新闻摘要", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "新闻内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "栏目Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "banner", value = "新闻缩略图", required = true, dataType = "File"),
            @ApiImplicitParam(name = "file", value = "新闻附件", required = true, dataType = "File")
    })
    public Result saveOrUpdate(MultipartHttpServletRequest multipartRequest,Article article,Long categoryId) throws Exception{
        if(categoryId == null){
            return ResultUtils.ERROR();
        }
        Category category = new Category();
        category.setId(categoryId);
        article.setCategory(category);
        articleService.saveOrUpdate(multipartRequest,article);
        return ResultUtils.SUCCESS();
    }

    @PostMapping("/delete")
    @ApiOperation("删除新闻")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleIds", value = "删除的Ids", required = true, dataType = "String,String..."),
    })
    public Result delete(String articleIds){
        List<Long> ids = new ArrayList<>();
        for (String articleId : articleIds.split(",")){
            ids.add(Long.parseLong(articleId));
        }
        articleService.delete(ids);
        return ResultUtils.SUCCESS();
    }
}
