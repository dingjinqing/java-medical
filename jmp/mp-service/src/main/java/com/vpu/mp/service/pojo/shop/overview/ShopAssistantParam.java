package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author:liufei
 * @Date:2019/7/17
 * @Description:
 */
@Data
@Component
public class ShopAssistantParam {
    private int shopId;
    private int sysId;
    private Byte isAuthOk;
    /** 商品库存偏小参数,默认5 */
    private int storeSizeNum = 5;
    /** 商品评价审核逾期参数，默认3 */
    private int commentOver = 3;
    /** 订单发货逾期参数，默认3 */
    private int deliverOver = 3;
    /** 退款申请处理预期参数，默认3 */
    private int refundOver = 3;
    /** 分销员审核超时参数,默认3 */
    private int applyOver = 3;
    /** 会员卡激活审核参数，默认2 */
    private int examineOver = 2;
    /** 优惠券库存参数，默认10 */
    private int couponSizeNum = 10;
}
