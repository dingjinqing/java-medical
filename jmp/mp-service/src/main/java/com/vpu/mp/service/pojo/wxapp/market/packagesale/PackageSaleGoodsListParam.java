package com.vpu.mp.service.pojo.wxapp.market.packagesale;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: 王兵兵
 * @create: 2020-02-20 10:12
 **/
@Getter
@Setter
public class PackageSaleGoodsListParam {

    private Integer packageId;
    private Integer groupId;

    private String search;
    private String sortName;
    private String sortOrder;

    /**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
