package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.CommentDetailVo;
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
public class GoodsCommentProcessor implements ProcessorPriority,ActivityGoodsListProcessor,GoodsDetailProcessor{
    @Autowired
    GoodsCommentProcessorDao goodsCommentProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return 0;
    }
    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<Integer> goodsIds = capsules.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());
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

    /*****************商品详情处理*******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo goodsDetailMpBo, GoodsDetailCapsuleParam param) {
        CommentDetailVo goodsCommentInfoForDetail = goodsCommentProcessorDao.getGoodsCommentInfoForDetail(param.getGoodsId());
       goodsDetailMpBo.setComment(goodsCommentInfoForDetail);
    }
}
