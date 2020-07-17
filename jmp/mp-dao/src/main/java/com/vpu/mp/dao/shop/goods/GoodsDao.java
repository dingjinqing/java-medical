package com.vpu.mp.dao.shop.goods;

import cn.hutool.core.util.StrUtil;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsSortItem;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsPageListCondition;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.pojo.shop.medical.goods.MedicalGoodsConstant;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS;

/**
 * 商品dao
 * @author 李晓冰
 * @date 2020年07月01日
 */
@Repository
public class GoodsDao extends ShopBaseDao {
    /**
     * 商品新增
     * @param goodsDo 商品数据
     * @return 商品id
     */
    public void insert(GoodsDo goodsDo) {
        GoodsRecord goodsRecord = db().newRecord(GOODS);
        FieldsUtil.assign(goodsDo, goodsRecord);
        // 临时添加
        goodsRecord.setShareConfig("{\"shareAction\":1,\"shareDoc\":null,\"shareImgAction\":1,\"shareImgUrl\":null,\"shareImgPath\":null}");
        goodsRecord.insert();
        goodsDo.setGoodsId(goodsRecord.getGoodsId());
    }

    public void update(GoodsDo goodsDo) {
        GoodsRecord goodsRecord = new GoodsRecord();
        FieldsUtil.assign(goodsDo, goodsRecord);
        // 临时添加
        goodsRecord.setShareConfig("{\"shareAction\":1,\"shareDoc\":null,\"shareImgAction\":1,\"shareImgUrl\":null,\"shareImgPath\":null}");
        db().executeUpdate(goodsRecord);
    }

    /**
     * 批量插入商品信息
     * @param goodsDos
     */
    public void batchInsert(List<GoodsDo> goodsDos) {
        List<String> goodsSns =new ArrayList<>(goodsDos.size());
        List<GoodsRecord> goodsRecords =new ArrayList<>(goodsDos.size());

        for (GoodsDo goodsDo : goodsDos) {
            goodsSns.add(goodsDo.getGoodsSn());
            GoodsRecord goodsRecord = new GoodsRecord();
            FieldsUtil.assign(goodsDo,goodsRecord);
            // 临时添加
            goodsRecord.setShareConfig("{\"shareAction\":1,\"shareDoc\":null,\"shareImgAction\":1,\"shareImgUrl\":null,\"shareImgPath\":null}");
            goodsRecords.add(goodsRecord);
        }
        db().batchInsert(goodsRecords).execute();

        Map<String, Integer> goodsSnIdMap = db().select(GOODS.GOODS_ID, GOODS.GOODS_SN).from(GOODS).where(GOODS.GOODS_SN.in(goodsSns)).fetchMap(GOODS.GOODS_SN, GOODS.GOODS_ID);

        for (GoodsDo goodsDo : goodsDos) {
            goodsDo.setGoodsId(goodsSnIdMap.get(goodsDo.getGoodsSn()));
        }
    }

    /**
     * 批量更新
     * @param goodsDos
     */
    public void batchUpdate(List<GoodsDo> goodsDos){
        List<GoodsRecord> goodsRecords =new ArrayList<>(goodsDos.size());
        for (GoodsDo goodsDo : goodsDos){
            GoodsRecord goodsRecord = new GoodsRecord();
            FieldsUtil.assign(goodsDo,goodsRecord);
            goodsRecords.add(goodsRecord);
        }
        db().batchUpdate(goodsRecords).execute();
    }


    public void deleteByGoodsId(Integer goodsId) {
        db().update(GOODS)
            .set(GOODS.GOODS_SN, DSL.concat(DelFlag.DEL_ITEM_PREFIX, GOODS.GOODS_SN).concat(GOODS.GOODS_ID))
            .set(GOODS.GOODS_NAME, DSL.concat(DelFlag.DEL_ITEM_PREFIX, GOODS.GOODS_NAME).concat(GOODS.GOODS_ID))
            .set(GOODS.DEL_FLAG, DelFlag.DISABLE_VALUE)
            .where(GOODS.GOODS_ID.eq(goodsId))
            .execute();
    }

    /**
     * 查询商品详情
     * @param goodsId 商品id
     * @return null表示不存在
     */
    public GoodsDo getByGoodsId(Integer goodsId) {
        GoodsDo goodsDo = db().selectFrom(GOODS).where(GOODS.GOODS_ID.eq(goodsId).and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)))
            .fetchAnyInto(GoodsDo.class);

        return goodsDo;
    }

    /**
     * 商品列表分页查询（以商品为维度）
     * @param goodsPageListCondition 分页查询条件
     * @param curPage 当前页 1开始
     * @param pageRows 展示数量
     * @return 分页结果
     */
    public PageResult<GoodsDo> getGoodsPageList(GoodsPageListCondition goodsPageListCondition, Integer curPage, Integer pageRows) {
        Condition condition = buildGoodsPageListCondition(goodsPageListCondition);
        List<SortField<?>> sortFields = buildGoodsPageOrderField(goodsPageListCondition.getPageSortItems());
        SelectSeekStepN<GoodsRecord> selectStep = db().selectFrom(GOODS).where(condition).orderBy(sortFields);
        PageResult<GoodsDo> pageResult = getPageResult(selectStep, curPage, pageRows, GoodsDo.class);
        return pageResult;
    }

    /**
     * 商品分页拼接过滤条件
     * @param goodsPageListCondition 过滤条件
     * @return 过滤条件condition
     */
    private Condition buildGoodsPageListCondition(GoodsPageListCondition goodsPageListCondition) {
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE);

        if (StrUtil.isNotBlank(goodsPageListCondition.getGoodsName())) {
            condition = condition.and(GOODS.GOODS_NAME.like(likeValue(goodsPageListCondition.getGoodsName())));
        }

        if (StrUtil.isNotBlank(goodsPageListCondition.getGoodsSn())) {
            condition = condition.and(GOODS.GOODS_SN.like(likeValue(goodsPageListCondition.getGoodsSn())));
        }

        if (goodsPageListCondition.getBrandIds() != null) {
            condition = condition.and(GOODS.BRAND_ID.in(goodsPageListCondition.getBrandIds()));
        }

        if (goodsPageListCondition.getSortIds() != null) {
            condition = condition.and(GOODS.SORT_ID.in(goodsPageListCondition.getSortIds()));
        }

        if (goodsPageListCondition.getLowShopPrice() != null) {
            condition = condition.and(GOODS.SHOP_PRICE.ge(goodsPageListCondition.getLowShopPrice()));
        }

        if (goodsPageListCondition.getHighShopPrice() != null) {
            condition = condition.and(GOODS.SHOP_PRICE.le(goodsPageListCondition.getHighShopPrice()));
        }

        if (goodsPageListCondition.getIsOnSale() != null) {
            condition = condition.and(GOODS.IS_ON_SALE.eq(goodsPageListCondition.getIsOnSale()));
        }

        if (goodsPageListCondition.getIsSaleOut() != null) {
            if (MedicalGoodsConstant.SALE_OUT_YES.equals(goodsPageListCondition.getIsSaleOut())) {
                condition = condition.and(GOODS.GOODS_NUMBER.eq(0));
            } else {
                condition = condition.and(GOODS.GOODS_NUMBER.gt(0));
            }
        }

        if (goodsPageListCondition.getIsMedical() != null) {
            condition = condition.and(GOODS.IS_MEDICAL.eq(goodsPageListCondition.getIsMedical()));
        }

        if (goodsPageListCondition.getGoodsIdsLimit() != null) {
            condition = condition.and(GOODS.GOODS_ID.in(goodsPageListCondition.getGoodsIdsLimit()));
        }

        return condition;
    }

    /**
     * 商品分页排序条件生成
     * @param goodsSortItems
     */
    private List<SortField<?>> buildGoodsPageOrderField(List<GoodsSortItem> goodsSortItems) {
        List<SortField<?>> sortFields = new ArrayList<>(0);

        if (goodsSortItems == null || goodsSortItems.size() == 0) {
            sortFields.add(GOODS.CREATE_TIME.desc());
        } else {
            Field<?> sortField;
            for (GoodsSortItem goodsSortItem : goodsSortItems) {

                switch (goodsSortItem.getColumnName()) {
                    case "shopPrice":
                        sortField = GOODS.SHOP_PRICE;
                        break;
                    case "goodsNumber":
                        sortField = GOODS.GOODS_NUMBER;
                        break;
                    case "goodsSaleNum":
                        sortField = GOODS.GOODS_SALE_NUM;
                        break;
                    default:
                        sortField = null;
                }

                if (sortField != null) {
                    if (goodsSortItem.isAsc()) {
                        sortFields.add(sortField.asc());
                    } else {
                        sortFields.add(sortField.desc());
                    }
                }
            }
        }

        return sortFields;
    }

    /**
     * 判断goodsSn是否存在
     * @param goodsSn
     * @return true 是 false 否
     */
    public boolean isGoodsSnExist(String goodsSn) {
        int count = db().fetchCount(GOODS, GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GOODS.GOODS_SN.eq(goodsSn)));
        return count > 0;
    }

    /**
     * 统计商品数量，包含已删除的
     * @return 商品数量
     */
    public int countAllGoods() {
        return db().fetchCount(GOODS);
    }

    /**
     * 查询所有已有goodsSn
     * @param goodsSn
     * @param isMedical
     * @return
     */
   public Map<String,Integer> listExistGoodsSn(List<String> goodsSn,Byte isMedical){
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GOODS.GOODS_SN.in(goodsSn));
       List<GoodsDo> goodsDos = db().select(GOODS.GOODS_ID, GOODS.GOODS_SN).from(GOODS).where(condition).fetchInto(GoodsDo.class);

       return goodsDos.stream().collect(Collectors.toMap(GoodsDo::getGoodsSn, GoodsDo::getGoodsId, (x1, x2) -> x1));
   }
}
