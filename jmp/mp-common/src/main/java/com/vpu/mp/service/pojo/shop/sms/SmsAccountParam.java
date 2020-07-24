package com.vpu.mp.service.pojo.shop.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 孔德成
 * @date 2020/7/24 16:47
 */
@Data
public class SmsAccountParam {
    /**
     * 卖家账号
     */
    private String sid;
    /**
     * 开放平台对应小程序
     */
    private Integer version = 4;
    /**
     * 账号名称
     */
    private String companyName;
}
