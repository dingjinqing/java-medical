package com.vpu.mp.service.foundation.util.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * 对外接口分页参数基类
 * @author 李晓冰
 * @date 2020年05月28日
 */
public class ApiBasePageParam {
    /**分页最大条码数量*/
    private static final Integer MAX_PAGE_SIZE = 50;

    /**开始页，如果为null则分页时按照第一页开始*/
    Integer page;
    /**查询条码数*/
    @JsonProperty("page_size")
    Integer pageSize;
    /**数据更新时间-开始时间，可以为null*/
    @JsonProperty("start_time")
    Timestamp startTime;
    /**数据根系时间-结束时间，可以为null,为null时视具体业务而定*/
    @JsonProperty("end_time")
    Timestamp endTime;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        } else {
            return pageSize;
        }
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
