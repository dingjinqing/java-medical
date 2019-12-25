package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import org.springframework.stereotype.Service;

/**
 * 赠品
 * @author 王帅
 */
@Service
public class GiftProcessor implements Processor{
    @Override
    public Byte getPriority() {
        return null;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GIFT;
    }
}
