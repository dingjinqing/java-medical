package com.vpu.mp.service.pojo.shop.overview.asset;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

/**
 * @author liufei
 * @date 2019/8/2
 * @description
 */
@Data
public class RevenueProfileParam {
    /** 筛选时间 */
    private Byte screeningTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endTime;
}
