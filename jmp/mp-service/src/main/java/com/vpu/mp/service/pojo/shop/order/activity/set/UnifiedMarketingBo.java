package com.vpu.mp.service.pojo.shop.order.activity.set;

import com.vpu.mp.service.pojo.shop.order.activity.firstSpecialOrderBo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 订单需要处理的统一营销
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class UnifiedMarketingBo {
    /**首单特惠存储*/
    private List<firstSpecialOrderBo> firstSpecial;

    private UnifiedMarketingBo() { }

    public static UnifiedMarketingBo create(){
        return new UnifiedMarketingBo();
    }
}
