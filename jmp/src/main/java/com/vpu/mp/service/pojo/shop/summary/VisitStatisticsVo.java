package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
