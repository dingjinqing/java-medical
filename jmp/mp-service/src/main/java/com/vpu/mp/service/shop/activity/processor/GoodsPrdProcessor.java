package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.info.ProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.info.GoodsPrdProcessorDataInfo;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsBaseCapsuleParam;
import com.vpu.mp.service.shop.activity.dao.GoodsPrdProcessorDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Service
public class GoodsPrdProcessor extends GoodsPrdProcessorDao implements ActivityGoodsListProcessor{
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public GoodsBaseCapsuleParam filterParamForList(List<ActivityGoodsListCapsule> capsules) {
        List<Integer> goodsIds = capsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        GoodsBaseCapsuleParam param = new GoodsBaseCapsuleParam();
        param.setGoodsIds(goodsIds);
        return param;
    }

    @Override
    public Map<Integer, GoodsPrdProcessorDataInfo> getActivityInfoForList(GoodsBaseCapsuleParam param) {
        return getGoodsPrdInfo(param.getGoodsIds());
    }

    @Override
    public void processForList(Map<Integer,? extends ProcessorDataInfo> prdInfos, List<ActivityGoodsListCapsule> capsules) {
        capsules.forEach(capsule->{
            Integer goodsId= capsule.getGoodsId();
            GoodsPrdProcessorDataInfo prdInfo = (GoodsPrdProcessorDataInfo) prdInfos.get(goodsId);
            if (prdInfo == null) {
                return;
            }
            capsule.setPrdMaxPrice(prdInfo.getMaxPrice());
            capsule.setDefaultPrd(prdInfo.getDefaultPrd());
        });
    }
}
