package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/15
 * @Description: 商城概览-数据展示-入参
 */
@Data
public class DataDemonstrationParam {
    /**
     * 数据展示筛选时间
     * 1表示当天，7表示近一周，30表示近一个月，90表示近三个月
     * 默认为1当天
     */
    private byte screeningTime;
}
