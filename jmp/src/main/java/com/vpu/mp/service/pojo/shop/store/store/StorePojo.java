package com.vpu.mp.service.pojo.shop.store.store;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兵兵
 *
 * 2019年7月8日
 */
@Data
@NoArgsConstructor
public class StorePojo {
	private Integer   storeId;
    private String    storeName;
    private String    manager;
    private String    mobile;
    private String    storeImgs;
    private Byte      businessState;
    private Byte      businessType;
    private String    openingTime;
    private String    closeTime;
    private String    provinceCode;
    private String    cityCode;
    private String    districtCode;
    private String    latitude;
    private String    longitude;
    private String    address;
    private Integer   group;
    private String    service;
    private String    content;
    private Integer   posShopId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Short     autoPick;
    private Byte      delFlag;
}
