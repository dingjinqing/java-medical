package com.vpu.mp.service.pojo.shop.market.bargain.analysis;

import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 砍价活动数据图表分析的格式化输出数据
 * @author: 王兵兵
 * @create: 2019-07-30 18:09
 **/
@Data
public class BargainAnalysisDataVo {
    private List<Integer> recordNumber = new ArrayList<Integer>();
    private List<Integer> userNumber = new ArrayList<Integer>();
    private List<Integer> orderNumber = new ArrayList<Integer>();
    private List<Integer> sourceNumber = new ArrayList<Integer>();
    private List<Date> dateList = new ArrayList<Date>();

    private BargainAnalysisTotalVo total;
}
