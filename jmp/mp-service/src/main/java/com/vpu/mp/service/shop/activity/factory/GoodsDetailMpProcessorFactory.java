package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsDetailCapsuleParam;
import com.vpu.mp.service.shop.activity.processor.GoodsDetailProcessor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Service
public class GoodsDetailMpProcessorFactory extends AbstractProcessorFactory<GoodsDetailProcessor, GoodsDetailMpCapsule>{
    @Override
    public void sortProcessors() {
        processors.sort(Comparator.comparing(GoodsDetailProcessor::getPriorityForDetail));
    }

    @Override
    public void doProcess(List<GoodsDetailMpCapsule> capsules, Integer userId) {
        if (capsules == null || capsules.size() == 0) {
            return;
        }
        doProcess(capsules.get(0),userId);
    }

    public void doProcess(GoodsDetailMpCapsule goods, Integer userId) {

        GoodsDetailCapsuleParam param  = new GoodsDetailCapsuleParam();
        param.setUserId(userId);
        param.setGoodsId(goods.getGoodsId());
        param.setSortId(goods.getSortId());
        param.setCatId(goods.getCatId());

        for (GoodsDetailProcessor processor : processors) {
            processor.processGoodsDetail(goods,param);
        }

    }
}
