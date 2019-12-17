package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.GroupBuyListRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import org.jooq.Record3;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GROUP_BUY_DEFINE;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service()
public class GroupBuyProcessorDao extends GroupBuyService {

    /**
     * 获取集合内商品所参与的拼团信息
     * @param goodsIds 待查询商品id集合
     * @param date 限制时间
     * @return key:商品id value:List<Record3<Integer, Integer, BigDecimal>> GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE
     * @author 李晓冰
     */
    public Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> getGoodsGroupBuyListInfo(List<Integer> goodsIds, Timestamp date) {
        // 获取有效拼团规格信息
        return db().select(GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
            .from(GROUP_BUY_DEFINE).innerJoin(GROUP_BUY_PRODUCT_DEFINE).on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
            .where(GROUP_BUY_DEFINE.START_TIME.lt(date)).and(GROUP_BUY_DEFINE.END_TIME.gt(date)).and(GROUP_BUY_DEFINE.STOCK.gt((short) 0))
            .and(GROUP_BUY_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GROUP_BUY_DEFINE.GOODS_ID.in(goodsIds))
            .orderBy(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GROUP_BUY_DEFINE.GOODS_ID)));
    }

    /**
     * 保存
     * @param groupBuyProductList
     * @return
     */
    public int save(GroupBuyListRecord groupBuyProductList) {
       return db().executeInsert(groupBuyProductList);
    }

    /**
     *  修改拼团库存和销量
     * @param activityId
     * @param productId
     * @param goodsNumber 商品数量
     * @return
     */
    public boolean updateGroupBuyStock(Integer activityId, Integer productId, Integer goodsNumber) {
        //规格库存`
        int prdFlag = db().update(GROUP_BUY_PRODUCT_DEFINE)
                .set(GROUP_BUY_PRODUCT_DEFINE.STOCK, GROUP_BUY_PRODUCT_DEFINE.STOCK.minus(goodsNumber))
                .set(GROUP_BUY_PRODUCT_DEFINE.SALE_NUM, GROUP_BUY_PRODUCT_DEFINE.SALE_NUM.add(goodsNumber))
                .where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(activityId))
                .and(GROUP_BUY_PRODUCT_DEFINE.PRODUCT_ID.eq(productId))
                .and(GROUP_BUY_PRODUCT_DEFINE.STOCK.ge(goodsNumber.shortValue())).execute();
        if (prdFlag==1){
            //总库存
            int tolFlag = db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STOCK, GROUP_BUY_DEFINE.STOCK.minus(goodsNumber))
                    .set(GROUP_BUY_DEFINE.SALE_NUM, GROUP_BUY_DEFINE.SALE_NUM.add(goodsNumber))
                    .where(GROUP_BUY_DEFINE.ID.eq(activityId))
                    .and(GROUP_BUY_PRODUCT_DEFINE.STOCK.ge(goodsNumber.shortValue())).execute();
            if (tolFlag==1){
                return true;
            }else {
                db().update(GROUP_BUY_PRODUCT_DEFINE)
                        .set(GROUP_BUY_PRODUCT_DEFINE.STOCK, GROUP_BUY_PRODUCT_DEFINE.STOCK.add(goodsNumber))
                        .set(GROUP_BUY_PRODUCT_DEFINE.SALE_NUM, GROUP_BUY_PRODUCT_DEFINE.SALE_NUM.minus(goodsNumber))
                        .where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(activityId))
                        .and(GROUP_BUY_PRODUCT_DEFINE.PRODUCT_ID.eq(productId))
                        .and(GROUP_BUY_PRODUCT_DEFINE.STOCK.ge(goodsNumber.shortValue())).execute();
            }
        }
        return false;
    }
}
