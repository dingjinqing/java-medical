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
    }
}
