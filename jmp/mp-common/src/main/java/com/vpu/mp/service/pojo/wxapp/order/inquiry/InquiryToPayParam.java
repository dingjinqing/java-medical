package com.vpu.mp.service.pojo.wxapp.order.inquiry;

import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yangpengcheng
 */
@Data
public class InquiryToPayParam {
    /** 用户id*/
    private WxAppSessionUser user;
    /**ip地址*/
    private String clientIp;
    private Integer doctorId;
    private Integer departmentId;
    private Integer patientId;
    private String imagUrl;
    private String descriptionDisease;
    /**支付金额*/
    private BigDecimal orderAmount =BigDecimal.ZERO;
}
