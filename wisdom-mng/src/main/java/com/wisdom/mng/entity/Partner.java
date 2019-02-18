package com.wisdom.mng.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/***
 * @author CHENWEICONG
 * @create 2019-01-29 9:23
 * @desc 合作伙伴
 */
@Entity
@ApiModel
public class Partner {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="partner")
    @ApiModelProperty(value = "图片")
    private String partner;

    @Column(name="orders")
    @ApiModelProperty(value = "图片排序")
    private Short orders;

    @Column(name="url")
    @ApiModelProperty(value = "图片链接")
    private String url;//图片链接

    @Column(name="post_status")
    @ApiModelProperty(value = "发布状态")
    private Short postStatus;//发布状态

    @Column(name="post_date")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "发布时间")
    private Date postDate;//发布时间

    @Column(name="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "创建时间")
    private Date createDate;//创建时间

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    private SysUser postUser;//发布人

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    private SysUser createUser;//创建人

    @Transient
    @ApiModelProperty(value = "发布开始时间区间")
    private Date startpostDate;//发布时间

    @Transient
    @ApiModelProperty(value = "发布开始时间区间")
    private Date endpostDate;//发布时间

    @Transient
    @ApiModelProperty(value = "创建开始时间区间")
    private Date startcreateDate;//创建时间

    @Transient
    @ApiModelProperty(value = "创建结束时间区间")
    private Date endcreateDate;//创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Short getOrders() {
        return orders;
    }

    public void setOrders(Short orders) {
        this.orders = orders;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Short getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Short postStatus) {
        this.postStatus = postStatus;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public SysUser getPostUser() {
        return postUser;
    }

    public void setPostUser(SysUser postUser) {
        this.postUser = postUser;
    }

    public SysUser getCreateUser() {
        return createUser;
    }

    public void setCreateUser(SysUser createUser) {
        this.createUser = createUser;
    }

    public Date getStartpostDate() {
        return startpostDate;
    }

    public void setStartpostDate(Date startpostDate) {
        this.startpostDate = startpostDate;
    }

    public Date getEndpostDate() {
        return endpostDate;
    }

    public void setEndpostDate(Date endpostDate) {
        this.endpostDate = endpostDate;
    }

    public Date getStartcreateDate() {
        return startcreateDate;
    }

    public void setStartcreateDate(Date startcreateDate) {
        this.startcreateDate = startcreateDate;
    }

    public Date getEndcreateDate() {
        return endcreateDate;
    }

    public void setEndcreateDate(Date endcreateDate) {
        this.endcreateDate = endcreateDate;
    }
}
