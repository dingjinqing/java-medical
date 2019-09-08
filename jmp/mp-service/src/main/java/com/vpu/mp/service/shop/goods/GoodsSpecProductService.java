package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT_BAK;

/**
 * @author 李晓冰
 * @date 2019年07月05日
 */
@Service

public class GoodsSpecProductService extends ShopBaseService {

    @Autowired
    private GoodsSpecService goodsSpecService;
    /**
     * 规格名值描述分割符
     */
   public static final String PRD_DESC_DELIMITER = ";";
    /**
     * 规格名值id分隔符
     */
    public static final String PRD_SPEC_DELIMITER = "!!";
    /**
     * 规格名和值分隔符
     */
    public static final String PRD_VAL_DELIMITER = ":";

    /**
     * 规格名值处理后map内id值的key名称
     */
    public static final String PRD_SPEC_ID_KEY = GoodsSpecService.PRD_SPEC_ID_KEY;


    /**
     * 插入商品sku之前预处理器prdSpecs字段，目前用于用户填写了自己的sku
     *
     * @param goodsSpecProducts 规格属性
     * @param goodsSpecs    规格名值
     * @param goodsId   商品id
     */
    public void insert(List<GoodsSpecProduct> goodsSpecProducts, List<GoodsSpec> goodsSpecs, Integer goodsId) {

        if (goodsSpecs == null || goodsSpecs.size() == 0) {
            GoodsSpecProduct goodsSpecProduct=goodsSpecProducts.get(0);
            goodsSpecProduct.setGoodsId(goodsId);
            insert(goodsSpecProduct);
        } else {
            // 先插入规格名和规格值信息
            Map<String, Map<String, Integer>> goodsSpecsMap = goodsSpecService
                .insertSpecAndSpecValWithPrepareResult(goodsSpecs, goodsId);

            for (GoodsSpecProduct goodsSpecProduct : goodsSpecProducts) {
                goodsSpecProduct.setGoodsId(goodsId);
                String prdDescs = goodsSpecProduct.getPrdDesc();

                StringBuilder sb = new StringBuilder();
                //specDesc参数格式为：颜色:绿色;尺寸:X
                for (String prdDesc : prdDescs.split(PRD_DESC_DELIMITER)) {
                    String[] s = prdDesc.split(PRD_VAL_DELIMITER);


                    String spec = s[0], specVal = s[1];
                    if (sb.length() != 0) {
                        sb.append(PRD_SPEC_DELIMITER);
                    }

                    Map<String, Integer> nameIdMap = goodsSpecsMap.get(spec);
                    sb.append(nameIdMap.get(PRD_SPEC_ID_KEY)).append(PRD_VAL_DELIMITER).append(nameIdMap.get(specVal));
                }
                //格式：1:2!!1:3
                goodsSpecProduct.setPrdSpecs(sb.toString());

                insert(goodsSpecProduct);
            }
        }

    }

    /**
     * 插入商品sku数据
     *
     * @param goodsSpecProduct 商品规格属性
     */
    private void insert(GoodsSpecProduct goodsSpecProduct) {
        GoodsSpecProductRecord goodsSpecProductRecord = db().newRecord(GOODS_SPEC_PRODUCT, goodsSpecProduct);
        goodsSpecProductRecord.insert();
        goodsSpecProduct.setPrdId(goodsSpecProductRecord.getPrdId());
    }

    /**
     * 根据商品ids删除规格值
     * @param db
     * @param goodsIds
     */
    public void deleteByGoodsIds(DSLContext db, List<Integer> goodsIds) {

        //将sku表内数据备份至sku_bak内
        db.insertInto(GOODS_SPEC_PRODUCT_BAK, GOODS_SPEC_PRODUCT_BAK.PRD_ID, GOODS_SPEC_PRODUCT_BAK.SHOP_ID, GOODS_SPEC_PRODUCT_BAK.GOODS_ID
                , GOODS_SPEC_PRODUCT_BAK.PRD_PRICE, GOODS_SPEC_PRODUCT_BAK.PRD_MARKET_PRICE, GOODS_SPEC_PRODUCT_BAK.PRD_COST_PRICE, GOODS_SPEC_PRODUCT_BAK.PRD_NUMBER
                , GOODS_SPEC_PRODUCT_BAK.PRD_SN, GOODS_SPEC_PRODUCT_BAK.PRD_CODES, GOODS_SPEC_PRODUCT_BAK.PRD_SPECS, GOODS_SPEC_PRODUCT_BAK.PRD_DESC
                , GOODS_SPEC_PRODUCT_BAK.DEL_FLAG, GOODS_SPEC_PRODUCT_BAK.SELF_FLAG, GOODS_SPEC_PRODUCT_BAK.LOW_SHOP_PRICE, GOODS_SPEC_PRODUCT_BAK.PRD_IMG
                , GOODS_SPEC_PRODUCT_BAK.PRICE_FLAG, GOODS_SPEC_PRODUCT_BAK.CREATE_TIME, GOODS_SPEC_PRODUCT_BAK.UPDATE_TIME)
                .select(db.select(GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.SHOP_ID, GOODS_SPEC_PRODUCT.GOODS_ID
                        , GOODS_SPEC_PRODUCT.PRD_PRICE, GOODS_SPEC_PRODUCT.PRD_MARKET_PRICE, GOODS_SPEC_PRODUCT.PRD_COST_PRICE, GOODS_SPEC_PRODUCT.PRD_NUMBER
                        , GOODS_SPEC_PRODUCT.PRD_SN, GOODS_SPEC_PRODUCT.PRD_CODES, GOODS_SPEC_PRODUCT.PRD_SPECS, GOODS_SPEC_PRODUCT.PRD_DESC
                        , GOODS_SPEC_PRODUCT.DEL_FLAG, GOODS_SPEC_PRODUCT.SELF_FLAG, GOODS_SPEC_PRODUCT.LOW_SHOP_PRICE, GOODS_SPEC_PRODUCT.PRD_IMG
                        , GOODS_SPEC_PRODUCT.PRICE_FLAG, GOODS_SPEC_PRODUCT.CREATE_TIME, GOODS_SPEC_PRODUCT.UPDATE_TIME).from(GOODS_SPEC_PRODUCT)
                        .where(GOODS_SPEC_PRODUCT.GOODS_ID.in(goodsIds)))
                .execute();

        //真删除sku内数据
        db.deleteFrom(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.GOODS_ID.in(goodsIds)).execute();

        //假删除规格名和值内数据
        goodsSpecService.deleteByGoodsIds(db, goodsIds);
    }

    /**
     * 删除属于商品id但是不在prdIds集合内的所有项
     * @param prdIds 规格项id
     * @param goodsId 商品id
     */
    private void deleteByPrdIdsAndGoodsId(List<Integer> prdIds,List<GoodsSpec> goodsSpecs,Integer goodsId) {
        DSLContext db=db();
        //将sku表内数据备份至sku_bak内
        db.insertInto(GOODS_SPEC_PRODUCT_BAK, GOODS_SPEC_PRODUCT_BAK.PRD_ID, GOODS_SPEC_PRODUCT_BAK.SHOP_ID, GOODS_SPEC_PRODUCT_BAK.GOODS_ID
            , GOODS_SPEC_PRODUCT_BAK.PRD_PRICE, GOODS_SPEC_PRODUCT_BAK.PRD_MARKET_PRICE, GOODS_SPEC_PRODUCT_BAK.PRD_COST_PRICE, GOODS_SPEC_PRODUCT_BAK.PRD_NUMBER
            , GOODS_SPEC_PRODUCT_BAK.PRD_SN, GOODS_SPEC_PRODUCT_BAK.PRD_CODES, GOODS_SPEC_PRODUCT_BAK.PRD_SPECS, GOODS_SPEC_PRODUCT_BAK.PRD_DESC
            , GOODS_SPEC_PRODUCT_BAK.DEL_FLAG, GOODS_SPEC_PRODUCT_BAK.SELF_FLAG, GOODS_SPEC_PRODUCT_BAK.LOW_SHOP_PRICE, GOODS_SPEC_PRODUCT_BAK.PRD_IMG
            , GOODS_SPEC_PRODUCT_BAK.PRICE_FLAG, GOODS_SPEC_PRODUCT_BAK.CREATE_TIME, GOODS_SPEC_PRODUCT_BAK.UPDATE_TIME)
            .select(db.select(GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.SHOP_ID, GOODS_SPEC_PRODUCT.GOODS_ID
                , GOODS_SPEC_PRODUCT.PRD_PRICE, GOODS_SPEC_PRODUCT.PRD_MARKET_PRICE, GOODS_SPEC_PRODUCT.PRD_COST_PRICE, GOODS_SPEC_PRODUCT.PRD_NUMBER
                , GOODS_SPEC_PRODUCT.PRD_SN, GOODS_SPEC_PRODUCT.PRD_CODES, GOODS_SPEC_PRODUCT.PRD_SPECS, GOODS_SPEC_PRODUCT.PRD_DESC
                , GOODS_SPEC_PRODUCT.DEL_FLAG, GOODS_SPEC_PRODUCT.SELF_FLAG, GOODS_SPEC_PRODUCT.LOW_SHOP_PRICE, GOODS_SPEC_PRODUCT.PRD_IMG
                , GOODS_SPEC_PRODUCT.PRICE_FLAG, GOODS_SPEC_PRODUCT.CREATE_TIME, GOODS_SPEC_PRODUCT.UPDATE_TIME).from(GOODS_SPEC_PRODUCT)
                .where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(goodsId)).and(GOODS_SPEC_PRODUCT.PRD_ID.notIn(prdIds)))
            .execute();

        //真删除sku内数据
        db.deleteFrom(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(goodsId))
            .and(GOODS_SPEC_PRODUCT.PRD_ID.notIn(prdIds)).execute();

        //假删除规格名和值内数据
        goodsSpecService.deleteByPrdIdsGoodsId(goodsSpecs,goodsId);
    }
    /**
     * 根据商品id查找对应sku
     * @param goodsId
     * @return
     */
    public List<GoodsSpecProduct> selectByGoodsId(Integer goodsId) {
        List<GoodsSpecProduct> goodsSpecProducts = db().selectFrom(GOODS_SPEC_PRODUCT)
                .where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(goodsId)).fetch().into(GoodsSpecProduct.class);

        return goodsSpecProducts;
    }

    public List<GoodsSpec> selectSpecByGoodsId(Integer goodsId) {
        List<GoodsSpec> goodsSpecs = goodsSpecService.selectByGoodsId(db(), goodsId);
        return goodsSpecs;
    }

    /**
     * 根据prdId修改商品规格信息并删除无用的（数据库中存在但是出入的对象集合中不存在）
     * @param goodsSpecProducts 规格对象集合
     */
    public void updateAndDelete(List<GoodsSpecProduct> goodsSpecProducts,List<GoodsSpec> goodsSpecs,Integer goodsId) {
        DSLContext db=db();

        List<Integer> goodsSpecProductIds=goodsSpecProducts.stream().map(item->item.getPrdId()).collect(Collectors.toList());

        // 删除无效sku，规格名规格值
        deleteByPrdIdsAndGoodsId(goodsSpecProductIds,goodsSpecs,goodsId);

        List<GoodsSpecProductRecord> listRecords=new ArrayList<>(goodsSpecProducts.size());
       goodsSpecProducts.forEach(item->{
           GoodsSpecProductRecord record=db.newRecord(GOODS_SPEC_PRODUCT);
           assign(item,record);
           listRecords.add(record);
       });
       db.batchUpdate(listRecords).execute();
       goodsSpecService.update(goodsSpecs);
    }
    /**
     * 	根据规格id查询规格明细
     * @return
     */
    public Map<Integer, GoodsSpecProductRecord> selectSpecByProIds(List<Integer> proIds) {
    	return db().selectFrom(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.in(proIds)).fetchMap(GOODS_SPEC_PRODUCT.PRD_ID);
    }

    /**
     * 根据id
     * @param goodsId 商品id
     * @return 规则 record
     */
    public Result<Record> getAllProductListByGoodsId(Integer goodsId) {
        Result<Record> recordResult = db().select()
                .from(GOODS_SPEC_PRODUCT)
                .where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(goodsId))
                .fetch();
        return recordResult;
    }
}
