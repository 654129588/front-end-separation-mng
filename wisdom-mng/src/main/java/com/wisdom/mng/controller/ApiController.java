package com.wisdom.mng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wisdom.mng.entity.*;
import com.wisdom.mng.service.*;
import com.wisdom.mng.utils.AddressUtils;
import com.wisdom.mng.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private VisitsService visitsService;

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

    @PostMapping("/partner/getPostPartner")
    @ApiOperation("合作伙伴列表")
    public Result getPostPartner(){
        List<Partner> partners = partnerService.findByPostStatus((short) 1);
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,partners);
    }

    @PostMapping("/product/getProduct")
    @ApiOperation("产品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "栏目ID", required = true, dataType = "Long")
    })
    public Result getProduct(Product product,Long categoryId){
        if(categoryId != null){
            Category category = new Category();
            category.setId(categoryId);
            product.setCategory(category);
        }
        List<Product> products = productService.findAllByAuto(product);
        for (Product product1 : products) {
            product1.setIdentification("product");
        }
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,products);
    }

    @PostMapping("/project/getProject")
    @ApiOperation("项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "栏目ID", required = true, dataType = "Long")
    })
    public Result getProject(Project project,Long categoryId){
        if(categoryId != null){
            Category category = new Category();
            category.setId(categoryId);
            project.setCategory(category);
        }
        List<Project> projects = projectService.findAllByAuto(project);
        for (Project project1 : projects) {
            project1.setIdentification("project");
        }
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,projects);
    }

    @PostMapping("/article/getArticle")
    @ApiOperation("新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "列表当前数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "列表请求个数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "栏目ID", required = true, dataType = "Long")
    })
    public Result getArticle(@Valid PageModel pageModel,Article article,Long categoryId){
        if(categoryId != null){
            Category category = new Category();
            category.setId(categoryId);
            article.setCategory(category);
        }
        Page<Article> articles = articleService.findByAuto(article, PageRequest.of(pageModel.getPageIndex(), pageModel.getPageSize(),new Sort(Sort.Direction.DESC, "id")));
        for (Article article1 : articles.getContent()) {
            article1.setIdentification("article");
        }
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,articles);
    }

    @PostMapping("/about/getAbout")
    @ApiOperation("关于列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "栏目ID", required = true, dataType = "Long")
    })
    public Result getAbout(About about,Long categoryId){
        if(categoryId != null){
            Category category = new Category();
            category.setId(categoryId);
            about.setCategory(category);
        }
        List<About> abouts = aboutService.findAllByAuto(about);
        for (About about1 : abouts) {
            about1.setIdentification("about");
        }
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,abouts);
    }

    @PostMapping("/wisdomLife/getWisdomLife")
    @ApiOperation("智慧生活列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "categoryId", value = "栏目ID", required = true, dataType = "Long")
    })
    public Result getWisdomLife(WisdomLife wisdomLife,Long categoryId){
        if(categoryId != null){
            Category category = new Category();
            category.setId(categoryId);
            wisdomLife.setCategory(category);
        }
        List<WisdomLife> wisdomLifes = widomLifeService.findAllByAuto(wisdomLife);
        for (WisdomLife wisdomLife1 : wisdomLifes) {
            wisdomLife1.setIdentification("wisdomLife");
        }
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,wisdomLifes);
    }

    @PostMapping("/saveVisits")
    @ApiOperation("保存文章浏览记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "identification", value = "标识", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long")
    })
    public Result saveVisit(String identification,Long id){
        if(id == null || StringUtils.isBlank(identification)){
            return ResultUtils.DATA("参数不正确",ResultUtils.RESULT_ERROR_CODE,null);
        }
        switch(identification){
            case "product":
                productService.saveVisits(id);
                break;
            case "project":
                projectService.saveVisits(id);
                break;
            case "about":
                aboutService.saveVisits(id);
                break;
            case "article":
                articleService.saveVisits(id);
                break;
            case "wisdomLife":
                widomLifeService.saveVisits(id);
                break;
            default :
                return ResultUtils.DATA("输入的标识不正确",ResultUtils.RESULT_ERROR_CODE,null);
        }
        return ResultUtils.SUCCESS();
    }

    @PostMapping("/getData")
    @ApiOperation("根据id获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "identification", value = "标识", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long")
    })
    public Result getData(String identification,Long id){
        if(id == null || StringUtils.isBlank(identification)){
            return ResultUtils.DATA("参数不正确",ResultUtils.RESULT_ERROR_CODE,null);
        }
        Object result = null;
        switch(identification){
            case "product":
                result = JSONObject.toJSON(productService.getOne(id));
                break;
            case "project":
                result = JSONObject.toJSON(projectService.getOne(id));
                break;
            case "about":
                result = JSONObject.toJSON(aboutService.getOne(id));
                break;
            case "article":
                result = JSONObject.toJSON(articleService.getOne(id));
                break;
            case "wisdomLife":
                result = JSONObject.toJSON(widomLifeService.getOne(id));
                break;
            default :
                return ResultUtils.DATA("输入的标识不正确",ResultUtils.RESULT_ERROR_CODE,null);
        }
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,result);
    }

    @PostMapping("/visits/saveVisits")
    @ApiOperation("保存浏览网站记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startVisitsDate", value = "访问开始时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endVisitsDate", value = "访问结束时间", required = true, dataType = "Date")
    })
    public Result visitsSaveVisits(HttpServletRequest request,Visits visits){
        try{
            String ipAddress = AddressUtils.getIpAddress(request);
            visits.setIp(ipAddress);
            visits.setRegion(AddressUtils.getAddress("ip="+ipAddress, "utf-8"));
            visitsService.saveOrUpdate(visits);
            return ResultUtils.SUCCESS();
        }catch (Exception e){
            return ResultUtils.ERROR();
        }

    }
}
