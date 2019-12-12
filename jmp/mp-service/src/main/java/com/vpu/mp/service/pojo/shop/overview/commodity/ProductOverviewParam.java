package com.vpu.mp.service.pojo.shop.overview.commodity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * author liufei
 * date 2019/7/22
 */
@Data
public class ProductOverviewParam {
    /** 指定时间段参数 */
    protected byte dynamicDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected Date endTime;
    /** 商家分类id */
    protected int sortId;
    /** 商品品牌id */
    protected int brandId;
    /** 商品标签id */
    protected int labelId;
    public Timestamp start;
    public Timestamp end;

    public ProductOverviewParam clear() {
        this.dynamicDate = 0;
        this.startTime = null;
        this.endTime = null;
        this.sortId = 0;
        this.brandId = 0;
        this.labelId = 0;
        this.start = null;
        this.end = null;
        return this;
    }
}
