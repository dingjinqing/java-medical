package com.vpu.mp.service.pojo.shop.store.service.order;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月17日
 */
@Data
public class ServiceOrderListQueryVo {

	private Integer storeId;

	private String orderSn;
    private Integer orderId;


    /**
     *预约人ID
	 */
	private String userId;

	/**
     *预约人姓名
	 */
	private String subscriber;

	private String serviceName;

    private String mobile;

    /**
     *预约日期
	 */
	private String serviceDate;

    /**
     *预约时段
	 */
	private String servicePeriod;

    /**
     *技师名称
	 */
	private String technicianName;

    /**
	 * 预约支付金额
	 */
	private String serviceSubsist;

    /**
     *客户留言
	 */
	private String addMessage;
}
