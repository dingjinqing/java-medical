package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.main.Tables.CATEGORY;
import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_BRAND;
import static com.vpu.mp.db.shop.Tables.GOODS_IMG;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.Tables.SORT;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;
import static com.vpu.mp.service.pojo.shop.goods.GoodsPageListParam.ASC;
import static com.vpu.mp.service.pojo.shop.goods.GoodsPageListParam.IS_ON_SALE_DEFAULT;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.vpu.mp.db.shop.tables.records.GoodsLabelCoupleRecord;
import com.vpu.mp.service.foundation.*;
import com.vpu.mp.service.pojo.shop.goods.*;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecVal;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import static com.vpu.mp.service.pojo.shop.goods.GoodsPageListVo.GoodsLabel;

import com.vpu.mp.db.shop.tables.records.GoodsImgRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;

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
    public GoodsLabelService goodsLabel;
    public GoodsLabelCoupleService goodsLabelCouple;

    private GoodsSpecProductService goodsSpecProductService = new GoodsSpecProductService();

    /**
     * 商品分页查询
     *
     * @param goodsPageListParam
     * @return
     */
    public PageResult<GoodsPageListVo> getPageList(GoodsPageListParam goodsPageListParam) {
        SelectOnConditionStep<?> selectFrom = db()
                .select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_SN, GOODS.SHOP_PRICE,
                        GOODS.CAT_ID, SORT.SORT_NAME, GOODS_BRAND.BRAND_NAME, GOODS.GOODS_NUMBER, GOODS.GOODS_SALE_NUM)
                .from(GOODS).leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID))
                .leftJoin(GOODS_BRAND).on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID));


        SelectConditionStep<?> select = this.buildOptions(selectFrom, goodsPageListParam);

        PageResult<GoodsPageListVo> pageResult = this.getPageResult(select, goodsPageListParam.getCurrentPage(),
                goodsPageListParam.getPageRows(), GoodsPageListVo.class);

        this.disposeCategoryName(pageResult.getDataList());

        this.disposeGoodsLabels(pageResult.getDataList());

        return pageResult;
    }

    /**
     * 遍历查询结果设置对应的平台分类
     *
     * @param goodsPageListVos
     */
    private void disposeCategoryName(List<GoodsPageListVo> goodsPageListVos) {
        Map<Short, String> catIdNameMap = mainDb().select(CATEGORY.CAT_ID, CATEGORY.CAT_NAME).from(CATEGORY).fetch()
                .intoMap(CATEGORY.CAT_ID, CATEGORY.CAT_NAME);

        for (GoodsPageListVo goodsPageListVo : goodsPageListVos) {
            Short catId = goodsPageListVo.getCatId();
            goodsPageListVo.setCatName(catIdNameMap.get(catId));
        }
    }

    /**
     * 处理商品的关联的标签
     *
     * @param goodsPageListVos
     */
    private void disposeGoodsLabels(List<GoodsPageListVo> goodsPageListVos) {
        List<Integer> goodsIds = new ArrayList<>(goodsPageListVos.size());
        List<Short> catIds = new ArrayList<>(goodsPageListVos.size());

        for (GoodsPageListVo v : goodsPageListVos) {
            goodsIds.add(v.getGoodsId());
            catIds.add(v.getCatId());
        }

        //gatid和标签映射
        Map<Integer, List<GoodsLabel>> goodsIdLabelMap = db().select().from(GOODS_LABEL).innerJoin(GOODS_LABEL_COUPLE)
                .on(GOODS_LABEL.ID.eq(GOODS_LABEL_COUPLE.LABEL_ID))
                .where(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCouple.GOODS_LABEL_CODE))
                .and(GOODS_LABEL_COUPLE.GTA_ID.in(goodsIds))
                .fetch().intoGroups(GOODS_LABEL_COUPLE.GTA_ID, GoodsLabel.class);


        //通过属于平台分类查询 分类类型和标签的对应
        Map<Integer, List<GoodsLabel>> catIdLabelMap = db().select().from(GOODS_LABEL).innerJoin(GOODS_LABEL_COUPLE)
                .on(GOODS_LABEL.ID.eq(GOODS_LABEL_COUPLE.LABEL_ID))
                .where(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCouple.CATEGORY_LABEL_CODE))
                .and(GOODS_LABEL_COUPLE.GTA_ID.in(catIds))
                .fetch().intoGroups(GOODS_LABEL_COUPLE.GTA_ID, GoodsLabel.class);


        for (GoodsPageListVo v : goodsPageListVos) {
            Integer goodsId = v.getGoodsId();
            Short categoryId = v.getCatId();

            List<GoodsLabel> goodsIdLabels = goodsIdLabelMap.get(goodsId);
            if (goodsIdLabels != null) {
                v.setGoodsLabels(goodsIdLabels);
            }

            @SuppressWarnings(value = "all")
            List<GoodsLabel> categoryIdLabels = catIdLabelMap.get(categoryId);

            if (categoryIdLabels != null) {
                List<GoodsLabel> oldLabel = v.getGoodsLabels();
                if (oldLabel == null) {
                    v.setGoodsLabels(categoryIdLabels);
                } else {
                    oldLabel.addAll(categoryIdLabels);
                }
            }
        }

    }

    /**
     * 分页条件拼凑
     *
     * @param select
     * @param goodsPageListParam
     * @return
     */
    private SelectConditionStep<?> buildOptions(SelectOnConditionStep<?> select,
                                                GoodsPageListParam goodsPageListParam) {
        SelectConditionStep<?> scs = select.where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));

        //默认显示在售商品
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

        //根据标签过滤商品
        if (goodsPageListParam.getLabelId() != null) {

            //根据标签类型和标签id值过滤出改标签对应的商品id或者平台分类id
            List<Integer> gtaIds = db().selectDistinct(GOODS_LABEL_COUPLE.GTA_ID).from(GOODS_LABEL_COUPLE)
                    .where(GOODS_LABEL_COUPLE.TYPE.eq(goodsPageListParam.getLabelType()))
                    .and(GOODS_LABEL_COUPLE.LABEL_ID.eq(goodsPageListParam.getLabelId()))
                    .fetch(GOODS_LABEL_COUPLE.GTA_ID);


            if (GoodsLabelCouple.GOODS_LABEL_CODE.equals(goodsPageListParam.getLabelType())) {
                //标签是针对单个商品的类型
                scs = scs.and(GOODS.GOODS_ID.in(gtaIds));
            } else {
                //标签是针对平台分类的类型
                scs = scs.and(GOODS.CAT_ID.in(gtaIds));
            }

        }


        //筛选排序条件，默认是根据创建时间进行排序
        String orderField = goodsPageListParam.getOrderField();
        String orderDire = goodsPageListParam.getOrderDirection();

        if (StringUtils.isBlank(orderField)) {
            scs.orderBy(GOODS.CREATE_TIME.desc());
            return scs;
        }

        if (GoodsPageListParam.SHOP_PRICE.equals(orderField)) {
            if (ASC.equals(orderDire)) {
                scs.orderBy(GOODS.SHOP_PRICE.asc(), GOODS.CREATE_TIME.desc());
            } else {
                scs.orderBy(GOODS.SHOP_PRICE.desc(), GOODS.CREATE_TIME.desc());
            }
        }

        if (GoodsPageListParam.GOODS_NUMBER.equals(orderField)) {
            if (ASC.equals(orderDire)) {
                scs.orderBy(GOODS.GOODS_NUMBER.asc(), GOODS.CREATE_TIME.desc());
            } else {
                scs.orderBy(GOODS.GOODS_NUMBER.desc(), GOODS.CREATE_TIME.desc());
            }
        }

        if (GoodsPageListParam.GOODS_SALE_NUM.equals(orderField)) {
            if (ASC.equals(orderDire)) {
                scs.orderBy(GOODS.GOODS_SALE_NUM.asc(), GOODS.CREATE_TIME.desc());
            } else {
                scs.orderBy(GOODS.GOODS_SALE_NUM.desc(), GOODS.CREATE_TIME.desc());
            }
        }

        return scs;
    }


    /**
     * @param ids 商品ID列表
     * @return
     * @author 黄荣刚
     */
    public List<GoodsView> selectGoodsViewList(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<GoodsView>();
        }
        List<GoodsView> goodsViewList = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_NUMBER, GOODS.SHOP_PRICE, GOODS.UNIT)
                .from(GOODS).where(GOODS.GOODS_ID.in(ids)).fetchInto(GoodsView.class);
        return goodsViewList;
    }


    /**
     * 先插入商品，从而得到商品的id，
     * 然后插入商品规格的属性和规格值，从而得到规格属性和规格值的id, 最后拼凑出prdSpecs再插入具体的商品规格
     *
     * @param goods
     */
    public void insert(Goods goods) {
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            insert(db, goods);

            // 商品图片增加
            if (goods.getGoodsImgs() != null && goods.getGoodsImgs().size() != 0) {
                insertGoodsImgs(db, goods.getGoodsImgs(), goods.getGoodsId());
            }

            // 商品关联标签添加
            if (goods.getGoodsLabels() != null && goods.getGoodsLabels().size() != 0) {
                insertGoodsLabels(db, goods.getGoodsLabels(), goods.getGoodsId());
            }

            // 用户使用默认的规格数据，则sku只有一条，对应的规格列表为空
            if (goods.getGoodsSpecs() == null || goods.getGoodsSpecs().size() == 0) {
                goodsSpecProductService.insert(db, goods.getGoodsSpecProducts().get(0), goods.getGoodsId());

            } else {
                // 如果存在规格列表字段，则表明用户自己定义了具体的规格
                goodsSpecProductService.insert(db, goods.getGoodsSpecProducts(), goods.getGoodsSpecs(), goods.getGoodsId());
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
     * 商品图片插入
     *
     * @param db
     * @param goodsImgs
     * @param goodsId
     */
    private void insertGoodsImgs(DSLContext db, List<String> goodsImgs, Integer goodsId) {

        InsertValuesStep2<GoodsImgRecord, Integer, String> insertInto = db().insertInto(GOODS_IMG, GOODS_IMG.GOODS_ID,
                GOODS_IMG.IMG_URL);

        for (String imgUrl : goodsImgs) {
            insertInto.values(goodsId, imgUrl);
        }

        insertInto.execute();
    }

    /**
     * 商品标签插入
     *
     * @param db
     * @param goodsLabels
     * @param goodsId
     */
    private void insertGoodsLabels(DSLContext db, List<Integer> goodsLabels, Integer goodsId) {

        List<GoodsLabelCouple> list = new ArrayList<>(goodsLabels.size());

        for (Integer labelId : goodsLabels) {
            list.add(new GoodsLabelCouple(null, labelId, goodsId, GoodsLabelCouple.GOODS_LABEL_CODE));
        }
        goodsLabelCouple.batchInsert(db, list);

    }

    /**
     * 预处理通过规格信息计算出商品的库存，最小商品价格信息, 并将结果注入到传入的引用对象。
     *
     * @param goods
     */
    private void calculateGoodsPriceAndNumber(Goods goods) {
        // 当存在商品规格时，统计商品总数和最低商品价格
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

    /**
     * 判断字段值是否重复
     *
     * @param goodsColumnExistParam
     * @return
     */
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
    private SelectConditionStep<?> buildGoodsColumnExistOption(SelectJoinStep<?> select,
                                                               GoodsColumnCheckExistParam goodsColumnExistParam) {
        SelectConditionStep<?> scs = select.where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));

        if (goodsColumnExistParam.getGoodsName() != null) {
            scs = scs.and(GOODS.GOODS_NAME.eq(goodsColumnExistParam.getGoodsName()));
        }

        if (goodsColumnExistParam.getGoodsSn() != null) {
            scs = scs.and(GOODS.GOODS_SN.eq(goodsColumnExistParam.getGoodsSn()));
        }

        // update 修改条目时排除自身
        if (goodsColumnExistParam.getGoodsId() != null) {
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
    private SelectConditionStep<?> buildGoodsSpecPrdColumnExistOption(SelectJoinStep<?> select,
                                                                      GoodsColumnCheckExistParam goodsColumnExistParam) {
        SelectConditionStep<?> scs = select.where(GOODS_SPEC_PRODUCT.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));

        if (goodsColumnExistParam.getPrdSn() != null) {
            scs = scs.and(GOODS_SPEC_PRODUCT.PRD_SN.eq(goodsColumnExistParam.getPrdSn()));
        }

        // 修改去重
        if (goodsColumnExistParam.getPrdId() != null) {
            scs = scs.and(GOODS_SPEC_PRODUCT.PRD_ID.ne(goodsColumnExistParam.getPrdId()));
        }

        return scs;
    }

    /**
     * 商品批量修改方法
     *
     * @param operateParam
     */
    public void batchOperate(GoodsBatchOperateParam operateParam) {
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            batchGoodsOprerate(db, operateParam);
            batchLabelOperate(db, operateParam);
        });
    }

    /**
     * 批量处理中处理商品表
     *
     * @param db
     * @param operateParam
     */
    private void batchGoodsOprerate(DSLContext db, GoodsBatchOperateParam operateParam) {
        String[] updateSql = operateParam.toUpdateSql();
        if (updateSql == null) {
            return;
        }
        db.batch(updateSql).execute();
    }

    /**
     * 批量处理中处理商品标签
     *
     * @param db
     * @param operateParam
     */
    private void batchLabelOperate(DSLContext db, GoodsBatchOperateParam operateParam) {
        List<Integer> goodsLabels = operateParam.getGoodsLabels();

        if (goodsLabels == null && goodsLabels.size() == 0) {
            return;
        }
        List<Integer> goodsIds = operateParam.getGoodsIds();

        List<GoodsLabelCoupleRecord> records = new ArrayList<>();
        for (Integer labelId : goodsLabels) {
            for (Integer goodsId : goodsIds) {
                GoodsLabelCoupleRecord gl = new GoodsLabelCoupleRecord();
                gl.setLabelId(labelId);
                gl.setGtaId(goodsId);
                gl.setType(GoodsLabelCouple.CATEGORY_LABEL_CODE);
                records.add(gl);
            }
        }

        db.batchInsert(records);
    }

    /**
     * 商品批量删除及单个删除
     *
     * @param operateParam
     */
    public void delete(GoodsBatchOperateParam operateParam) {
        List<Integer> goodsIds = operateParam.getGoodsIds();
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            db.update(GOODS).set(GOODS.DEL_FLAG, DelFlag.DISABLE_VALUE)
                    .set(GOODS.GOODS_SN, DSL.concat(DelFlag.DEL_ITEM_PREFIX)
                            .concat(GOODS.GOODS_ID)
                            .concat(DelFlag.DEL_ITEM_SPLITER)
                            .concat(GOODS.GOODS_SN))
                    .set(GOODS.GOODS_NAME, DSL.concat(DelFlag.DEL_ITEM_PREFIX)
                            .concat(GOODS.GOODS_ID)
                            .concat(DelFlag.DEL_ITEM_SPLITER)
                            .concat(GOODS.GOODS_NAME))
                    .where(GOODS.GOODS_ID.in(goodsIds))
                    .execute();

            delteImg(db, goodsIds);

            goodsSpecProductService.deleteByGoodsIds(db, goodsIds);

            goodsLabelCouple.deleteByGoodsIds(db, goodsIds);

        });


    }

    /**
     * 删除关联图片
     *
     * @param goodsIds
     */
    private void delteImg(DSLContext db, List<Integer> goodsIds) {
        db.delete(GOODS_IMG).where(GOODS_IMG.GOODS_ID.in(goodsIds)).execute();
    }

    /**
     *  商品修改
     * @param goods
     */
    public void update(Goods goods) {
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);

            updateGoods(db, goods);

            updateImgs(db, goods);

            updateLabels(db, goods);

            updateSpec(db, goods);
        });
    }

    /**
     * 修改商品表
     *
     * @param db
     * @param goods
     */
    private void updateGoods(DSLContext db, Goods goods) {
        if (goods.getGoodsSpecProducts() != null) {
            calculateGoodsPriceAndNumber(goods);
        }

        if (goods.getGoodsSn() == null) {
            goods.setGoodsSn(Util.randomId());
        }

        GoodsRecord goodsRecord = db.fetchOne(GOODS, GOODS.GOODS_ID.eq(goods.getGoodsId()));

        assign(goods, goodsRecord);

        goodsRecord.store();
    }


    /**
     * 修改商品图片
     *
     * @param db
     * @param goods
     */
    private void updateImgs(DSLContext db, Goods goods) {
        List<Integer> goodsIds = Arrays.asList(goods.getGoodsId());

        delteImg(db, goodsIds);

        if (goods.getGoodsImgs() != null && goods.getGoodsImgs().size() != 0) {
            insertGoodsImgs(db, goods.getGoodsImgs(), goods.getGoodsId());
        }
    }

    /**
     * 修改商品标签
     *
     * @param db
     * @param goods
     */
    private void updateLabels(DSLContext db, Goods goods) {
        List<Integer> goodsIds = Arrays.asList(goods.getGoodsId());

        goodsLabelCouple.deleteByGoodsIds(db, goodsIds);

        if (goods.getGoodsLabels() != null && goods.getGoodsLabels().size() != 0) {
            insertGoodsLabels(db, goods.getGoodsLabels(), goods.getGoodsId());
        }
    }

    /**
     * 修改商品sku
     *
     * @param db
     * @param goods
     */
    private void updateSpec(DSLContext db, Goods goods) {
        boolean isChange = goodsSpecProductService.isChange(db, goods.getGoodsSpecProducts(), goods.getGoodsId());

        if (!isChange) {
            return;
        }

        goodsSpecProductService.deleteByGoodsIds(db, Arrays.asList(goods.getGoodsId()));


        goods.getGoodsSpecProducts().forEach(goodsSpecProduct -> goodsSpecProduct.setGoodsId(null));

        // 用户使用默认的规格数据，则sku只有一条，对应的规格列表为空
        if (goods.getGoodsSpecs() == null || goods.getGoodsSpecs().size() == 0) {
            goodsSpecProductService.insert(db, goods.getGoodsSpecProducts().get(0), goods.getGoodsId());
        } else {
            // 如果存在规格列表字段，则表明用户自己定义了具体的规格

            for (GoodsSpec goodsSpec : goods.getGoodsSpecs()) {
                goodsSpec.setSpecId(null);
                if (goodsSpec.getGoodsSpecVals() != null) {
                    for (GoodsSpecVal goodsSpecVal : goodsSpec.getGoodsSpecVals()) {
                        goodsSpecVal.setSpecValId(null);
                    }
                }
            }

            goodsSpecProductService.insert(db, goods.getGoodsSpecProducts(), goods.getGoodsSpecs(), goods.getGoodsId());
        }

    }
}
