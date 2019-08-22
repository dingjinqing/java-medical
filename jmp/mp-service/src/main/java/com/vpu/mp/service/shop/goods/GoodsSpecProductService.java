package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
     * 判断传入的数据是否修改
     *
     * @return
     */
    public boolean isChange(DSLContext db, List<GoodsSpecProduct> goodsSpecProducts, Integer goodsId) {
        List<String> result = db.select(GOODS_SPEC_PRODUCT.PRD_DESC).from(GOODS_SPEC_PRODUCT)
                .where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(goodsId))
                .fetch(0, String.class);

        Map<String, Object> map = new HashMap<>(result.size());

        for (String str : result) {
            map.put(str.trim(), null);
        }

        for (GoodsSpecProduct gsp : goodsSpecProducts) {
            map.remove(gsp.getPrdDesc() == null ? "" : gsp.getPrdDesc().trim());
        }

        if (result.size() == goodsSpecProducts.size() && map.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 插入商品sku之前预处理器prdSpecs字段，目前用于用户填写了自己的sku
     *
     * @param goodsSpecProducts 规格属性
     * @param goodsSpecs    规格名值
     * @param goodsId   商品id
     */
    public void insert(List<GoodsSpecProduct> goodsSpecProducts, List<GoodsSpec> goodsSpecs, Integer goodsId,BigDecimal marketPrice) {

        Map<String, Map<String, Integer>> goodsSpecsMap = goodsSpecService
                .insertSpecAndSpecValWithPrepareResult(goodsSpecs, goodsId);

        for (GoodsSpecProduct goodsSpecProduct : goodsSpecProducts) {
            goodsSpecProduct.setGoodsId(goodsId);
            goodsSpecProduct.setPrdMarketPrice(marketPrice);
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

    /**
     * 插入单条，目前用于商品使用默认的sku的时候（即不填写sku的时候）
     *
     * @param goodsSpecProduct 商品规格属性
     * @param goodsId 商品id
     */
    public void insert(GoodsSpecProduct goodsSpecProduct, Integer goodsId, BigDecimal marketPrice) {
        goodsSpecProduct.setGoodsId(goodsId);
        goodsSpecProduct.setPrdMarketPrice(marketPrice);
        insert(goodsSpecProduct);
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
     *
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
     * 根据商品id查找对应sku
     *
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

    public void updateSpec(DSLContext db, List<GoodsSpecProduct> goodsSpecProducts) {
        List<Integer> ids = goodsSpecProducts.stream().map(r -> r.getPrdId()).collect(Collectors.toList());

        List<GoodsSpecProductRecord> recordList = db.selectFrom(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.in(ids)).fetch().into(GoodsSpecProductRecord.class);

        Map<Integer, GoodsSpecProduct> goodsSpecMap = goodsSpecProducts.stream().collect(Collectors.toMap(r -> r.getPrdId(), r -> r));

        for (GoodsSpecProductRecord record : recordList) {
            GoodsSpecProduct goodsSpecProduct = goodsSpecMap.get(record.getPrdId());
            record.setPrdCostPrice(goodsSpecProduct.getPrdCostPrice());
            record.setPrdPrice(goodsSpecProduct.getPrdPrice());
            record.setPrdNumber(goodsSpecProduct.getPrdNumber());
        }
        db.batchUpdate(recordList).execute();
    }
}
