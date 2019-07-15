package com.vpu.mp.service.pojo.shop.store.service;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月15日
 */
@Data
public class StoreServiceParam {
	private Integer    id;
    private Integer    storeId;
    private String     serviceName;
    private String     serviceSn;
    private Integer    saleNum;
    private BigDecimal servicePrice;
    private BigDecimal serviceSubsist;
    private Integer    catId;
    private Byte       serviceShelf;
    private String     serviceImg;
    private Date       startDate;
    private Date       endDate;
    private String     startPeriod;
    private String     endPeriod;
    private Integer    serviceDuration;
    private Byte       serviceType;
    private Integer    servicesNumber;
    private Integer    techServicesNumber;
    private String     content;
    private Timestamp  createTime;
    private Timestamp  updateTime;
    private String     chargeResolve;
    private Byte       delFlag;
}
