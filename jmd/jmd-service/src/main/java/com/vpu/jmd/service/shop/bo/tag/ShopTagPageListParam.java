package com.vpu.jmd.service.shop.bo.tag;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

/**
 * 店铺标签分页列表入参
 * @author liangchen
 * @date 2020.05.27
 */
@Data
public class ShopTagPageListParam{
    /** 标签名称 */
    private String name;
    /** 分页信息 */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
