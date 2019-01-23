package com.wisdom.mng.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
public class PageModel {

    @NotNull(message = "当前数不能为空")
    @DecimalMin(value = "0",message = "当前数不能少于{value}")
    @ApiModelProperty(value = "当前数")
    private Integer pageIndex;

    @NotNull(message = "当前页请求个数不能为空")
    @DecimalMin(value = "0",message = "页请求个数不能少于{value}")
    @ApiModelProperty(value = "请求个数")
    private Integer PageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }
}
