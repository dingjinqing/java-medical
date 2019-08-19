package com.vpu.mp.service.pojo.shop.market.givegift;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/15 17:44
 */
@Data
public class GiveGiftListParam {

    /**
     * 导航类型  0全部 1进行中 2 未开始 3 已过期 4 已停用
     */
    private Byte navType;

    /* 分页信息 */
    private Integer currentPage;
    private Integer pageRows;
}
