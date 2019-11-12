package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

/**
 * The type Service order template.
 *
 * @author liufei
 * @date 11 /12/19
 */
@Data
public class ServiceOrderTemplate {
    /**
     * The Service name.预约服务
     */
    public String serviceName;
    /**
     * The Store name.预约门店名称
     */
    public String storeName;
    /**
     * The Address.预约门店地址
     */
    public String address;
    /**
     * The Service date.预约日期
     */
    public String serviceDate;
    /**
     * The Service period.预约时间段
     */
    public String servicePeriod;
    /**
     * The Mobile.预约人电话
     */
    public String mobile;
    /**
     * The Subscriber.预约人姓名
     */
    public String subscriber;
}
