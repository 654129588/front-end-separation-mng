package com.wisdom.mng.controller;

import com.wisdom.mng.entity.*;
import com.wisdom.mng.service.ArticleService;
import com.wisdom.mng.service.BannerService;
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
 * @create 2019-01-22 11:14
 * @desc
 */
@RestController
@RequestMapping("banner")
@Api(value="轮播图管理接口")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @PostMapping("/getList")
    @ApiOperation("轮播图列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "列表当前数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "列表请求个数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "startpostDate", value = "发布开始时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endpostDate", value = "发布结束时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "startcreateDate", value = "创建开始时间区间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endcreateDate", value = "创建结束时间区间", required = true, dataType = "Date")
    })
    public Result getList(@Valid PageModel pageModel, Banner banner){
        Page<Banner> articles = bannerService.findByAuto(banner, PageRequest.of(pageModel.getPageIndex(), pageModel.getPageSize(),new Sort(Sort.Direction.DESC, "id")));
        return ResultUtils.DATA("请求成功",ResultUtils.RESULT_SUCCESS_CODE,articles);
    }


    @PostMapping("/saveOrUpdate")
    @ApiOperation("保存轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "url", value = "图片链接", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orders", value = "图片排序", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "postStatus", value = "发布状态", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "banner", value = "图片", required = true, dataType = "File")
    })
    public Result saveOrUpdate(MultipartHttpServletRequest multipartRequest, Banner banner) throws Exception{
        bannerService.saveOrUpdate(multipartRequest,banner);
        return ResultUtils.SUCCESS();
    }

    @PostMapping("/delete")
    @ApiOperation("删除轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bannerIds", value = "删除的Ids", required = true, dataType = "String,String..."),
    })
    public Result delete(String bannerIds){
        List<Long> ids = new ArrayList<>();
        for (String bannerId : bannerIds.split(",")){
            ids.add(Long.parseLong(bannerId));
        }
        bannerService.delete(ids);
        return ResultUtils.SUCCESS();
    }

}
