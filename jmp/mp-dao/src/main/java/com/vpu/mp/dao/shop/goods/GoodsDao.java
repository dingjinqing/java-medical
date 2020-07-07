package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

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
    public void insert(GoodsDo goodsDo){
        GoodsRecord goodsRecord = db().newRecord(GOODS);
        FieldsUtil.assign(goodsDo,goodsRecord);
        goodsRecord.insert();
        goodsDo.setGoodsId(goodsRecord.getGoodsId());
    }

    public void update(GoodsDo goodsDo) {
        GoodsRecord goodsRecord=new GoodsRecord();
        FieldsUtil.assign(goodsDo,goodsRecord);
        db().executeUpdate(goodsRecord);
    }

    public void deleteByGoodsId(Integer goodsId) {
        db().update(GOODS)
            .set(GOODS.GOODS_SN, DSL.concat(DelFlag.DEL_ITEM_PREFIX,GOODS.GOODS_SN))
            .set(GOODS.GOODS_NAME, DSL.concat(DelFlag.DEL_ITEM_PREFIX,GOODS.GOODS_NAME))
            .set(GOODS.DEL_FLAG,DelFlag.DISABLE_VALUE)
            .where(GOODS.GOODS_ID.eq(goodsId))
            .execute();
    }

    /**
     * 查询商品详情
     * @param goodsId 商品id
     * @return null表示不存在
     */
    public GoodsDo getByGoodsId(Integer goodsId){
        GoodsDo goodsDo = db().selectFrom(GOODS).where(GOODS.GOODS_ID.eq(goodsId).and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)))
            .fetchAnyInto(GoodsDo.class);

        return goodsDo;
    }

    

    /**
     * 判断goodsSn是否存在
     * @param goodsSn
     * @return true 是 false 否
     */
    public boolean isGoodsSnExist(String goodsSn){
        int count = db().fetchCount(GOODS, GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GOODS.GOODS_SN.eq(goodsSn)));
        return count>0;
    }

    /**
     * 统计商品数量，包含已删除的
     * @return 商品数量
     */
    public int countAllGoods(){
        return db().fetchCount(GOODS);
    }



}
