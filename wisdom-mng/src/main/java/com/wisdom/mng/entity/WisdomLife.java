package com.wisdom.mng.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/***
 * @author CHENWEICONG
 * @create 2019-01-22 11:46
 * @desc
 */
@Entity
@ApiModel
public class WisdomLife {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="title")
    @ApiModelProperty(value = "标题")
    private String title;//标题

    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    @NotFound(action= NotFoundAction.IGNORE)
    private Category category;//栏目

    @Column(name="push_status")
    @ApiModelProperty(value = "推送首页")
    private Short pushStatus;//推送首页

    @Column(name="excerpt")
    @ApiModelProperty(value = "摘要")
    private String excerpt;//摘要

    @Column(name="content",columnDefinition="longtext")
    @ApiModelProperty(value = "内容")
    private String content;//内容

    @Column(name="banner")
    @ApiModelProperty(value = "缩略图")
    private String banner;//缩略图

    @Column(name="file")
    @ApiModelProperty(value = "附件")
    private String file;//附件

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

    @Transient
    private String identification;

    @Column(name="visits")
    @ApiModelProperty(value = "访问数")
    private Integer visits;

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Short getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Short pushStatus) {
        this.pushStatus = pushStatus;
    }
}
