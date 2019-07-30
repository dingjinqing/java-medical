package com.vpu.mp.service.pojo.shop.decoration;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

@Data
public class ChooseLinkParam {
	public Integer catId;
    public String  pageName;
    /**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
