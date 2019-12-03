package com.vpu.mp.service.pojo.wxapp.order.marketing.process;

import com.vpu.mp.service.pojo.wxapp.order.marketing.member.OrderMemberVo;
import lombok.Builder;
import lombok.Data;

/**
 * 临时存储默认营销信息
 * @author 王帅
 */
@Data
@Builder
public class DefaultMarketingProcess {
    private OrderMemberVo card;
    /**会员卡 0 ，优惠券 1*/
    private byte type;
}
