package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.shop.activity.processor.ActivityGoodsListProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GoodsListMpProcessorFactory extends AbstractProcessorFactory<ActivityGoodsListProcessor, GoodsListMpBo> {

    @Override
    public void doProcess(List<GoodsListMpBo> capsules, Integer userId) {
        for (ActivityGoodsListProcessor processor : processors) {
                processor.processForList(capsules,userId);
        }
    }
}
