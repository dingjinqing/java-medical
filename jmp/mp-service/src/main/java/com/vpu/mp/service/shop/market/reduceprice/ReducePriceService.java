package com.vpu.mp.service.shop.market.reduceprice;

import static com.vpu.mp.db.shop.tables.ReducePrice.REDUCE_PRICE;
import static com.vpu.mp.db.shop.tables.ReducePriceGoods.REDUCE_PRICE_GOODS;
import static com.vpu.mp.db.shop.tables.ReducePriceProduct.REDUCE_PRICE_PRODUCT;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;

import com.vpu.mp.db.shop.tables.records.ReducePriceGoodsRecord;
import com.vpu.mp.db.shop.tables.records.ReducePriceProductRecord;
import com.vpu.mp.db.shop.tables.records.ReducePriceRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.reduceprice.*;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.shop.goods.es.EsGoodsConstant;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;

import static org.jooq.impl.DSL.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 王兵兵
 * @create: 2019-08-14 10:02
 **/
@Service
public class ReducePriceService extends ShopBaseService {
    /**
     * 启用状态
     */
    public static final byte STATUS_NORMAL = 1;
    /**
     * 停用状态
     */
    public static final byte STATUS_DISABLED = 0;

    /**
     * 新建限时降价活动
     *
     */
    public void addReducePrice(ReducePriceAddParam param) {
        this.transaction(()->{
            ReducePriceRecord record = db().newRecord(REDUCE_PRICE);
            assign(param,record);
            if(param.getShareConfig() != null) {
                record.setShareConfig(Util.toJson(param.getShareConfig()));
            }
            record.insert();
            Integer reducePriceId = record.getId();
            for(ReducePriceGoodsAddParam goods : param.getReducePriceGoodsAddParams()){
                ReducePriceGoodsRecord goodsRecord = db().newRecord(REDUCE_PRICE_GOODS);
                assign(goods,goodsRecord);
                goodsRecord.setReducePriceId(reducePriceId);
                goodsRecord.insert();
                if(goods.getReducePriceProduct() != null && goods.getReducePriceProduct().length > 0){
                    Integer goodsId = goodsRecord.getGoodsId();
                    for(ReducePriceGoodsProductAddParam goodsProduct : goods.getReducePriceProduct()){
                        ReducePriceProductRecord productRecord = db().newRecord(REDUCE_PRICE_PRODUCT);
                        assign(goodsProduct,productRecord);
                        productRecord.setReducePriceId(reducePriceId);
                        productRecord.setGoodsId(goodsId);
                        productRecord.insert();
                    }
                }
            }
        });
    }

    /**
     * 限时降价活动列表分页查询
     *
     */
    public PageResult<ReducePricePageListQueryVo> getPageList(ReducePricePageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(REDUCE_PRICE.ID,REDUCE_PRICE.NAME,REDUCE_PRICE.START_TIME,REDUCE_PRICE.END_TIME,REDUCE_PRICE.STATUS).
            from(REDUCE_PRICE);
        if(param.getState() > 0) {
            /** 状态过滤*/
            Timestamp now = DateUtil.getLocalDateTime();
            switch(param.getState()) {
                case (byte)1:
                    select.where(REDUCE_PRICE.STATUS.eq(STATUS_NORMAL)).and(REDUCE_PRICE.START_TIME.lt(now)).and(REDUCE_PRICE.END_TIME.gt(now));
                    break;
                case (byte)2:
                    select.where(REDUCE_PRICE.STATUS.eq(STATUS_NORMAL)).and(REDUCE_PRICE.START_TIME.gt(now));
                    break;
                case (byte)3:
                    select.where(REDUCE_PRICE.STATUS.eq(STATUS_NORMAL)).and(REDUCE_PRICE.END_TIME.lt(now));
                    break;
                case (byte)4:
                    select.where(REDUCE_PRICE.STATUS.eq(STATUS_DISABLED));
                    break;
                default:
            }
        }
        select.where(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(REDUCE_PRICE.CREATE_TIME.desc());
        PageResult<ReducePricePageListQueryVo> res = getPageResult(select,param.getCurrentPage(),param.getPageRows(),ReducePricePageListQueryVo.class);

        /**查询活动商品数量、订单付款数、付款用户数、付款总金额 */
        for(ReducePricePageListQueryVo vo : res.dataList){
            vo.setGoodsAmount(getReducePriceActGoodsAmount(vo.getId()));
            vo.setOrderAmount(getReducePriceActOrderAmount(vo.getId()));
            vo.setUserAmount(getReducePriceActUserAmount(vo.getId()));
            vo.setPaymentTotalAmount(getReducePricePaymentTotalAmount(vo.getId()));
        }

        return res;
    }

    /**
     * 取单个限时降价活动信息
     *
     */
    public ReducePriceVo getReducePriceById(Integer id){
        ReducePriceRecord record = db().select(REDUCE_PRICE.ID,REDUCE_PRICE.NAME,REDUCE_PRICE.START_TIME,REDUCE_PRICE.END_TIME,
            REDUCE_PRICE.LIMIT_AMOUNT,REDUCE_PRICE.PERIOD_ACTION,REDUCE_PRICE.POINT_TIME,REDUCE_PRICE.EXTEND_TIME,REDUCE_PRICE.LIMIT_FLAG,REDUCE_PRICE.SHARE_CONFIG).
            from(REDUCE_PRICE).where(REDUCE_PRICE.ID.eq(id)).fetchOne().into(ReducePriceRecord.class);
        ReducePriceVo res = record.into(ReducePriceVo.class);
        res.setShopShareConfig(Util.parseJson(record.getShareConfig(), ShopShareConfig.class));
        res.setReducePriceGoods(getReducePriceGoodsVoList(id));

        return res;
    }

    /**
     * 更新限时降价活动
     *
     */
    public void updateReducePrice(ReducePriceUpdateParam param) {
        ReducePriceRecord record = new ReducePriceRecord();
        assign(param,record);
        db().executeUpdate(record);
    }

    /**
     * 删除限时降价活动
     *
     */
    public void delReducePrice(Integer id) {
        db().update(REDUCE_PRICE).
            set(REDUCE_PRICE.DEL_FLAG,DelFlag.DISABLE.getCode()).
            set(REDUCE_PRICE.DEL_TIME,DateUtil.getLocalDateTime()).
            where(REDUCE_PRICE.ID.eq(id)).
            execute();
    }

    /**
     * 限时降价订单
     *
     */
    public PageResult<MarketOrderListVo> getReducePriceOrderList(MarketOrderListParam param) {
        return saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param,OrderConstant.GOODS_TYPE_REDUCE_PRICE);
    }

    private int getReducePriceActGoodsAmount(int id){
        return db().selectCount().from(REDUCE_PRICE_GOODS).where(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID.eq(id)).fetchOne().into(Integer.class);
    }

    private int getReducePriceActOrderAmount(int id){
        return db().select(countDistinct(ORDER_GOODS.ORDER_SN)).from(ORDER_GOODS).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(OrderConstant.GOODS_TYPE_REDUCE_PRICE)).fetchOne().into(Integer.class);
    }

    private int getReducePriceActUserAmount(int id){
        return db().select(countDistinct(ORDER_INFO.USER_ID)).from(ORDER_GOODS).leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN)).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(OrderConstant.GOODS_TYPE_REDUCE_PRICE)).fetchOne().into(Integer.class);
    }

    private BigDecimal getReducePricePaymentTotalAmount(int id){
        BigDecimal res =  db().select(sum(ORDER_GOODS.DISCOUNTED_GOODS_PRICE)).from(ORDER_GOODS).leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN)).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(OrderConstant.GOODS_TYPE_REDUCE_PRICE)).and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_WAIT_DELIVERY)).fetchOne().into(BigDecimal.class);
        return res == null ? BigDecimal.ZERO : res;
    }

    private List<ReducePriceGoodsVo> getReducePriceGoodsVoList(int id){
        List<ReducePriceGoodsVo> res = db().select(REDUCE_PRICE_GOODS.ID,REDUCE_PRICE_GOODS.GOODS_ID,REDUCE_PRICE_GOODS.DISCOUNT,REDUCE_PRICE_GOODS.REDUCE_PRICE,REDUCE_PRICE_GOODS.GOODS_PRICE).from(REDUCE_PRICE_GOODS).where(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID.eq(id)).fetchInto(ReducePriceGoodsVo.class);
        if(!res.isEmpty()){
            for(ReducePriceGoodsVo reducePriceGoods : res){
                reducePriceGoods.setGoodsView(saas().getShopApp(getShopId()).goods.getGoodsView(reducePriceGoods.getGoodsId()));
                List<ReducePriceProductVo> reducePriceProduct = db().select(REDUCE_PRICE_PRODUCT.ID,REDUCE_PRICE_PRODUCT.PRODUCT_ID,REDUCE_PRICE_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_DESC,GOODS_SPEC_PRODUCT.PRD_PRICE.as("originalPrice")).from(REDUCE_PRICE_PRODUCT).innerJoin(GOODS_SPEC_PRODUCT).on(REDUCE_PRICE_PRODUCT.PRODUCT_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).where(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.eq(id)).and(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(reducePriceGoods.getGoodsId())).fetchInto(ReducePriceProductVo.class);
                reducePriceGoods.setReducePriceProduct(reducePriceProduct);
            }
        }
        return res;
    }

    /**
     * 根据商品ID和当前时间获取限时降价的商品价格
     * @param goodsId 商品ID
     * @param date 当前时间
     * @return 在活动有效期内返回价格否则返回null
     */
    public BigDecimal getShowPriceByGoodsId(Integer goodsId,Timestamp date){
        Integer reducePriceId = db().select(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID)
            .from(REDUCE_PRICE_GOODS)
            .leftJoin(REDUCE_PRICE).on(REDUCE_PRICE.ID.eq(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID))
            .where(REDUCE_PRICE_GOODS.GOODS_ID.eq(goodsId))
            .and(REDUCE_PRICE.STATUS.eq(STATUS_NORMAL))
            .and(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(REDUCE_PRICE.START_TIME.lessThan(date))
            .and(REDUCE_PRICE.END_TIME.greaterThan(date))
            .orderBy(REDUCE_PRICE.CREATE_TIME.asc())
            .fetchOne(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID);
        if( reducePriceId == null ){
            return null;
        }
        Optional<ReducePriceRecord> reducePriceRecordOptional =
            db().selectFrom(REDUCE_PRICE).where(REDUCE_PRICE.ID.eq(reducePriceId)).fetchOptional();
        if( !reducePriceRecordOptional.isPresent() ){
            return null;
        }
        ReducePriceRecord reducePriceRecord = reducePriceRecordOptional.get();

        Optional<ReducePriceProductRecord> priceProductRecordOptional =
            getReducePriceProductRecordByGoodsId(reducePriceId,goodsId);
        if( !priceProductRecordOptional.isPresent() ){
            return null;
        }
        ReducePriceProductRecord reducePriceProductRecord = priceProductRecordOptional.get();
        BigDecimal price = reducePriceProductRecord.getPrdPrice();
        if (!isReturnPrice(reducePriceRecord)) {
            return null;
        } else {
            return price;
        }
    }

    /**
     * 判断当前活动是否还有效
     * @param reducePriceRecord 限时减价活动
     * @return 有效：true
     */
    private boolean isReturnPrice(ReducePriceRecord reducePriceRecord ){
        LocalTime startLocalTime;
        LocalTime endLocalTime;
        LocalTime nowLocalTime = LocalTime.now();
        List<Integer> dayArray = new ArrayList<>(7);
        if(StringUtils.isNotBlank(reducePriceRecord.getPointTime())){
            String[] pointTime = reducePriceRecord.getPointTime().split("@");
            String[] startArray = pointTime[0].split(":");
            String[] endArray = pointTime[1].split(":");
            startLocalTime = LocalTime.of(Integer.parseInt(startArray[0]),Integer.parseInt(startArray[1]));
            endLocalTime = LocalTime.of(Integer.parseInt(endArray[0]),Integer.parseInt(endArray[1]));
            if( nowLocalTime.isAfter(endLocalTime) || nowLocalTime.isBefore(startLocalTime) ){
                return false;
            }
        }
        if( StringUtils.isNotBlank(reducePriceRecord.getExtendTime()) ){
            dayArray = Arrays.stream(reducePriceRecord.getExtendTime().split("@"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        }
        if ( EsGoodsConstant.EsGoodsShowPriceReducePeriodAction.EVERY_DAY.equals(reducePriceRecord.getPeriodAction()) ){
            return true;
        }else if ( EsGoodsConstant.EsGoodsShowPriceReducePeriodAction.EVERY_WEEK.equals(reducePriceRecord.getPeriodAction()) ){
            Integer dayOfWeek = DateUtil.getLocalDate().getDayOfWeek().getValue();
            return dayArray.contains(dayOfWeek);
        }else if ( EsGoodsConstant.EsGoodsShowPriceReducePeriodAction.EVERY_MONTH.equals(reducePriceRecord.getPeriodAction()) ){
            Integer dayOfMonth = DateUtil.getLocalDate().getDayOfMonth();
            return dayArray.contains(dayOfMonth);
        }
        return false;
    }

    private Optional<ReducePriceProductRecord> getReducePriceProductRecordByGoodsId(Integer reducePriceId,Integer goodsId){
        return db().selectFrom(REDUCE_PRICE_PRODUCT)
            .where(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.eq(reducePriceId))
            .and(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(goodsId))
            .fetchOptional();
    }

}
