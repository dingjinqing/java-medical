package com.vpu.mp.service.pojo.shop.store.service;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

/**
 * @author 王兵兵
 *
 * 2019年7月15日
 */
@Data
public class StoreServiceListQueryParam {
	/**
	 *  服务名称
	 */
	private String serviceName;

	/**
	 *  服务分类
	 */
	private Integer catId;

    @PositiveOrZero
	private Integer storeId;

    /**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
