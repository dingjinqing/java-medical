package com.vpu.mp.service.pojo.wxapp.market.bargain;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2019-12-31 13:58
 **/
@Getter
@Setter
public class BargainUsersListParam {
    @NotNull
    private Integer recordId;

    /**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
