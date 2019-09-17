package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.main.tables.Shop;
import com.vpu.mp.db.shop.tables.records.*;
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
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.decoration.ChooseLinkService;
import com.vpu.mp.service.shop.decoration.ShopMpDecorationService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
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
 * 商品
 * 商品关联的附属数据信息主要包含：商品sku(b2c_goods_spec_product)，规格名(b2c_spec),规格值(b2c_spec_vals),
 * 商品图片(b2c_goods_img),平台分类(b2c_category)和商家分类(b2c_sort)在查询商品时可以根据这两个进行筛选,
 * 商品标签(b2c_goods_label,b2c_goods_label_couple)标签可以打在平台和商家分类上，在通过标签查商品时要进行关联查询，
 * 会员等价卡价格(b2c_grade_prd)，运费模板(b2c_deliver_fee_template)，会员专享商品(b2c_goods_card_couple),商品页模板，
 * 分销改价(b2c_goods_rebate_price)
 *
 * @author 李晓冰
 * @date 2019年6月25日
 */
@Service
// TODO:1.所有操作添加操作记录
// TODO:2.自定义商品上架时间的时候在新增删除修改的时候修改定时任务

public class GoodsService extends ShopBaseService {

    @Autowired
    public GoodsBrandService goodsBrand;
    @Autowired
    public GoodsSortService goodsSort;
    @Autowired
    public GoodsCommentService goodsComment;
    @Autowired
    public GoodsLabelService goodsLabel;
    @Autowired
    public GoodsLabelCoupleService goodsLabelCouple;
    @Autowired
    public GoodsDeliverTemplateService goodsDeliver;
    @Autowired
    public ChooseLinkService chooseLink;
    @Autowired
    protected MemberCardService memberCardService;
    @Autowired
    protected GoodsSpecProductService goodsSpecProductService;
    @Autowired
    protected QrCodeService qrCodeService;
    @Autowired
    protected ImageService imageService;
    @Autowired
    protected ShopMpDecorationService shopMpDecorationService;

    /**
     * 全部商品页面各个下拉框的数据初始化
     *
     * @return {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsInitialVo}
     */
    public GoodsInitialVo pageInitValue() {
        GoodsInitialVo goodsInitialVo = new GoodsInitialVo();

        goodsInitialVo.setGoodsBrands(goodsBrand.listGoodsBrandName());

        goodsInitialVo.setGoodsLabels(goodsLabel.listGoodsLabelName());

        goodsInitialVo.setGoodsSorts(goodsSort.getList(new GoodsSortListParam()));

        goodsInitialVo.setSysCates(saas.sysCate.getSysCate());

        return goodsInitialVo;
    }

    /**
     * 全部商品界面：商品分页查询
     *
     * @param goodsPageListParam
     * @return
     */
    public PageResult<GoodsPageListVo> getPageList(GoodsPageListParam goodsPageListParam) {
        SelectOnConditionStep<?> selectFrom = db()
            .select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_SN, GOODS.SHOP_PRICE,
                GOODS.SOURCE,GOODS.GOODS_TYPE, GOODS.CAT_ID, SORT.SORT_NAME, GOODS.SORT_ID, GOODS_BRAND.BRAND_NAME, GOODS.GOODS_NUMBER, GOODS.GOODS_SALE_NUM)
            .from(GOODS).leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID)).leftJoin(GOODS_BRAND)
            .on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID));

        // 拼接过滤条件
        SelectConditionStep<?> select = this.buildOptions(selectFrom, goodsPageListParam);

        // 拼接排序
        select = this.buildOrderFields(select, goodsPageListParam);

        PageResult<GoodsPageListVo> pageResult = this.getPageResult(select, goodsPageListParam.getCurrentPage(),
            goodsPageListParam.getPageRows(), GoodsPageListVo.class);

        // 处理商品平台分类：通过id值获取name值
        saas.sysCate.disposeCategoryName(pageResult.getDataList());
        // 处理标签名称准备数据
        List<Integer> goodsIds = new ArrayList<>(pageResult.getDataList().size());
        pageResult.getDataList().forEach(item -> goodsIds.add(item.getGoodsId()));
        Map<Integer, List<GoodsLabelListVo>> goodsLabels = this.getGoodsLabels(goodsIds);
        // 处理商品规格默认id值准备数据
        Map<Integer, Integer> goodsIdPrdIdMap = db().select(GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.GOODS_ID).from(GOODS_SPEC_PRODUCT)
            .where(GOODS_SPEC_PRODUCT.GOODS_ID.in(goodsIds)).and(GOODS_SPEC_PRODUCT.PRD_SPECS.eq(""))
            .fetch().intoMap(GOODS_SPEC_PRODUCT.GOODS_ID, GOODS_SPEC_PRODUCT.PRD_ID);

        pageResult.getDataList().forEach(item -> {
            // 设置标签名称
            item.setGoodsLabels(goodsLabels.get(item.getGoodsId()));
            // 设置图片绝对地址
            item.setGoodsImg(getImgFullUrlUtil(item.getGoodsImg()));
            // 设置商品默认规格id
            item.setPrdId(goodsIdPrdIdMap.get(item.getGoodsId()));
        });

        return pageResult;
    }

    /**
     * 商品（规格）分页查询
     */
    public PageResult<GoodsPageListVo> getProductPageList(GoodsPageListParam goodsPageListParam) {
        SelectOnConditionStep<?> selectFrom = db()
            .select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_SN, GOODS.SHOP_PRICE,
                GOODS.CAT_ID, SORT.SORT_NAME, GOODS.SORT_ID, GOODS_BRAND.BRAND_NAME, GOODS.GOODS_NUMBER, GOODS.GOODS_SALE_NUM)
            .select(GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_IMG, GOODS_SPEC_PRODUCT.PRD_DESC)
            .from(GOODS)
            .leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID))
            .leftJoin(GOODS_SPEC_PRODUCT).on(GOODS_SPEC_PRODUCT.GOODS_ID.eq(GOODS.GOODS_ID))
            .leftJoin(GOODS_BRAND)
            .on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID));
        SelectConditionStep<?> select;
        select = buildOptions(selectFrom, goodsPageListParam);
        select = buildOrderFields(select, goodsPageListParam);
        PageResult<GoodsPageListVo> pageResult = getPageResult(select, goodsPageListParam.getCurrentPage(),
            goodsPageListParam.getPageRows(), GoodsPageListVo.class);
        saas.sysCate.disposeCategoryName(pageResult.getDataList());
        List<Integer> goodsIds = new ArrayList<>(pageResult.getDataList().size());
        pageResult.getDataList().forEach(item -> goodsIds.add(item.getGoodsId()));
        Map<Integer, List<GoodsLabelListVo>> goodsLabels = this.getGoodsLabels(goodsIds);
        pageResult.getDataList().forEach(item -> item.setGoodsLabels(goodsLabels.get(item.getGoodsId())));
        return pageResult;
    }

    /**
     * 规格
     *
     * @param goodsId 商品ID
     * @return GoodsProductVo
     */
    public List<GoodsProductVo> getAllProductListByGoodsId(Integer goodsId) {
        return goodsSpecProductService.getAllProductListByGoodsId(goodsId).into(GoodsProductVo.class);
    }

    /**
     * 获取商品的关联的标签
     *
     * @param goodsIds 商品ids
     */
    private Map<Integer, List<GoodsLabelListVo>> getGoodsLabels(List<Integer> goodsIds) {
        return goodsLabel.getGtaLabelMap(goodsIds, GoodsLabelCoupleTypeEnum.GOODSTYPE);
    }

    /**
     * 分页条件拼凑
     *
     * @param select
     * @param goodsPageListParam
     * @return
     */
    private SelectConditionStep<?> buildOptions(SelectOnConditionStep<?> select, GoodsPageListParam goodsPageListParam) {
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
            scs = scs.and(GOODS.GOODS_SN.like(likeValue(goodsPageListParam.getGoodsSn())));
        }
        if (goodsPageListParam.getBrandId() != null) {
            scs = scs.and(GOODS.BRAND_ID.eq(goodsPageListParam.getBrandId()));
        }
        if (goodsPageListParam.getSource() != null) {
            scs = scs.and(GOODS.SOURCE.eq(goodsPageListParam.getSource()));
        }
        if (goodsPageListParam.getGoodsType() != null) {
            scs = scs.and(GOODS.GOODS_TYPE.eq(goodsPageListParam.getGoodsType()));
        }
        if (goodsPageListParam.getCatId() != null) {
            List<Integer> catIds=new ArrayList<>();
            catIds.add(goodsPageListParam.getCatId().intValue());
            List<Short> childrenId = saas.sysCate.findChildrenByParentId(catIds);
            scs = scs.and(GOODS.CAT_ID.in(childrenId));
        }
        if (goodsPageListParam.getSortId() != null) {
            List<Integer> childrenId = goodsSort.findChildrenByParentId(goodsPageListParam.getSortId());
            childrenId.add(goodsPageListParam.getSortId());
            scs = scs.and(GOODS.SORT_ID.in(childrenId));
        }
        // 根据标签过滤商品
        if (goodsPageListParam.getLabelId() != null) {
            // 根据标签类型和标签id值过滤出该标签对应的商品id或者平台分类id
            Map<Byte, List<Integer>> byteListMap = goodsLabelCouple.selectGatIdsByLabelIds(Arrays.asList(goodsPageListParam.getLabelId()));
            List<Integer> integers = byteListMap.get(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode());
            Condition condition=DSL.noCondition();
            if (integers!=null && integers.size() > 0) {
               condition = condition.or(GOODS.GOODS_ID.in(integers));
            }
            integers = byteListMap.get(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode());
            if (integers != null && integers.size() > 0) {
                List<Integer> sortChildrenId=goodsSort.findChildrenByParentId(integers);
                condition = condition.or(GOODS.SORT_ID.in(sortChildrenId));
            }
            integers = byteListMap.get(GoodsLabelCoupleTypeEnum.CATTYPE.getCode());
            if (integers != null && integers.size() > 0) {
                List<Short> catChildrenId=saas.sysCate.findChildrenByParentId(integers);
                condition = condition.or(GOODS.CAT_ID.in(catChildrenId));
            }
            scs = scs.and(condition);
        }
        if (goodsPageListParam.getSaleTimeStart() != null) {
            scs =scs.and(GOODS.SALE_TIME.ge(goodsPageListParam.getSaleTimeStart()));
        }
        if (goodsPageListParam.getSaleTimeEnd() != null) {
            scs =scs.and(GOODS.SALE_TIME.le(goodsPageListParam.getSaleTimeEnd()));
        }
        if (goodsPageListParam.getLowShopPrice() != null) {
            scs = scs.and(GOODS.SHOP_PRICE.ge(goodsPageListParam.getLowShopPrice()));
        }
        if (goodsPageListParam.getHighShopPrice() != null) {
            scs = scs.and(GOODS.SHOP_PRICE.le(goodsPageListParam.getHighShopPrice()));
        }
        if (goodsPageListParam.getGoodsNumber() != null) {
            scs = scs.and(GOODS.GOODS_NUMBER.le(goodsPageListParam.getGoodsNumber()));
        }
        if (goodsPageListParam.getSaleType() != null) {
            scs = scs.and(GOODS.SALE_TYPE.le(goodsPageListParam.getSaleType()));
        }
        return scs;
    }

    /**
     * 排序条件过滤
     *
     * @return
     */
    private SelectConditionStep<?> buildOrderFields(SelectConditionStep<?> scs, GoodsPageListParam goodsPageListParam) {
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
     * @return 商品id列表
     * @author 黄荣刚
     */
    public List<GoodsView> selectGoodsViewList(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<GoodsView> goodsViewList = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG,
            GOODS.GOODS_NUMBER, GOODS.SHOP_PRICE, GOODS.UNIT).from(GOODS).where(GOODS.GOODS_ID.in(ids))
            .fetchInto(GoodsView.class);
        goodsViewList.forEach(item -> item.setGoodsImg(getImgFullUrlUtil(item.getGoodsImg())));
        return goodsViewList;
    }

    /**
     * 取单个GoodsView
     */
    public GoodsView getGoodsView(Integer goodsId) {
        GoodsView goodsView = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_NUMBER, GOODS.SHOP_PRICE, GOODS.UNIT).
            from(GOODS).where(GOODS.GOODS_ID.eq(goodsId)).
            fetchOne().into(GoodsView.class);
        goodsView.setGoodsImg(getImgFullUrlUtil(goodsView.getGoodsImg()));
        return goodsView;
    }

    /**
     * 先插入商品，从而得到商品的id， 然后插入商品规格的属性和规格值，
     * 从而得到规格属性和规格值的id, 最后拼凑出prdSpecs再插入具体的商品规格
     *
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

            // 商品规格处理
            goodsSpecProductService.insert(goods.getGoodsSpecProducts(), goods.getGoodsSpecs(), goods.getGoodsId());

            //插入商品规格对应的会员卡价格
            insertGradePrd(goods.getGoodsGradePrds(), goods.getGoodsSpecProducts(), goods.getGoodsId());

            //插入商品专属会员信息
            insertMemberCards(goods);

            //插入商品分销改价信息
            insertGoodsRebatePrices(goods.getGoodsRebatePrices(), goods.getGoodsSpecProducts(), goods.getGoodsId());

        });
    }

    /**
     * 插入数据并设置对应入参的id值
     * @param goods {@link com.vpu.mp.service.pojo.shop.goods.goods}
     */
    private void insertGoods(Goods goods) {
        //计算商品的价格和库存量
        calculateGoodsPriceAndNumber(goods);

        if (StringUtils.isBlank(goods.getGoodsSn())) {
            goods.setGoodsSn(Util.randomId());
        }

        // 设置商品分享海报配置信息
        setGoodsShareConfig(goods);

        GoodsRecord goodsRecord = db().newRecord(GOODS, goods);
        goodsRecord.insert();
        goods.setGoodsId(goodsRecord.getGoodsId());
    }

    /**
     * 设置商品分享海报配置信息
     *
     * @param goods {@link com.vpu.mp.service.pojo.shop.goods.goods}
     */
    private void setGoodsShareConfig(Goods goods) {
        //商品海报分享设置
        if (goods.getGoodsSharePostConfig() == null) {
            GoodsSharePostConfig goodsSharePostConfig = new GoodsSharePostConfig();
            goodsSharePostConfig.setShareAction(GoodsSharePostConfig.DEFAULT_ACTION);
            goodsSharePostConfig.setShareImgAction(GoodsSharePostConfig.DEFAULT_SHARE_IMG_ACTION);
            goods.setGoodsSharePostConfig(goodsSharePostConfig);
        }
        goods.setShareConfig(Util.toJson(goods.getGoodsSharePostConfig()));
    }

    /**
     * 插入商品专属会员卡
     *
     * @param goods
     */
    private void insertMemberCards(Goods goods) {
        if (goods.getIsCardExclusive() == null || goods.getIsCardExclusive() == 0 || goods.getMemberCardIds() == null || goods.getMemberCardIds().size() == 0) {
            return;
        }
        memberCardService.batchUpdateGoods(Arrays.asList(goods.getGoodsId()), goods.getMemberCardIds(), CardConstant.GOODS_TYPE);
    }

    /**
     * 插入商品规格对应的会员卡价格
     *
     * @param goodsGradePrds    商品规格对应会员卡
     * @param goodsSpecProducts 商品规格
     * @param goodsId           商品id
     */
    private void insertGradePrd(List<GoodsGradePrd> goodsGradePrds, List<GoodsSpecProduct> goodsSpecProducts, Integer goodsId) {

        if (goodsGradePrds == null || goodsGradePrds.size() == 0) {
            return;
        }

        Map<String, Integer> collect = goodsSpecProducts.stream().collect(Collectors.toMap(GoodsSpecProduct::getPrdDesc, GoodsSpecProduct::getPrdId));
        DefaultDSLContext db = db();
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
     *
     * @param goodsRebatePrices
     * @param goodsSpecProducts
     * @param goodsId
     */
    private void insertGoodsRebatePrices(List<GoodsRebatePrice> goodsRebatePrices, List<GoodsSpecProduct> goodsSpecProducts, Integer goodsId) {

        if (goodsRebatePrices == null || goodsRebatePrices.size() == 0) {
            return;
        }

        Map<String, Integer> collect = goodsSpecProducts.stream().collect(Collectors.toMap(GoodsSpecProduct::getPrdDesc, GoodsSpecProduct::getPrdId));
        DefaultDSLContext db = db();
        List<GoodsRebatePriceRecord> goodsRebatePriceRecords = goodsRebatePrices.stream().map(goodsRebatePrice -> {
            GoodsRebatePriceRecord goodsRebatePriceRecord = db.newRecord(GOODS_REBATE_PRICE, goodsRebatePrice);
            goodsRebatePriceRecord.setGoodsId(goodsId);
            goodsRebatePriceRecord.setProductId(collect.get(goodsRebatePrice.getPrdDesc()));
            return goodsRebatePriceRecord;
        }).collect(Collectors.toList());

        db.batchInsert(goodsRebatePriceRecords).execute();
    }

    /**
     * 商品图片插入
     *
     * @param goodsImgs 商品图片地址列表
     * @param goodsId   商品id
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
     *
     * @param goodsLabels 标签id列表
     * @param goodsId     商品id
     */
    private void insertGoodsLabels(List<Integer> goodsLabels, Integer goodsId) {

        List<GoodsLabelCouple> list = new ArrayList<>(goodsLabels.size());

        for (Integer labelId : goodsLabels) {
            list.add(new GoodsLabelCouple(null, labelId, goodsId, GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()));
        }
        goodsLabelCouple.batchInsert(list);
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
            BigDecimal smallestMarketPrice = BigDecimal.valueOf(Double.MAX_VALUE);
            BigDecimal smallestCostPrice = BigDecimal.valueOf(Double.MAX_VALUE);

            Integer goodsSumNumber = 0;
            for (GoodsSpecProduct specProduct : goods.getGoodsSpecProducts()) {
                goodsSumNumber += specProduct.getPrdNumber();
                if (smallestGoodsPrice.compareTo(specProduct.getPrdPrice()) > 0) {
                    smallestGoodsPrice = specProduct.getPrdPrice();
                }
                if (smallestMarketPrice.compareTo(specProduct.getPrdMarketPrice()) > 0) {
                    smallestMarketPrice = specProduct.getPrdMarketPrice();
                }
                if (smallestCostPrice.compareTo(specProduct.getPrdCostPrice()) > 0) {
                    smallestCostPrice = specProduct.getPrdCostPrice();
                }
            }
            goods.setGoodsNumber(goodsSumNumber);
            goods.setShopPrice(smallestGoodsPrice);
            goods.setMarketPrice(smallestMarketPrice);
            goods.setCostPrice(smallestCostPrice);
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
        transaction(() -> {
            batchGoodsOperate(operateParam);
            batchLabelOperate(operateParam);
        });
    }

    /**
     * 批量处理中处理商品表
     *
     * @param operateParam
     */
    private void batchGoodsOperate(GoodsBatchOperateParam operateParam) {
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
        transaction(() -> {
           DSLContext db=db();
            db.update(GOODS).set(GOODS.DEL_FLAG, DelFlag.DISABLE.getCode())
                .set(GOODS.GOODS_SN,
                    DSL.concat(DelFlag.DEL_ITEM_PREFIX).concat(GOODS.GOODS_ID).concat(DelFlag.DEL_ITEM_SPLITER)
                        .concat(GOODS.GOODS_SN))
                .set(GOODS.GOODS_NAME, DSL.concat(DelFlag.DEL_ITEM_PREFIX).concat(GOODS.GOODS_ID)
                    .concat(DelFlag.DEL_ITEM_SPLITER).concat(GOODS.GOODS_NAME))
                .where(GOODS.GOODS_ID.in(goodsIds)).execute();

            // 删除关联图片
            deleteImg(goodsIds);
            // 删除关联规格
            goodsSpecProductService.deleteByGoodsIds(goodsIds);
            //删除关联标签
            goodsLabelCouple.deleteByGoodsIds(goodsIds);
            //删除商品规格会员价信息
            deleteGradePrd(goodsIds);
            //删除关联会员专属信息
            memberCardService.deleteOwnEnjoyGoodsByGcta(goodsIds, CardConstant.GOODS_TYPE);
            //删除商品规格分销信息
            deleteGoodsRebatePrices(goodsIds);
        });
    }

    /**
     * 删除关联图片
     *
     * @param goodsIds
     */
    private void deleteImg(List<Integer> goodsIds) {
        db().delete(GOODS_IMG).where(GOODS_IMG.GOODS_ID.in(goodsIds)).execute();
    }

    /**
     * 删除商品规格会员价信息
     *
     * @param goodsIds
     */
    private void deleteGradePrd(List<Integer> goodsIds) {
        db().update(GRADE_PRD).set(GRADE_PRD.DEL_FLAG, DelFlag.DISABLE.getCode()).where(GRADE_PRD.GOODS_ID.in(goodsIds)).execute();
    }

    /**
     * 删除商品规格分销价信息
     *
     * @param goodsIds
     */
    private void deleteGoodsRebatePrices(List<Integer> goodsIds) {
        db().update(GOODS_REBATE_PRICE).set(GOODS_REBATE_PRICE.DEL_FLAG, DelFlag.DISABLE.getCode())
            .where(GOODS_REBATE_PRICE.GOODS_ID.in(goodsIds)).execute();
    }

    /**
     * 商品修改
     *
     * @param goods
     */
    public void update(Goods goods) {
        transaction(() -> {

            updateGoods(goods);

            // 商品图片修改
            updateImgs(goods);

            // 商品关联标签添加
            updateLabels(goods);

            // 修改商品规格
            updateSpecPrd(goods);

            // 修改商品规格会员价
            updateGradePrd(goods.getGoodsGradePrds(),goods.getGoodsSpecProducts(),goods.getGoodsId());

            // 修改专属会员卡
            updateMemberCards(goods);

            //修改分销改价
            updateGoodsRebatePrices(goods.getGoodsRebatePrices(),goods.getGoodsSpecProducts(),goods.getGoodsId());
        });
    }

    /**
     * 修改商品表
     * @param goods {@link com.vpu.mp.service.pojo.shop.goods.goods}
     */
    private void updateGoods(Goods goods) {
        //计算商品的价格和库存量
        calculateGoodsPriceAndNumber(goods);


        if (StringUtils.isBlank(goods.getGoodsSn())) {
            goods.setGoodsSn(Util.randomId());
        }

        //设置商品分享海报配置信息
        setGoodsShareConfig(goods);

        GoodsRecord goodsRecord = db().fetchOne(GOODS, GOODS.GOODS_ID.eq(goods.getGoodsId()));

        assign(goods, goodsRecord);

        goodsRecord.store();
    }

    /**
     * 修改商品规格对应的会员卡价格，由于是一种关联表，每次修改可以先删除旧的然后再新增
     * @param goodsGradePrds    商品规格对应会员卡
     * @param goodsSpecProducts 商品规格
     * @param goodsId           商品id
     */
    private void updateGradePrd(List<GoodsGradePrd> goodsGradePrds, List<GoodsSpecProduct> goodsSpecProducts, Integer goodsId) {
        // 删除旧的项，
        db().update(GRADE_PRD).set(GRADE_PRD.DEL_FLAG,DelFlag.DISABLE.getCode()).where(GRADE_PRD.GOODS_ID.eq(goodsId)).execute();
        insertGradePrd(goodsGradePrds,goodsSpecProducts,goodsId);
    }

    /**
     * 修改商品专属会员卡
     * @param goods
     */
    private void updateMemberCards(Goods goods) {
        //删除关联会员专属信息
        memberCardService.deleteOwnEnjoyGoodsByGcta(Arrays.asList(goods.getGoodsId()), CardConstant.GOODS_TYPE);
        insertMemberCards(goods);
    }

    /**
     * 插入商品分销改价信息
     * @param goodsRebatePrices
     * @param goodsSpecProducts
     * @param goodsId
     */
    private void updateGoodsRebatePrices(List<GoodsRebatePrice> goodsRebatePrices, List<GoodsSpecProduct> goodsSpecProducts, Integer goodsId) {
        db().update(GOODS_REBATE_PRICE)
            .set(GOODS_REBATE_PRICE.DEL_FLAG,DelFlag.DISABLE.getCode()).where(GOODS_REBATE_PRICE.GOODS_ID.eq(goodsId)).execute();
        insertGoodsRebatePrices(goodsRebatePrices,goodsSpecProducts,goodsId);
    }

    /**
     * 修改商品图片
     *
     * @param goods
     */
    private void updateImgs(Goods goods) {
        List<Integer> goodsIds = Arrays.asList(goods.getGoodsId());

        deleteImg(goodsIds);

        if (goods.getGoodsImgs() != null && goods.getGoodsImgs().size() != 0) {
            insertGoodsImgs(goods.getGoodsImgs(), goods.getGoodsId());
        }
    }

    /**
     * 修改商品标签
     * @param goods
     */
    private void updateLabels(Goods goods) {
        List<Integer> goodsIds = Arrays.asList(goods.getGoodsId());

        goodsLabelCouple.deleteByGoodsIds(goodsIds);

        if (goods.getGoodsLabels() != null && goods.getGoodsLabels().size() != 0) {
            insertGoodsLabels(goods.getGoodsLabels(), goods.getGoodsId());
        }
    }

    /**
     * 修改商品sku
     * @param goods 商品项
     */
    private void updateSpecPrd(Goods goods) {
        List<GoodsSpecProduct> oldPrds=filterOldGoodsSpecProduct(goods.getGoodsSpecProducts());

        List<GoodsSpecProduct> newPrds = filterNewGoodsSpecProduct(goods.getGoodsSpecProducts());

        // 用户在修该商品的时候删除了部分规格项则并修改了部分规格项，则需要将无效规格从数据库删除，并更新相应规格项
        goodsSpecProductService.updateAndDeleteForGoodsUpdate(oldPrds,goods.getGoodsSpecs(),goods.getGoodsId());

        // 用户从默认规格改为自定义规格或者新增加了规格值或规格项(可能newPrds是空数组，没有新增规格)
        goodsSpecProductService.insertForUpdate(newPrds,goods.getGoodsSpecs(),goods.getGoodsId());
    }
    /**
     * 根据对象是否存prdId值提取出原有的规格对象（用来执行update操作，可能存在被删除项）
     * @param goodsSpecProducts 前端出入的全部规格对象
     * @return 原有的规格对象
     */
    private List<GoodsSpecProduct> filterOldGoodsSpecProduct(List<GoodsSpecProduct> goodsSpecProducts) {
        List<GoodsSpecProduct> oldPrds = goodsSpecProducts.stream()
            .filter(goodsSpecProduct -> goodsSpecProduct.getPrdId() != null).collect(Collectors.toList());
        return oldPrds;
    }

    /**
     * 根据对象是否存prdId值提取出新的规格对象（用来执行insert操作）
     * @param goodsSpecProducts 前端出入的全部规格对象
     * @return 新的规格对象
     */
    private List<GoodsSpecProduct> filterNewGoodsSpecProduct(List<GoodsSpecProduct> goodsSpecProducts) {
        List<GoodsSpecProduct> newPrds = goodsSpecProducts.stream()
            .filter(goodsSpecProduct -> goodsSpecProduct.getPrdId() == null).collect(Collectors.toList());
        return newPrds;
    }
    /**
     * 查询商品详情
     *
     * @param goodsId 商品id
     * @return 商品信息
     */
    public GoodsVo select(Integer goodsId) {
        GoodsVo goodsVo = db().select()
            .from(GOODS).leftJoin(GOODS_BRAND).on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID))
            .leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID))
            .where(GOODS.GOODS_ID.eq(goodsId)).fetchOne().into(GoodsVo.class);

        if (goodsVo == null) {
            return null;
        }

        //设置主绝对路径图片,都是全路径
        goodsVo.setGoodsImgPath(goodsVo.getGoodsImg());
        goodsVo.setGoodsImg(getImgFullUrlUtil(goodsVo.getGoodsImg()));

        // 设置幅图片
        setGoodsImgs(goodsVo);

        //设置标签
        Map<Integer, List<GoodsLabelListVo>> gtaLabelMap = goodsLabel.getGtaLabelMap(Arrays.asList(goodsId), GoodsLabelCoupleTypeEnum.GOODSTYPE);
        goodsVo.setGoodsLabelListVos(gtaLabelMap.get(goodsId));

        //设置sku
        List<GoodsSpecProduct> goodsSpecProducts = goodsSpecProductService.selectByGoodsId(goodsId);
        goodsSpecProducts.forEach(goodsSpecProduct -> goodsSpecProduct.setPrdImgPath(getImgFullUrlUtil(goodsSpecProduct.getPrdImg())));
        goodsVo.setGoodsSpecProducts(goodsSpecProducts);

        List<GoodsSpec> goodsSpecs = goodsSpecProductService.selectSpecByGoodsId(goodsId);
        goodsVo.setGoodsSpecs(goodsSpecs);

        //设置商品规格会员价
        List<GoodsGradePrd> goodsGradePrds = selectGoodsGradePrd(goodsId);
        goodsVo.setGoodsGradePrds(goodsGradePrds);

        // 设置专属会员卡信息
        List<Integer> cardIds = selectMemberCard(goodsId);
        goodsVo.setMemberCardIds(cardIds);

        //设置模板名称
        XcxCustomerPageRecord pageDecorate = shopMpDecorationService.getPageById(goodsVo.getGoodsPageId());
        if (pageDecorate == null) {
            goodsVo.setGoodsPageName(null);
        } else {
            goodsVo.setGoodsPageName(pageDecorate.getPageName());
        }

        // 设置规格分销信息
        List<GoodsRebatePrice> goodsRebatePrices = selectGoodsRebatePrice(goodsId);
        goodsVo.setGoodsRebatePrices(goodsRebatePrices);

        // 反序列化商品海报分享信息
        GoodsSharePostConfig goodsSharePostConfig = Util.parseJson(goodsVo.getShareConfig(), GoodsSharePostConfig.class);
        goodsSharePostConfig.setShareImgPath(goodsSharePostConfig.getShareImgUrl());
        goodsSharePostConfig.setShareImgUrl(getImgFullUrlUtil(goodsSharePostConfig.getShareImgUrl()));
        goodsVo.setGoodsSharePostConfig(goodsSharePostConfig);

        return goodsVo;
    }

    /**
     * 设置商品副图片
     * @param goods 商品对象
     */
    private void setGoodsImgs(GoodsVo goods) {
        Integer goodsId=goods.getGoodsId();

        List<String> fetch = db().select(GOODS_IMG.IMG_URL).from(GOODS_IMG).where(GOODS_IMG.GOODS_ID.eq(goodsId)).fetch(GOODS_IMG.IMG_URL);
        List<String> goodsImgs = new ArrayList<>(fetch.size());
        List<String> goodsImgsPath=new ArrayList<>(fetch.size());
        // 设置图片绝对路径
        fetch.forEach(item -> {
            goodsImgs.add(getImgFullUrlUtil(item));
            goodsImgsPath.add(getImgFullUrlUtil(item));
        });
        goods.setGoodsImgs(goodsImgs);
        goods.setGoodsImgsPath(goodsImgsPath);
    }

    /**
     * 获取商品规格会员价
     *
     * @param goodsId
     * @return
     */
    private List<GoodsGradePrd> selectGoodsGradePrd(Integer goodsId) {
        List<GoodsGradePrd> goodsGradePrds = db().select().from(GRADE_PRD).where(GRADE_PRD.GOODS_ID.eq(goodsId))
            .and(GRADE_PRD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchInto(GoodsGradePrd.class);
        return goodsGradePrds;
    }

    /**
     * 获取商品专享会员卡
     *
     * @param goodsId 商品id
     * @return 会员卡ids
     */
    private List<Integer> selectMemberCard(Integer goodsId) {
        return memberCardService.selectOwnEnjoyCardByGcta(goodsId, CardConstant.GOODS_TYPE);
    }

    /**
     * 获取商品规格分销信息
     *
     * @param goodsId 商品id
     * @return 商品规格分销集合
     */
    private List<GoodsRebatePrice> selectGoodsRebatePrice(Integer goodsId) {
        List<GoodsRebatePrice> goodsRebatePrices = db().select().from(GOODS_REBATE_PRICE).where(GOODS_REBATE_PRICE.GOODS_ID.eq(goodsId))
            .and(GOODS_REBATE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchInto(GoodsRebatePrice.class);

        return goodsRebatePrices;
    }

    /**
     * 通过商品id数组查询商品
     */
    public Map<Integer, GoodsRecord> getGoodsByIds(List<Integer> goodsIds) {
        return db().selectFrom(GOODS).where(GOODS.GOODS_ID.in(goodsIds)).
            fetchMap(GOODS.GOODS_ID);
    }

    /**
     * 获取商品小程序展示页面
     *
     * @param goodsId 商品id
     * @return GoodsQrCodeVo
     */
    public GoodsQrCodeVo getGoodsQrCode(Integer goodsId) {
        String paramName = "goods_id=";
        String urlParam = paramName + goodsId;
        String mpQrCode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, urlParam);
        GoodsQrCodeVo goodsQrCodeVo = new GoodsQrCodeVo();
        goodsQrCodeVo.setImgFullUrl(mpQrCode);
        goodsQrCodeVo.setPageUrl(QrCodeTypeEnum.GOODS_ITEM.getPathUrl(urlParam));
        return goodsQrCodeVo;
    }

    /**
     * 将相对路劲修改为全路径
     *
     * @param relativePath 相对路径
     * @return null或全路径
     */
    private String getImgFullUrlUtil(String relativePath) {
        if (StringUtils.isBlank(relativePath)) {
            return null;
        } else {
            return imageService.imageUrl(relativePath);
        }
    }
}
