package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.shop.activity.dao.FullReductionProcessorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public class FullReductionProcessor implements Processor,ActivityGoodsListProcessor {

    @Autowired
    FullReductionProcessorDao fullReductionProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_FULL_REDUCTION_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GENERAL;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<GoodsListMpBo> availableCapsules = capsules.stream().filter(x -> !GoodsConstant.isGoodsTypeIn13510(x.getActivityType())).collect(Collectors.toList());

        Timestamp now =DateUtil.getLocalDateTime();
        availableCapsules.forEach(capsule->{
            boolean has = fullReductionProcessorDao.getIsFullReductionListInfo(capsule.getGoodsId(), capsule.getCatId(), capsule.getSortId(), capsule.getBrandId(), now);
            if (has) {
                GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
                activity.setActivityType(BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION);
                capsule.getGoodsActivities().add(activity);
            }
        });
    }
}
