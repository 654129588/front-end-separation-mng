package com.wisdom.mng.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
public class SysFunction {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message ="菜单名称不允许为空")
    @Column(name="function_name")
    private String functionName;

    @Column(name="function_level")
    private Integer functionLevel;

    @Column(name="function_url")
    private String functionUrl;

    @Column(name="orders")
    private Integer orders;

    @Column(name="parent_id")
    private Long parentId;

    @Transient
    private List<SysRole> sysRoles;

    @Transient
    private List<SysFunction> childSysFunction;

    @Transient
    private String text;

    @Transient
    private String state;

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public String getState() {
        if(functionLevel == 1){
            return "closed";
        }else{
            return "open";
        }
    }


    public List<SysFunction> getChildSysFunction() {
        return childSysFunction;
    }

    public void setChildSysFunction(List<SysFunction> childSysFunction) {
        this.childSysFunction = childSysFunction;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return this.functionName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Integer getFunctionLevel() {
        return functionLevel;
    }

    public void setFunctionLevel(Integer functionLevel) {
        this.functionLevel = functionLevel;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
