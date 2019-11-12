package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.shop.activity.dao.GoodsCommentProcessorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Service
public class GoodsCommentProcessor implements ProcessorPriority,ActivityGoodsListProcessor{
    @Autowired
    GoodsCommentProcessorDao goodsCommentProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return 0;
    }
    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<ActivityGoodsListCapsule> capsules, Integer userId) {
        List<Integer> goodsIds = capsules.stream().map(ActivityGoodsListCapsule::getGoodsId).collect(Collectors.toList());
        Map<Integer, Long> goodsCommentNumInfo = goodsCommentProcessorDao.getGoodsCommentNumInfo(goodsIds);

        capsules.forEach(capsule->{
            Long commentNum = goodsCommentNumInfo.get(capsule.getGoodsId());
            if (commentNum != null) {
                capsule.setCommentNum(commentNum.intValue());
            } else {
                capsule.setCommentNum(0);
            }
        });
    }
}
