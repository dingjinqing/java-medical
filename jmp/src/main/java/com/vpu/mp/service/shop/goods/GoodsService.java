package com.vpu.mp.service.shop.goods;


import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.goods.Goods;
import com.vpu.mp.service.pojo.shop.goods.GoodsColumnCheckExistParam;
import com.vpu.mp.service.pojo.shop.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.GoodsPageListResp;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.main.Tables.CATEGORY;
import static com.vpu.mp.service.pojo.shop.goods.GoodsPageListParam.ASC;
import static com.vpu.mp.service.pojo.shop.goods.GoodsPageListParam.IS_ON_SALE_DEFAULT;

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
     *  分页查询
     * @param goodsPageListParam
     * @return
     */
    public PageResult<GoodsPageListResp> getPageList(GoodsPageListParam goodsPageListParam) {
        SelectOnConditionStep<?> selectFrom = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_SN,
                GOODS.SHOP_PRICE,GOODS.CAT_ID,
                SORT.SORT_NAME, GOODS_BRAND.BRAND_NAME, GOODS.GOODS_NUMBER, GOODS.GOODS_SALE_NUM)
                .from(GOODS).join(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID))
                .join(GOODS_BRAND).on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID));

        SelectConditionStep<?> select = this.buildOptions(selectFrom, goodsPageListParam);

        PageResult<GoodsPageListResp> pageResult = this.getPageResult(select, goodsPageListParam.getCurrentPage(), goodsPageListParam.getPageRows(), GoodsPageListResp.class);

        this.disposeCategoryName(pageResult.getDataList());

        return pageResult;
    }

    private void disposeCategoryName(List<GoodsPageListResp> goodsPageListResps){
        Map<Short, String> catIdNameMap = mainDb()
                .select(CATEGORY.CAT_ID, CATEGORY.CAT_NAME)
                .from(CATEGORY).fetch().intoMap(CATEGORY.CAT_ID, CATEGORY.CAT_NAME);
        System.out.println(catIdNameMap);
    }


    /**
     *  分页条件拼凑
     * @param select
     * @param goodsPageListParam
     * @return
     */
    private SelectConditionStep<?> buildOptions(SelectOnConditionStep<?> select, GoodsPageListParam goodsPageListParam) {
        SelectConditionStep<?> scs = select.where(GOODS.DEL_FLAG.eq((byte) 0));

        if (goodsPageListParam.getIsOnSale() == null) {
            goodsPageListParam.setIsOnSale(IS_ON_SALE_DEFAULT);
        }

        scs = scs.and(GOODS.IS_ON_SALE.eq(goodsPageListParam.getIsOnSale()));

        if (goodsPageListParam.getGoodsName() != null) {
            scs = scs.and(GOODS.GOODS_NAME.like(likeValue(goodsPageListParam.getGoodsName())));
        }

        if (goodsPageListParam.getCatId() != null) {
            scs = scs.and(GOODS.CAT_ID.eq(goodsPageListParam.getCatId()));
        }

        if (goodsPageListParam.getSortId() != null) {
            scs = scs.and(GOODS.SORT_ID.eq(goodsPageListParam.getSortId()));
        }

        if (goodsPageListParam.getBrandId() != null) {
            scs = scs.and(GOODS.BRAND_ID.eq(goodsPageListParam.getBrandId()));
        }

        if (goodsPageListParam.getGoodsType() != null) {
            scs = scs.and(GOODS.GOODS_TYPE.eq(goodsPageListParam.getGoodsType()));
        }

        if (goodsPageListParam.getLowShopPrice() != null) {
            scs = scs.and(GOODS.SHOP_PRICE.ge(goodsPageListParam.getLowShopPrice()));
        }

        if (goodsPageListParam.getHighShopPrice() != null) {
            scs = scs.and(GOODS.SHOP_PRICE.le(goodsPageListParam.getHighShopPrice()));
        }

        String orderField = goodsPageListParam.getOrderField();
        String orderDire = goodsPageListParam.getOrderDirection();

        if (orderField==null){
            scs.orderBy(GOODS.CREATE_TIME.desc());
            return scs;
        }

        if ("shopPrice".equals(orderField)) {
            if (ASC.equals(orderDire)) {
                scs.orderBy(GOODS.SHOP_PRICE.asc(), GOODS.CREATE_TIME.desc());
            }else{
                scs.orderBy(GOODS.SHOP_PRICE.desc(), GOODS.CREATE_TIME.desc());
            }
        }

        if ("goodsNumber".equals(orderField)) {
            if (ASC.equals(orderDire)) {
                scs.orderBy(GOODS.GOODS_NUMBER.asc(), GOODS.CREATE_TIME.desc());
            }else{
                scs.orderBy(GOODS.GOODS_NUMBER.desc(), GOODS.CREATE_TIME.desc());
            }
        }

        if ("goodsSaleNum".equals(orderField)) {
            if (ASC.equals(orderDire)) {
                scs.orderBy(GOODS.GOODS_SALE_NUM.asc(), GOODS.CREATE_TIME.desc());
            }else{
                scs.orderBy(GOODS.GOODS_SALE_NUM.desc(), GOODS.CREATE_TIME.desc());
            }
        }

        return scs;
    }

    /**
     * 先插入商品，从而得到商品的id，
     * 然后插入商品规格的属性和规格值，从而得到规格属性和规格值的id,
     * 最后拼凑出prdSpecs再插入具体的商品规格
     *
     * @param goods
     */
    public void insert(Goods goods) {
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            insert(db, goods);

            //用户使用默认的规格数据，则sku只有一条，对应的规格列表为空
            if (goods.getGoodsSpecs() == null || goods.getGoodsSpecs().size() == 0) {
                goodsSpecProductService.insert(db, goods.getGoodsSpecProducts().get(0), goods.getGoodsId());

            } else {//如果存在规格列表字段，则表明用户自己定义了具体的规格
                Map<String, Map<String, Integer>> goodsSpecMap = goodsSpecService
                        .insertSpecAndSpecValWithPrepareResult(db, goods.getGoodsSpecs(), goods.getGoodsId());

                goodsSpecProductService.insert(db, goods.getGoodsSpecProducts(), goodsSpecMap, goods.getGoodsId());
            }

        });
    }

    /**
     * 插入数据并设置对应入参的id值
     *
     * @param db
     * @param goods
     */
    private void insert(DSLContext db, Goods goods) {

        if (goods.getGoodsSpecProducts() != null) {
            calculateGoodsPriceAndNumber(goods);
        }

        if (goods.getGoodsSn() == null) {
            goods.setGoodsSn(Util.randomId());
        }

        GoodsRecord goodsRecord = db.newRecord(GOODS, goods);
        goodsRecord.insert();
        goods.setGoodsId(goodsRecord.getGoodsId());
    }

    /**
     * 预处理通过规格信息计算出商品的库存，最小商品价格信息,
     * 并将结果注入到传入的引用对象。
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
     * 商品名和商品码查重
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
     * 商品规格字段重复检查
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

