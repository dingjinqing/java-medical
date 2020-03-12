package com.vpu.mp.service.pojo.shop.store.service.order;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

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
     * 排序依据列，0预约提交时间，1预约到店时间，默认0
     */
	private Byte orderByColumn = 0;
    /**
     * 排序规则，0倒叙，1正序，默认0
     */
	private Byte orderByDirection = 0;
	
	/**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
