package com.vpu.mp.service.pojo.shop.market.bargain;

import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月26日
 */
@Data
public class BargainUserListQueryParam {

	@NotNull
	private int recordId;
	
	private String username;
	
	private String mobile;
	
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
