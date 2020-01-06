package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.FirstSpecialProductRecord;
import com.vpu.mp.db.shop.tables.records.FirstSpecialRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.firstspecial.FirstSpecialMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.firstspecial.FirstSpecialPrdMpVo;
import com.vpu.mp.service.shop.market.firstspecial.FirstSpecialService;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.FirstSpecial.FIRST_SPECIAL;
import static com.vpu.mp.db.shop.tables.FirstSpecialGoods.FIRST_SPECIAL_GOODS;
import static com.vpu.mp.db.shop.tables.FirstSpecialProduct.FIRST_SPECIAL_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class FirstSpecialProcessorDao extends ShopBaseService {

    public static byte FOREVER_YES = 1;
    public static byte FOREVER_NO = 0;

    /**
     * 获取集合内商品的首单特惠信息
     *
     * @param goodsIds 商品id集合
     * @param date     日期
     * @return key: 商品id，value: Result<Record3<Integer, Integer, BigDecimal>>
     */
    public Map<Integer, Result<Record3<Integer, Integer, BigDecimal>>> getGoodsFirstSpecialForListInfo(List<Integer> goodsIds, Timestamp date) {
        Map<Integer, Result<Record2<Integer, Integer>>> firstSpecials = db().select(FIRST_SPECIAL.ID, FIRST_SPECIAL_GOODS.GOODS_ID).from(FIRST_SPECIAL).innerJoin(FIRST_SPECIAL_GOODS).on(FIRST_SPECIAL.ID.eq(FIRST_SPECIAL_GOODS.FIRST_SPECIAL_ID))
            .where(FIRST_SPECIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(FIRST_SPECIAL.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(FIRST_SPECIAL_GOODS.GOODS_ID.in(goodsIds))
            .and(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_YES)
                .or(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_NO).and(FIRST_SPECIAL.START_TIME.lt(date).and(FIRST_SPECIAL.END_TIME.gt(date)))))
            .orderBy(FIRST_SPECIAL.FIRST.desc())
            .fetch().intoGroups(FIRST_SPECIAL_GOODS.GOODS_ID);

        Condition condition = DSL.noCondition();
        for (Map.Entry<Integer, Result<Record2<Integer, Integer>>> entry : firstSpecials.entrySet()) {
            Record2<Integer, Integer> value = entry.getValue().get(0);
            condition = condition.or(FIRST_SPECIAL_PRODUCT.FIRST_SPECIAL_ID.eq(value.get(FIRST_SPECIAL.ID)).and(FIRST_SPECIAL_PRODUCT.GOODS_ID.eq(value.get(FIRST_SPECIAL_GOODS.GOODS_ID))));
        }

        return db().select(FIRST_SPECIAL_PRODUCT.ID, FIRST_SPECIAL_PRODUCT.GOODS_ID, FIRST_SPECIAL_PRODUCT.PRD_PRICE)
            .from(FIRST_SPECIAL_PRODUCT)
            .where(condition)
            .fetch().intoGroups(FIRST_SPECIAL_PRODUCT.GOODS_ID);
    }

    /**
     * 获取商品详情-首单特惠信息
     * @param firstSpecialId 首单特惠活动id
     * @param goodsId 商品id
     * @param date 时间点
     * @return 首单特惠详情
     */
    public FirstSpecialMpVo getFirstSpecialInfo(Integer firstSpecialId,Integer goodsId, Timestamp date) {
        FirstSpecialMpVo vo = new FirstSpecialMpVo();
        vo.setActivityId(firstSpecialId);
        vo.setActivityType(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
        vo.setActState(BaseConstant.ACTIVITY_STATUS_CAN_USE);

        FirstSpecialRecord firstSpecialRecord = db().selectFrom(FIRST_SPECIAL).where(FIRST_SPECIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(FIRST_SPECIAL.ID.eq(firstSpecialId))).fetchAny();
        // 活动不存在
        if (firstSpecialRecord == null) {
            logger().debug("小程序-商品详情-首单特惠获取失败-首单特惠活动不存在[{}]-详情处理退出", firstSpecialId);
            vo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_HAS);
            return vo;
        }
        // 活动未开启
        if (BaseConstant.ACTIVITY_STATUS_DISABLE.equals(firstSpecialRecord.getStatus())) {
            logger().debug("小程序-商品详情-拼团信息-该活动未启用[activityId:{}]", firstSpecialId);
            vo.setActState(BaseConstant.ACTIVITY_STATUS_STOP);
        }

        // 活动时间范围限制
        if (firstSpecialRecord.getIsForever().equals(FirstSpecialService.FOREVER_NO)) {
            if (date.compareTo(firstSpecialRecord.getStartTime()) < 0) {
                logger().debug("活动未开始[activityId:{}]", firstSpecialId);
                vo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_START);
                vo.setStartTime((firstSpecialRecord.getStartTime().getTime() - date.getTime()) / 1000);
            }
            if (date.compareTo(firstSpecialRecord.getEndTime()) > 0) {
                logger().debug("活动结束[activityId:{}]", firstSpecialId);
                vo.setActState(BaseConstant.ACTIVITY_STATUS_END);
            }
            vo.setEndTime((firstSpecialRecord.getEndTime().getTime() - date.getTime()) / 1000);
            vo.setStartTimestamp(firstSpecialRecord.getStartTime());
            vo.setEndTimestamp(firstSpecialRecord.getEndTime());
            vo.setIsForever(false);
        } else {
            vo.setIsForever(true);
        }

        // 是否限购数量
        vo.setIsLimit(vo.getLimitAmount()>0);
        // 限购的数量
        vo.setLimitAmount(vo.getLimitAmount());
        // 是否开启超限购购买标记
        vo.setLimitFlag(FirstSpecialService.LIMIT_FLAG_YES.equals(firstSpecialRecord.getLimitFlag()));

        // 查询对应的规格信息
        List<FirstSpecialProductRecord> firstSpecialProductRecords = db().selectFrom(FIRST_SPECIAL_PRODUCT)
            .where(FIRST_SPECIAL_PRODUCT.FIRST_SPECIAL_ID.eq(firstSpecialId).and(FIRST_SPECIAL_PRODUCT.GOODS_ID.eq(goodsId)))
            .orderBy(FIRST_SPECIAL_PRODUCT.PRD_PRICE.desc(),FIRST_SPECIAL_PRODUCT.PRD_ID.desc())
            .fetchInto(FirstSpecialProductRecord.class);

        List<FirstSpecialPrdMpVo> firstSpecialPrdMpVos = new ArrayList<>(firstSpecialProductRecords.size());
        firstSpecialProductRecords.forEach(record->{
            FirstSpecialPrdMpVo v = new FirstSpecialPrdMpVo();
            v.setProductId(record.getPrdId());
            v.setFirsSpecialPrice(record.getPrdPrice());
            firstSpecialPrdMpVos.add(v);
        });
        vo.setFirstSpecialPrdMpVos(firstSpecialPrdMpVos);

        return vo;
    }

    /**
     * 获取首单特惠--规格活动信息
     *
     * @param productIdList 规格Id
     * @return firstSpecialsPrdIdList
     */
    public Result<? extends Record> getGoodsFirstSpecialPrdId(List<Integer> productIdList, Timestamp date) {
        return db().select(FIRST_SPECIAL_PRODUCT.ID, FIRST_SPECIAL_PRODUCT.PRD_ID, FIRST_SPECIAL_PRODUCT.PRD_PRICE, FIRST_SPECIAL.LIMIT_AMOUNT, FIRST_SPECIAL.LIMIT_FLAG)
            .from(FIRST_SPECIAL_PRODUCT)
            .leftJoin(FIRST_SPECIAL).on(FIRST_SPECIAL.ID.eq(FIRST_SPECIAL_PRODUCT.FIRST_SPECIAL_ID))
            .where(FIRST_SPECIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(FIRST_SPECIAL.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(FIRST_SPECIAL_PRODUCT.PRD_ID.in(productIdList))
            .and(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_YES)
                .or(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_NO).and(FIRST_SPECIAL.START_TIME.lt(date)
                    .and(FIRST_SPECIAL.END_TIME.gt(date)))))
            .orderBy(FIRST_SPECIAL.FIRST.desc())
            .fetch();
    }

}
