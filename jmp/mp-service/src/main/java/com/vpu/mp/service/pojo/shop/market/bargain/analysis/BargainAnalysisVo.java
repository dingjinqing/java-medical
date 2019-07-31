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
public class BargainAnalysisVo {
    private List<Integer> recordNumber;
    private List<Integer> userNumber;
    private List<Integer> orderNumber;
    private List<Integer> sourceNumber;
    private List<Date> dateList;

    public BargainAnalysisVo(){
        if(recordNumber == null){
            recordNumber = new ArrayList<Integer>();
        }
        if(userNumber == null){
            userNumber = new ArrayList<Integer>();
        }
        if(orderNumber == null){
            orderNumber = new ArrayList<Integer>();
        }
        if(sourceNumber == null){
            sourceNumber = new ArrayList<Integer>();
        }
        if(dateList == null){
            dateList = new ArrayList<Date>();
        }
    }
}
