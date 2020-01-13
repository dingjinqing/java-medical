package com.vpu.mp.service.shop.market.award;

import com.vpu.mp.service.pojo.shop.market.sharereward.ShareRule;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liufei
 * @date 1/13/20
 */
@Data
public class AwardParam {
    Integer activityId;
    Integer userId;
    ShareRule rule;
    String orderSn = StringUtils.EMPTY;
}
