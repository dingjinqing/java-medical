package com.vpu.mp.service.shop.activity.processor.list;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.AbstractCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.GoodsPrdForListInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.ActivityGoodsListMpParam;
import com.vpu.mp.service.shop.activity.dao.GoodsPrdProcessorDao;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Service
public class GoodsPrdForListProcessor extends GoodsPrdProcessorDao implements ActivityGoodsListProcessor<GoodsPrdForListInfo> {
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public ActivityGoodsListMpParam filterParam(List<ActivityGoodsListCapsule> capsules) {
        List<Integer> goodsIds = capsules.stream().map(AbstractCapsule::getCapsuleId).collect(Collectors.toList());
        ActivityGoodsListMpParam param = new ActivityGoodsListMpParam();
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, GoodsPrdForListInfo> getActivityInfo(ActivityGoodsListMpParam param) {
        return getGoodsPrdInfo(param.getGoodsIds());
    }

    @Override
    public void process(Map<Integer, GoodsPrdForListInfo> prdInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId= capsule.getCapsuleId();
            GoodsPrdForListInfo prdInfo = prdInfos.get(goodsId);
            if (prdInfo == null) {
                return;
            }
            capsule.setPrdMaxPrice(prdInfo.getMaxPrice());
            capsule.setDefaultPrd(prdInfo.getDefaultPrd());
        });
    }
}
