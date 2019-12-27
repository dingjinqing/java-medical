package com.vpu.mp.service.pojo.shop.market.message;

/**
 * 模版消息通传参类常量
 * @author 卢光耀
 * @date 2019-09-02 13:41
 *
*/
public class RabbitParamConstant {

    public static class Type{
        /**
         * 模版消息通用类型
         */
        public static final Integer GENERAL_TYPE = 1001;
        
        /**
         *微信公众号模板消息类型
         */
        public static final Integer MP_TEMPLE_TYPE=1002;

        
        /**
         * 小程序订阅消息，小程序请以2开头。切大于2000 否则发不出去
         */
        public static final Integer MA_SUBSCRIBEMESSAGE_TYPE=2001;
        
        /**
         *微信公众号模板消息类型,用户没有使用过小程序，比如扫公众号二维码
         */
        public static final Integer MP_TEMPLE_TYPE_NO_USER=1003;

//    可以参考
//    const TEMPLATE_TYPE_ORDER = 1;          // 订单类型
//    const TEMPLATE_TYPE_APPOINTMENT = 2;    // 预约类型
//    const TEMPLATE_TYPE_COUPON = 3;         // 优惠券类型
//    const TEMPLATE_TYPE_GROUP = 4;          // 拼团类型
//    const TEMPLATE_TYPE_CARD = 5;           // 会员卡类型
//    const TEMPLATE_TYPE_DISTRIBUTION = 6;   // 分销
//    const TEMPLATE_TYPE_CUSTOM = 7;         // 商家自定义
//    const TEMPLATE_TYPE_BARGAIN = 8;        // 砍价
//    const TEMPLATE_TYPE_AUDIT = 9;          // 审核
//    const TEMPLATE_TYPE_DRAW  = 10;         // 抽奖
    }
}
