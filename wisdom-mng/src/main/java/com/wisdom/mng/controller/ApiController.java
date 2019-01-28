package com.wisdom.mng.controller;

import com.wisdom.mng.entity.*;
import com.wisdom.mng.service.*;
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

import javax.validation.Valid;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-25 16:29
 * @desc 前端API接口
 */
@RestController
@RequestMapping("api")
@Api(value="前端API接口")
public class ApiController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private AboutService aboutService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WidomLifeService widomLifeService;

    @PostMapping("/category/getAllCategory")
    @ApiOperation("栏目树列表")
    public Result getAllCategory(){
        List<Category> categorys = categoryService.getCategorys();
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,categorys);
    }

    @PostMapping("/banner/getPostBanner")
    @ApiOperation("轮播图列表")
    public Result getPostBanner(){
        List<Banner> banners = bannerService.findByPostStatus((short) 1);
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,banners);
    }

    @PostMapping("/product/getProduct")
    @ApiOperation("产品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer")
    })
    public Result getProduct(Product product){
        List<Product> products = productService.findAllByAuto(product);
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,products);
    }

    @PostMapping("/project/getProject")
    @ApiOperation("项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer")
    })
    public Result getProject(Project project){
        List<Project> projects = projectService.findAllByAuto(project);
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,projects);
    }

    @PostMapping("/article/getArticle")
    @ApiOperation("新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer")
    })
    public Result getArticle(Article article){
        List<Article> articles = articleService.findAllByAuto(article);
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,articles);
    }

    @PostMapping("/about/getAbout")
    @ApiOperation("关于列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer")
    })
    public Result getAbout(About about){
        List<About> abouts = aboutService.findAllByAuto(about);
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,abouts);
    }

    @PostMapping("/wisdomLife/getWisdomLife")
    @ApiOperation("智慧生活列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer")
    })
    public Result getWisdomLife(WisdomLife wisdomLife){
        List<WisdomLife> wisdomLifes = widomLifeService.findAllByAuto(wisdomLife);
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,wisdomLifes);
    }
}
