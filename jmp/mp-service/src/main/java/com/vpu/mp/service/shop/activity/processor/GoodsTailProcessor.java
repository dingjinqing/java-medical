package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.GradePrdRecord;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.GoodsDetailMpCapsule;
import com.vpu.mp.service.pojo.wxapp.activity.param.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 小程序-商品列表-处理最终价格信息
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class GoodsTailProcessor implements ProcessorPriority,ActivityGoodsListProcessor,GoodsDetailProcessor {

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return Byte.MAX_VALUE;
    }
    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<ActivityGoodsListCapsule> capsules, Integer userId) {
        capsules.forEach(capsule->{
            // 被活动处理了的话商品价就是活动价（已被活动设置），否则就是商品的规格最低价(新增商品时该字段存的就是最低价)
            if (capsule.getProcessedTypes().size() == 0) {
                capsule.setRealPrice(capsule.getShopPrice());
                capsule.setLinePrice(capsule.getMarketPrice());
            } else {
                capsule.setLinePrice(capsule.getPrdMaxPrice());
            }
        });
    }
    /*****************商品详情处理******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpCapsule capsule, GoodsDetailCapsuleParam param) {
        List<GoodsPrdMpVo> products = capsule.getProducts();

        List<GradePrdRecord> gradeCardPrice = capsule.getGradeCardPrice();

        if (gradeCardPrice != null && gradeCardPrice.size() > 0) {
            Map<Integer, BigDecimal> gradePriceMap = gradeCardPrice.stream().collect(Collectors.toMap(GradePrdRecord::getPrdId, GradePrdRecord::getGradePrice));
            products.forEach(prd-> prd.setPrdRealPrice(gradePriceMap.get(prd.getPrdId())));
        }
    }

}
