package com.vpu.mp.service.pojo.shop.operation;


import lombok.Getter;

/**
 * 操作模块(装修:1001、商品:2001、订单:3001、会员:4001、营销,5001)
 * @author: 卢光耀
 * @date: 2019-07-16 10:57
 *
*/
@Getter
public enum   RecordContentTemplate {

    GOODS_CONTENT_ADD(2001,RecordContentMessage.GOODS_CONTENT_ADD);

    /**
     * 得到返回码
	 */
    private int code;

    /**
     * 返回信息
     */
    private String message;

    private RecordContentTemplate(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
