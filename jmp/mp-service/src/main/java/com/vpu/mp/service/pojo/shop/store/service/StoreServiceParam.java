package com.vpu.mp.service.pojo.shop.store.service;

import com.vpu.mp.service.pojo.shop.config.pledge.group.DeleteGroup;
import com.vpu.mp.service.pojo.shop.config.pledge.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author 王兵兵
 *
 * 2019年7月15日
 */
@Data
public class StoreServiceParam {
    @NotBlank(groups = {UpdateGroup.class, DeleteGroup.class})
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
