package com.vpu.mp.service.shop.activity.processor.list;

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
public class GoodsListTailProcessor implements ActivityGoodsListProcessor<ActivityForListInfo> {
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Map<Integer, ActivityForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return null;
    }

    @Override
    public void process(Map<Integer, ActivityForListInfo> activityInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            // 被活动处理了的话就是活动价，否则就是商品的规格最低价(新增商品时该字段存的就是最低价)
            capsule.setRealPrice(capsule.getGoodsPrice());

            if (capsule.getProcessedTypes().size() == 0) {
                capsule.setLinePrice(capsule.getMarketPrice());
            } else {
                capsule.setLinePrice(capsule.getPrdMaxPrice());
            }
        });
    }
}
