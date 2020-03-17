package com.vpu.mp.service.pojo.wxapp.market.form;

import com.vpu.mp.service.foundation.validator.ListValid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2020/3/16
 */
@Data
@NoArgsConstructor
public class FormSubmitListVo {


    private Integer submitId;
    private Integer pageId;
    private Integer userId;
    private String openId;
    private  String nickName;
    private Integer sendScore;
    private String sendCoupons;
    private Timestamp createTime;
    private Timestamp updateTime;
    private List<SendCoupon> sendCouponList;
    public class SendCoupon{

    }
}



