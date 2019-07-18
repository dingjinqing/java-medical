package com.vpu.mp.service.pojo.shop.goods.deliver;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;


/**
 * 运费模版分页信息
 * @author liangchen
 * @date  2019年7月11日
 */

@Data
public class GoodsDeliverPageListParam {
	
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
