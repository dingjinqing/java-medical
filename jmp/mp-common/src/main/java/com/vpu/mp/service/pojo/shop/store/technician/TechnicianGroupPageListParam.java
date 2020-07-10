package com.vpu.mp.service.pojo.shop.store.technician;

import javax.validation.constraints.NotNull;

import com.vpu.mp.common.foundation.data.JsonResultMessage;
import com.vpu.mp.common.foundation.util.Page;

import lombok.Data;

/**
 * @author 黄荣刚
 * @date 2019年7月18日
 *
 */
@Data
public class TechnicianGroupPageListParam {
	/** 店铺ID*/
	@NotNull(message =JsonResultMessage.STORE_STORE_ID_NULL )
	private Integer storeId;
	
	 /**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
