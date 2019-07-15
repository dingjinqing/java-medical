package com.vpu.mp.service.pojo.shop.store.service;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月15日
 */
@Data
public class StoreServiceCategoryListQueryParam {
	/**
	 *  分类名称
	 */
	private String catName;
	
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
