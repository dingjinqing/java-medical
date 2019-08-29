package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/7/19 16:32
 */
@Data
public class GroupBuyEditParam {

    @NotNull
    private Integer id;
    /**
     * 活动名称
     */
    @NotNull
    private String name;
    /**
     * 分享设置
     */
    private GroupBuyShareConfigParam share;
    private String shareConfig;
    /**
     * 产品规格配置
     */
    @Size(min = 1)
    private List<GroupBuyProductParam> product;
}
