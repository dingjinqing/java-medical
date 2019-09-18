package com.vpu.mp.service.pojo.shop.order.virtual;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangronggang
 * @date 2019年8月1日
 */
@Data
@NoArgsConstructor
public class CouponPackOrderPageParam {
	/** 优惠劵包  */
	private String packName;
	/** 订单号 */
	private String orderSn;
	/** 下单用户信息（用户名或电话）*/
	private String userInfo;
	/** 下单时间范围 起始*/
	private Timestamp startTime;
	/** 下单时间范围 结束 */
	private Timestamp endTime;

    /** 是否退款订单 **/
    private Boolean refund;
	
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}

