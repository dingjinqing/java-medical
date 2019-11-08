package com.vpu.mp.service.pojo.shop.member.address;

import lombok.Data;
/**
 * 小程序调wx地址簿数据接受
 * @author 王帅
 */
@Data
public class WxAddress {
    private String errMsg;
    private String provinceName;
    private String cityName;
    private String countyName;
    private String detailInfo;
    private String nationalCode;
    private String postalCode;
    private String telNumber;
    private String userName;
}
