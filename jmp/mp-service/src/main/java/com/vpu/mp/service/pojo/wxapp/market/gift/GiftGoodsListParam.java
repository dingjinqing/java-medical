package com.vpu.mp.service.pojo.wxapp.market.gift;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2020-03-24 16:12
 **/
@Getter
@Setter
public class GiftGoodsListParam {

    /**活动主键*/
    @NotNull
    private Integer giftId;

    private String search;

    /**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
