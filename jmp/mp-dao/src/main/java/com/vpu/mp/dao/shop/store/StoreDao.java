package com.vpu.mp.dao.shop.store;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.StoreDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreBestSellersParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreBestSellersVo;
import com.vpu.mp.service.pojo.wxapp.store.showmain.StoreOrderStatisticVo;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectHavingStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.vpu.mp.db.shop.tables.GoodsMedicalInfo.GOODS_MEDICAL_INFO;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.service.pojo.shop.doctor.DoctorListParam.ASC;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConfigConstant.STORE_AUTO_PICK_ENABLE;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConfigConstant.STORE_BUSINESS_OPENING;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConfigConstant.STORE_BUSINESS_WORKDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;

/**
 * @author chenjie
 * @date 2020年08月24日
 */
@Repository
public class StoreDao extends ShopBaseDao {

    private static final String TOTAL_PRICE = "totalPrice";

    private static final String GOODS_NUMBER = "goodsNumber";

    public StoreBasicVo getStoreByNo(String storeNo) {
        return db().selectFrom(STORE).where(STORE.STORE_CODE.eq(storeNo)).and(STORE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchAnyInto(StoreBasicVo.class);
    }

    public List<StoreBasicVo> listStoreCodes() {
        Condition condition = STORE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE);
        return db().select(STORE.STORE_ID, STORE.STORE_CODE).from(STORE).where(condition).fetchInto(StoreBasicVo.class);
    }

    /**
     * 根据有货门店storeCode获取门店列表
     * @param stores 有货门店storeCode
     * @return List<StoreDo>
     */
    public List<StoreDo> getStoreOpen(List<String> stores, Integer deliveryType) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String dateStringParse = sdf.format(date);
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        SelectConditionStep<Record> select = db().select().from(STORE)
            .where(STORE.BUSINESS_STATE.eq(STORE_BUSINESS_OPENING));
        // 如果不是工作日，查询每天营业的门店
        if (weekday == SATURDAY || weekday == SUNDAY) {
            select.and(STORE.BUSINESS_TYPE.eq(STORE_BUSINESS_WORKDAY));
        }
        // 查询未打烊门店
        select.and(STORE.OPENING_TIME.lt(dateStringParse));
        select.and(STORE.CLOSE_TIME.gt(dateStringParse));
        if (stores != null && stores.size() != 0) {
            logger().info("门店库存校验{}",stores);
            select.and(STORE.STORE_CODE.in(stores));
        }
        if (deliveryType == OrderConstant.DELIVER_TYPE_SELF) {
            select.and(STORE.AUTO_PICK.eq(STORE_AUTO_PICK_ENABLE));
        }
        select.limit(15);
        return select.fetchInto(StoreDo.class);
    }

    /**
     * 查询门店热销商品
     * @param storeBestSellersParam 热销商品入参
     * @return PageResult<StoreBestSellersVo>
     */
    public PageResult<StoreBestSellersVo> getBestSellers(StoreBestSellersParam storeBestSellersParam) {
        SelectConditionStep<? extends Record> where = db().select(
            GOODS_MEDICAL_INFO.GOODS_COMMON_NAME,
            GOODS_MEDICAL_INFO.GOODS_QUALITY_RATIO,
            GOODS_MEDICAL_INFO.GOODS_PRODUCTION_ENTERPRISE,
            DSL.sum(ORDER_GOODS.COST_PRICE).as(TOTAL_PRICE),
            DSL.sum(ORDER_GOODS.GOODS_NUMBER).as(GOODS_NUMBER))
            .from(ORDER_INFO)
            .leftJoin(ORDER_GOODS)
            .on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
            .leftJoin(GOODS_MEDICAL_INFO)
            .on(ORDER_GOODS.GOODS_ID.eq(GOODS_MEDICAL_INFO.GOODS_ID))
            .where(ORDER_INFO.STORE_ID.eq(storeBestSellersParam.getStoreId()));
        buildOption(where, storeBestSellersParam);
        where.orderBy(ORDER_GOODS.CREATE_TIME.desc());
        return this.getPageResult(where, storeBestSellersParam.getCurrentPage(),
            storeBestSellersParam.getPageRows(), StoreBestSellersVo.class);
    }

    private void buildOption(SelectConditionStep<? extends Record> select, StoreBestSellersParam storeBestSellersParam) {
        if (storeBestSellersParam.getStartDate()!=null && storeBestSellersParam.getEndDate()!=null) {
            select.and(ORDER_GOODS.CREATE_TIME.lt(Timestamp.valueOf(storeBestSellersParam.getEndDate())));
            select.and(ORDER_GOODS.CREATE_TIME.gt(Timestamp.valueOf(storeBestSellersParam.getStartDate())));
        }
        select.groupBy(ORDER_GOODS.GOODS_SN
            , GOODS_MEDICAL_INFO.GOODS_COMMON_NAME
            , GOODS_MEDICAL_INFO.GOODS_QUALITY_RATIO
            , GOODS_MEDICAL_INFO.GOODS_PRODUCTION_ENTERPRISE);
        goodsFiledSorted(select, storeBestSellersParam);

    }

    /**
     * 对热销商品按指定字段进行排序
     * @param select 查询实体
     * @param param 排序参数
     */
    private void goodsFiledSorted(SelectHavingStep<? extends Record> select, StoreBestSellersParam param) {
        Field<BigDecimal> totalPrice = DSL.sum(ORDER_GOODS.COST_PRICE).as(TOTAL_PRICE);
        Field<BigDecimal> goodsNumber = DSL.sum(ORDER_GOODS.GOODS_NUMBER).as(GOODS_NUMBER);
        if (param.getOrderDirection() == null || param.getOrderField() == null) {
            return;
        }
        if (ASC.equals(param.getOrderDirection())) {
            switch (param.getOrderField()) {
                case TOTAL_PRICE:
                    select.orderBy(totalPrice.asc());
                    break;
                case GOODS_NUMBER:
                    select.orderBy(goodsNumber.asc());
                    break;
                default:
                    break;
            }
        } else {
            switch (param.getOrderField()) {
                case TOTAL_PRICE:
                    select.orderBy(totalPrice.desc());
                    break;
                case GOODS_NUMBER:
                    select.orderBy(goodsNumber.desc());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 根据storeIds查询list
     * @param storeIds
     * @return
     */
    public List<StoreOrderStatisticVo> getListByStoreIds(List<Integer> storeIds){
        return db().select().from(STORE).where(STORE.STORE_ID.in(storeIds))
            .and(STORE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .fetchInto(StoreOrderStatisticVo.class);
    }

}
