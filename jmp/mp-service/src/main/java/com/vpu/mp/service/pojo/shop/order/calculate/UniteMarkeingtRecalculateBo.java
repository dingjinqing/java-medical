package com.vpu.mp.service.pojo.shop.order.calculate;

import com.vpu.mp.service.pojo.shop.decoration.ActivityVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 统一活动改价bo
 * ********************规则**********************
 * 分销 > 首单特惠 > 会员价
 *                          (两者取最小)
 *                  限时降价
 *********************规则***********************
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class UniteMarkeingtRecalculateBo {
    private BigDecimal price;
    private Byte ActivityType;
    private Integer ActivityId;

    private UniteMarkeingtRecalculateBo(BigDecimal price, Byte activityType, Integer activityId) {
        this.price = price;
        ActivityType = activityType;
        ActivityId = activityId;
    }

    public static UniteMarkeingtRecalculateBo create(BigDecimal price, Byte activityType, Integer activityId){
        return new UniteMarkeingtRecalculateBo(price, activityType, activityId);
    }
}
