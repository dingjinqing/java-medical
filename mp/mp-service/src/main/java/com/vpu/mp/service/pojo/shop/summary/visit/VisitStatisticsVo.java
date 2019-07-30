package com.vpu.mp.service.pojo.shop.summary.visit;

import java.util.List;

import lombok.Data;

/**
 * 访问统计
 *
 * @author 郑保乐
 */
@Data
public class VisitStatisticsVo {

    private List<String> date;
    private List<Double> list;
}
