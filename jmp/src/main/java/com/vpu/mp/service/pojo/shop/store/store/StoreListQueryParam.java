package com.vpu.mp.service.pojo.shop.store.store;

import com.vpu.mp.service.foundation.Page;

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
	public String groupName;
	public Integer groupId;
	public Boolean isAuthPos;
	public String keywords;//门店名称/编码/负责人
	
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
