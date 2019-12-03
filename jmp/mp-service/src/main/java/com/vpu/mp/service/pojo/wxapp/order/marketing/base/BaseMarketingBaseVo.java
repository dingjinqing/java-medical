package com.vpu.mp.service.pojo.wxapp.order.marketing.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 王帅
 */
@Getter
@Setter
@ToString
public abstract class BaseMarketingBaseVo {
    private BigDecimal totalDiscount;
    private BigDecimal totalGoodsNumber;
    private BigDecimal totalPrice;
    private BigDecimal ratio;
    /**折扣标识（如优惠卷sn,会员卡no）*/
    private String identity;
    private List<OrderGoodsBo> bos;
    /**
     * 会员卡属性
     */
    @JsonIgnore
    private Byte baseCardType;
    @JsonIgnore
    private Integer baseCardId;
    /**
     * 初始化折扣比例
     * @return 折扣比例
     */
    public BigDecimal initRatio (){
        ratio = BigDecimalUtil.multiplyOrDivide(BigDecimalUtil.BigDecimalPlus.create(totalDiscount, BigDecimalUtil.Operator.Divide),
            BigDecimalUtil.BigDecimalPlus.create(totalPrice, null)
            );
        return ratio;
    }

    public boolean checkRatio(){
        if(ratio != null && (BigDecimalUtil.compareTo(ratio, BigDecimal.ZERO) >= 0 || BigDecimalUtil.compareTo(ratio, BigDecimal.ONE) <= 0)){
            return true;
        }
        return false;
    }
}
