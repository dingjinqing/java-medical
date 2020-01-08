package com.vpu.mp.service.shop.market.reduceprice;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.ReducePriceGoodsRecord;
import com.vpu.mp.db.shop.tables.records.ReducePriceProductRecord;
import com.vpu.mp.db.shop.tables.records.ReducePriceRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfigVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsProductVo;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.reduceprice.*;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.shop.goods.GoodsService;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.ReducePrice.REDUCE_PRICE;
import static com.vpu.mp.db.shop.tables.ReducePriceGoods.REDUCE_PRICE_GOODS;
import static com.vpu.mp.db.shop.tables.ReducePriceProduct.REDUCE_PRICE_PRODUCT;
import static org.jooq.impl.DSL.countDistinct;
import static org.jooq.impl.DSL.sum;

/**
 * @author: 王兵兵
 * @create: 2019-08-14 10:02
 **/
@Service
public class ReducePriceService extends ShopBaseService {

    public static final Byte PERIOD_ACTION_NORMAL = 0;
    public static final Byte PERIOD_ACTION_EVERY_DAY = 1;
    public static final Byte PERIOD_ACTION_EVERY_MONTH = 2;
    public static final Byte PERIOD_ACTION_EVERY_WEEK = 3;


    @Autowired
    GoodsService goodsService;
    @Autowired
    DomainConfig domainConfig;

    /**
     * 新建限时降价活动
     */
    public void addReducePrice(ReducePriceAddParam param) {
        this.transaction(() -> {
            ReducePriceRecord record = db().newRecord(REDUCE_PRICE);
            assign(param, record);
            if (param.getShareConfig() != null) {
                record.setShareConfig(Util.toJson(param.getShareConfig()));
            }
            record.insert();
            Integer reducePriceId = record.getId();
            for (ReducePriceGoodsAddParam goods : param.getReducePriceGoodsAddParams()) {
                ReducePriceGoodsRecord goodsRecord = db().newRecord(REDUCE_PRICE_GOODS);
                assign(goods, goodsRecord);
                goodsRecord.setReducePriceId(reducePriceId);
                goodsRecord.insert();
                Integer goodsId = goodsRecord.getGoodsId();
                if (goods.getReducePriceProduct() != null && goods.getReducePriceProduct().length > 0) {
                    for (ReducePriceGoodsProductAddParam goodsProduct : goods.getReducePriceProduct()) {
                        ReducePriceProductRecord productRecord = db().newRecord(REDUCE_PRICE_PRODUCT);
                        assign(goodsProduct, productRecord);
                        productRecord.setReducePriceId(reducePriceId);
                        productRecord.setGoodsId(goodsId);
                        productRecord.insert();
                    }
                } else {
                    List<GoodsProductVo> allProductListByGoodsId = goodsService.getAllProductListByGoodsId(goodsId);
                    GoodsProductVo defaultPrd = allProductListByGoodsId.get(0);
                    ReducePriceProductRecord productRecord = db().newRecord(REDUCE_PRICE_PRODUCT);
                    productRecord.setReducePriceId(reducePriceId);
                    productRecord.setGoodsId(goodsId);
                    productRecord.setPrdId(defaultPrd.getPrdId());
                    productRecord.setPrdPrice(goods.getGoodsPrice());
                    productRecord.insert();
                }
            }
        });
    }

    /**
     * 限时降价活动列表分页查询
     */
    public PageResult<ReducePricePageListQueryVo> getPageList(ReducePricePageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(REDUCE_PRICE.ID, REDUCE_PRICE.FIRST, REDUCE_PRICE.NAME, REDUCE_PRICE.START_TIME, REDUCE_PRICE.END_TIME, REDUCE_PRICE.STATUS).
            from(REDUCE_PRICE);
        if (param.getState() > 0) {
            /** 状态过滤*/
            Timestamp now = DateUtil.getLocalDateTime();
            switch (param.getState()) {
                case (byte) 1:
                    select.where(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(REDUCE_PRICE.START_TIME.lt(now)).and(REDUCE_PRICE.END_TIME.gt(now));
                    break;
                case (byte) 2:
                    select.where(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(REDUCE_PRICE.START_TIME.gt(now));
                    break;
                case (byte) 3:
                    select.where(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(REDUCE_PRICE.END_TIME.lt(now));
                    break;
                case (byte) 4:
                    select.where(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_DISABLE));
                    break;
                default:
            }
        }
        select.where(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(REDUCE_PRICE.CREATE_TIME.desc());
        PageResult<ReducePricePageListQueryVo> res = getPageResult(select, param.getCurrentPage(), param.getPageRows(), ReducePricePageListQueryVo.class);

        /**查询活动商品数量、订单付款数、付款用户数、付款总金额 */
        for (ReducePricePageListQueryVo vo : res.dataList) {
            vo.setGoodsAmount(getReducePriceActGoodsAmount(vo.getId()));
            vo.setOrderAmount(getReducePriceActOrderAmount(vo.getId()));
            vo.setUserAmount(getReducePriceActUserAmount(vo.getId()));
            vo.setPaymentTotalAmount(getReducePricePaymentTotalAmount(vo.getId()));
            vo.setCurrentState(Util.getActStatus(vo.getStatus(), vo.getStartTime(), vo.getEndTime()));
        }

        return res;
    }

    /**
     * 取单个限时降价活动信息
     */
    public ReducePriceVo getReducePriceById(Integer id) {
        ReducePriceRecord record = db().select(REDUCE_PRICE.ID, REDUCE_PRICE.NAME, REDUCE_PRICE.START_TIME, REDUCE_PRICE.END_TIME,
            REDUCE_PRICE.LIMIT_AMOUNT, REDUCE_PRICE.PERIOD_ACTION, REDUCE_PRICE.POINT_TIME, REDUCE_PRICE.EXTEND_TIME, REDUCE_PRICE.LIMIT_FLAG, REDUCE_PRICE.FIRST, REDUCE_PRICE.SHARE_CONFIG).
            from(REDUCE_PRICE).where(REDUCE_PRICE.ID.eq(id)).fetchOne().into(ReducePriceRecord.class);
        ReducePriceVo res = record.into(ReducePriceVo.class);
        res.setShopShareConfig(Util.parseJson(record.getShareConfig(), PictorialShareConfigVo.class));
        if (res.getShopShareConfig() != null && StringUtil.isNotEmpty(res.getShopShareConfig().getShareImg())) {
            res.getShopShareConfig().setShareImgFullUrl(domainConfig.imageUrl(res.getShopShareConfig().getShareImg()));
        }
        res.setReducePriceGoods(getReducePriceGoodsVoList(id));

        return res;
    }

    /**
     * 更新限时降价活动
     */
    public void updateReducePrice(ReducePriceUpdateParam param) {
        ReducePriceRecord record = new ReducePriceRecord();
        assign(param, record);
        db().executeUpdate(record);
    }

    /**
     * 删除限时降价活动
     */
    public void delReducePrice(Integer id) {
        db().update(REDUCE_PRICE).
            set(REDUCE_PRICE.DEL_FLAG, DelFlag.DISABLE.getCode()).
            set(REDUCE_PRICE.DEL_TIME, DateUtil.getLocalDateTime()).
            where(REDUCE_PRICE.ID.eq(id)).
            execute();
    }

    /**
     * 限时降价订单
     */
    public PageResult<MarketOrderListVo> getReducePriceOrderList(MarketOrderListParam param) {
        return saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param, BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE);
    }

    private int getReducePriceActGoodsAmount(int id) {
        return db().selectCount().from(REDUCE_PRICE_GOODS).where(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID.eq(id)).fetchOne().into(Integer.class);
    }

    private int getReducePriceActOrderAmount(int id) {
        return db().select(countDistinct(ORDER_GOODS.ORDER_SN)).from(ORDER_GOODS).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE)).fetchOne().into(Integer.class);
    }

    private int getReducePriceActUserAmount(int id) {
        return db().select(countDistinct(ORDER_INFO.USER_ID)).from(ORDER_GOODS).leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN)).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE)).fetchOne().into(Integer.class);
    }

    private BigDecimal getReducePricePaymentTotalAmount(int id) {
        BigDecimal res = db().select(sum(ORDER_GOODS.DISCOUNTED_GOODS_PRICE)).from(ORDER_GOODS).leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN)).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE)).and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_WAIT_DELIVERY)).fetchOne().into(BigDecimal.class);
        return res == null ? BigDecimal.ZERO : res;
    }

    private List<ReducePriceGoodsVo> getReducePriceGoodsVoList(int id) {
        List<ReducePriceGoodsVo> res = db().select(REDUCE_PRICE_GOODS.ID, REDUCE_PRICE_GOODS.GOODS_ID, REDUCE_PRICE_GOODS.DISCOUNT, REDUCE_PRICE_GOODS.REDUCE_PRICE, REDUCE_PRICE_GOODS.GOODS_PRICE).from(REDUCE_PRICE_GOODS).where(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID.eq(id)).fetchInto(ReducePriceGoodsVo.class);
        if (!res.isEmpty()) {
            for (ReducePriceGoodsVo reducePriceGoods : res) {
                reducePriceGoods.setGoodsView(saas().getShopApp(getShopId()).goods.getGoodsView(reducePriceGoods.getGoodsId()));
                List<ReducePriceProductVo> reducePriceProduct = db().select(REDUCE_PRICE_PRODUCT.ID, REDUCE_PRICE_PRODUCT.PRD_ID, REDUCE_PRICE_PRODUCT.PRD_PRICE, GOODS_SPEC_PRODUCT.PRD_DESC, GOODS_SPEC_PRODUCT.PRD_PRICE.as("originalPrice")).from(REDUCE_PRICE_PRODUCT).innerJoin(GOODS_SPEC_PRODUCT).on(REDUCE_PRICE_PRODUCT.PRD_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).where(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.eq(id)).and(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(reducePriceGoods.getGoodsId())).fetchInto(ReducePriceProductVo.class);
                reducePriceGoods.setReducePriceProduct(reducePriceProduct);
            }
        }
        return res;
    }

    public Map<Integer, BigDecimal> getShowPriceByGoodsIds(List<Integer> goodsIds, Timestamp date) {
        Map<Integer, Result<Record5<Integer, Byte, String, String, Integer>>> recordMap = db().select(
            REDUCE_PRICE.ID,
            REDUCE_PRICE.PERIOD_ACTION,
            REDUCE_PRICE.EXTEND_TIME,
            REDUCE_PRICE.POINT_TIME,
            REDUCE_PRICE_GOODS.GOODS_ID)
            .from(REDUCE_PRICE_GOODS)
            .leftJoin(REDUCE_PRICE).on(REDUCE_PRICE.ID.eq(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID))
            .where(REDUCE_PRICE_GOODS.GOODS_ID.in(goodsIds))
            .and(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(REDUCE_PRICE.START_TIME.lessThan(date))
            .and(REDUCE_PRICE.END_TIME.greaterThan(date))
            .orderBy(REDUCE_PRICE.CREATE_TIME.asc())
            .fetch()
            .intoGroups(REDUCE_PRICE_GOODS.GOODS_ID);
        Map<Integer, Integer> goodsIdAndReducePriceIdMap = recordMap.entrySet()
            .stream()
            .filter(x -> isActivityGoingOn(x.getValue().get(0)))
            .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().get(0).get(REDUCE_PRICE.ID)));
        Condition condition = DSL.trueCondition();
        goodsIdAndReducePriceIdMap.forEach(
            (key, value) ->
                condition.or(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(key).and(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.eq(value)))
        );
        Map<Integer, BigDecimal> resultPriceMap = new HashMap<>(goodsIdAndReducePriceIdMap.size());
        Map<Integer, Result<Record2<Integer, BigDecimal>>> beatPrice = db()
            .select(REDUCE_PRICE_PRODUCT.GOODS_ID, REDUCE_PRICE_PRODUCT.PRD_PRICE)
            .from(REDUCE_PRICE_PRODUCT)
            .where(condition)
            .fetchGroups(REDUCE_PRICE_PRODUCT.GOODS_ID);
        beatPrice.forEach((k, v) -> {
            if (v.size() > 1) {
                resultPriceMap.put(k, v.sortAsc(REDUCE_PRICE_PRODUCT.PRD_PRICE).get(0).get(REDUCE_PRICE_PRODUCT.PRD_PRICE));
            } else {
                resultPriceMap.put(k, v.get(0).get(REDUCE_PRICE_PRODUCT.PRD_PRICE));
            }
        });
        return resultPriceMap;

    }

    /**
     * 根据商品ID和当前时间获取限时降价的商品价格
     *
     * @param goodsId 商品ID
     * @param date    当前时间
     * @return 在活动有效期内返回价格否则返回null
     */
    public BigDecimal getShowPriceByGoodsId(Integer goodsId, Timestamp date) {
        Integer reducePriceId = db().select(REDUCE_PRICE.ID)
            .from(REDUCE_PRICE_GOODS)
            .leftJoin(REDUCE_PRICE).on(REDUCE_PRICE.ID.eq(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID))
            .where(REDUCE_PRICE_GOODS.GOODS_ID.eq(goodsId))
            .and(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(REDUCE_PRICE.START_TIME.lessThan(date))
            .and(REDUCE_PRICE.END_TIME.greaterThan(date))
            .orderBy(REDUCE_PRICE.CREATE_TIME.asc())
            .fetchOne(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID);
        if (reducePriceId == null) {
            return null;
        }
        Optional<ReducePriceRecord> reducePriceRecordOptional =
            db().selectFrom(REDUCE_PRICE).where(REDUCE_PRICE.ID.eq(reducePriceId)).fetchOptional();
        if (!reducePriceRecordOptional.isPresent()) {
            return null;
        }
        ReducePriceRecord reducePriceRecord = reducePriceRecordOptional.get();

        Optional<ReducePriceProductRecord> priceProductRecordOptional =
            getReducePriceProductRecordByGoodsId(reducePriceId, goodsId);
        if (!priceProductRecordOptional.isPresent()) {
            return null;
        }
        ReducePriceProductRecord reducePriceProductRecord = priceProductRecordOptional.get();
        BigDecimal price = reducePriceProductRecord.getPrdPrice();
        if (!isActivityGoingOn(reducePriceRecord)) {
            return null;
        } else {
            return price;
        }
    }


    /**
     * 计算当前正在进行的活动的预计结束时间
     *
     * @param endLocalTime 周期性活动指定的每次活动结束时刻
     * @param periodAction 活动的时间类型 null 表示活动仅指定了开始结束日期，未指定周期日
     * @param endDate      活动设定的失效时间
     * @return 结束的时间
     */
    public Timestamp getCurrentActivityEndDate(LocalTime endLocalTime, Byte periodAction, Timestamp endDate) {
        LocalDate nowDate = LocalDate.now();
        Timestamp currentActivityEndDate = null;
        if (PERIOD_ACTION_NORMAL.equals(periodAction)) {
            currentActivityEndDate = endDate;
        } else {
            currentActivityEndDate = DateUtil.convertToTimestamp(nowDate, endLocalTime);
        }
        return currentActivityEndDate;
    }

    /**
     * 获取活动下一次的开始时间
     * ps 调用此方法的活动都处于有效（当前时间早于活动结束时间）未进行阶段
     *
     * @param extendTime     指定的日
     * @param startLocalTime 活动的在指定日的开始时刻
     * @param periodAction   活动的时间类型
     * @param startDate      活动设定的开始时间
     * @param endDate        活动设定的失效时间
     * @return 下次活动的开始时间
     */
    public Timestamp getNextActivityStartDate(List<Integer> extendTime, LocalTime startLocalTime, LocalTime endLocalTime, Byte periodAction, Timestamp startDate, Timestamp endDate) {
        Timestamp nextStartTimestamp = null;

        Timestamp nowTimestamp = DateUtil.getLocalDateTime();
        LocalDate comparedDate = LocalDate.now();
        LocalTime comparedTime = LocalTime.now();

        // 活动未开始
        if (nowTimestamp.compareTo(startDate) < 0) {
            comparedDate = startDate.toLocalDateTime().toLocalDate();
            comparedTime = startDate.toLocalDateTime().toLocalTime();
        }

        // 按照每天指定时间段内开始活动
        if (PERIOD_ACTION_EVERY_DAY.equals(periodAction)) {

            // 当前时刻如果小于活动开始时刻则表明今天的活动尚未开启，否则活动已经结束需等待第二天开始
            int offsetDay = comparedTime.isBefore(startLocalTime) ? 0 : 1;
            comparedDate = comparedDate.plusDays(offsetDay);
            nextStartTimestamp = DateUtil.convertToTimestamp(comparedDate, startLocalTime);

        } else if (PERIOD_ACTION_EVERY_MONTH.equals(periodAction)) {

            //当前日大于目标日或日相同但是当前时刻大于等于目标时刻则本月活动已结束
            if (comparedDate.getDayOfMonth() > extendTime.get(0) || (comparedDate.getDayOfMonth() == extendTime.get(0) && comparedTime.compareTo(endLocalTime) >= 0)) {
                comparedDate = comparedDate.plusMonths(1);
            }
            LocalDate nextDate = LocalDate.of(comparedDate.getYear(), comparedDate.getMonth(), extendTime.get(0));
            nextStartTimestamp = DateUtil.convertToTimestamp(nextDate, startLocalTime);

        } else if (PERIOD_ACTION_EVERY_WEEK.equals(periodAction)) {
            // 当前时间是一周的第几天
            int nowDayOfWeek = comparedDate.getDayOfWeek().getValue();

            // 判断是否是今天有活动但是还没到要执行的时间
            if (extendTime.contains(nowDayOfWeek) && comparedTime.isBefore(startLocalTime)) {
                nextStartTimestamp = DateUtil.convertToTimestamp(comparedDate, startLocalTime);
            } else {
                // 非当天的最近一次活动是一周内的第几天
                Optional<Integer> optional = extendTime.stream().filter(x -> x > nowDayOfWeek).findFirst();
                int nextActivityDayOfWeek = optional.orElseGet(() -> extendTime.get(0));
                // (nextActivityDayOfWeek -nowDayOfWeek+7)%7 计算下次活动时间到今天的相差的天数
                int offsetDays = (nextActivityDayOfWeek + 7 - nowDayOfWeek) % 7;

                comparedDate = comparedDate.plusDays(offsetDays);
                nextStartTimestamp = DateUtil.convertToTimestamp(comparedDate, startLocalTime);
            }
        } else {
            // 仅指定了开始日期和结束日期的时候nextStartDate即为startDate
            nextStartTimestamp = startDate;
        }
        // 已经超过最后截止日期
        if (nextStartTimestamp.compareTo(endDate) > 0) {
            nextStartTimestamp = null;
        }
        return nextStartTimestamp;
    }


    /**
     * 判断当前活动是否还有效
     *
     * @param reducePriceRecord 限时减价活动
     * @return 有效：true
     */
    private boolean isActivityGoingOn(ReducePriceRecord reducePriceRecord) {
        return isActivityGoingOn(reducePriceRecord.getPointTime(), reducePriceRecord.getExtendTime(), reducePriceRecord.getPeriodAction());
    }

    private boolean isActivityGoingOn(Record5<Integer, Byte, String, String, Integer> reducePriceRecord) {
        return isActivityGoingOn(reducePriceRecord.get(REDUCE_PRICE.POINT_TIME)
            , reducePriceRecord.get(REDUCE_PRICE.EXTEND_TIME)
            , reducePriceRecord.get(REDUCE_PRICE.PERIOD_ACTION));
    }

    /**
     * 判断处于开始和结束时间之间的活动是否处于正在进行中
     *
     * @param pointTimeStr 指定的开始结束时间点
     * @param extendTime   指定的日期
     * @param periodAction 周期类型
     * @return true正在进行中，false 未进行中
     */
    public boolean isActivityGoingOn(String pointTimeStr, String extendTime, Byte periodAction) {
        LocalTime startLocalTime;
        LocalTime endLocalTime;
        LocalTime nowLocalTime = LocalTime.now();
        List<Integer> dayArray = new ArrayList<>(7);
        if (StringUtils.isNotBlank(pointTimeStr)) {
            String[] pointTime = pointTimeStr.split("@");
            String[] startArray = pointTime[0].split(":");
            String[] endArray = pointTime[1].split(":");
            startLocalTime = LocalTime.of(Integer.parseInt(startArray[0]), Integer.parseInt(startArray[1]));
            endLocalTime = LocalTime.of(Integer.parseInt(endArray[0]), Integer.parseInt(endArray[1]));
            if (nowLocalTime.isAfter(endLocalTime) || nowLocalTime.isBefore(startLocalTime)) {
                return false;
            }
        }
        if (StringUtils.isNotBlank(extendTime)) {
            dayArray = Arrays.stream(extendTime.split("@"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        }
        if (PERIOD_ACTION_EVERY_DAY.equals(periodAction) || PERIOD_ACTION_NORMAL.equals(periodAction)) {
            return true;
        } else if (PERIOD_ACTION_EVERY_WEEK.equals(periodAction)) {
            Integer dayOfWeek = DateUtil.getLocalDate().getDayOfWeek().getValue();
            return dayArray.contains(dayOfWeek);
        } else if (PERIOD_ACTION_EVERY_MONTH.equals(periodAction)) {
            Integer dayOfMonth = DateUtil.getLocalDate().getDayOfMonth();
            return dayArray.contains(dayOfMonth);
        }
        return false;
    }

    private Optional<ReducePriceProductRecord> getReducePriceProductRecordByGoodsId(Integer reducePriceId, Integer goodsId) {
        return db().selectFrom(REDUCE_PRICE_PRODUCT)
            .where(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.eq(reducePriceId))
            .and(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(goodsId))
            .orderBy(REDUCE_PRICE_PRODUCT.PRD_PRICE.asc())
            .fetchOptional();
    }

    private Optional<ReducePriceProductRecord> getReducePriceProductRecordByGoodsIds(List<Integer> reducePriceId, Integer goodsId) {
        return db().selectFrom(REDUCE_PRICE_PRODUCT)
            .where(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.in(reducePriceId))
            .and(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(goodsId))
            .fetchOptional();
    }

    private <T> Collector<T, ?, List<T>> toSortedList(Comparator<? super T> c) {
        return Collectors.collectingAndThen(
            Collectors.toCollection(ArrayList::new), l -> {
                l.sort(c);
                return l;
            });
    }

}
