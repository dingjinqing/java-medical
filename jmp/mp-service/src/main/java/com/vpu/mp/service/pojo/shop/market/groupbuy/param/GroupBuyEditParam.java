package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/7/19 16:32
 */
@Data
public class GroupBuyEditParam {

    private Integer id;
    private String name;
    /**
     * 分享设置
     */
    private GroupBuyShareConfigParam share;
    private String shareConfig;

}
