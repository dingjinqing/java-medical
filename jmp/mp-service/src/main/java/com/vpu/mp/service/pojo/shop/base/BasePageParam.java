package com.vpu.mp.service.pojo.shop.base;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用页面入参
 * @author: 卢光耀
 * @date: 2019-07-16 14:23
 *
*/
@Getter
@Setter
public class BasePageParam {
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;

    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
