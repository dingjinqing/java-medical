package com.vpu.mp.service.pojo.shop.store.service.order;

import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月17日
 */
@Data
public class ServiceOrderListQueryParam {
	
	@NotNull
	private Integer storeId;
	
	/**
	 *订单状态 0：待服务，1：已取消，2：已完成,3待付款 
	 */
	private Byte orderStatus;
	
	private String mobile;
	
	private String serviceDateStart;
	
	private String serviceDateEnd;
	
	private String technicianName;
	
	/**
	 * 预约人姓名或服务名称
	 */
	private String keywords;
	
	/**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
