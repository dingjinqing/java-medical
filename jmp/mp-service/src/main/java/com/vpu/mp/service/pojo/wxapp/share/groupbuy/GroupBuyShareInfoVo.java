package com.vpu.mp.service.pojo.wxapp.share.groupbuy;

import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 *小程序-拼团活动分享操作返回类
 * @author 李晓冰
 * @date 2019年12月27日
 */
@Getter
@Setter
public class GroupBuyShareInfoVo extends GoodsShareInfo {
    /** 参团人数，默认样式时使用 */
    private Short limitAmount;
    /** 活动最低价格，默认样式时使用 */
    private BigDecimal groupBuyPrice;
}
