package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.shop.activity.dao.BargainProcessorDao;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class BargainProcessor implements Processor,ActivityGoodsListProcessor {
    @Autowired
    BargainProcessorDao bargainProcessorDao;
    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_BARGAIN_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_BARGAIN;
    }

    /*****装修商品列表*****/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<GoodsListMpBo> availableCapsules = capsules.stream().filter(x -> BaseConstant.ACTIVITY_TYPE_BARGAIN.equals(x.getActivityType())).collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsules.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, BargainRecord> goodsBargainInfo = bargainProcessorDao.getGoodsBargainListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsules.forEach(capsule->{
            if (goodsBargainInfo.get(capsule.getGoodsId()) == null) {
                return;
            }
            BargainRecord bargainRecord = goodsBargainInfo.get(capsule.getGoodsId());
            capsule.setRealPrice(BargainService.BARGAIN_TYPE_FIXED == bargainRecord.getBargainType()?bargainRecord.getExpectationPrice():bargainRecord.getFloorPrice());
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();

            activity.setActivityId(bargainRecord.getId());
            activity.setActivityType(BaseConstant.ACTIVITY_TYPE_BARGAIN);
            capsule.getGoodsActivities().add(activity);
            capsule.getProcessedTypes().add(BaseConstant.ACTIVITY_TYPE_BARGAIN);
        });
    }
}
