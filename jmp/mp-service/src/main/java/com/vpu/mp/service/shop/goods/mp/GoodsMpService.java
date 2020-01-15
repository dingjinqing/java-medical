package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.config.UpYunConfig;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.gift.GoodsGiftPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.search.*;
import com.vpu.mp.service.shop.activity.factory.GoodsDetailMpProcessorFactory;
import com.vpu.mp.service.shop.activity.factory.GoodsListMpProcessorFactory;
import com.vpu.mp.service.shop.activity.factory.ProcessorFactoryBuilder;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.goods.es.EsGoodsSearchMpService;
import com.vpu.mp.service.shop.goods.es.EsUtilSearchService;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabelSearchService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.foundation.data.BaseConstant.GOODS_AREA_TYPE_SECTION;

/**
 * @author 李晓冰
 * @date 2019年10月12日
 * 小程序和装修内商品相关
 */
@Service
@Slf4j
public class GoodsMpService extends ShopBaseService {

    @Autowired
    public GoodsBrandSortMpService goodsBrandSortMp;

    @Autowired
    ImageService imageService;
    @Autowired
    ConfigService configService;
    @Autowired
    GoodsLabelMpService goodsLabelMpService;

    @Autowired
    ProcessorFactoryBuilder processorFactoryBuilder;

    @Autowired
    EsGoodsSearchMpService esGoodsSearchMpService;
    @Autowired
    EsGoodsLabelSearchService esGoodsLabelSearchService;
    @Autowired
    private EsUtilSearchService esUtilSearchService;
    @Autowired
    protected UpYunConfig upYunConfig;
    @Autowired
    protected Calculate calculate;
    @Autowired
    public MPGoodsRecommendService mpGoodsRecommendService;

    /**
     * 从es或者数据库内获取数据，并交给处理器进行处理
     *
     * @param param  装修页面配置的商品获取过滤条件
     * @param userId 用户id
     * @return 对应的商品集合信息
     */
    public List<? extends GoodsListMpVo> getPageIndexGoodsList(GoodsListMpParam param, Integer userId) {
        List<GoodsListMpBo> goodsListCapsules;
        // 是否展示售罄
        Byte soldOutGoods = configService.shopCommonConfigService.getSoldOutGoods();
        if (GoodsConstant.SOLD_OUT_GOODS_SHOW.equals(soldOutGoods)) {
            param.setSoldOutGoodsShow(true);
        } else {
            param.setSoldOutGoodsShow(false);
        }

        if (esUtilSearchService.esState()) {
            try {
                /*如果商品范围选择的是商品标签，先查询商品标签的es，获得商品标签对应的goodsIds*/
                if( StringUtils.isNotBlank(param.getGoodsArea()) &&
                    param.getGoodsArea().equals(GoodsListMpParam.LABEL_AREA) ){
                    List<Integer> goodIds = esGoodsLabelSearchService.
                        getGoodsIdsByLabelIds(param.getGoodsAreaData(), EsGoodsConstant.GENERAL_PAGE);
                    param.setGoodsItems(goodIds);
                }
                // 从es获取
                log.debug("小程序-es-搜索商品列表");
                goodsListCapsules = getPageIndexGoodsListFromEs(param);
                log.debug("小程序-es-搜索商品列表结果:{}",goodsListCapsules);
            } catch (Exception e) {
                log.debug("小程序-es-搜索商品列表错误-转换db获取数据:" + e.getMessage());
                goodsListCapsules = getPageIndexGoodsListFromDb(param);
                log.debug("小程序-db-搜索商品列表结果:{}",goodsListCapsules);
            }
        } else {
            log.debug("小程序-db-搜索商品列表");
            goodsListCapsules = getPageIndexGoodsListFromDb(param);
            log.debug("小程序-db-搜索商品列表结果:{}",goodsListCapsules);
        }

        disposeGoodsList(goodsListCapsules, userId);
        return goodsListCapsules;
    }

    /**
     * 装修页面 商品列表模块中获取配置后的商品集合数据 Es获取
     *
     * @param param 装修页面配置的商品获取过滤条件
     * @return 对应的商品集合信息
     */
    private List<GoodsListMpBo> getPageIndexGoodsListFromEs(GoodsListMpParam param) throws IOException {
        PageResult<GoodsListMpBo> goodsListMpBoPageResult = esGoodsSearchMpService.queryGoodsByParam(param);
        return goodsListMpBoPageResult.dataList;
    }

    /**
     * 装修页面 商品列表模块中获取配置后的商品集合数据 Db获取
     *
     * @param param 装修页面配置的商品获取过滤条件
     * @return 对应的商品集合信息
     */
    private List<GoodsListMpBo> getPageIndexGoodsListFromDb(GoodsListMpParam param) {
        // 手动推荐展示但是未指定商品数据
        boolean specifiedNoContent = (GoodsConstant.POINT_RECOMMEND.equals(param.getRecommendType())) && (param.getGoodsItems() == null || param.getGoodsItems().size() == 0);
        if (specifiedNoContent) {
            return new ArrayList<>();
        }
        Condition condition = buildPageIndexCondition(param);
        List<GoodsListMpBo> goodsListCapsules;
        // 手动推荐拼接排序条件
        if (GoodsConstant.POINT_RECOMMEND.equals(param.getRecommendType())) {
            goodsListCapsules = findActivityGoodsListCapsulesDao(condition, null, null, null, param.getGoodsItems());
        } else {
            List<SortField<?>> orderFields = new ArrayList<>();
            if (GoodsListMpParam.SALE_NUM_SORT.equals(param.getSortType())) {
                orderFields.add(GOODS.GOODS_SALE_NUM.desc());
            } else if (GoodsListMpParam.SHOP_PRICE_SORT.equals(param.getSortType())) {
                orderFields.add(GOODS.SHOP_PRICE.asc());
            } else {
                orderFields.add(GOODS.GOODS_ID.desc());
            }
            goodsListCapsules = findActivityGoodsListCapsulesDao(condition, orderFields, 0, param.getGoodsNum(), null);
        }
        logger().debug("商品列表数据信息：" + goodsListCapsules.toString());
        return goodsListCapsules;
    }

    /**
     * 创建自动推荐商品过滤条件
     *
     * @param param 过滤参数
     * @return 拼接后的条件
     */
    private Condition buildPageIndexCondition(GoodsListMpParam param) {
        // 获取在售商品
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));

        // 不展示售罄商品
        if (!param.getSoldOutGoodsShow()) {
            condition = condition.and(GOODS.GOODS_NUMBER.gt(0));
        }
        // 指定商品列表
        if (GoodsConstant.POINT_RECOMMEND.equals(param.getRecommendType())) {
            condition = condition.and(GOODS.GOODS_ID.in(param.getGoodsItems()));
            return condition;
        }

        if (!StringUtils.isBlank(param.getKeywords())) {
            condition = condition.and(GOODS.GOODS_NAME.like(likeValue(param.getKeywords())));
        }

        if (param.getMinPrice() != null) {
            condition = condition.and(GOODS.SHOP_PRICE.gt(param.getMinPrice()));
        }
        if (param.getMaxPrice() != null) {
            condition = condition.and(GOODS.SHOP_PRICE.lt(param.getMaxPrice()));
        }

        // TODO: PHP 处理category参数字段，目前阶段未使用到

        // 指定商品范围选项过滤数据
        if (param.getGoodsAreaData() != null && param.getGoodsAreaData().size() > 0) {
            if (GoodsListMpParam.SORT_AREA.equals(param.getGoodsArea())) {
                condition = condition.and(GOODS.SORT_ID.in(param.getGoodsAreaData()));
            } else if (GoodsListMpParam.CAT_AREA.equals(param.getGoodsArea())) {
                condition = condition.and(GOODS.CAT_ID.in(param.getGoodsAreaData()));
            } else if (GoodsListMpParam.BRAND_AREA.equals(param.getGoodsArea())) {
                condition = condition.and(GOODS.BRAND_ID.in(param.getGoodsAreaData()));
            } else if (GoodsListMpParam.LABEL_AREA.equals(param.getGoodsArea())) {
                List<Integer> allIds = goodsLabelMpService.getGoodsLabelCouple(param.getGoodsAreaData(), GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());
                // 如果存在关联所有商品的标签则就不用再进行过滤了
                if (allIds.size() == 0) {
                    List<Integer> catIds = goodsLabelMpService.getGoodsLabelCouple(param.getGoodsAreaData(), GoodsLabelCoupleTypeEnum.CATTYPE.getCode());
                    List<Integer> sortIds = goodsLabelMpService.getGoodsLabelCouple(param.getGoodsAreaData(), GoodsLabelCoupleTypeEnum.SORTTYPE.getCode());
                    List<Integer> goodsIds = goodsLabelMpService.getGoodsLabelCouple(param.getGoodsAreaData(), GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode());
                    condition = condition.and(GOODS.CAT_ID.in(catIds).or(GOODS.SORT_ID.in(sortIds)).or(GOODS.GOODS_ID.in(goodsIds)));
                }
            }
        }
        // 商品活动过滤
        if (GoodsListMpParam.GOODS_TYPE_IS_CARD_EXCLUSIVE.equals(param.getGoodsType())) {
            condition = condition.and(GOODS.IS_CARD_EXCLUSIVE.eq(GoodsConstant.CARD_EXCLUSIVE));
        } else {
            if (param.getGoodsType() != null && param.getGoodsType() != 0) {
                condition = condition.and(GOODS.GOODS_TYPE.eq(param.getGoodsType()));
            }
        }

        return condition;
    }

    /**
     * 通过商品id集合回去对应的数据信息
     *
     * @param goodsIds 商品id集合
     * @param userId   用户id集合
     * @return {@link GoodsListMpParam}集
     */
    public List<? extends GoodsListMpVo> getGoodsListNormal(List<Integer> goodsIds, Integer userId) {
        List<GoodsListMpBo> goodsListCapsules;
        GoodsListMpParam param = new GoodsListMpParam();
        param.setRecommendType(GoodsConstant.POINT_RECOMMEND);
        param.setGoodsItems(goodsIds);
        if (esUtilSearchService.esState()) {
            try {
                // 从es获取
                log.debug("小程序-es-搜索商品列表");
                goodsListCapsules = getPageIndexGoodsListFromEs(param);
                log.debug("小程序-es-搜索商品列表结果:{}",goodsListCapsules);
            } catch (Exception e) {
                log.debug("小程序-es-搜索商品列表错误-转换db获取数据:" + e.getMessage());
                goodsListCapsules = getGoodsListNormalFromDb(goodsIds);
                log.debug("小程序-db-搜索商品列表结果:{}",goodsListCapsules);
            }
        } else {
            log.debug("小程序-db-搜索商品列表");
            goodsListCapsules = getGoodsListNormalFromDb(goodsIds);
            log.debug("小程序-db-搜索商品列表结果:{}",goodsListCapsules);
        }

        disposeGoodsList(goodsListCapsules, userId);

        return goodsListCapsules;
    }

    /**
     * 通过条件查询商品列表 如满包邮
     * @param param 查询条件
     * @return GoodsListMpBo
     */
    public PageResult<GoodsListMpBo> getGoodsListNormal(GoodsSearchParam param){
        Condition condition = handleSearchCondition(param);

        SelectConditionStep<Record12<Integer, String, Byte, BigDecimal, BigDecimal, Integer, Integer, String, Integer, Integer, Integer, Integer>> select =
                db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_TYPE.as("activity_type"), GOODS.SHOP_PRICE, GOODS.MARKET_PRICE,
                        GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_IMG,
                        GOODS.GOODS_NUMBER, GOODS.SORT_ID, GOODS.CAT_ID, GOODS.BRAND_ID).from(GOODS).where(condition);
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsListMpBo.class);
    }

    /**
     * 查询条件
     * @param param
     * @return
     */
    public Condition handleSearchCondition(GoodsSearchParam param) {
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));
        if (param.getShowSoldOut()){
            condition= condition.and(GOODS.GOODS_NUMBER.gt(0));
        }
        if (param.getGoodsAreaType().equals(GOODS_AREA_TYPE_SECTION)){
            Condition  conditionor=null;
            if (param.getGoodsIds()!=null&&param.getGoodsIds().size()>0){
                conditionor=GOODS.GOODS_ID.in(param.getGoodsIds());
            }
            if (param.getCatIds()!=null&&param.getCatIds().size()>0){
                if (conditionor==null){
                    conditionor=GOODS.CAT_ID.in(param.getCatIds());
                }else {
                    conditionor=   conditionor.or(GOODS.CAT_ID.in(param.getCatIds()));
                }
            }
            if (param.getSortIds()!=null&&param.getSortIds().size()>0){
                if (conditionor==null){
                    conditionor=GOODS.SORT_ID.in(param.getSortIds());
                }else {
                    conditionor= conditionor.or(GOODS.SORT_ID.in(param.getSortIds()));
                }
            }
            if (conditionor!=null){
                condition = condition.and(conditionor);
            }
        }
        if (param.getKeyWords()!=null&&!param.getKeyWords().isEmpty()){
            condition = condition.and(GOODS.GOODS_NAME.like(likeValue(param.getKeyWords())));
        }
        return condition;
    }

    /**
     * 通过商品id集合回去对应的数据信息
     *
     * @param goodsIds 商品id集合
     * @return {@link GoodsListMpParam}集
     */
    private List<GoodsListMpBo> getGoodsListNormalFromDb(List<Integer> goodsIds) {
        if (goodsIds == null) {
            return new ArrayList<>();
        }
        return findActivityGoodsListCapsulesDao(GOODS.GOODS_ID.in(goodsIds), null, null, null, goodsIds);
    }

    /**
     * 处理获取的推荐商品规格，评价，标签，活动tag,最终划线价和商品价格
     *
     * @param goodsListCapsules 通过过滤条件获取的商品信息
     * @param userId            用户id 可为null(在admin页面装修的时候传入的就是null)
     */
    public void disposeGoodsList(List<GoodsListMpBo> goodsListCapsules, Integer userId) {
        GoodsListMpProcessorFactory processorFactory = processorFactoryBuilder.getProcessorFactory(GoodsListMpProcessorFactory.class);
        // 处理规格，评价，标签，活动tag,最终划线价和商品价格
        processorFactory.doProcess(goodsListCapsules, userId);
    }

    /**
     * 小程序端获取商品详情信息
     *
     * @param param {@link GoodsDetailMpParam}
     */
    public GoodsDetailMpVo getGoodsDetailMp(GoodsDetailMpParam param) {
        GoodsDetailMpBo goodsDetailMpBo;

        if (esUtilSearchService.esState()) {
            try {
                log.debug("小程序-es-搜索商品详情");
                goodsDetailMpBo = esGoodsSearchMpService.queryGoodsById(param.getGoodsId());
                log.debug("小程序-es-搜索商品详情结果:{}", goodsDetailMpBo);
                // 商品已删除，在es内不存在
                if (goodsDetailMpBo == null) {
                    return createDeletedGoodsDetailMpVo();
                }
                goodsDetailMpBo.setIsDisposedByEs(true);
            } catch (Exception e) {
                log.debug("小程序-es-搜索商品详情错误-转换db获取数据:" + e.getMessage());
                goodsDetailMpBo = getGoodsDetailMpInfoDao(param.getGoodsId());
                log.debug("小程序-db-搜索商品详情结果 {}", goodsDetailMpBo);
                // 商品从数据库内查询，但是数据已经被删除
                if (goodsDetailMpBo == null) {
                    return createDeletedGoodsDetailMpVo();
                }
                if (DelFlag.DISABLE_VALUE.equals(goodsDetailMpBo.getDelFlag())) {
                    return goodsDetailMpBo;
                }
            }
        } else {
            log.debug("小程序-db-搜索商品详情信息");
            goodsDetailMpBo = getGoodsDetailMpInfoDao(param.getGoodsId());
            log.debug("小程序-db-搜索商品详情信息 {}", goodsDetailMpBo);
            // 商品从数据库内查询，但是数据已经被删除
            if (goodsDetailMpBo == null) {
                return createDeletedGoodsDetailMpVo();
            }
            if (DelFlag.DISABLE_VALUE.equals(goodsDetailMpBo.getDelFlag())) {
                return goodsDetailMpBo;
            }
        }

        GoodsDetailMpProcessorFactory processorFactory = processorFactoryBuilder.getProcessorFactory(GoodsDetailMpProcessorFactory.class);
        GoodsDetailCapsuleParam capsuleParam = new GoodsDetailCapsuleParam();
        capsuleParam.setUserId(param.getUserId());
        capsuleParam.setActivityId(param.getActivityId());
        capsuleParam.setActivityType(param.getActivityType());
        capsuleParam.setLat(param.getLat());
        capsuleParam.setLon(param.getLon());
        processorFactory.doProcess(goodsDetailMpBo, capsuleParam);
        return goodsDetailMpBo;
    }

    /**
     * 创建一个处于删除状态的vo
     * @return {@link GoodsDetailMpVo}
     */
    private GoodsDetailMpVo createDeletedGoodsDetailMpVo(){
        GoodsDetailMpBo goodsDetailMpBo = new GoodsDetailMpBo();
        goodsDetailMpBo.setDelFlag(DelFlag.NORMAL_VALUE);
        return goodsDetailMpBo;
    }

    /**
     * 小程序端-商品搜索界面-可使用搜索条件数据初始化
     * 由ES反向推到可用数据
     *
     * @return {@link GoodsSearchFilterConditionMpVo}
     */
    public GoodsSearchFilterConditionMpVo getGoodsSearchFilterCondition() {
        if (esUtilSearchService.esState()){
            try {
                log.debug("小程序-es-商品搜索条件反推");
                return esGoodsSearchMpService.getGoodsParam();
            } catch (IOException e) {
                log.debug("小程序-es-商品搜索条件反推错误-转换db获取数据:" + e.getMessage());
                return getGoodsSearchFilterConditionFromDb();
            }
        } else {
            log.debug("小程序-db-商品搜索条件反推");
            return getGoodsSearchFilterConditionFromDb();
        }
    }

    /**
     * 从数据库反小程序端商品搜索条件
     * @return {@link GoodsSearchFilterConditionMpVo}
     */
    private GoodsSearchFilterConditionMpVo getGoodsSearchFilterConditionFromDb(){
        GoodsSearchFilterConditionMpVo vo = new GoodsSearchFilterConditionMpVo();
        vo.setGoodsBrands(goodsBrandSortMp.getGoodsSearchFilterCondition());
        vo.setGoodsLabels(goodsLabelMpService.getGoodsSearchFilterCondition());
        return vo;
    }

    /**
     * 搜索小程序商品信息
     *
     * @param param 商品信息过滤条件
     * @return 搜索出来的商品信息
     */
    public GoodsSearchContentVo searchGoods(GoodsSearchMpParam param) {
        PageResult<GoodsListMpBo> pageResult = null;

        Byte soldOutGoods = configService.shopCommonConfigService.getSoldOutGoods();
        if (GoodsConstant.SOLD_OUT_GOODS_SHOW.equals(soldOutGoods)) {
            param.setSoldOutGoodsShow(true);
        } else {
            param.setSoldOutGoodsShow(false);
        }

        if (esUtilSearchService.esState()) {
            try {
                log.debug("小程序-es-搜索商品");
                pageResult = esGoodsSearchMpService.queryGoodsByParam(param);
            } catch (Exception e) {
                log.debug("小程序-es-搜索商品-转换db获取数据:" + e.getMessage());
                pageResult = searchGoodsFromDb(param);
            }
        } else {
            log.debug("小程序-db-搜索商品");
            pageResult = searchGoodsFromDb(param);
        }

        disposeGoodsList(pageResult.dataList, param.getUserId());

        //是否显示划线价开关
        Byte delMarket = configService.shopCommonConfigService.getDelMarket();
        //是否显示购买按钮
        ShowCartConfig showCart = configService.shopCommonConfigService.getShowCart();

        GoodsSearchContentVo vo =new GoodsSearchContentVo();
        vo.setDelMarket(delMarket);
        vo.setShowCart(showCart);
        vo.setPageResult(pageResult);

        return vo;
    }

    /**
     * 数据库搜索商品
     *
     * @param param 搜索条件
     * @return 搜索到的内容
     */
    private PageResult<GoodsListMpBo> searchGoodsFromDb(GoodsSearchMpParam param) {

        Condition condition = buildCondition(param);

        SelectConditionStep<Record12<Integer, String, Byte, BigDecimal, BigDecimal, Integer, Integer, String, Integer, Integer, Integer, Integer>> select =
            db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_TYPE.as("activity_type"), GOODS.SHOP_PRICE, GOODS.MARKET_PRICE,
                GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_IMG,
                GOODS.GOODS_NUMBER, GOODS.SORT_ID, GOODS.CAT_ID, GOODS.BRAND_ID).from(GOODS).where(condition);

        buildOrderFields(select, param);

        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), GoodsListMpBo.class);
    }

    /**
     * 商品搜索-db-条件拼接
     *
     * @param param 搜索条件
     * @return
     */
    private Condition buildCondition(GoodsSearchMpParam param) {
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));

        if (!param.getSoldOutGoodsShow()) {
            condition = condition.and(GOODS.GOODS_NUMBER.gt(0));
        }

        if (param.getKeyWords() != null) {
            condition = condition.and(GOODS.GOODS_NAME.like(likeValue(param.getKeyWords())));
        }

        if (param.getBrandIds() != null && param.getBrandIds().size() > 0) {
            condition = condition.and(GOODS.BRAND_ID.in(param.getBrandIds()));
        }

        if (param.getLabelIds() != null && param.getLabelIds().size() > 0) {
            Result<Record2<Integer, Byte>> labelCoupleList = goodsLabelMpService.getGoodsLabelsCoupleTypeInfoByIds(param.getLabelIds());
            boolean allType = false;
            List<Integer> gtaGoodsIds = new ArrayList<>();
            for (int i = 0; i < labelCoupleList.size(); i++) {
                Record2<Integer, Byte> record2 = labelCoupleList.get(i);
                if (GoodsLabelCoupleTypeEnum.ALLTYPE.getCode().equals(record2.get(GOODS_LABEL_COUPLE.TYPE))) {
                    allType = true;
                    break;
                }
                if (GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode().equals(record2.get(GOODS_LABEL_COUPLE.TYPE))) {
                    gtaGoodsIds.add(record2.get(GOODS_LABEL_COUPLE.GTA_ID));
                }
            }
            if (!allType) {
                condition = condition.and(GOODS.GOODS_ID.in(gtaGoodsIds));
            }
        }

        return condition;
    }

    /**
     * 排序字段拼接
     *
     * @param select 待排序语句
     * @param param  请求条件
     */
    private void buildOrderFields(SelectConditionStep<?> select, GoodsSearchMpParam param) {
        if (SortItemEnum.SALE_NUM.equals(param.getSortItem())) {
            if (SortDirectionEnum.DESC.equals(param.getSortDirection())) {
                select.orderBy(GOODS.GOODS_SALE_NUM.desc(), GOODS.CREATE_TIME.desc());
            } else {
                select.orderBy(GOODS.GOODS_SALE_NUM.asc(), GOODS.CREATE_TIME.desc());
            }
        } else {
            // 默认价格排序
            if (SortDirectionEnum.DESC.equals(param.getSortDirection())) {
                select.orderBy(GOODS.SHOP_PRICE.desc(), GOODS.CREATE_TIME.desc());
            } else {
                select.orderBy(GOODS.SHOP_PRICE.asc(), GOODS.CREATE_TIME.desc());
            }
        }

    }

    /**
     * 根据过滤条件获取商品列表中的商品信息，
     * 返回结果的顺序和goodsIds的顺序一致,若果查询的结果在goodsId中不存在则默认添加至返回列表的末尾
     *
     * @param condition   过滤条件
     * @param orderFields 排序结合
     * @param offset      分页开始位置 如果limit为null则不会进行分页，如果offset为null,则默认为从0开始
     * @param limit       分页数量为null表示不进行分页
     * @param goodsIds    指定的商品id顺序
     * @return {@link GoodsListMpBo}
     */
    private List<GoodsListMpBo> findActivityGoodsListCapsulesDao(Condition condition, List<SortField<?>> orderFields, Integer offset, Integer limit, List<Integer> goodsIds) {
        if (condition == null) {
            condition = DSL.noCondition();
        }

        if (orderFields == null || orderFields.size() == 0) {
            orderFields = new ArrayList<>();
            orderFields.add(GOODS.GOODS_ID.asc());
        }

        Select<?> select = null;

        SelectSeekStepN<Record12<Integer, String, Byte, BigDecimal, BigDecimal, Integer, Integer, String, Integer, Integer, Integer, Integer>> record12 =
            db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_TYPE.as("activity_type"), GOODS.SHOP_PRICE, GOODS.MARKET_PRICE,
                GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_IMG,
                GOODS.GOODS_NUMBER, GOODS.SORT_ID, GOODS.CAT_ID, GOODS.BRAND_ID)
                .from(GOODS).where(condition).orderBy(orderFields);

        select = record12;

        // 拼接分页
        if (limit != null) {
            offset = offset == null ? 0 : offset;
            select = record12.limit(offset, limit);
        }

        List<GoodsListMpBo> returnList;

        if (goodsIds != null) {
            Map<Integer, GoodsListMpBo> map = select.fetchMap(GOODS.GOODS_ID, GoodsListMpBo.class);
            // 按照顺序排列商品，remove 和 addAll操作：针对根据条件查询后商品数量大于goodsIds数量的情况
            returnList = goodsIds.stream().filter(id -> map.get(id) != null).map(map::remove).collect(Collectors.toList());
            returnList.addAll(map.values());
        } else {
            returnList = select.fetchInto(GoodsListMpBo.class);
        }
        return returnList;
    }

    /**
     * 获取商品基本信息详情
     *
     * @param goodsId 商品id（该商品可能已删除或下架）
     * @return {@link GoodsDetailMpBo}
     */
    private GoodsDetailMpBo getGoodsDetailMpInfoDao(Integer goodsId) {
        Record record1 = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_TYPE, GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_NUMBER,
            GOODS.SORT_ID, GOODS.CAT_ID, GOODS.BRAND_ID, GOODS_BRAND.BRAND_NAME, GOODS.DELIVER_TEMPLATE_ID, GOODS.DELIVER_PLACE, GOODS.GOODS_WEIGHT, GOODS.DEL_FLAG, GOODS.IS_ON_SALE,
            GOODS.GOODS_IMG, GOODS.GOODS_VIDEO_ID, GOODS.GOODS_VIDEO, GOODS.GOODS_VIDEO_IMG, GOODS.GOODS_VIDEO_SIZE,
            GOODS.LIMIT_BUY_NUM, GOODS.LIMIT_MAX_NUM, GOODS.IS_CARD_EXCLUSIVE, GOODS.IS_PAGE_UP, GOODS.GOODS_PAGE_ID, GOODS.GOODS_AD, GOODS.GOODS_DESC)
            .from(GOODS).leftJoin(GOODS_BRAND).on(GOODS.BRAND_ID.eq(GOODS_BRAND.ID))
            .where(GOODS.GOODS_ID.eq(goodsId)).fetchAny();
        if (record1 == null) {
            log.debug("商品详情-{}已被从数据库真删除", goodsId);
            return null;
        }

        GoodsDetailMpBo capsule = record1.into(GoodsDetailMpBo.class);
        if (DelFlag.DISABLE_VALUE.equals(capsule.getDelFlag())) {
            return capsule;
        }

        // 图片处理
        List<String> imgs = db().select().from(GOODS_IMG).where(GOODS_IMG.GOODS_ID.eq(goodsId)).fetch(GOODS_IMG.IMG_URL);
        capsule.getGoodsImgs().addAll(imgs);
        //处理视频长度和宽度
        if (capsule.getGoodsVideoId() != null) {
            Record3<Integer, Integer, Integer> record = db().select(UPLOADED_VIDEO.VIDEO_HEIGHT, UPLOADED_VIDEO.VIDEO_WIDTH, UPLOADED_VIDEO.VIDEO_SIZE).from(UPLOADED_VIDEO)
                .where(UPLOADED_VIDEO.VIDEO_ID.eq(capsule.getGoodsVideoId()).and(UPLOADED_VIDEO.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))).fetchAny();
            if (record != null) {
                capsule.setVideoHeight(record.get(UPLOADED_VIDEO.VIDEO_HEIGHT));
                capsule.setVideoWidth(record.get(UPLOADED_VIDEO.VIDEO_WIDTH));
                double size = record.get(UPLOADED_VIDEO.VIDEO_SIZE) * 1.0 / 1024 / 1024;
                capsule.setGoodsVideoSize(BigDecimalUtil.setDoubleScale(size, 2, true));
            }
        }
        return capsule;
    }

    /**
     * 获取指定商品收藏数量信息
     *
     * @param goodsId 商品id
     * @return {@link GoodsRecord}
     */
    public GoodsRecord getGoodsCollectionInfoDao(Integer goodsId) {
        Record2<Integer, BigDecimal> record = db().select(GOODS.GOODS_COLLECT_NUM, GOODS.SHOP_PRICE).from(GOODS)
            .where(GOODS.GOODS_ID.eq(goodsId).and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode())))
            .fetchAny();

        if (record == null) {
            return null;
        } else {
            return record.into(GoodsRecord.class);
        }
    }

    /**
     * 增加商品的收藏数量
     * @param goodsId 商品id
     * @param flag true 增加 false 减少
     */
    public void incOrDecGoodsCollectionNumDao(Integer goodsId, boolean flag) {
        if (flag) {
            db().update(GOODS).set(GOODS.GOODS_COLLECT_NUM, GOODS.GOODS_COLLECT_NUM.add(1))
                .where(GOODS.GOODS_ID.eq(goodsId)).execute();
        } else {
            db().update(GOODS).set(GOODS.GOODS_COLLECT_NUM, GOODS.GOODS_COLLECT_NUM.sub(1))
                .where(GOODS.GOODS_ID.eq(goodsId)).execute();
        }
    }

    /**
     * 根据商家分类获取对应商品的ID集合
     * @param sortId 商家分类ID
     * @return 商品ID集合
     */
    public List<Integer> getGoodsIdsBySortIdDao(Integer sortId) {
        return db().select(GOODS.GOODS_ID).from(GOODS).where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GOODS.SORT_ID.eq(sortId))).fetch(GOODS.GOODS_ID);
    }

    /**
     * 根据条件放回查询goodsId
     * @return
     */
    public List<Integer> getGoodsIdsByCondition( Condition condition){
        return db().select(GOODS.GOODS_ID).from(GOODS).where(condition).fetchInto(Integer.class);
    }

    /**
     * 小程序-商品详情-获取对应增品规格信息，规格数量大于0
     * @param prdIds 要取的规格ID
     * @return 增品规格信息
     */
    public List<GoodsGiftPrdMpVo> getGoodsDetailGiftPrdsInfoDao(List<Integer> prdIds) {
        Result<Record6<String, String, Integer, String, BigDecimal, String>> prdResults = db().select(GOODS.GOODS_IMG, GOODS.GOODS_NAME, GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_DESC, GOODS_SPEC_PRODUCT.PRD_PRICE, GOODS_SPEC_PRODUCT.PRD_IMG).from(GOODS).innerJoin(GOODS_SPEC_PRODUCT).on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID))
            .where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(GOODS_SPEC_PRODUCT.PRD_NUMBER.gt(0)).and(GOODS_SPEC_PRODUCT.PRD_ID.in(prdIds)))
            .fetch();

        List<GoodsGiftPrdMpVo> giftPrds = new ArrayList<>(prdResults.size());

        for (Record6<String, String, Integer, String, BigDecimal, String> prdResult : prdResults) {
            GoodsGiftPrdMpVo prd =new GoodsGiftPrdMpVo();
            prd.setProductId(prdResult.get(GOODS_SPEC_PRODUCT.PRD_ID));
            prd.setPrdImg(StringUtils.isBlank(prdResult.get(GOODS_SPEC_PRODUCT.PRD_IMG))?prdResult.get(GOODS.GOODS_IMG):prdResult.get(GOODS_SPEC_PRODUCT.PRD_IMG));
            prd.setPrdPrice(prdResult.get(GOODS_SPEC_PRODUCT.PRD_PRICE));
            prd.setGoodsName(prdResult.get(GOODS.GOODS_NAME));
            prd.setPrdDesc(prdResult.get(GOODS_SPEC_PRODUCT.PRD_DESC));
            giftPrds.add(prd);
        }
        return giftPrds;
    }

}
