package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.ReducePriceProductRecord;
import com.vpu.mp.db.shop.tables.records.ReducePriceRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.reduce.ReducePriceMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.reduce.ReducePricePrdMpVo;
import com.vpu.mp.service.shop.market.firstspecial.FirstSpecialService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.ReducePrice.REDUCE_PRICE;
import static com.vpu.mp.db.shop.tables.ReducePriceGoods.REDUCE_PRICE_GOODS;
import static com.vpu.mp.db.shop.tables.ReducePriceProduct.REDUCE_PRICE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class ReducePriceProcessorDao extends ShopBaseService {

    @Autowired
    private ReducePriceService reducePriceService;

    /**
     * 获取商品的限时降价最低价格信息（正在进行或者将会进行的限时降价信息）
     * @param goodsIds 商品id集合
     * @param date 时间
     * @return key:商品id value:List<Record3<Integer, Integer, BigDecimal>> REDUCE_PRICE.ID, REDUCE_PRICE_PRODUCT.GOODS_ID, REDUCE_PRICE_PRODUCT.PRD_PRICE
     */
    public  Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> getGoodsReduceListInfo(List<Integer> goodsIds, Timestamp date) {
        // 获取正在进行或未来有效的限时降价活动
        Map<Integer, List<Record2<Integer, Integer>>> goodsGroup = db().select(REDUCE_PRICE.ID, REDUCE_PRICE_GOODS.GOODS_ID).from(REDUCE_PRICE)
            .innerJoin(REDUCE_PRICE_GOODS).on(REDUCE_PRICE.ID.eq(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID))
            .where(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(REDUCE_PRICE_GOODS.GOODS_ID.in(goodsIds))
            .and(REDUCE_PRICE.END_TIME.gt(date))
            .orderBy(REDUCE_PRICE.CREATE_TIME)
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(REDUCE_PRICE_GOODS.GOODS_ID)));

        Condition condition = DSL.noCondition();
        for (Map.Entry<Integer, List<Record2<Integer, Integer>>> entry : goodsGroup.entrySet()) {
            Integer key = entry.getKey();
            List<Record2<Integer, Integer>> value = entry.getValue();
            // 按活动时间排序
            condition = condition.or(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(key).and(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.eq(value.get(0).get(REDUCE_PRICE.ID))));
        }

        return db().select(REDUCE_PRICE.ID, REDUCE_PRICE_PRODUCT.GOODS_ID, REDUCE_PRICE_PRODUCT.PRD_PRICE)
            .from(REDUCE_PRICE).innerJoin(REDUCE_PRICE_PRODUCT).on(REDUCE_PRICE.ID.eq(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID))
            .where(condition)
            .orderBy(REDUCE_PRICE_PRODUCT.PRD_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(REDUCE_PRICE_PRODUCT.GOODS_ID)));
    }

    /**
     * 获取限时降价信息
     * @param goodsId 商品ID
     * @param date 日期
     * @return 限时降价信息，null无有效限时降价
     */
    public ReducePriceMpVo getReducePriceInfo(Integer goodsId,Timestamp date){
        List<ReducePriceRecord> reducePriceRecords = db().select(REDUCE_PRICE.asterisk()).from(REDUCE_PRICE).innerJoin(REDUCE_PRICE_GOODS).on(REDUCE_PRICE.ID.eq(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID))
            .where(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(REDUCE_PRICE_GOODS.GOODS_ID.eq(goodsId)))
            .orderBy(REDUCE_PRICE.FIRST.desc(),REDUCE_PRICE.CREATE_TIME.desc()).fetchInto(ReducePriceRecord.class);

        if (reducePriceRecords==null||reducePriceRecords.size() == 0) {
            logger().debug("小程序-商品详情-限时降价详情无数据");
            return null;
        }

        ReducePriceRecord reducePriceRecord = reducePriceRecords.get(0);
        Integer reduceId = reducePriceRecord.getId();

        // 活动结束
        if (date.compareTo(reducePriceRecord.getEndTime()) > 0) {
            logger().debug("活动结束[activityId:{}]", reduceId);
            return null;
        }

        ReducePriceMpVo vo =new ReducePriceMpVo();
        vo.setActivityId(reducePriceRecord.getId());
        vo.setActivityType(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
        vo.setActState(BaseConstant.ACTIVITY_STATUS_CAN_USE);

        // 设置的时间周期类型
        Byte periodAction = reducePriceRecord.getPeriodAction();
        // 活动的开始时刻（按周期重复时选择的每天的时刻）
        LocalTime startLocalTime = null;
        // 活动的结束时刻（按周期重复时选择的每天的时刻）
        LocalTime endLocalTime = null;
        // 按周期重复时选取的扩展天
        List<Integer> dayArr = null;

        // 拼接开始time和结束time
        if (ReducePriceService.PERIOD_ACTION_NORMAL.equals(periodAction)) {
            String[] pointTimeArr = reducePriceRecord.getPointTime().split("@");
            String[] startTimeArr = pointTimeArr[0].split(":");
            String[] endTimeArr = pointTimeArr[1].split(":");
            startLocalTime = LocalTime.of(Integer.parseInt(startTimeArr[0]), Integer.parseInt(startTimeArr[1]));
            endLocalTime = LocalTime.of(Integer.parseInt(endTimeArr[0]), Integer.parseInt(endTimeArr[1]));
        }
        // 拼接日期
        if (StringUtils.isNotBlank(reducePriceRecord.getExtendTime())) {
            dayArr = Arrays.stream(reducePriceRecord.getExtendTime().split("@")).map(Integer::valueOf).sorted().collect(Collectors.toList());
        }

        // 设置是否限购
        vo.setIsLimit(reducePriceRecord.getLimitAmount()>0);
        // 限购的数量
        vo.setLimitAmount(reducePriceRecord.getLimitAmount());
        // 是否开启超限购购买标记
        vo.setLimitFlag(FirstSpecialService.LIMIT_FLAG_YES.equals(reducePriceRecord.getLimitFlag()));

        // 设置规格活动价格
        List<ReducePriceProductRecord> reducePriceProductRecords = db().selectFrom(REDUCE_PRICE_PRODUCT).where(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.eq(reduceId).and(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(goodsId)))
            .fetchInto(ReducePriceProductRecord.class);
        List<ReducePricePrdMpVo> reducePricePrdMpVos = new ArrayList<>(reducePriceProductRecords.size());
        reducePriceProductRecords.forEach(record->{
            ReducePricePrdMpVo v= new ReducePricePrdMpVo();
            v.setProductId(record.getPrdId());
            v.setReducePrice(record.getPrdPrice());
        });
        vo.setReducePricePrdMpVos(reducePricePrdMpVos);

        // 活动未开始
        if (date.compareTo(reducePriceRecord.getStartTime()) < 0) {
            logger().debug("活动未开始[activityId:{}]", reduceId);
            vo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_START);
            vo.setNextStartTimestamp(reducePriceService.getNextActivityStartDate(dayArr, startLocalTime, endLocalTime, periodAction, reducePriceRecord.getStartTime(), reducePriceRecord.getEndTime()));
        } else {
            // 活动在可用时间范围内
            if (reducePriceService.isActivityGoingOn(reducePriceRecord.getPointTime(), reducePriceRecord.getExtendTime(), reducePriceRecord.getPeriodAction())) {
                vo.setCurrentEndTimestamp(reducePriceService.getCurrentActivityEndDate(endLocalTime,periodAction,reducePriceRecord.getEndTime()));
            } else {
                Timestamp nextActivityStartDate = reducePriceService.getNextActivityStartDate(dayArr, startLocalTime, endLocalTime, periodAction, reducePriceRecord.getStartTime(), reducePriceRecord.getEndTime());
                // 活动将截止
                if (nextActivityStartDate == null) {
                    return null;
                } else {
                    vo.setNextStartTimestamp(nextActivityStartDate);
                }
            }
        }
        return vo;
    }
}
