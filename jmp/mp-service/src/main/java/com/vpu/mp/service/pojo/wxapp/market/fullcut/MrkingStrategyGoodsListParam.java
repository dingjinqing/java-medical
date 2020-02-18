package com.vpu.mp.service.pojo.wxapp.market.fullcut;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2020-02-18 10:04
 **/
@Getter
@Setter
public class MrkingStrategyGoodsListParam {

    /** 满折满减活动ID */
    @NotNull
    private Integer strategyId;

    private String search;

    /**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
