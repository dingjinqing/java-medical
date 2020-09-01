package com.vpu.mp.service.pojo.shop.store.goods;

import com.vpu.mp.common.foundation.util.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author 王兵兵
 *
 * 2019年7月12日
 */
@Data
public class StoreGoodsListQueryParam {
	
	private Integer catId;
	private Byte isOnSale;
	
	/**
     * 是否同步pos  0未同步，1同步
     */
	private Byte isSync;
	
	private String keywords;

	private Integer storeId;

	private Byte isSaleOut;
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
