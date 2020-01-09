package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.reduce.ReducePriceMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.reduce.ReducePricePrdMpVo;
import com.vpu.mp.service.shop.activity.dao.ReducePriceProcessorDao;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.ReducePrice.REDUCE_PRICE;
import static com.vpu.mp.db.shop.tables.ReducePriceProduct.REDUCE_PRICE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 * 限时降价 返利
 */
@Service
public class ReducePriceProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor{
    @Autowired
    ReducePriceProcessorDao reducePriceProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_REDUCE_PRICE_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<GoodsListMpBo> availableCapsule = capsules.stream().filter(x -> BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE.equals(x.getActivityType()) && x.getProcessedTypes().size() == 0).collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsule.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsReduceListInfo = reducePriceProcessorDao.getGoodsReduceListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsule.forEach(capsule->{
            if (goodsReduceListInfo.get(capsule.getGoodsId()) == null) {
                return;
            }
            Record3<Integer, Integer, BigDecimal> record3 = goodsReduceListInfo.get(capsule.getGoodsId()).get(0);

            capsule.setRealPrice(record3.get(REDUCE_PRICE_PRODUCT.PRD_PRICE));
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
            activity.setActivityId(record3.get(REDUCE_PRICE.ID));
            activity.setActivityType(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
            capsule.getGoodsActivities().add(activity);
            capsule.getProcessedTypes().add(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
        });
    }

    /*****************商品详情处理*******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        // 已经被其它活动处理则退出
        if (capsule.getActivity() != null) {
            return;
        }

        ReducePriceMpVo reducePriceInfo = reducePriceProcessorDao.getReducePriceInfo(param.getGoodsId(), DateUtil.getLocalDateTime());
        if (reducePriceInfo == null) {
            return;
        }

        Map<Integer, GoodsPrdMpVo> prdMap = capsule.getProducts().stream().collect(Collectors.toMap(GoodsPrdMpVo::getPrdId, Function.identity()));

        List<ReducePricePrdMpVo> newReducePrds = reducePriceInfo.getReducePricePrdMpVos().stream().filter(reducePrd -> {
            GoodsPrdMpVo goodsPrdMpVo = prdMap.get(reducePrd.getProductId());
            if (goodsPrdMpVo == null) {
                return false;
            } else {
                reducePrd.setPrdPrice(goodsPrdMpVo.getPrdRealPrice());
                return true;
            }
        }).collect(Collectors.toList());

        reducePriceInfo.setReducePricePrdMpVos(newReducePrds);
        capsule.setActivity(reducePriceInfo);
    }
}
