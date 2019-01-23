package com.wisdom.mng.controller;

import com.wisdom.mng.entity.*;
import com.wisdom.mng.service.ArticleService;
import com.wisdom.mng.service.ProjectService;
import com.wisdom.mng.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-23 10:31
 * @desc
 */
@RestController
@RequestMapping("project")
@Api(value="项目管理接口")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/getList")
    @ApiOperation("项目列表")
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
    public Result getList(@Valid PageModel pageModel, Project project){
        Page<Project> articles = projectService.findByAuto(project, PageRequest.of(pageModel.getPageIndex(), pageModel.getPageSize(),new Sort(Sort.Direction.DESC, "id")));
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,articles);
    }


    @PostMapping("/saveOrUpdate")
    @ApiOperation("保存项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "title", value = "项目标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "excerpt", value = "项目摘要", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "项目内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "栏目Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "banner", value = "项目缩略图", required = true, dataType = "File"),
            @ApiImplicitParam(name = "file", value = "项目附件", required = true, dataType = "File")
    })
    public Result saveOrUpdate(MultipartHttpServletRequest multipartRequest, Project project, Long categoryId) throws Exception{
        if(categoryId == null){
            return ResultUtils.ERROR();
        }
        Category category = new Category();
        category.setId(categoryId);
        project.setCategory(category);
        projectService.saveOrUpdate(multipartRequest,project);
        return ResultUtils.SUCCESS();
    }

    @PostMapping("/delete")
    @ApiOperation("删除项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectIds", value = "删除的Ids", required = true, dataType = "String,String..."),
    })
    public Result delete(String projectIds){
        List<Long> ids = new ArrayList<>();
        for (String projectId : projectIds.split(",")){
            ids.add(Long.parseLong(projectId));
        }
        projectService.delete(ids);
        return ResultUtils.SUCCESS();
    }
}
