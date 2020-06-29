package com.vpu.jmd.service.base;

import com.vpu.jmd.service.base.bo.Page;

/**
 * 通用页面入参
 * @author: 卢光耀
 * @date: 2019-07-16 14:23
 *
*/

public class BasePageParam {
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;

    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageRows() {
        return pageRows;
    }

    public void setPageRows(Integer pageRows) {
        this.pageRows = pageRows;
    }
}
