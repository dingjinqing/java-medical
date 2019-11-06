package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 小程序-商品列表-处理最终价格信息
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class GoodsTailProcessor implements ActivityGoodsListProcessor {
    @Override
    public Byte getPriority() {
        return Byte.MAX_VALUE;
    }

    @Override
    public Map<Integer, ActivityForListInfo> getActivityInfoForList(ActivityGoodsListMpParam param) {
        return null;
    }

    @Override
    public void processForList(Map<Integer,? extends ActivityForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            // 被活动处理了的话划线价就是活动价（已被活设置），否则就是商品的规格最低价(新增商品时该字段存的就是最低价)
            if (capsule.getProcessedTypes().size() == 0) {
                capsule.setRealPrice(capsule.getShopPrice());
                capsule.setLinePrice(capsule.getMarketPrice());
            } else {
                capsule.setLinePrice(capsule.getPrdMaxPrice());
            }
        });
    }
}
