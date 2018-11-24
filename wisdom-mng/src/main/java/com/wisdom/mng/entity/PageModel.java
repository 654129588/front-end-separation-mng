package com.wisdom.mng.entity;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PageModel {

    @NotNull(message = "当前数不能为空")
    @DecimalMin(value = "0",message = "当前数不能少于{value}")
    private Integer pageIndex;

    @NotNull(message = "当前页请求个数不能为空")
    @DecimalMin(value = "10",message = "页请求个数不能少于{value}")
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