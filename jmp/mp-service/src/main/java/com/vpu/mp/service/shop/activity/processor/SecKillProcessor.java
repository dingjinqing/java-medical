package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsActivityBaseMpVo;
import com.vpu.mp.service.shop.activity.dao.SecKillProcessorDao;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 * 秒杀
 */
@Service
public class SecKillProcessor implements ProcessorPriority,ActivityGoodsListProcessor {
    @Autowired
    SecKillProcessorDao secKillProcessorDao;
    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_SEC_KILL_PRIORITY;
    }
    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<ActivityGoodsListCapsule> capsules, Integer userId) {
        List<ActivityGoodsListCapsule> availableCapsules = capsules.stream().filter(x -> GoodsConstant.ACTIVITY_TYPE_SEC_KILL.equals(x.getGoodsType())).collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsSecKillListInfo = secKillProcessorDao.getGoodsSecKillListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsules.forEach(capsule->{
            if (goodsSecKillListInfo.get(capsule.getGoodsId()) == null) {
                return;
            }
            Record3<Integer, Integer, BigDecimal> record3 = goodsSecKillListInfo.get(capsule.getGoodsId()).get(0);

            capsule.setRealPrice(record3.get(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE));
            GoodsActivityBaseMpVo activity = new GoodsActivityBaseMpVo();
            activity.setActivityType(GoodsConstant.ACTIVITY_TYPE_SEC_KILL);
            capsule.getActivities().add(activity);
            capsule.getProcessedTypes().add(GoodsConstant.ACTIVITY_TYPE_SEC_KILL);
        });
    }
}
