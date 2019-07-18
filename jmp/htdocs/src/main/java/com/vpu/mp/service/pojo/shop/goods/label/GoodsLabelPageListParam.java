package com.vpu.mp.service.pojo.shop.goods.label;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

/**
 * @author 黄荣刚
 * @date 2019年7月4日
 *
 */
@Data
public class GoodsLabelPageListParam {
	
	/** 是否删除默认值0 未删除，1已删除 */
	public static final int DEL_FLAG = 0;
	
	 /**
     * 	搜索条件
     */
    private String labelName;
	 /**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
