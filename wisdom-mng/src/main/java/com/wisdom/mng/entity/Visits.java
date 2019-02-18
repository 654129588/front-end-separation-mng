package com.wisdom.mng.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/***
 * @author CHENWEICONG
 * @create 2019-01-29 11:29
 * @desc
 */
@Entity
@ApiModel
public class Visits {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="start_visits_date")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "访问开始时间")
    private Date startVisitsDate;

    @Column(name="end_visits_date")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "访问结束时间")
    private Date endVisitsDate;

    @Column(name="region")
    @ApiModelProperty(value = "省份")
    private String region;

    @Column(name="ip")
    @ApiModelProperty(value = "ip")
    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartVisitsDate() {
        return startVisitsDate;
    }

    public void setStartVisitsDate(Date startVisitsDate) {
        this.startVisitsDate = startVisitsDate;
    }

    public Date getEndVisitsDate() {
        return endVisitsDate;
    }

    public void setEndVisitsDate(Date endVisitsDate) {
        this.endVisitsDate = endVisitsDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
