package com.vpu.mp.service.pojo.shop.store.verify;

import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兵兵
 *
 * 2019年7月11日
 */
@Data
@NoArgsConstructor
public class VerifierListQueryParam {
	@NotNull
	public Integer storeId;
	
	public String mobile;
	public String username;
	
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
