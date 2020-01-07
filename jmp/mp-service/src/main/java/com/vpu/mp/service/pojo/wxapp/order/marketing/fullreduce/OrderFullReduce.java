package com.vpu.mp.service.pojo.wxapp.order.marketing.fullreduce;

import com.vpu.mp.service.pojo.shop.market.fullcut.MrkingStrategyVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.base.BaseMarketingBaseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class OrderFullReduce extends BaseMarketingBaseVo {
    MrkingStrategyVo info;
}
