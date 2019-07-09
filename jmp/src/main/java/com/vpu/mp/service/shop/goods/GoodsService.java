package com.vpu.mp.service.shop.goods;


import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.goods.Goods;
import com.vpu.mp.service.pojo.shop.goods.GoodsColumnCheckExistParam;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;

/**
 * 商品品牌
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
public class GoodsService extends BaseService {

    public GoodsBrandService goodsBrand;
    public GoodsSortService goodsSort;
    public GoodsCommentService goodsComment;
    public CommentConfigService commentConfig;
    public GoodsLabelService goodsLabel;
    public GoodsLabelCoupleService goodsLabelCouple;

    private GoodsSpecProductService goodsSpecProductService = new GoodsSpecProductService();
    private GoodsSpecService goodsSpecService = new GoodsSpecService();

    /**
     * 	先插入商品，从而得到商品的id，
     * 	然后插入商品规格的属性和规格值，从而得到规格属性和规格值的id,
     *	 最后拼凑出prdSpecs再插入具体的商品规格
     *
     * @param goods
     */
    public void insert(Goods goods) {
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            insert(db, goods);

            //用户使用默认的规格数据，则sku只有一条，对应的规格列表为空
            if (goods.getGoodsSpecs()==null||goods.getGoodsSpecs().size()==0){
                goodsSpecProductService.insert(db,goods.getGoodsSpecProducts().get(0), goods.getGoodsId());

            }else{//如果存在规格列表字段，则表明用户自己定义了具体的规格
                Map<String, Map<String, Integer>> goodsSpecMap = goodsSpecService
                        .insertSpecAndSpecValWithPrepareResult(db, goods.getGoodsSpecs(), goods.getGoodsId());

                goodsSpecProductService.insert(db, goods.getGoodsSpecProducts(), goodsSpecMap, goods.getGoodsId());
            }

        });
    }

    /**
     * 	插入数据并设置对应入参的id值
     *
     * @param db
     * @param goods
     */
    private void insert(DSLContext db, Goods goods) {

        if (goods.getGoodsSpecProducts() != null) {
            calculateGoodsPriceAndNumber(goods);
        }

        if (goods.getGoodsSn() == null) {
            goods.setGoodsSn(Util.UUID());
        }

        GoodsRecord goodsRecord = db.newRecord(GOODS, goods);
        goodsRecord.insert();
        goods.setGoodsId(goodsRecord.getGoodsId());
    }

    /**
     * 	预处理通过规格信息计算出商品的库存，最小商品价格信息,
     * 	并将结果注入到传入的引用对象。
     *
     * @param goods
     */
    private void calculateGoodsPriceAndNumber(Goods goods) {
        //当存在商品规格时，统计商品总数和最低商品价格
        if (goods.getGoodsSpecProducts().size() > 0) {
            BigDecimal smallestGoodsPrice = BigDecimal.valueOf(Double.MAX_VALUE);
            Integer goodsSumNumber = 0;
            for (GoodsSpecProduct specProduct : goods.getGoodsSpecProducts()) {
                goodsSumNumber += specProduct.getPrdNumber();
                if (smallestGoodsPrice.compareTo(specProduct.getPrdPrice()) > 0) {
                    smallestGoodsPrice = specProduct.getPrdPrice();
                }
            }
            goods.setGoodsNumber(goodsSumNumber);
            goods.setShopPrice(smallestGoodsPrice);
        }
    }


    public boolean isColumnValueExist(GoodsColumnCheckExistParam goodsColumnExistParam) {
        SelectSelectStep<Record1<Integer>> selectFrom = db().selectCount();

        SelectConditionStep<?> scs;

        switch (goodsColumnExistParam.getColumnCheckFor()) {
            case E_GOODS_SPEC_PRODUCTION:
                scs = buildGoodsSpecPrdColumnExistOption(selectFrom.from(GOODS_SPEC_PRODUCT), goodsColumnExistParam);
                break;
            default:
                scs = buildGoodsColumnExistOption(selectFrom.from(GOODS), goodsColumnExistParam);
        }

        Record record = scs.fetchOne();
        Integer count = record.getValue(0, Integer.class);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *	 商品名和商品码查重
     *
     * @param select
     * @param goodsColumnExistParam
     * @return
     */
    private SelectConditionStep<?> buildGoodsColumnExistOption(SelectJoinStep<?> select, GoodsColumnCheckExistParam goodsColumnExistParam) {
        SelectConditionStep<?> scs = select.where(GOODS.DEL_FLAG.eq((byte) 0));

        if (goodsColumnExistParam.getGoodsName() != null) {
            scs = scs.and(GOODS.GOODS_NAME.eq(goodsColumnExistParam.getGoodsName()));
        }

        if (goodsColumnExistParam.getGoodsSn() != null) {
            scs = scs.and(GOODS.GOODS_SN.eq(goodsColumnExistParam.getGoodsSn()));
        }

        if (goodsColumnExistParam.getGoodsId() != null) {//update 修改条目时排除自身
            scs = scs.and(GOODS.GOODS_ID.ne(goodsColumnExistParam.getGoodsId()));
        }
        return scs;
    }

    /**
     * 	商品规格字段重复检查
     *
     * @param select
     * @param goodsColumnExistParam
     * @return
     */
    private SelectConditionStep<?> buildGoodsSpecPrdColumnExistOption(SelectJoinStep<?> select, GoodsColumnCheckExistParam goodsColumnExistParam) {
        SelectConditionStep<?> scs = select.where(GOODS_SPEC_PRODUCT.DEL_FLAG.eq((byte) 0));

        if (goodsColumnExistParam.getPrdSn() != null) {
            scs = scs.and(GOODS_SPEC_PRODUCT.PRD_SN.eq(goodsColumnExistParam.getPrdSn()));
        }

        if (goodsColumnExistParam.getPrdId() != null) {//修改去重
            scs = scs.and(GOODS_SPEC_PRODUCT.PRD_ID.ne(goodsColumnExistParam.getPrdId()));
        }

        return scs;
    }


}

