package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.PresaleProductRecord;
import com.vpu.mp.db.shop.tables.records.PresaleRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleVo;
import com.vpu.mp.service.pojo.shop.market.presale.ProductVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.presale.PreSaleMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.presale.PreSalePrdMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Presale.PRESALE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
@Slf4j
public class PreSaleProcessorDao extends PreSaleService {

    @Autowired
    OrderInfoService order;

    /**
     * 获取商品集合内的预售信息
     *
     * @param goodsIds 商品id集合
     * @param date     日期
     * @return key:商品id，value:List<Record3<Integer, Integer, BigDecimal>> PRESALE.ID, PRESALE.GOODS_ID, PRESALE_PRODUCT.PRESALE_PRICE
     */
    public Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> getGoodsPreSaleListInfo(List<Integer> goodsIds, Timestamp date) {
        // 一阶段或二阶段付定金时间限制
        // 付定金：时间限制在第一阶段或第二阶段内 ，全款：时间限制在活动指定的时间内（和第一阶段使用相同字段）
        Condition condition = (PRESALE.PRE_START_TIME.lt(date).and(PRESALE.PRE_END_TIME.gt(date))).or(PRESALE.PRE_START_TIME_2.gt(date).and(PRESALE.PRE_END_TIME_2.lt(date)));

        return db().select(PRESALE.ID, PRESALE.GOODS_ID, PRESALE_PRODUCT.PRESALE_PRICE)
            .from(PRESALE).innerJoin(PRESALE_PRODUCT).on(PRESALE.ID.eq(PRESALE_PRODUCT.PRESALE_ID))
            .where(PRESALE.GOODS_ID.in(goodsIds))
            .and(PRESALE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(PRESALE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(condition)
            .orderBy(PRESALE_PRODUCT.PRESALE_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(PRESALE.GOODS_ID)));
    }

    /**
     * 商品的预售信息
     *
     * @param activityId 活动ID
     * @param now 限制时间
     * @return 预售信息
     */
    public PreSaleMpVo getGoodsPreSaleInfo(Integer activityId, Timestamp now) {
        // 一阶段或二阶段付定金时间限制
        // 付定金：时间限制在第一阶段或第二阶段内
        //全款：时间限制在活动指定的时间内（和第一阶段使用相同字段）
        Condition condition = (PRESALE.PRE_START_TIME.lt(now).and(PRESALE.PRE_END_TIME.gt(now))).or(PRESALE.PRE_START_TIME_2.gt(now).and(PRESALE.PRE_END_TIME_2.lt(now)));

        PresaleRecord presaleRecord = db().selectFrom(PRESALE).where(PRESALE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(PRESALE.ID.eq(activityId)).and(condition))
            .fetchAny();

        PreSaleMpVo vo = new PreSaleMpVo();
        vo.setActivityId(activityId);
        vo.setActivityType(BaseConstant.ACTIVITY_TYPE_PRE_SALE);
        vo.setActState(BaseConstant.ACTIVITY_STATUS_CAN_USE);

        if (presaleRecord == null) {
            vo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_HAS);
            logger().debug("小程序-商品详情-预售活动-activityId:{}-{}", activityId, "活动不存");
            return vo;
        }

        if (BaseConstant.ACTIVITY_STATUS_DISABLE.equals(presaleRecord.getStatus())) {
            vo.setActState(BaseConstant.ACTIVITY_STATUS_STOP);
            logger().debug("小程序-商品详情-预售活动-activityId:{}-{}", activityId, "活动已停用");
        }
        // 处理活动的开始或结束状态
        setPreSaleActState(presaleRecord,vo,now);
        //设置相关属性
        vo.setPreSaleType(presaleRecord.getPresaleType());
        vo.setDeliverType(presaleRecord.getDeliverType());
        vo.setDeliverTime(presaleRecord.getDeliverTime());
        vo.setDeliverDays(presaleRecord.getDeliverDays());
        vo.setUseCoupon(PreSaleService.PRE_SALE_USE_COUPON.equals(presaleRecord.getDiscountType()));
        vo.setReturnDeposit(PreSaleService.PRE_SALE_RETURN_DEPOSIT.equals(presaleRecord.getReturnType()));
        vo.setShowPreSaleNumber(PreSaleService.PRE_SALE_SHOW_SALE_NUM.equals(presaleRecord.getShowSaleNumber()));
        vo.setOriginalBuy(PreSaleService.PRE_SALE_ORIGINAL_BUY.equals(presaleRecord.getBuyType()));
        vo.setLimitAmount(presaleRecord.getBuyNumber());
        vo.setFinalPaymentStart(presaleRecord.getStartTime());
        vo.setFinalPaymentEnd(presaleRecord.getEndTime());

        // 处理对应的规格信息
        List<PresaleProductRecord> presaleProductRecords = db().selectFrom(PRESALE_PRODUCT).where(PRESALE_PRODUCT.PRESALE_ID.eq(presaleRecord.getId()).and(PRESALE_PRODUCT.GOODS_ID.eq(presaleRecord.getGoodsId())))
            .fetchInto(PresaleProductRecord.class);
        List<PreSalePrdMpVo> prdMpVos = new ArrayList<>(presaleProductRecords.size());
        presaleProductRecords.forEach(record->{
            PreSalePrdMpVo v= new PreSalePrdMpVo();
            v.setProductId(record.getProductId());
            v.setStock(record.getPresaleNumber());
            v.setPreSalePrice(record.getPresalePrice());
            // 阶段付款
            if (PreSaleService.PRE_SALE_TYPE_SPLIT.equals(vo.getPreSaleType())) {
                v.setDepositPrice(record.getPresaleMoney());
                // 两个阶段且超过第一阶段的时候
                if (PreSaleService.PRE_SALE_TWO_PHASE.equals(presaleRecord.getPrePayStep()) && presaleRecord.getPreEndTime().compareTo(now) < 0) {
                    v.setDiscountPrice(record.getPreDiscountMoney_2());
                } else {
                    v.setDiscountPrice(record.getPreDiscountMoney_1());
                }
            }
            prdMpVos.add(v);
        });
        vo.setPreSalePrdMpVos(prdMpVos);

        return vo;
    }

    /**
     * 设置活动的开始或结束状态
     * @param presaleRecord 预售活动数据
     * @param vo 处理结果
     * @param now 时间
     */
    private void setPreSaleActState(PresaleRecord presaleRecord, PreSaleMpVo vo, Timestamp now){
        Integer activityId = presaleRecord.getId();

        // 两种类型活动都未开始
        if (presaleRecord.getPreStartTime().compareTo(now) < 0) {
            vo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_START);
            logger().debug("小程序-商品详情-预售活动-activityId:{}-{}", activityId, "活动未开始");
            vo.setStartTime((presaleRecord.getPreStartTime().getTime() - now.getTime()) / 1000);
            vo.setEndTime((presaleRecord.getPreEndTime().getTime() - now.getTime()) / 1000);
        } else {
            // 全款付活动结束
            if (PreSaleService.PRE_SALE_TYPE_ALL_MONEY.equals(presaleRecord.getPresaleType())) {
                if (presaleRecord.getPreEndTime().compareTo(now) > 0) {
                    vo.setActState(BaseConstant.ACTIVITY_STATUS_STOP);
                    logger().debug("小程序-商品详情-预售活动-activityId:{}-{}", activityId, "活动已结束");
                }
            } else {
                // 阶段付款
                // 只有一个阶段
                if (PreSaleService.PRE_SALE_ONE_PHASE.equals(presaleRecord.getPrePayStep())) {
                    // 活动已结束
                    if (presaleRecord.getPreEndTime().compareTo(now) > 0) {
                        vo.setActState(BaseConstant.ACTIVITY_STATUS_STOP);
                        logger().debug("小程序-商品详情-预售活动-activityId:{}-{}", activityId, "活动已结束");
                    }
                }else{
                    // 有两个阶段 且处于第一阶段结束，第二阶段未开始 活动未开始状态
                    if (presaleRecord.getPreEndTime().compareTo(now) < 0 && presaleRecord.getPreStartTime_2().compareTo(now) > 0) {
                        vo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_START);
                        logger().debug("小程序-商品详情-预售活动-activityId:{}-{}", activityId, "活动未开始");
                        vo.setStartTime((presaleRecord.getPreStartTime_2().getTime() - now.getTime()) / 1000);
                        vo.setEndTime((presaleRecord.getPreEndTime_2().getTime() - now.getTime()) / 1000);
                    }
                    // 第二阶段都结束了
                    if (presaleRecord.getPreEndTime_2().compareTo(now) < 0) {
                        vo.setActState(BaseConstant.ACTIVITY_STATUS_STOP);
                        logger().debug("小程序-商品详情-预售活动-activityId:{}-{}", activityId, "活动已结束");
                    }
                }
            }
        }
    }

    /**
     * 下单校验
     * @param param
     * @param activityInfo
     * @throws MpException
     */
    public void orderCheck(OrderBeforeParam param, PreSaleVo activityInfo) throws MpException {
        log.info("下单：预售校验start");
        if(activityInfo == null ||
            DelFlag.DISABLE_VALUE.equals(activityInfo.getDelFlag()) ||
            BaseConstant.ACTIVITY_STATUS_DISABLE.equals(activityInfo.getStatus()) ||
            activityInfo.getGoodsId() == null ||
            CollectionUtils.isEmpty(activityInfo.getProducts())
        ) {
            log.error("活动停用");
            throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_DISABLE);
        }
        if(param.getDate().before(activityInfo.getStartTime())) {
            log.error("活动未开始");
            throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_NO_START);
        }
        if(PreSaleService.PRESALE_MONEY_INTERVAL.equals(activityInfo.getPrePayStep())) {
            //定金期数2
            if((param.getDate().after(activityInfo.getPreEndTime()) && param.getDate().before(activityInfo.getPreStartTime2()))
                || param.getDate().after(activityInfo.getPreEndTime2())) {
                log.error("活动已结束");
                throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_END);
            }
        }else if(param.getDate().after(activityInfo.getPreEndTime())){
            //定金期数1
            log.error("活动已结束");
            throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_END);
        }
        if(activityInfo.getBuyNumber() != null && activityInfo.getBuyNumber() > 0){
            Integer hasBuyNumber = order.getPreSaletUserBuyNumber(param.getWxUserInfo().getUserId(), activityInfo.getId());
            if(hasBuyNumber >= activityInfo.getBuyNumber()) {
                log.error("购买数量已达活动上限");
                throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_NUMBER_LIMIT);
            }
        }
        if(activityInfo.getGoodsId().equals(param.getGoodsIds().get(0))) {
            //预售商品只支持一个商品,所以get(0)
            log.error("该商品不支持预售");
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NOT_SUPORT_PRESALE);
        }
        boolean flag = false;
        for (ProductVo productVo : activityInfo.getProducts()) {
            //库存校验
            if(productVo.getProductId().equals(param.getGoods().get(0).getProductId())){
                if(productVo.getPresaleNumber() >= param.getGoods().get(0).getGoodsNumber()) {
                    flag = true;
                }
            }
        }
        if(flag == false) {
            log.error("预售活动库存不足");
            throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_GOODS_OUT_OF_STOCK);
        }
        log.info("下单：预售校验end");
    }

    /**
     * 初始化营销价格
     * @param param
     * @param activityInfo
     */
    public void orderInit(OrderBeforeParam param, PreSaleVo activityInfo) {
        log.info("下单：预售初始化start");
        //优惠是否叠加
        if(!PreSaleService.PRE_SALE_USE_COUPON.equals(activityInfo.getDiscountType())) {
            param.setMemberCardNo(StringUtils.EMPTY);
            param.setCouponSn(StringUtils.EMPTY);
        }

        OrderBeforeParam.Goods goods = param.getGoods().get(0);
        for (ProductVo productVo : activityInfo.getProducts()) {
            if(productVo.getProductId().equals(goods.getProductId())){
                goods.setProductPrice(new BigDecimal((productVo.getPresalePrice().toString())));
                break;
            }
        }
        log.info("下单：预售初始化end");
    }

    public void updateStockAndSales(Map<Integer, Integer> updateParam, Integer activityId) throws MpException {
        log.info("预售更新活动库存start");
        Map<Integer, PresaleProductRecord> records = db().selectFrom(SUB_TABLE).where(SUB_TABLE.PRESALE_ID.eq(activityId).and(SUB_TABLE.PRODUCT_ID.in(updateParam.keySet()))).fetchMap(SUB_TABLE.PRODUCT_ID);
        List<PresaleProductRecord> executeParam = new ArrayList<>(records.size());
        for (Map.Entry<Integer, Integer> entry: updateParam.entrySet()) {
            PresaleProductRecord record = records.get(entry.getKey());
            if(record == null){
                if(entry.getValue() < 0 ) {
                    continue;
                }else {
                    logger().error("updateStockAndSales.预售下单,商品不存在，id:{}", entry.getKey());
                    throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NOT_EXIST, null);
                }
            }
            if(entry.getValue() > 0 && record.getPresaleNumber() < entry.getValue()){
                logger().error("updateStockAndSales.预售下单,库存不足，id:{}", entry.getKey());
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_OUT_OF_STOCK, null);
            }
            log.info("{}预售更新活动库存,prdId:{},num:{}", entry.getValue() > 0 ? "下单" : "退款", entry.getKey(), entry.getValue());
            record.setPresaleNumber(record.getPresaleNumber() - entry.getValue());
            record.setSaleNumber(record.getSaleNumber() + entry.getValue());
            executeParam.add(record);
        }
        db().batchUpdate(executeParam).execute();
        log.info("预售更新活动库存end");
    }
}
