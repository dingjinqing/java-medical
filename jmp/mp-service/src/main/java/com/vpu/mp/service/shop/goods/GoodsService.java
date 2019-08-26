package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.GoodsImgRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRebatePriceRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GradePrdRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.goods.*;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelListVo;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListParam;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecVal;
import com.vpu.mp.service.shop.decoration.ChooseLinkService;
import com.vpu.mp.service.shop.member.MemberCardService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam.ASC;

/**
 * 商品品牌
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
@Service

public class GoodsService extends ShopBaseService {

    @Autowired public GoodsBrandService goodsBrand;
    @Autowired public GoodsSortService goodsSort;
    @Autowired public GoodsCommentService goodsComment;
    @Autowired public GoodsLabelService goodsLabel;
    @Autowired public GoodsLabelCoupleService goodsLabelCouple;
    @Autowired public GoodsDeliverTamplateService goodsDeliver;
    @Autowired public ChooseLinkService chooseLink;
    @Autowired protected MemberCardService memberCardService;
    @Autowired protected GoodsSpecProductService goodsSpecProductService;


    public GoodsInitialVo pageInitValue(){
        GoodsInitialVo goodsInitialVo = new GoodsInitialVo();

        goodsInitialVo.setGoodsBrands(goodsBrand.listGoodsBrandName());

        goodsInitialVo.setGoodsLabels(goodsLabel.listGoodsLabelName());

        goodsInitialVo.setGoodsSorts(goodsSort.getList(new GoodsSortListParam()));

        goodsInitialVo.setSysCates(saas.sysCate.getSysCate());

        return goodsInitialVo;
    }
    /**
     * 商品分页查询
     *
     * @param goodsPageListParam
     * @return
     */
    public PageResult<GoodsPageListVo> getPageList(GoodsPageListParam goodsPageListParam) {
        SelectOnConditionStep<?> selectFrom = db()
                .select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_SN, GOODS.SHOP_PRICE,
                        GOODS.CAT_ID, SORT.SORT_NAME, GOODS.SORT_ID, GOODS_BRAND.BRAND_NAME, GOODS.GOODS_NUMBER, GOODS.GOODS_SALE_NUM)
                .from(GOODS).leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID)).leftJoin(GOODS_BRAND)
                .on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID));

        // 拼接过滤条件
        SelectConditionStep<?> select = this.buildOptions(selectFrom, goodsPageListParam);

        // 拼接排序
        select = this.buildOrderFileds(select, goodsPageListParam);

        PageResult<GoodsPageListVo> pageResult = this.getPageResult(select, goodsPageListParam.getCurrentPage(),
                goodsPageListParam.getPageRows(), GoodsPageListVo.class);

        saas.sysCate.disposeCategoryName(pageResult.getDataList());

        this.disposeGoodsLabels(pageResult.getDataList());

        return pageResult;
    }

   

    /**
     * 处理商品的关联的标签
     *
     * @param goodsPageListVos
     */
    private void disposeGoodsLabels(List<GoodsPageListVo> goodsPageListVos) {
        //通过商品id关联对应 的标签
        List<Integer> goodsIds = new ArrayList<>(goodsPageListVos.size());

        for (GoodsPageListVo v : goodsPageListVos) {
            goodsIds.add(v.getGoodsId());
        }

        // gatid和标签映射
        Map<Integer, List<GoodsLabelListVo>> goodsIdLabelMap = goodsLabel.getGtaLabelMap(goodsIds, GoodsLabelCoupleTypeEnum.GOODSTYPE);

        for (GoodsPageListVo v : goodsPageListVos) {
            Integer goodsId = v.getGoodsId();

            //商品关联
            List<GoodsLabelListVo> goodsIdLabels = goodsIdLabelMap.get(goodsId);
            if (goodsIdLabels != null) {
                v.setGoodsLabels(goodsIdLabels);
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
        SelectConditionStep<?> scs = select.where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));

        // 默认显示在售商品
        if (goodsPageListParam.getIsOnSale() == null) {
            goodsPageListParam.setIsOnSale(GoodsPageListParam.IS_ON_SALE_DEFAULT);
        }

        scs = scs.and(GOODS.IS_ON_SALE.eq(goodsPageListParam.getIsOnSale()));

        if (!StringUtils.isBlank(goodsPageListParam.getGoodsName())) {
            scs = scs.and(GOODS.GOODS_NAME.like(likeValue(goodsPageListParam.getGoodsName())));
        }

        if (!StringUtils.isBlank(goodsPageListParam.getGoodsSn())) {
            scs=scs.and(GOODS.GOODS_SN.like(likeValue(goodsPageListParam.getGoodsSn())));
        }

        if (goodsPageListParam.getCatId() != null) {
            scs = scs.and(GOODS.CAT_ID.eq(goodsPageListParam.getCatId()));
        }

        if (goodsPageListParam.getSortId() != null) {
            List<Integer> childrenId = goodsSort.findChildrenByParentId(goodsPageListParam.getSortId());
            childrenId.add(goodsPageListParam.getSortId());
            scs = scs.and(GOODS.SORT_ID.in(childrenId));
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

        // 根据标签过滤商品
        if (goodsPageListParam.getLabelId() != null) {

            // 根据标签类型和标签id值过滤出该标签对应的商品id或者平台分类id
            List<Integer> gatIds = goodsLabelCouple.selectGatIdsByLabelIds(
                    Arrays.asList(goodsPageListParam.getLabelId()), goodsPageListParam.getLabelType());

            // 目前仅仅考虑了标签属于商品，属于平台分类和全部商品的情况，未考虑标签属于商家分类的情况
            if (GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode().equals(goodsPageListParam.getLabelType())) {
                // 标签是针对单个商品的类型
                scs = scs.and(GOODS.GOODS_ID.in(gatIds));
            } else if (GoodsLabelCoupleTypeEnum.CATTYPE.getCode().equals(goodsPageListParam.getLabelType())) {
                // 标签是针对平台分类的类型
                scs = scs.and(GOODS.CAT_ID.in(gatIds));
            } else if (GoodsLabelCoupleTypeEnum.SORTTYPE.getCode().equals(goodsPageListParam.getLabelType())) {
                // 标签是针对商家分类的类型
                scs = scs.and(GOODS.SORT_ID.in(gatIds));
            } else {
                //全部商品情况不用添加过滤条件
            }

        }
        return scs;
    }

    /**
     * 排序条件过滤
     *
     * @return
     */
    private SelectConditionStep<?> buildOrderFileds(SelectConditionStep<?> scs, GoodsPageListParam goodsPageListParam) {
        // 筛选排序条件，默认是根据创建时间进行排序
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
     * @author 黄荣刚
     * @return 商品id列表
     */
    public List<GoodsView> selectGoodsViewList(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<GoodsView>();
        }
        List<GoodsView> goodsViewList = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG,
                GOODS.GOODS_NUMBER, GOODS.SHOP_PRICE, GOODS.UNIT).from(GOODS).where(GOODS.GOODS_ID.in(ids))
                .fetchInto(GoodsView.class);
        return goodsViewList;
    }

    /**
     * 取单个GoodsView
     */
    public GoodsView getGoodsView(Integer goodsId){
        return db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_NUMBER, GOODS.SHOP_PRICE, GOODS.UNIT).
            from(GOODS).where(GOODS.GOODS_ID.eq(goodsId)).
            fetchOne().into(GoodsView.class);
    }

    /**
     * 先插入商品，从而得到商品的id， 然后插入商品规格的属性和规格值，
     * 从而得到规格属性和规格值的id, 最后拼凑出prdSpecs再插入具体的商品规格
     * @param goods 商品信息
     */
    public void insert(Goods goods) {
        transaction(() -> {
            insertGoods(goods);

            // 商品图片增加
            if (goods.getGoodsImgs() != null && goods.getGoodsImgs().size() != 0) {
                insertGoodsImgs(goods.getGoodsImgs(), goods.getGoodsId());
            }

            // 商品关联标签添加
            if (goods.getGoodsLabels() != null && goods.getGoodsLabels().size() != 0) {
                insertGoodsLabels(goods.getGoodsLabels(), goods.getGoodsId());
            }


            if (goods.getGoodsSpecs() == null || goods.getGoodsSpecs().size() == 0) {
                // 用户使用默认的规格数据，则sku只有一条，对应的规格列表为空
                goodsSpecProductService.insert(goods.getGoodsSpecProducts().get(0), goods.getGoodsId(),goods.getMarketPrice());
            } else {
                // 如果存在规格列表字段，则表明用户自己定义了具体的规格
                goodsSpecProductService.insert(goods.getGoodsSpecProducts(), goods.getGoodsSpecs(),goods.getGoodsId(),goods.getMarketPrice());
            }

            //插入商品规格对应的会员卡价格
            insertGradePrd(goods.getGoodsGradePrds(),goods.getGoodsSpecProducts(),goods.getGoodsId());

            //插入商品专属会员信息
            insertMemberCards(goods);

            //插入商品分销改价信息
            insertGoodsRebatPrices(goods.getGoodsRebatePrices(),goods.getGoodsSpecProducts(),goods.getGoodsId());

        });
    }

    /**
     * 插入数据并设置对应入参的id值
     * @param goods 商品基础信息
     */
    private void insertGoods(Goods goods) {
        //计算商品的价格和库存量
        calculateGoodsPriceAndNumber(goods);

        if (StringUtils.isBlank(goods.getGoodsSn())) {
            goods.setGoodsSn(Util.randomId());
        }

        //商品海报分享设置
        if (goods.getGoodsSharePostConfig() == null) {
            GoodsSharePostConfig goodsSharePostConfig = new GoodsSharePostConfig();
            goodsSharePostConfig.setShareAction(GoodsSharePostConfig.DEFAULT_ACTION);
            goodsSharePostConfig.setShareImgAction(GoodsSharePostConfig.DEFAULT_SHARE_IMG_ACTION);
            goods.setGoodsSharePostConfig(goodsSharePostConfig);
        }
        goods.setShareConfig(Util.toJson(goods.getGoodsSharePostConfig()));

        GoodsRecord goodsRecord = db().newRecord(GOODS, goods);
        goodsRecord.insert();
        goods.setGoodsId(goodsRecord.getGoodsId());
    }

    private void insertMemberCards(Goods goods) {
        if (goods.getIsCardExclusive()==null||goods.getIsCardExclusive()==0||goods.getMemberCardIds()==null||goods.getMemberCardIds().size()==0) {
            return;
        }
        memberCardService.batchUpdateGoods(Arrays.asList(goods.getGoodsId()),goods.getMemberCardIds());
    }

    /**
     * 商品图片插入
     * @param goodsImgs 商品图片地址列表
     * @param goodsId 商品id
     */
    private void insertGoodsImgs(List<String> goodsImgs, Integer goodsId) {

        InsertValuesStep2<GoodsImgRecord, Integer, String> insertInto = db().insertInto(GOODS_IMG, GOODS_IMG.GOODS_ID,
                GOODS_IMG.IMG_URL);

        for (String imgUrl : goodsImgs) {
            insertInto.values(goodsId, imgUrl);
        }

        insertInto.execute();
    }

    /**
     * 商品标签插入
     * @param goodsLabels 标签id列表
     * @param goodsId   商品id
     */
    private void insertGoodsLabels(List<Integer> goodsLabels, Integer goodsId) {

        List<GoodsLabelCouple> list = new ArrayList<>(goodsLabels.size());

        for (Integer labelId : goodsLabels) {
            list.add(new GoodsLabelCouple(null, labelId, goodsId, GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()));
        }
        goodsLabelCouple.batchInsert(list);
    }

    /**
     * 插入商品规格对应的会员卡价格
     * @param goodsGradePrds 商品规格对应会员卡
     * @param goodsSpecProducts 商品规格
     * @param goodsId   商品id
     */
    private void insertGradePrd(List<GoodsGradePrd> goodsGradePrds,List<GoodsSpecProduct> goodsSpecProducts,Integer goodsId){

        if (goodsGradePrds == null || goodsGradePrds.size() == 0) {
            return;
        }

        Map<String, Integer> collect = goodsSpecProducts.stream().collect(Collectors.toMap(GoodsSpecProduct::getPrdDesc, GoodsSpecProduct::getPrdId));
        DefaultDSLContext db=db();
        List<GradePrdRecord> gradePrdRecords = goodsGradePrds.stream().map(goodsGradePrd -> {
            GradePrdRecord gradePrdRecord = db.newRecord(GRADE_PRD, goodsGradePrd);
            gradePrdRecord.setGoodsId(goodsId);
            gradePrdRecord.setPrdId(collect.get(goodsGradePrd.getPrdDesc()));
            return gradePrdRecord;
        }).collect(Collectors.toList());

        db.batchInsert(gradePrdRecords).execute();
    }

    /**
     * 插入商品分销改价信息
     * @param goodsRebatePrices
     * @param goodsSpecProducts
     * @param goodsId
     */
    private void insertGoodsRebatPrices(List<GoodsRebatePrice> goodsRebatePrices, List<GoodsSpecProduct> goodsSpecProducts, Integer goodsId){

        if (goodsRebatePrices == null || goodsRebatePrices.size() == 0) {
            return;
        }

        Map<String, Integer> collect = goodsSpecProducts.stream().collect(Collectors.toMap(GoodsSpecProduct::getPrdDesc, GoodsSpecProduct::getPrdId));
        DefaultDSLContext db=db();
        List<GoodsRebatePriceRecord> goodsRebatePriceRecords = goodsRebatePrices.stream().map(goodsRebatePrice -> {
            GoodsRebatePriceRecord goodsRebatePriceRecord = db.newRecord(GOODS_REBATE_PRICE, goodsRebatePrice);
            goodsRebatePriceRecord.setGoodsId(goodsId);
            goodsRebatePriceRecord.setProductId(collect.get(goodsRebatePrice.getPrdDesc()));
            return goodsRebatePriceRecord;
        }).collect(Collectors.toList());

        db.batchInsert(goodsRebatePriceRecords).execute();
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
        SelectConditionStep<?> scs = select.where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));

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
        //判断del_flag应该可以去掉，目前删除商品的时候会把sku备份到bak里面，prd表内是真删除
        SelectConditionStep<?> scs = select.where(GOODS_SPEC_PRODUCT.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));

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
        transaction(()->{
            batchGoodsOprerate(operateParam);
            batchLabelOperate(operateParam);
        });
    }

    /**
     * 批量处理中处理商品表
     *
     * @param operateParam
     */
    private void batchGoodsOprerate(GoodsBatchOperateParam operateParam) {
        List<GoodsRecord> goodsRecords = operateParam.toUpdateGoodsRecord();
        db().batchUpdate(goodsRecords).execute();
    }

    /**
     * 批量处理中处理商品标签
     *
     * @param operateParam
     */
    private void batchLabelOperate(GoodsBatchOperateParam operateParam) {
        List<Integer> goodsLabels = operateParam.getGoodsLabels();

        if (goodsLabels == null || goodsLabels.size() == 0) {
            return;
        }
        List<Integer> goodsIds = operateParam.getGoodsIds();

        List<GoodsLabelCouple> goodsLabelCouples = goodsLabelCouple.calculateGtaLabelDiffer(goodsIds, goodsLabels, GoodsLabelCoupleTypeEnum.GOODSTYPE);

        goodsLabelCouple.batchInsert(goodsLabelCouples);
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
            db.update(GOODS).set(GOODS.DEL_FLAG, DelFlag.DISABLE.getCode())
                    .set(GOODS.GOODS_SN,
                            DSL.concat(DelFlag.DEL_ITEM_PREFIX).concat(GOODS.GOODS_ID).concat(DelFlag.DEL_ITEM_SPLITER)
                                    .concat(GOODS.GOODS_SN))
                    .set(GOODS.GOODS_NAME, DSL.concat(DelFlag.DEL_ITEM_PREFIX).concat(GOODS.GOODS_ID)
                            .concat(DelFlag.DEL_ITEM_SPLITER).concat(GOODS.GOODS_NAME))
                    .where(GOODS.GOODS_ID.in(goodsIds)).execute();

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
     * 商品修改
     *
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
            insertGoodsImgs(goods.getGoodsImgs(), goods.getGoodsId());
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
            insertGoodsLabels(goods.getGoodsLabels(), goods.getGoodsId());
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
            goodsSpecProductService.updateSpec(db,goods.getGoodsSpecProducts());
            return;
        }

        goodsSpecProductService.deleteByGoodsIds(db, Arrays.asList(goods.getGoodsId()));

        goods.getGoodsSpecProducts().forEach(goodsSpecProduct -> goodsSpecProduct.setPrdId(null));

        // 用户使用默认的规格数据，则sku只有一条，对应的规格列表为空
        if (goods.getGoodsSpecs() == null || goods.getGoodsSpecs().size() == 0) {
            goodsSpecProductService.insert(goods.getGoodsSpecProducts().get(0), goods.getGoodsId(),goods.getMarketPrice());
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

            goodsSpecProductService.insert(goods.getGoodsSpecProducts(), goods.getGoodsSpecs(), goods.getGoodsId(),goods.getMarketPrice());
        }

    }

    public GoodsVo select(Integer goodsId) {
        GoodsVo goodsVo = db().select()
                .from(GOODS).leftJoin(GOODS_BRAND).on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID))
                .leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID))
                .where(GOODS.GOODS_ID.eq(goodsId)).fetchOne().into(GoodsVo.class);
        //设置图片
        List<String> imgs=selectImg(goodsId);
        goodsVo.setGoodsImgs(imgs);
        //设置标签
        Map<Integer, List<GoodsLabelListVo>> gtaLabelMap = goodsLabel.getGtaLabelMap(Arrays.asList(goodsId), GoodsLabelCoupleTypeEnum.GOODSTYPE);
        goodsVo.setGoodsLabelListVos(gtaLabelMap.get(goodsId));

        //设置sku
        List<GoodsSpecProduct> goodsSpecProducts = goodsSpecProductService.selectByGoodsId(goodsId);
        goodsVo.setGoodsSpecProducts(goodsSpecProducts);

        List<GoodsSpec> goodsSpecs = goodsSpecProductService.selectSpecByGoodsId(goodsId);
        goodsVo.setGoodsSpecs(goodsSpecs);

        return goodsVo;
    }

    /**
     *  查找图片
     * @param goodsId
     * @return
     */
    private List<String> selectImg(Integer goodsId){
        List<String> fetch = db().select(GOODS_IMG.IMG_URL).from(GOODS_IMG).where(GOODS_IMG.GOODS_ID.eq(goodsId)).fetch(GOODS_IMG.IMG_URL);
        return fetch;
    }
    
    /**
     * 	通过商品id数组查询商品
     */
    public Map<Integer, GoodsRecord> getGoodsByIds(List<Integer> goodsIds){
        return db().selectFrom(GOODS).where(GOODS.GOODS_ID.in(goodsIds)).
            fetchMap(GOODS.GOODS_ID);
    }
}
