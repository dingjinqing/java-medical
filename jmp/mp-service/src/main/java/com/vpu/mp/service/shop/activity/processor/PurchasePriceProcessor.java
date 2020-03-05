package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.PurchasePricePromotion;
import com.vpu.mp.service.shop.activity.dao.PurchasePriceProcessorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加价购处理器
 * @author 李晓冰
 * @date 2020年01月14日
 */
@Service
public class PurchasePriceProcessor implements Processor,GoodsDetailProcessor {
    @Autowired
    PurchasePriceProcessorDao purchasePriceProcessorDao;

    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_FREE_SHIP_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE;
    }

    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        List<PurchasePricePromotion> promotions = purchasePriceProcessorDao.getPurchasePriceInfoForDetail(capsule.getGoodsId(), DateUtil.getLocalDateTime());
        if (promotions == null) {
            return;
        }
        capsule.getPromotions().put(BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE,promotions);
    }
}
