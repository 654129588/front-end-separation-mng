package com.wisdom.mng.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/***
 * @author CHENWEICONG
 * @create 2019-01-21 9:36
 * @desc 栏目
 */
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(value = "栏目名称")
    private String name;

    @ApiModelProperty(value = "栏目级别")
    private Short categoryLevel;

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "logo")
    private String logo;

    @ApiModelProperty(value = "标识")
    private String identification;

    @ApiModelProperty(value = "排序")
    private Short orders;

    @Transient
    private List<Category> childCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Short getOrders() {
        return orders;
    }

    public void setOrders(Short orders) {
        this.orders = orders;
    }

    public Short getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Short categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public List<Category> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<Category> childCategory) {
        this.childCategory = childCategory;
    }
}
