package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.activity.dao.GiftProcessorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 赠品
 * @author 王帅
 */
@Service
public class GiftProcessor implements Processor{

    @Autowired
    private GiftProcessorDao giftDao;
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GIFT;
    }

    public void getGifts(Integer userId, List<OrderGoodsBo> goodsBo, List<Byte> orderType){
        giftDao.getGifts( userId, goodsBo, orderType);
    }

}
