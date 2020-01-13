package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.FreeShipPromotion;
import com.vpu.mp.service.shop.activity.dao.FreeShipProcessorDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年01月13日
 */
@Slf4j
@Service
public class FreeShipProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor {

    @Autowired
    FreeShipProcessorDao freeShipProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_FULL_REDUCTION_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_FREESHIP_ORDER;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {

    }

    /*****************商品详情处理*******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        List<FreeShipPromotion> freeShipProcessorForDetail = freeShipProcessorDao.getFreeShipProcessorForDetail(capsule.getGoodsId(), capsule.getCatId(), capsule.getSortId(), DateUtil.getLocalDateTime());

        if (freeShipProcessorForDetail != null && freeShipProcessorForDetail.size() > 0) {
            capsule.getPromotions().put(BaseConstant.ACTIVITY_TYPE_FREESHIP_ORDER,freeShipProcessorForDetail);
        }
    }
}
