package com.vpu.mp.service.pojo.shop.market.groupbuy;


import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 孔德成
 * @date 2019/7/22 14:16
 */
@Data
@NoArgsConstructor
public class GroupBuyListParam {

    /**
     *  1全部拼团活动 2 进行中 3 未开始 4 已过期 5 已停用
     */
    private Integer type;

    /**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;

}
