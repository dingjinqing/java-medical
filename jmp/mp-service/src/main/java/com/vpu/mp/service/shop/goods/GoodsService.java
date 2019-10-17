package com.vpu.mp.service.shop.goods;

import com.vpu.mp.config.UpYunConfig;
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
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.decoration.ChooseLinkService;
import com.vpu.mp.service.shop.decoration.ShopMpDecorationService;
import com.vpu.mp.service.shop.goods.mp.GoodsMpService;
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
import java.util.*;
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
    protected UpYunConfig upYunConfig;
    @Autowired
    protected ShopMpDecorationService shopMpDecorationService;
    @Autowired
    public GoodsPriceService goodsPrice;

    /**
     * 全部商品页面各个下拉框的数据初始化
     * @return {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsInitialVo}
     */
    public GoodsInitialVo pageInitValue(GoodsPageListParam goodsPageListParam) {
        GoodsInitialVo goodsInitialVo = new GoodsInitialVo();

        goodsInitialVo.setGoodsBrands(goodsBrand.listGoodsBrandName());

        goodsInitialVo.setGoodsLabels(goodsLabel.listGoodsLabelName());

        Condition condition = this.buildOptions(goodsPageListParam);

        goodsInitialVo.setGoodsSorts(goodsSort.getListBindedGoods(condition,goodsPageListParam.getSelectType()));

        Map<Integer,Integer> goodsNumberMap = this.getSysCateNumberMap(condition,goodsPageListParam.getSelectType());
        // 此处商品表里cat字段为int类型而category表里该字段为smallint类型，在这里做转换处理
        List<Integer> catIds = new ArrayList<>(goodsNumberMap.keySet());

        goodsInitialVo.setSysCates(saas.sysCate.getList(catIds, goodsNumberMap));

        return goodsInitialVo;
    }

    /**
     *  处理平台分类对应的已有商品或规格数量，由于该表在主库内所以需要在此预处理
     * @param condition 过滤已拼接条件
     * @param selectType 查询类型
     * @return 平台分类id和数据量对应表
     */
    private Map<Integer, Integer> getSysCateNumberMap(Condition condition, Integer selectType) {
        String goodsNumberFiledName = "goods_number";
        if (GoodsPageListParam.GOODS_LIST.equals(selectType)) {
            return db().select(GOODS.CAT_ID, DSL.count().as(goodsNumberFiledName))
                .from(GOODS).where(condition)
                .groupBy(GOODS.CAT_ID).fetch().intoMap(GOODS.CAT_ID, DSL.field(goodsNumberFiledName, Integer.class));
        } else {
            return db().select(GOODS.CAT_ID, DSL.count().as(goodsNumberFiledName))
                .from(GOODS).innerJoin(GOODS_SPEC_PRODUCT).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID)).where(condition)
                .groupBy(GOODS.CAT_ID).fetch().intoMap(GOODS.CAT_ID, DSL.field(goodsNumberFiledName, Integer.class));
        }
    }

    /**
     * 获取商家，标签，品牌list全数据
     * 该方法不需要进行特殊处理，仅需查询出平台、商家下所有的分类，标签，品牌即可。
     * @return {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsInitialVo}
     */
    public GoodsInitialVo getSortBrandLabelList() {
        GoodsInitialVo goodsInitialVo = new GoodsInitialVo();

        goodsInitialVo.setGoodsBrands(goodsBrand.listGoodsBrandName());

        goodsInitialVo.setGoodsLabels(goodsLabel.listGoodsLabelName());

        goodsInitialVo.setGoodsSorts(goodsSort.getList(new GoodsSortListParam()));

        return goodsInitialVo;
    }

    /**
     * 全部商品界面：商品分页查询，包含了部分规格信息（规格价格范围，规格类型数量）
     * @param goodsPageListParam {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam}
     * @return {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo}
     */
    public PageResult<GoodsPageListVo> getPageList(GoodsPageListParam goodsPageListParam) {
        // 拼接过滤条件
        Condition condition = this.buildOptions(goodsPageListParam);

        SelectConditionStep<?> selectFrom = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_SN, GOODS.SHOP_PRICE,
            GOODS.SOURCE, GOODS.GOODS_TYPE, GOODS.CAT_ID, SORT.SORT_NAME, GOODS.SORT_ID, GOODS_BRAND.BRAND_NAME, GOODS.GOODS_NUMBER, GOODS.GOODS_SALE_NUM)
            .from(GOODS).leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID)).leftJoin(GOODS_BRAND)
            .on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID)).where(condition);

        // 拼接排序
        selectFrom = this.buildOrderFields(selectFrom, goodsPageListParam);

        PageResult<GoodsPageListVo> pageResult = this.getPageResult(selectFrom, goodsPageListParam.getCurrentPage(),
            goodsPageListParam.getPageRows(), GoodsPageListVo.class);

        // 结果集标签、平台分类、规格信息二次处理
        this.disposeGoodsPageListVo(pageResult.getDataList(),goodsPageListParam);

        return pageResult;
    }

    /**
     *  获取所有符合条件的商品id集合
     * @param goodsPageListParam {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam}
     * @return 商品id结合
     */
    public List<Integer> getGoodsIdsListAll(GoodsPageListParam goodsPageListParam) {
        // 拼接过滤条件
        Condition condition = this.buildOptions(goodsPageListParam);

        List<Integer> goodsIds = db().select(GOODS.GOODS_ID)
            .from(GOODS).leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID)).leftJoin(GOODS_BRAND)
            .on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID)).where(condition).fetch(GOODS.GOODS_ID);

        return goodsIds;
    }

    /**
     *  根据商品id集合获取对应的商品信息和规格信息
     * @param goodsIds
     * @return GoodsPageListVo
     */
    public List<GoodsPageListVo> getGoodsAndProductsByGoodsIds(List<Integer> goodsIds) {
        List<GoodsPageListVo> goodsPageListVos = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_SN, GOODS.SHOP_PRICE,
            GOODS.SOURCE, GOODS.GOODS_TYPE, GOODS.CAT_ID, SORT.SORT_NAME, GOODS.SORT_ID, GOODS_BRAND.BRAND_NAME, GOODS.GOODS_NUMBER, GOODS.GOODS_SALE_NUM)
            .from(GOODS).leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID)).leftJoin(GOODS_BRAND)
            .on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID)).where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GOODS.GOODS_ID.in(goodsIds))
            .fetchInto(GoodsPageListVo.class);

        GoodsPageListParam pageListParam=new GoodsPageListParam();
        pageListParam.setSelectType(GoodsPageListParam.GOODS_LIST_WITH_PRD);

        this.disposeGoodsPageListVo(goodsPageListVos,pageListParam);

        return goodsPageListVos;
    }
    /**
     * 商品（规格）分页查询，每条记录包含了商品部分信息和必要的规格信息
     * @param goodsPageListParam {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam}
     * @return {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo}
     */
    public PageResult<GoodsPageListVo> getProductPageList(GoodsPageListParam goodsPageListParam) {
        // 拼接过滤条件
        Condition condition = this.buildOptions(goodsPageListParam);

        SelectConditionStep<?> selectFrom = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG, GOODS.GOODS_SN, GOODS.SHOP_PRICE,
            GOODS.SOURCE, GOODS.GOODS_TYPE,GOODS.CAT_ID, SORT.SORT_NAME, GOODS.SORT_ID, GOODS_BRAND.BRAND_NAME,
            GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_DESC, GOODS_SPEC_PRODUCT.PRD_PRICE,
            GOODS_SPEC_PRODUCT.PRD_NUMBER, GOODS_SPEC_PRODUCT.PRD_SN,
            GOODS_SPEC_PRODUCT.PRD_IMG)
            .from(GOODS).leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID)).leftJoin(GOODS_BRAND)
            .on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID)).innerJoin(GOODS_SPEC_PRODUCT).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID))
            .where(condition);

        // 拼接排序
        selectFrom = this.buildOrderFields(selectFrom, goodsPageListParam);

        PageResult<GoodsPageListVo> pageResult = this.getPageResult(selectFrom, goodsPageListParam.getCurrentPage(),
            goodsPageListParam.getPageRows(), GoodsPageListVo.class);

        // 结果集标签、平台分类、规格信息二次处理
        this.disposeGoodsPageListVo(pageResult.getDataList(),goodsPageListParam);

        return pageResult;
    }

    /**
     * 获取符合条件的全部商品规格id集合
     * @param goodsPageListParam {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam}
     * @return 规格id集合
     */
    public List<Integer> getProductIdsListAll(GoodsPageListParam goodsPageListParam) {
        // 拼接过滤条件
        Condition condition = this.buildOptions(goodsPageListParam);

        List<Integer> prdIds = db().select(GOODS_SPEC_PRODUCT.PRD_ID)
            .from(GOODS).leftJoin(SORT).on(GOODS.SORT_ID.eq(SORT.SORT_ID)).leftJoin(GOODS_BRAND)
            .on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID)).innerJoin(GOODS_SPEC_PRODUCT).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID))
            .where(condition).fetch(GOODS_SPEC_PRODUCT.PRD_ID);

        return prdIds;
    }

    /**
     * 分页条件拼凑
     * @param goodsPageListParam 过滤条件
     * @return where 过滤结果对象
     */
    private Condition buildOptions(GoodsPageListParam goodsPageListParam) {
        Condition condition = DSL.noCondition();
        condition = condition.and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        // 处理在售状态
        condition = this.buildIsOnSaleOptions(condition,goodsPageListParam);

        if (!StringUtils.isBlank(goodsPageListParam.getGoodsName())) {
            condition = condition.and(GOODS.GOODS_NAME.like(likeValue(goodsPageListParam.getGoodsName())));
        }
        if (!StringUtils.isBlank(goodsPageListParam.getGoodsSn())) {
            condition = condition.and(GOODS.GOODS_SN.like(likeValue(goodsPageListParam.getGoodsSn())));
        }
        if (goodsPageListParam.getBrandId() != null) {
            condition = condition.and(GOODS.BRAND_ID.eq(goodsPageListParam.getBrandId()));
        }
        if (goodsPageListParam.getSource() != null) {
            condition = condition.and(GOODS.SOURCE.eq(goodsPageListParam.getSource()));
        }
        if (goodsPageListParam.getGoodsType() != null) {
            condition = condition.and(GOODS.GOODS_TYPE.eq(goodsPageListParam.getGoodsType()));
        }
        // 处理平台和商家分类
        condition = this.buildSortCatOpitons(condition, goodsPageListParam);
        // 处理标签分类
        condition = this.buildLabelOptions(condition, goodsPageListParam);

        if (goodsPageListParam.getSaleTimeStart() != null) {
            condition = condition.and(GOODS.SALE_TIME.ge(goodsPageListParam.getSaleTimeStart()));
        }
        if (goodsPageListParam.getSaleTimeEnd() != null) {
            condition = condition.and(GOODS.SALE_TIME.le(goodsPageListParam.getSaleTimeEnd()));
        }
        // 处理价格过滤方式
        condition = this.buildShopPriceOptions(condition, goodsPageListParam);

        return condition;
    }

    /**
     * 处理商品在售（上下架）状态
     * 出售中和已售罄都可以为上架状态（isOnSale = 1），而仓库中表示下架（isOnSale = 0）
     * 出售中：isOnSale=1&&goodsNumber!=0,已售罄：isOnSale=1&&goodsNumber==0
     * 仓库中：isOnSale=0
     * @param condition   已有过滤条件
     * @param goodsPageListParam goodsPageListParam 过滤条件
     * @return condition 拼装后的过滤条件
     */
    private Condition buildIsOnSaleOptions(Condition condition,GoodsPageListParam goodsPageListParam) {
        // 查询在售（包含售罄商品和规格）
        if (GoodsPageListParam.IS_ON_SALE_DEFAULT.equals(goodsPageListParam.getIsOnSale())) {
            condition = condition.and(GOODS.IS_ON_SALE.eq(GoodsPageListParam.IS_ON_SALE_DEFAULT));

            // 查询商品列表的售罄
            if (Boolean.TRUE.equals(goodsPageListParam.getIsSaleOut()) && GoodsPageListParam.GOODS_LIST.equals(goodsPageListParam.getSelectType())) {
                condition = condition.and(GOODS.GOODS_NUMBER.eq(0));
            }
            // 查询商品列表的未售罄
            if (Boolean.FALSE.equals(goodsPageListParam.getIsSaleOut()) && GoodsPageListParam.GOODS_LIST.equals(goodsPageListParam.getSelectType())) {
                condition = condition.and(GOODS.GOODS_NUMBER.ne(0));
            }
            // 查询规格列表的售罄
            if (Boolean.TRUE.equals(goodsPageListParam.getIsSaleOut())&&GoodsPageListParam.GOODS_PRD_LIST.equals(goodsPageListParam.getSelectType())) {
                condition = condition.and(GOODS_SPEC_PRODUCT.PRD_NUMBER.eq(0));
            }
            // 查询规格列表的未售罄
            if (Boolean.FALSE.equals(goodsPageListParam.getIsSaleOut())&&GoodsPageListParam.GOODS_PRD_LIST.equals(goodsPageListParam.getSelectType())){
                condition = condition.and(GOODS_SPEC_PRODUCT.PRD_NUMBER.ne(0));
            }
        }
        // 查询仓库中（下架）
        if (GoodsPageListParam.NOT_ON_SALE.equals(goodsPageListParam.getIsOnSale())) {
            condition = condition.and(GOODS.IS_ON_SALE.eq(GoodsPageListParam.NOT_ON_SALE));
        }
        return condition;
    }

    /**
     * 处理平台分类和商家分类过滤条件
     * @param condition   已有过滤条件
     * @param goodsPageListParam goodsPageListParam 过滤条件
     * @return condition 拼装后的过滤条件
     */
    private Condition buildSortCatOpitons(Condition condition, GoodsPageListParam goodsPageListParam) {
        // 平台分类，首先需要根据当前平台分类id查询出所有的子孙节点
        if (goodsPageListParam.getCatId() != null) {
            List<Integer> catIds = new ArrayList<>();
            catIds.add(goodsPageListParam.getCatId());
            List<Integer> childrenId = saas.sysCate.findChildrenByParentId(catIds);
            condition = condition.and(GOODS.CAT_ID.in(childrenId));
        }
        // 商家分类
        if (goodsPageListParam.getSortId() != null) {
            List<Integer> childrenId = goodsSort.findChildrenByParentId(goodsPageListParam.getSortId());
            condition = condition.and(GOODS.SORT_ID.in(childrenId));
        }
        return condition;
    }

    /**
     * 处理标签过滤条件
     * @param condition          已有过滤条件
     * @param goodsPageListParam goodsPageListParam 滤条件
     * @return condition 拼装后的过滤条件
     */
    private Condition buildLabelOptions(Condition condition, GoodsPageListParam goodsPageListParam) {
        if (goodsPageListParam.getLabelId() != null) {
            // 根据标签id值过滤出该标签对应的商品id或者平台、商家分类id
            Map<Byte, List<Integer>> byteListMap = goodsLabelCouple.selectGatIdsByLabelIds(Arrays.asList(goodsPageListParam.getLabelId()));
            Condition labelCondition = DSL.noCondition();
            // 处理标签直接打在商品上的情况
            List<Integer> integers = byteListMap.get(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode());
            if (integers != null && integers.size() > 0) {
                labelCondition = labelCondition.or(GOODS.GOODS_ID.in(integers));
            }
            // 处理标签打在商家分了上
            integers = byteListMap.get(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode());
            if (integers != null && integers.size() > 0) {
                List<Integer> sortChildrenId = goodsSort.findChildrenByParentId(integers);
                labelCondition = labelCondition.or(GOODS.SORT_ID.in(sortChildrenId));
            }
            // 处理标签打在平台分类上
            integers = byteListMap.get(GoodsLabelCoupleTypeEnum.CATTYPE.getCode());
            if (integers != null && integers.size() > 0) {
                List<Integer> catChildrenId = saas.sysCate.findChildrenByParentId(integers);
                labelCondition = labelCondition.or(GOODS.CAT_ID.in(catChildrenId));
            }
            condition = condition.and(labelCondition);
        }
        return condition;
    }

    /**
     * 处理价格过滤条件
     * 当{@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam#getSelectType()} 为2时价格按照规格价格进行筛选，否则按照商品价格进行筛选
     * @param condition          已有过滤条件
     * @param goodsPageListParam goodsPageListParam 带过滤条件的待执行语句
     * @return condition 拼装后的过滤条件
     */
    private Condition buildShopPriceOptions(Condition condition, GoodsPageListParam goodsPageListParam) {
        if (GoodsPageListParam.GOODS_PRD_LIST.equals(goodsPageListParam.getSelectType()) ) {
            if (goodsPageListParam.getLowShopPrice() != null) {
                condition = condition.and(GOODS_SPEC_PRODUCT.PRD_PRICE.ge(goodsPageListParam.getLowShopPrice()));
            }
            if (goodsPageListParam.getHighShopPrice() != null) {
                condition = condition.and(GOODS_SPEC_PRODUCT.PRD_PRICE.le(goodsPageListParam.getHighShopPrice()));
            }
        }else {
            if (goodsPageListParam.getLowShopPrice() != null) {
                condition = condition.and(GOODS.SHOP_PRICE.ge(goodsPageListParam.getLowShopPrice()));
            }
            if (goodsPageListParam.getHighShopPrice() != null) {
                condition = condition.and(GOODS.SHOP_PRICE.le(goodsPageListParam.getHighShopPrice()));
            }
        }
        return condition;
    }

    /**
     * 排序条件过滤
     * @param scs 待排序语句
     * @param goodsPageListParam
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
     * 处理商品或规格分页查询结果，设置对应的标签、平台分类、商品规格信息等
     * @param dataList 分页结果集
     * @param pageListParam 数据筛选条件
     */
    private void disposeGoodsPageListVo( List<GoodsPageListVo> dataList, GoodsPageListParam pageListParam) {

        // 处理商品平台分类：通过id值获取name值
        saas.sysCate.disposeCategoryName(dataList);

        // 处理标签名称准备数据
        List<Integer> goodsIds = new ArrayList<>(dataList.size());

        dataList.forEach(item -> goodsIds.add(item.getGoodsId()));
        Map<Integer, List<GoodsLabelListVo>> goodsLabels = this.getGoodsLabels(goodsIds);

        // 获取商品对应的规格集合数据
        Map<Integer, List<GoodsSpecProduct>> goodsIdPrdGroups = goodsSpecProductService.selectGoodsSpecPrdGroup(goodsIds);

        dataList.forEach(item -> {
            // 设置标签名称
            item.setGoodsLabels(goodsLabels.get(item.getGoodsId()) == null ? new ArrayList<>() : goodsLabels.get(item.getGoodsId()));
            // 设置图片绝对地址
            item.setGoodsImg(getImgFullUrlUtil(item.getGoodsImg()));
            // 处理商品对应的规格数据,由于分页问题导致无法连表查询
            List<GoodsSpecProduct> goodsSpecProducts = goodsIdPrdGroups.get(item.getGoodsId());

            this.disposeGoodsSpecProductsInfo(item, goodsSpecProducts,pageListParam);
        });
    }

    /**
     * 为商品设置相应的规格信息
     * @param goods             {@link com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo}
     * @param goodsSpecProducts 商品对应的{@link com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct}集合
     */
    private void disposeGoodsSpecProductsInfo(GoodsPageListVo goods, List<GoodsSpecProduct> goodsSpecProducts,GoodsPageListParam pageListParam) {
        // 这种情况一般是手动修改了数据库数据但是存在错误
        if (goodsSpecProducts == null || goodsSpecProducts.size() == 0) {
            return;
        }
        // 默认规格
        if (goodsSpecProducts.size() == 1 && "".equals(goodsSpecProducts.get(0).getPrdSpecs())) {
            GoodsSpecProduct prd = goodsSpecProducts.get(0);
            goods.setPrdId(prd.getPrdId());
            goods.setPrdMaxShopPrice(prd.getPrdPrice());
            goods.setPrdMinShopPrice(prd.getPrdPrice());
            goods.setPrdTypeNum(0);
            goods.setIsDefaultPrd(true);
        } else {
            BigDecimal maxPrice = BigDecimal.valueOf(-1);
            BigDecimal minPrice = BigDecimal.valueOf(Double.MAX_VALUE);
            for (GoodsSpecProduct goodsSpecProduct : goodsSpecProducts) {
                if (maxPrice.compareTo(goodsSpecProduct.getPrdPrice()) < 0) {
                    maxPrice = goodsSpecProduct.getPrdPrice();
                }
                if (minPrice.compareTo(goodsSpecProduct.getPrdPrice()) > 0) {
                    minPrice = goodsSpecProduct.getPrdPrice();
                }
            }
            goods.setPrdMaxShopPrice(maxPrice);
            goods.setPrdMinShopPrice(minPrice);
            goods.setPrdTypeNum(goodsSpecProducts.size());
            goods.setIsDefaultPrd(false);

            if (GoodsPageListParam.GOODS_LIST_WITH_PRD.equals(pageListParam.getSelectType())) {
                goods.setGoodsSpecProducts(goodsSpecProducts);
            }
        }
    }

    /**
     * 获取商品的关联的标签
     * @param goodsIds 商品ids
     */
    private Map<Integer, List<GoodsLabelListVo>> getGoodsLabels(List<Integer> goodsIds) {
        return goodsLabel.getGtaLabelMap(goodsIds, GoodsLabelCoupleTypeEnum.GOODSTYPE);
    }

    /**
     * 规格
     * @param goodsId 商品ID
     * @return GoodsProductVo
     */
    public List<GoodsProductVo> getAllProductListByGoodsId(Integer goodsId) {
        return goodsSpecProductService.getAllProductListByGoodsId(goodsId).into(GoodsProductVo.class);
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
     *
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
     * 批量处理中会同时修改同一商品的全部规格数据，单独修改某一规格库存和价格时不可使用本方法
     *
     * @param operateParam
     */
    private void batchGoodsOperate(GoodsBatchOperateParam operateParam) {
        List<GoodsRecord> goodsRecords = operateParam.toUpdateGoodsRecord();

        Map<Integer, List<PrdPriceNumberParam>> goodsPriceNumbers = operateParam.getGoodsPriceNumbers();
        List<GoodsSpecProductRecord> goodsSpecProductRecords = new ArrayList<>(0);
        // 单独处理规格价格和规格数量,
        if (goodsPriceNumbers != null && goodsPriceNumbers.size() > 0) {
            goodsRecords.forEach(goodsRecord -> {
                Integer goodsId = goodsRecord.getGoodsId();
                List<PrdPriceNumberParam> prdPriceNumberParams = goodsPriceNumbers.get(goodsId);
                if (prdPriceNumberParams == null || prdPriceNumberParams.size() == 0) {
                    return;
                }
                BigDecimal smallestShopPrice = BigDecimal.valueOf(Double.MAX_VALUE);
                Integer goodsNumberSum = 0;
                for (PrdPriceNumberParam prdPriceNumberParam : prdPriceNumberParams) {
                    GoodsSpecProductRecord record = new GoodsSpecProductRecord();
                    record.setPrdId(prdPriceNumberParam.getPrdId());
                    if (prdPriceNumberParam.getShopPrice() != null) {
                        record.setPrdPrice(prdPriceNumberParam.getShopPrice());
                        if (smallestShopPrice.compareTo(prdPriceNumberParam.getShopPrice()) > 0) {
                            smallestShopPrice = prdPriceNumberParam.getShopPrice();
                            goodsRecord.setShopPrice(smallestShopPrice);
                        }
                    }
                    if (prdPriceNumberParam.getGoodsNumber() != null) {
                        record.setPrdNumber(prdPriceNumberParam.getGoodsNumber());
                        goodsNumberSum += prdPriceNumberParam.getGoodsNumber();
                        goodsRecord.setGoodsNumber(goodsNumberSum);
                    }
                    goodsSpecProductRecords.add(record);
                }
            });
        }
        db().batchUpdate(goodsSpecProductRecords).execute();
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
     * 单独修改某一个商品规格的数量或价格
     *
     * @param operateParam
     */
    public void updateGoodsPrdPriceNumbers(GoodsBatchOperateParam operateParam) {
        Map<Integer, List<PrdPriceNumberParam>> goodsPriceNumbers = operateParam.getGoodsPriceNumbers();
        // 待修改商品和规格集合
        List<GoodsRecord> goodsRecordsForUpdate = new ArrayList<>(goodsPriceNumbers.size());
        List<GoodsSpecProductRecord> specProductRecordsForUpdate = new ArrayList<>(goodsPriceNumbers.size());

        for (Map.Entry<Integer, List<PrdPriceNumberParam>> entry : goodsPriceNumbers.entrySet()) {
            Integer goodsId = entry.getKey();
            List<PrdPriceNumberParam> prdPriceNumberParams = entry.getValue();

            Map<Integer, GoodsSpecProductRecord> specIdMap = goodsSpecProductService.selectSpecPrdIdMap(goodsId);
            // 为需要修改的规格项设置规格价格和规格数量
            for (PrdPriceNumberParam prdPriceNumberParam : prdPriceNumberParams) {
                GoodsSpecProductRecord record = specIdMap.get(prdPriceNumberParam.getPrdId());
                if (prdPriceNumberParam.getShopPrice() != null) {
                    record.setPrdPrice(prdPriceNumberParam.getShopPrice());
                }

                if (prdPriceNumberParam.getGoodsNumber() != null) {
                    record.setPrdNumber(prdPriceNumberParam.getGoodsNumber());
                }
                specProductRecordsForUpdate.add(record);
            }
            // 重新计算商品的最低商品价格和总数量
            Integer goodsNumberSum = 0;
            BigDecimal shopPrice = BigDecimal.valueOf(Double.MAX_VALUE);
            for (GoodsSpecProductRecord specProductRecord : specIdMap.values()) {
                goodsNumberSum += specProductRecord.getPrdNumber();
                if (shopPrice.compareTo(specProductRecord.getPrdPrice()) > 0) {
                    shopPrice = specProductRecord.getPrdPrice();
                }
            }
            GoodsRecord goodsRecord = new GoodsRecord();
            goodsRecord.setGoodsId(goodsId);
            goodsRecord.setGoodsNumber(goodsNumberSum);
            goodsRecord.setShopPrice(shopPrice);
            goodsRecordsForUpdate.add(goodsRecord);
        }
        transaction(() -> {
            db().batchUpdate(goodsRecordsForUpdate).execute();
            db().batchUpdate(specProductRecordsForUpdate).execute();
        });
    }

    /**
     * 商品批量删除及单个删除
     *
     * @param operateParam
     */
    public void delete(GoodsBatchOperateParam operateParam) {
        List<Integer> goodsIds = operateParam.getGoodsIds();
        transaction(() -> {
            DSLContext db = db();
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
            updateGradePrd(goods.getGoodsGradePrds(), goods.getGoodsSpecProducts(), goods.getGoodsId());

            // 修改专属会员卡
            updateMemberCards(goods);

            //修改分销改价
            updateGoodsRebatePrices(goods.getGoodsRebatePrices(), goods.getGoodsSpecProducts(), goods.getGoodsId());
        });
    }

    /**
     * 修改商品表
     *
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
     *
     * @param goodsGradePrds    商品规格对应会员卡
     * @param goodsSpecProducts 商品规格
     * @param goodsId           商品id
     */
    private void updateGradePrd(List<GoodsGradePrd> goodsGradePrds, List<GoodsSpecProduct> goodsSpecProducts, Integer goodsId) {
        // 删除旧的项，
        db().update(GRADE_PRD).set(GRADE_PRD.DEL_FLAG, DelFlag.DISABLE.getCode()).where(GRADE_PRD.GOODS_ID.eq(goodsId)).execute();
        insertGradePrd(goodsGradePrds, goodsSpecProducts, goodsId);
    }

    /**
     * 修改商品专属会员卡
     *
     * @param goods
     */
    private void updateMemberCards(Goods goods) {
        //删除关联会员专属信息
        memberCardService.deleteOwnEnjoyGoodsByGcta(Arrays.asList(goods.getGoodsId()), CardConstant.GOODS_TYPE);
        insertMemberCards(goods);
    }

    /**
     * 插入商品分销改价信息
     *
     * @param goodsRebatePrices
     * @param goodsSpecProducts
     * @param goodsId
     */
    private void updateGoodsRebatePrices(List<GoodsRebatePrice> goodsRebatePrices, List<GoodsSpecProduct> goodsSpecProducts, Integer goodsId) {
        db().update(GOODS_REBATE_PRICE)
            .set(GOODS_REBATE_PRICE.DEL_FLAG, DelFlag.DISABLE.getCode()).where(GOODS_REBATE_PRICE.GOODS_ID.eq(goodsId)).execute();
        insertGoodsRebatePrices(goodsRebatePrices, goodsSpecProducts, goodsId);
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
     *
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
     *
     * @param goods 商品项
     */
    private void updateSpecPrd(Goods goods) {
        List<GoodsSpecProduct> oldPrds = filterOldGoodsSpecProduct(goods.getGoodsSpecProducts());

        List<GoodsSpecProduct> newPrds = filterNewGoodsSpecProduct(goods.getGoodsSpecProducts());

        // 用户在修该商品的时候删除了部分规格项则并修改了部分规格项，则需要将无效规格从数据库删除，并更新相应规格项
        goodsSpecProductService.updateAndDeleteForGoodsUpdate(oldPrds, goods.getGoodsSpecs(), goods.getGoodsId());

        // 用户从默认规格改为自定义规格或者新增加了规格值或规格项(可能newPrds是空数组，没有新增规格)
        goodsSpecProductService.insertForUpdate(newPrds, goods.getGoodsSpecs(), goods.getGoodsId());
    }

    /**
     * 根据对象是否存prdId值提取出原有的规格对象（用来执行update操作，可能存在被删除项）
     *
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
     *
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

        // 设置商品视频全路径信息
        goodsVo.setGoodsVideoUrl(getVideoFullUrlUtil(goodsVo.getGoodsVideo(),true));
        goodsVo.setGoodsVideoImgUrl(getVideoFullUrlUtil(goodsVo.getGoodsVideoImg(),false));

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
     *
     * @param goods 商品对象
     */
    private void setGoodsImgs(GoodsVo goods) {
        Integer goodsId = goods.getGoodsId();

        List<String> fetch = db().select(GOODS_IMG.IMG_URL).from(GOODS_IMG).where(GOODS_IMG.GOODS_ID.eq(goodsId)).fetch(GOODS_IMG.IMG_URL);
        List<String> goodsImgs = new ArrayList<>(fetch.size());
        List<String> goodsImgsPath = new ArrayList<>(fetch.size());
        // 设置图片绝对路径
        fetch.forEach(item -> {
            goodsImgs.add(getImgFullUrlUtil(item));
            goodsImgsPath.add(item);
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
    public List<GoodsGradePrd> selectGoodsGradePrd(Integer goodsId) {
        return db().select().from(GRADE_PRD).where(GRADE_PRD.GOODS_ID.eq(goodsId))
            .and(GRADE_PRD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchInto(GoodsGradePrd.class);
    }
    /**
     * 批量获取商品规格会员价
     *
     * @param goodsIds
     * @return
     */
    public Map<Integer,List<GoodsGradePrd>> selectGoodsGradePrdByGoodsIds(List<Integer> goodsIds) {
        return db().select().from(GRADE_PRD).where(GRADE_PRD.GOODS_ID.in(goodsIds))
            .and(GRADE_PRD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchInto(GoodsGradePrd.class)
            .stream().collect(Collectors.groupingBy(GoodsGradePrd::getGoodsId));
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
     *  通过商品id 和指定列查询商品
     * @param goodsIds ids
     * @param fields 列
     * @return
     */
    public Result<Record> getGoodsByIdsAndFields(List<Integer> goodsIds, SelectFieldOrAsterisk... fields){
        return db().select(fields).from(GOODS).where(GOODS.GOODS_ID.in(goodsIds)).fetch();
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

    /**
     *  商品视频和快照图片相对路径转换全路径
     * @param relativePath 相对路径
     * @param videoOrSnapShop true: 视频，false: 快照
     * @return 全路径
     */
    private String getVideoFullUrlUtil(String relativePath,boolean videoOrSnapShop){
        if (StringUtils.isBlank(relativePath)) {
            return null;
        } else {
            return videoOrSnapShop ? upYunConfig.videoUrl(relativePath) : upYunConfig.imageUrl(relativePath);
        }
    }

    /**
     * 通过商品id查询商品
     */
    public Optional<GoodsRecord> getGoodsById(Integer goodsId) {
        return db().selectFrom(GOODS).where(GOODS.GOODS_ID.eq(goodsId)).
            fetchOptional();
    }

    /**
     * 通过商品id查询商品重量
     */
    public BigDecimal getGoodsWeightById(Integer goodsId) {
        return db().select(GOODS.GOODS_WEIGHT).from(GOODS).where(GOODS.GOODS_ID.eq(goodsId).and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))).fetchOne().into(BigDecimal.class);
    }

    /**
     *  根据规格ID查寻商品信息
     * @param productId
     * @return
     */
    public Record getGoodsByProductId(Integer productId){
        return  db().select().from(GOODS).leftJoin(GOODS_SPEC_PRODUCT).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID)).where(GOODS_SPEC_PRODUCT.PRD_ID.eq(productId)).fetchOne();
    }
}
