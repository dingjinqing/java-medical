package com.vpu.mp.service.pojo.shop.market.activity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 抽奖活动出参
 *
 * @author 郑保乐
 */
@Data
@AllArgsConstructor
public class LotteryVo {

    private List<Lottery> lotteries;
}
