package com.vpu.mp.service.pojo.shop.market.lottery.prize;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/6 13:27
 */
@Data
public class LotteryPrize {

    private Integer id;
    private Integer lotteryId;
    private Byte    lotteryGrade;
    private Byte    chance;
    private Byte    presentType;
    private Integer presentNumber;
    private Integer integralScore;
    private Byte    couponId;
    private Byte    prdId;
    private Byte    prdKeepDays;
    private String  presentDetail;
    private String  iconImgsImage;
    private String  iconImgs;

}
