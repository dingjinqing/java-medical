package com.vpu.mp.service.pojo.saas.question.param;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;

import java.sql.Timestamp;

public class FeedBackParam extends BasePageParam {

    private String name;

    private Integer categoryId;

    private Byte lookType;

    private Timestamp startTime;

    private Timestamp endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Byte getLookType() {
        return lookType;
    }

    public void setLookType(Byte lookType) {
        this.lookType = lookType;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }




}
