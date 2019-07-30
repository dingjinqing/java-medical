package com.vpu.mp.service.pojo.shop.store.store;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兵兵
 *
 * 2019年7月4日
 */
@Data
@NoArgsConstructor
public class StoreListQueryParam {
	private String groupName;
	private Integer groupId;
	private Boolean isAuthPos;
	/**
	 *  门店名称/编码/负责人
	 */
	private String keywords;
	
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
