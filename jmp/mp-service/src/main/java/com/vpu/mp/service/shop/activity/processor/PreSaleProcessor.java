package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.presale.PreSaleMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.presale.PreSalePrdMpVo;
import com.vpu.mp.service.shop.activity.dao.PreSaleProcessorDao;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Presale.PRESALE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Slf4j
@Service
public class PreSaleProcessor implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor {
    @Autowired
    PreSaleProcessorDao preSaleProcessorDao;

    /*****处理器优先级*****/
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_PRE_SALE_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_PRE_SALE;
    }

    /*****************商品列表处理*******************/
    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {
        List<GoodsListMpBo> availableCapsules = capsules.stream().filter(x -> BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(x.getActivityType())).collect(Collectors.toList());
        List<Integer> goodsIds = availableCapsules.stream().map(GoodsListMpBo::getGoodsId).collect(Collectors.toList());
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsPreSaleListInfo = preSaleProcessorDao.getGoodsPreSaleListInfo(goodsIds, DateUtil.getLocalDateTime());

        availableCapsules.forEach(capsule->{
            if (goodsPreSaleListInfo.get(capsule.getGoodsId()) == null) {
                return;
            }
            Record3<OrderBeforeParamInteger, Integer, BigDecimal> record3 = goodsPreSaleListInfo.get(capsule.getGoodsId()).get(0);
            capsule.setRealPrice(record3.get(PRESALE_PRODUCT.PRESALE_PRICE));
            GoodsActivityBaseMp activity = new GoodsActivityBaseMp();
            activity.setActivityId(record3.get(PRESALE.ID));
            activity.setActivityType(BaseConstant.ACTIVITY_TYPE_PRE_SALE);
            capsule.getGoodsActivities().add(activity);
            capsule.getProcessedTypes().add(BaseConstant.ACTIVITY_TYPE_PRE_SALE);

        });
    }

    /*****************商品详情处理*******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        if (param.getActivityId() == null || !BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(param.getActivityType())) {
            return;
        }

        PreSaleMpVo goodsPreSaleInfo = preSaleProcessorDao.getGoodsPreSaleInfo(param.getActivityId(), DateUtil.getLocalDateTime());

        if (BaseConstant.ACTIVITY_STATUS_NOT_HAS.equals(goodsPreSaleInfo.getActState())) {
            capsule.setActivity(goodsPreSaleInfo);
            return;
        }

        Map<Integer, GoodsPrdMpVo> prdMap = capsule.getProducts().stream().collect(Collectors.toMap(GoodsPrdMpVo::getPrdId, Function.identity()));
        List<PreSalePrdMpVo> preSalePrdMpVos = goodsPreSaleInfo.getPreSalePrdMpVos();

        int goodsNum = 0;
        List<PreSalePrdMpVo> newPreSalePrds = new ArrayList<>(prdMap.size());
        for (PreSalePrdMpVo preSalePrd : preSalePrdMpVos) {
            GoodsPrdMpVo goodsPrdMpVo = prdMap.get(preSalePrd.getProductId());
            if (goodsPrdMpVo == null) {
                continue;
            }
            // 库存数量从新设置
            int stock = preSalePrd.getStock()>goodsPrdMpVo.getPrdNumber()? goodsPrdMpVo.getPrdNumber():preSalePrd.getStock();
            preSalePrd.setStock(stock);
            goodsNum+=stock;
            preSalePrd.setPrdPrice(goodsPrdMpVo.getPrdRealPrice());
            newPreSalePrds.add(preSalePrd);
        }
        if (goodsNum == 0 && BaseConstant.needToConsiderNotHasNum(goodsPreSaleInfo.getActState())) {
            log.debug("小程序-商品详情-砍价商品数量已用完");
            goodsPreSaleInfo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_HAS_NUM);
        }
        capsule.setGoodsNumber(goodsNum);
        goodsPreSaleInfo.setPreSalePrdMpVos(newPreSalePrds);
        capsule.setActivity(goodsPreSaleInfo);
    }
}
