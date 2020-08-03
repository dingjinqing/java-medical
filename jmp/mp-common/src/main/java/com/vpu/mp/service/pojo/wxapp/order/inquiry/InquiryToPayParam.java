package com.vpu.mp.service.pojo.wxapp.order.inquiry;

import com.vpu.mp.service.pojo.wxapp.image.ImageSimpleVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private List<ImageSimpleVo> imageList=new ArrayList<>();
    private String descriptionDisease;
    private String orderSn;
    /**支付金额*/
    private BigDecimal orderAmount =BigDecimal.ZERO;
}
