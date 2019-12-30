package com.vpu.mp.service.pojo.wxapp.share.groupbuy;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 拼团活动-分享小程序-入参
 * @author 李晓冰
 * @date 2019年12月27日
 */
@Data
public class GroupBuyShareInfoParam {
    /** 活动Id */
    private Integer activityId;
    /** 活动分享图片中需要显示的拼团价格 */
    private BigDecimal realPrice;
    /** 活动分享图片中需要显示的划线价格 */
    private BigDecimal linePrice;
    /** 发起分享操作的用户Id*/
    private Integer userId;

    private Integer width;
}
