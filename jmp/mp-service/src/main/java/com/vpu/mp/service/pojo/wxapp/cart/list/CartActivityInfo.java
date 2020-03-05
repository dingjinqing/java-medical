package com.vpu.mp.service.pojo.wxapp.cart.list;

import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 孔德成
 * @date 2019/11/13 10:15
 */
@Getter
@Setter
public class CartActivityInfo extends GoodsActivityBaseMp {

    /**
     * 活动状态  0失效 1生效
     */
    private Byte status = CartConstant.ACTIVITY_STATUS_VALID;
    /**
     * 显示位置 0显示,1标签,2上方 3选择内容
     */
    private Byte type ;
    /**
     * 专享会员等级
     */
    private Integer exclusiveGrade;

    /**
     * 首单特惠价格
     */
    private BigDecimal firstSpecialPrice;
    /**
     * 首单特惠限购数量
     */
    private Integer firstSpecialNumber;
    /**
     * 会员价格
     */
    private BigDecimal memberPriceType;

    /**
     * 秒杀价格
     */
    private BigDecimal secKillPrice;

}
