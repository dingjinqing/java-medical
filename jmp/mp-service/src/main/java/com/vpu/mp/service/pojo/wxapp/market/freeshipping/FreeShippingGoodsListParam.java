package com.vpu.mp.service.pojo.wxapp.market.freeshipping;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 满包邮商品列表
 * @author 孔德成
 * @date 2019/12/11 10:25
 */
@Getter
@Setter
public class FreeShippingGoodsListParam extends BasePageParam {
    Integer userId;
    /**
     * 活动id
     */
    @NotNull
    Integer ruleId;
    /**
     * 查询内容
     */
    String searchText;
}
