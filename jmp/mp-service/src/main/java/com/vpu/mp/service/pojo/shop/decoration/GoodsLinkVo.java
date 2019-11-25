package com.vpu.mp.service.pojo.shop.decoration;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

@Data
public class GoodsLinkVo {
	public Integer goodsId;
	public String goodsName;
	public String goodsImg;
	public String goodsSn;
	public String keyWords;
	public static Integer page;
	
	/**
     * 	分页信息
     */
    public int currentPage = Page.DEFAULT_CURRENT_PAGE;
    public int pageRows = Page.DEFAULT_PAGE_ROWS;
}
