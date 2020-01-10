package com.vpu.mp.service.shop.market.firstspecial;

import com.vpu.mp.db.shop.tables.records.FirstSpecialGoodsRecord;
import com.vpu.mp.db.shop.tables.records.FirstSpecialProductRecord;
import com.vpu.mp.db.shop.tables.records.FirstSpecialRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.firstspecial.*;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.tables.FirstSpecial.FIRST_SPECIAL;
import static com.vpu.mp.db.shop.tables.FirstSpecialGoods.FIRST_SPECIAL_GOODS;
import static com.vpu.mp.db.shop.tables.FirstSpecialProduct.FIRST_SPECIAL_PRODUCT;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.OrderGoods.ORDER_GOODS;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static org.jooq.impl.DSL.countDistinct;
import static org.jooq.impl.DSL.sum;

/**
 * @author: 王兵兵
 * @create: 2019-08-16 10:02
 **/
@Service
public class FirstSpecialService extends ShopBaseService {
    /**
     * 启用状态
     */
    public static final byte STATUS_NORMAL = 1;
    /**
     * 停用状态
     */
    public static final byte STATUS_DISABLED = 0;
    /**
     *  永久有效
     */
    public static final byte FOREVER_YES =1;
    /**
     *  非永久有效
     */
    public static final byte FOREVER_NO =0;

    /**
     * 开启超购限制
     */
    public static final Byte LIMIT_FLAG_YES = 1;

    /**
     * 新建首单特惠活动
     *
     */
    public void addFirstSpecial(FirstSpecialAddParam param) {
        this.transaction(()->{
            FirstSpecialRecord record = db().newRecord(FIRST_SPECIAL);
            assign(param,record);
            if(param.getShareConfig() != null) {
                record.setShareConfig(Util.toJson(param.getShareConfig()));
            }
            record.insert();
            Integer firstSpecialId = record.getId();
            for(FirstSpecialGoodsParam goods : param.getFirstSpecialGoodsParams()){
                FirstSpecialGoodsRecord goodsRecord = db().newRecord(FIRST_SPECIAL_GOODS);
                assign(goods,goodsRecord);
                goodsRecord.setFirstSpecialId(firstSpecialId);
                goodsRecord.insert();
                if(goods.getGoodsProductParams() != null && goods.getGoodsProductParams().length > 0){
                    Integer goodsId = goodsRecord.getGoodsId();
                    for(FirstSpecialGoodsProductParam goodsProduct : goods.getGoodsProductParams()){
                        FirstSpecialProductRecord productRecord = db().newRecord(FIRST_SPECIAL_PRODUCT);
                        assign(goodsProduct,productRecord);
                        productRecord.setFirstSpecialId(firstSpecialId);
                        productRecord.setGoodsId(goodsId);
                        productRecord.insert();
                    }
                }
            }
        });
    }

    /**
     * 首单特惠活动列表分页查询
     *
     */
    public PageResult<FirstSpecialPageListQueryVo> getPageList(FirstSpecialPageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(FIRST_SPECIAL.ID,FIRST_SPECIAL.NAME,FIRST_SPECIAL.FIRST,FIRST_SPECIAL.IS_FOREVER,FIRST_SPECIAL.START_TIME,FIRST_SPECIAL.END_TIME,FIRST_SPECIAL.STATUS).
            from(FIRST_SPECIAL);
        if(param.getState() > 0) {
            /** 状态过滤*/
            Timestamp now = DateUtil.getLocalDateTime();
            switch(param.getState()) {
                case (byte)1:
                    select.where(FIRST_SPECIAL.STATUS.eq(STATUS_NORMAL)).and(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_YES).or(FIRST_SPECIAL.START_TIME.lt(now).and(FIRST_SPECIAL.END_TIME.gt(now))));
                    break;
                case (byte)2:
                    select.where(FIRST_SPECIAL.STATUS.eq(STATUS_NORMAL)).and(FIRST_SPECIAL.START_TIME.gt(now));
                    break;
                case (byte)3:
                    select.where(FIRST_SPECIAL.STATUS.eq(STATUS_NORMAL)).and(FIRST_SPECIAL.END_TIME.lt(now));
                    break;
                case (byte)4:
                    select.where(FIRST_SPECIAL.STATUS.eq(STATUS_DISABLED));
                    break;
                default:
            }
        }
        select.where(FIRST_SPECIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(FIRST_SPECIAL.CREATE_TIME.desc());
        PageResult<FirstSpecialPageListQueryVo> res = getPageResult(select,param.getCurrentPage(),param.getPageRows(),FirstSpecialPageListQueryVo.class);

        /**查询活动商品数量、订单付款数、付款用户数、付款总金额 */
        for(FirstSpecialPageListQueryVo vo : res.dataList){
            vo.setGoodsAmount(getFirstSpecialActGoodsAmount(vo.getId()));
            vo.setOrderAmount(getFirstSpecialActOrderAmount(vo.getId()));
            vo.setUserAmount(getFirstSpecialActUserAmount(vo.getId()));
            vo.setPaymentTotalAmount(getFirstSpecialPaymentTotalAmount(vo.getId()));
            vo.setCurrentState(Util.getActStatus(vo.getStatus(),vo.getStartTime(),vo.getEndTime(),vo.getIsForever()));
        }

        return res;
    }

    /**
     * 取单个首单特惠活动信息
     *
     */
    public FirstSpecialVo getFirstSpecialById(Integer id){
        FirstSpecialRecord record = db().select(FIRST_SPECIAL.ID,FIRST_SPECIAL.NAME,FIRST_SPECIAL.FIRST,FIRST_SPECIAL.IS_FOREVER,FIRST_SPECIAL.START_TIME,FIRST_SPECIAL.END_TIME,
            FIRST_SPECIAL.LIMIT_AMOUNT,FIRST_SPECIAL.LIMIT_FLAG,FIRST_SPECIAL.SHARE_CONFIG).
            from(FIRST_SPECIAL).where(FIRST_SPECIAL.ID.eq(id)).fetchOne().into(FirstSpecialRecord.class);
        FirstSpecialVo res = record.into(FirstSpecialVo.class);
        res.setShopShareConfig(Util.parseJson(record.getShareConfig(), ShopShareConfig.class));
        res.setFirstSpecialGoods(getFirstSpecialGoodsVoList(id));

        return res;
    }

    /**
     * 更新首单特惠活动
     *
     */
    public void updateFirstSpecial(FirstSpecialUpdateParam param) {
        this.transaction(()->{
            FirstSpecialRecord record = db().newRecord(FIRST_SPECIAL);
            assign(param,record);
            if(param.getShareConfig() != null) {
                record.setShareConfig(Util.toJson(param.getShareConfig()));
            }
            db().executeUpdate(record);
            if(param.getFirstSpecialGoodsParams() != null && param.getFirstSpecialGoodsParams().length > 0){
                for(FirstSpecialGoodsParam goods : param.getFirstSpecialGoodsParams()){
                    FirstSpecialGoodsRecord goodsRecord = db().newRecord(FIRST_SPECIAL_GOODS);
                    assign(goods,goodsRecord);
                    db().executeUpdate(goodsRecord);
                    if(goods.getGoodsProductParams() != null && goods.getGoodsProductParams().length > 0){
                        for(FirstSpecialGoodsProductParam goodsProduct : goods.getGoodsProductParams()){
                            FirstSpecialProductRecord productRecord = db().newRecord(FIRST_SPECIAL_PRODUCT);
                            assign(goodsProduct,productRecord);
                            db().executeUpdate(productRecord);
                        }
                    }
                }
            }

        });
    }

    /**
     * 删除首单特惠活动
     *
     */
    public void delFirstSpecial(Integer id) {
        db().update(FIRST_SPECIAL).
            set(FIRST_SPECIAL.DEL_FLAG,DelFlag.DISABLE.getCode()).
            set(FIRST_SPECIAL.DEL_TIME,DateUtil.getLocalDateTime()).
            where(FIRST_SPECIAL.ID.eq(id)).
            execute();
    }

    /**
     * 首单特惠订单
     *
     */
    public PageResult<MarketOrderListVo> getFirstSpecialOrderList(MarketOrderListParam param) {
        return saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param, BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL);
    }

    /**
     *  获取正在活动的首单特惠商品规格
     * @return
     */
    public List<Integer> getOnGoingPrdIds(){
        Timestamp nowDate =new Timestamp(System.currentTimeMillis());
        return db().select(FIRST_SPECIAL_PRODUCT.PRD_ID).from(FIRST_SPECIAL_PRODUCT).leftJoin(FIRST_SPECIAL).on(FIRST_SPECIAL.ID.ge(FIRST_SPECIAL_PRODUCT.FIRST_SPECIAL_ID))
                .where(FIRST_SPECIAL.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).and(FIRST_SPECIAL.STATUS.eq(STATUS_NORMAL))
                .and(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_YES)
                        .or(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_NO).and(FIRST_SPECIAL.START_TIME.lt(nowDate)).and(FIRST_SPECIAL.END_TIME.gt(nowDate))))
                .groupBy(FIRST_SPECIAL_PRODUCT.PRD_ID).fetch().getValues(FIRST_SPECIAL_PRODUCT.PRD_ID);

    }

    private int getFirstSpecialActGoodsAmount(int id){
        return db().selectCount().from(FIRST_SPECIAL_GOODS).where(FIRST_SPECIAL_GOODS.FIRST_SPECIAL_ID.eq(id)).fetchOne().into(Integer.class);
    }

    private int getFirstSpecialActOrderAmount(int id){
        return db().select(countDistinct(ORDER_GOODS.ORDER_SN)).from(ORDER_GOODS).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL)).fetchOne().into(Integer.class);
    }

    private int getFirstSpecialActUserAmount(int id){
        return db().select(countDistinct(ORDER_INFO.USER_ID)).from(ORDER_GOODS).leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN)).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL)).fetchOne().into(Integer.class);
    }

    private BigDecimal getFirstSpecialPaymentTotalAmount(int id){
        BigDecimal res =  db().select(sum(ORDER_GOODS.DISCOUNTED_GOODS_PRICE)).from(ORDER_GOODS).leftJoin(ORDER_INFO).on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN)).where(ORDER_GOODS.ACTIVITY_ID.eq(id)).and(ORDER_GOODS.ACTIVITY_TYPE.eq(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL)).and(ORDER_INFO.ORDER_STATUS.gt(OrderConstant.ORDER_WAIT_DELIVERY)).fetchOne().into(BigDecimal.class);
        return res == null ? BigDecimal.ZERO : res;
    }

    private List<FirstSpecialGoodsVo> getFirstSpecialGoodsVoList(int id){
        List<FirstSpecialGoodsVo> res = db().select(FIRST_SPECIAL_GOODS.ID,FIRST_SPECIAL_GOODS.GOODS_ID,FIRST_SPECIAL_GOODS.DISCOUNT,FIRST_SPECIAL_GOODS.REDUCE_PRICE,FIRST_SPECIAL_GOODS.GOODS_PRICE).from(FIRST_SPECIAL_GOODS).where(FIRST_SPECIAL_GOODS.FIRST_SPECIAL_ID.eq(id)).fetchInto(FirstSpecialGoodsVo.class);
        if(!res.isEmpty()){
            for(FirstSpecialGoodsVo firstSpecialGoods : res){
                firstSpecialGoods.setGoodsView(saas().getShopApp(getShopId()).goods.getGoodsView(firstSpecialGoods.getGoodsId()));
                List<FirstSpecialProductVo> firstSpecialProduct = db().select(FIRST_SPECIAL_PRODUCT.ID,FIRST_SPECIAL_PRODUCT.PRD_ID,FIRST_SPECIAL_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_DESC,GOODS_SPEC_PRODUCT.PRD_PRICE.as("originalPrice")).from(FIRST_SPECIAL_PRODUCT).innerJoin(GOODS_SPEC_PRODUCT).on(FIRST_SPECIAL_PRODUCT.PRD_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).where(FIRST_SPECIAL_PRODUCT.FIRST_SPECIAL_ID.eq(id)).and(FIRST_SPECIAL_PRODUCT.GOODS_ID.eq(firstSpecialGoods.getGoodsId())).fetchInto(FirstSpecialProductVo.class);
                firstSpecialGoods.setFirstSpecialProduct(firstSpecialProduct);
            }
        }
        return res;
    }

}
