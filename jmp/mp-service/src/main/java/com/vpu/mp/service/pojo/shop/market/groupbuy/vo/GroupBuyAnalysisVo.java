package com.vpu.mp.service.pojo.shop.market.groupbuy.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/9 15:16
 */
@Data
public class GroupBuyAnalysisVo {

    List<BigDecimal> marketPriceList = new ArrayList<>();
    List<BigDecimal> goodsPriceList = new ArrayList<>();
    List<BigDecimal> ratioList = new ArrayList<>();
    List<Integer> oldUserList = new ArrayList<>();
    List<Integer> newUserList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();;

}
