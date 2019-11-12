package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.shop.activity.dao.GroupBuyProcessorDao;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GROUP_BUY_DEFINE;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GroupBuyProcessor implements ProcessorPriority,ActivityGoodsListProcessor {

    @Autowired
    GroupBuyProcessorDao groupBuyProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_GROUP_BUY_PRIORITY;
    }
    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<ActivityGoodsListCapsule> capsules, Integer userId) {
        List<ActivityGoodsListCapsule> availableCapsules = capsules.stream().filter(x -> GoodsConstant.ACTIVITY_TYPE_GROUP_BUY.equals(x.getGoodsType())).collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsGroupBuyListInfo = groupBuyProcessorDao.getGoodsGroupBuyListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsules.forEach(capsule->{
            if (goodsGroupBuyListInfo.get(capsule.getGoodsId()) == null) {
                return;
            }
            Record3<Integer, Integer, BigDecimal> record3 = goodsGroupBuyListInfo.get(capsule.getGoodsId()).get(0);
            capsule.setRealPrice(record3.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE));
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();

            activity.setActivityId(record3.get(GROUP_BUY_DEFINE.ID));
            activity.setActivityType(GoodsConstant.ACTIVITY_TYPE_GROUP_BUY);
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_GROUP_BUY);
        });
    }
}
