package com.vpu.mp.service.pojo.wxapp.market.freeshipping;

import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import lombok.Getter;
import lombok.Setter;

/**
 * 满包邮列表
 * @author 孔德成
 * @date 2020/1/9 17:28
 */
@Getter
@Setter
public class FreeShipGoodsSearchVo {
    /**搜索结果数据，包含分页信息*/
    PageResult<? extends GoodsListMpVo> pageResult;
    /**划线价开关 0关，1显示*/
    Byte delMarket;
    /**购物车按钮显示配置*/
    ShowCartConfig showCart;
    /**
     * 活动规则
     */
    FreeShippingVo freeShipping;

}
