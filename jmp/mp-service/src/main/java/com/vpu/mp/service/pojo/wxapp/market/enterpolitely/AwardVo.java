package com.vpu.mp.service.pojo.wxapp.market.enterpolitely;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author liufei
 * @date 12/25/19
 */
@Data
@Builder
public class AwardVo {
    //    活动类型：优惠券 大转盘抽奖 跳转自定义链接  积分  余额  分裂优惠券
    Byte awardType;
    Integer activityId;
    // 奖励内容：大转盘抽奖（lottery_id），跳转自定义链接（customize_img_path，customize_url），积分（score），余额（balance），优惠券（title，bg_img，coupon_list）
    String awardContent;
    Map<String, String> extContent;
}
