package com.vpu.mp.service.pojo.shop.market.form.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 孔德成
 * @date 2020/5/6
 */
@Getter
@Setter
@ToString
public class SendCoupon {
    private String act_code;
    private int denomination;
    private String consume_text;
    private String receive_text;
    private Integer coupon_id;
    private int use_score;
    private int score_number;
    private int limitSurplusFlag;
}
