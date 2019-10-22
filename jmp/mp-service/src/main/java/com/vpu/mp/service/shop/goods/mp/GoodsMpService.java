package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsLabelMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsListMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsT;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.goods.GoodsCommentService;
import com.vpu.mp.service.shop.goods.GoodsPriceService;
import com.vpu.mp.service.shop.image.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS;

/**
 * @author 李晓冰
 * @date 2019年10月12日
 * 小程序和装修内商品相关
 */
@Service
public class GoodsMpService extends ShopBaseService {

    @Autowired
    public GoodsBrandSortMpService goodsBrandSortMp;

    @Autowired
    ImageService imageService;
    @Autowired
    ConfigService configService;
    @Autowired
    GoodsCommentService goodsCommentService;
    @Autowired
    GoodsPriceService goodsPriceService;

    @Autowired
    GoodsLabelMpService goodsLabelMpService;
    @Autowired
    GoodsProductMpService goodsProductMpService;
    @Autowired
    GoodsActivityMpService goodsActivityMpService;

    /**
     * 装修页面 商品列表模块中获取配置后的商品集合数据
     *
     * @param param 装修页面配置的商品获取过滤条件
     * @return 对应的商品集合信息
     */
    public List<GoodsListMpVo> getGoodsList(GoodsListMpParam param) {
        Byte soldOutGoods = configService.shopCommonConfigService.getSoldOutGoods();
        if (soldOutGoods == 1) {
            param.setSoldOutGoodsShow(soldOutGoods);
        }
        List<GoodsT> goodsTList;
        // 手动推荐展示
        if (0 != param.getRecommendType()) {
            // 手动推荐商品逻辑
            if (param.getGoodsItems() == null || param.getGoodsItems().size() == 0) {
                return new ArrayList<>();
            }
            goodsTList=getPointGoodsList(param);
        } else {
            // 自动推荐展示
            goodsTList = getAutoGoodsList(param);
        }
        disposeGoodsList(goodsTList);

        List<GoodsListMpVo> goodsListMpVos = convertGoodsTGoodsListMpVo(goodsTList);

        return goodsListMpVos;
    }

    /**
     *  根据指定的商品id值获取处理好信息的商品结果集合（商家分类展示一级分类关联的商品时使用到）
     * @param goodsIds 指定的商品结果结合
     * @return
     */
    public List<GoodsListMpVo> getGoodsList(List<Integer> goodsIds) {
        GoodsListMpParam param = new GoodsListMpParam();
        param.setGoodsItems(goodsIds);
        return getGoodsList(param);
    }
    /**
     * 获取指定商品模式下的商品信息
     * @param param 自动推荐过滤条件
     * @return 商品结果集合
     */
    private List<GoodsT> getPointGoodsList(GoodsListMpParam param) {
        List<Integer> goodsIds = param.getGoodsItems();

        Condition condition = GOODS.GOODS_ID.in(goodsIds);
        if (param.getSoldOutGoodsShow() == GoodsConstant.SOLD_OUT_GOODS_SHOW) {
            condition = condition.and(GOODS.GOODS_NUMBER.gt(0));
        }

        List<GoodsT> goodsTList = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.SHOP_PRICE, GOODS.MARKET_PRICE,GOODS.GOODS_TYPE,
            GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_IMG, GOODS.CREATE_TIME, GOODS.DEL_FLAG, GOODS.IS_ON_SALE, GOODS.GOODS_NUMBER, GOODS.SORT_ID, GOODS.CAT_ID)
            .from(GOODS).where(condition).fetchInto(GoodsT.class);
        return goodsTList;
    }

    /**
     * 获取自动推荐时的商品信息
     * @param param 自动推荐过滤条件
     * @return 商品结果集合
     */
    private List<GoodsT> getAutoGoodsList(GoodsListMpParam param) {
        // 处理过滤条件
        Condition condition = buildConditionForAutoGoodsList(param);

        SelectConditionStep<?> select = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.SHOP_PRICE, GOODS.MARKET_PRICE,GOODS.GOODS_TYPE,
            GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_IMG, GOODS.CREATE_TIME, GOODS.DEL_FLAG, GOODS.IS_ON_SALE, GOODS.GOODS_NUMBER, GOODS.SORT_ID, GOODS.CAT_ID)
            .from(GOODS).where(condition);

        if (GoodsListMpParam.SALE_NUM_SORT.equals(param.getSortType())) {
            select.orderBy(GOODS.GOODS_SALE_NUM.desc());
        } else if (GoodsListMpParam.SHOP_PRICE_SORT.equals(param.getSortType())) {
            select.orderBy(GOODS.SHOP_PRICE);
        } else {
            select.orderBy(GOODS.GOODS_ID.desc());
        }
        // TODO: 排序时逻辑未添加对活动类型为1,3,5的类型的特殊处理，php处理了，但是目前开发未涉及到，不清楚具体业务场景

        List<GoodsT> goodsTList = select.limit(param.getGoodsNum()).fetchInto(GoodsT.class);

        return goodsTList;
    }

    /**
     * 创建自动推荐商品过滤条件
     *
     * @param param 过滤参数
     * @return 拼接后的条件
     */
    private Condition buildConditionForAutoGoodsList(GoodsListMpParam param) {
        // 获取在售商品且商品数量大于0
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode());
        condition = condition.and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));

        if (!StringUtils.isBlank(param.getKeywords())) {
            condition = condition.and(GOODS.GOODS_NAME.like(likeValue(param.getKeywords())));
        }
        // 是否展示售罄商品
        if (param.getSoldOutGoodsShow() == GoodsConstant.SOLD_OUT_GOODS_SHOW) {
            condition = condition.and(GOODS.GOODS_NUMBER.gt(0));
        }

        if (param.getMinPrice() != null) {
            condition = condition.and(GOODS.SHOP_PRICE.gt(param.getMinPrice()));
        }
        if (param.getMaxPrice() != null) {
            condition = condition.and(GOODS.SHOP_PRICE.lt(param.getMaxPrice()));
        }

        // TODO: PHP 处理category参数字段，目前阶段未使用到

        // 指定商品范围选项过滤数据
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
        // 商品活动过滤
        if (GoodsListMpParam.GOODS_TYPE_IS_CARD_EXCLUSIVE.equals(param.getGoodsType()) ) {
            condition = condition.and(GOODS.IS_CARD_EXCLUSIVE.eq(GoodsConstant.CARD_EXCLUSIVE));
        } else {
            if (param.getGoodsType() != null) {
                condition = condition.and(GOODS.GOODS_TYPE.eq(param.getGoodsType()));
            }
        }

        return condition;
    }

    /**
     * 处理获取的自动推荐商品
     * @param goodsTList
     */
    private void disposeGoodsList(List<GoodsT> goodsTList) {
        // 获取评价审核信息
        Byte commentConfig = configService.commentConfigService.geCommentConfig();
        List<Integer> goodsIds = goodsTList.stream().map(GoodsT::getGoodsId).collect(Collectors.toList());
        // 处理商品价格信息
        disposeDecorateGoodsPrice(goodsIds, goodsTList);

        // 获取各个商品评价数量
        Map<Integer, Integer> goodsCommentMap = goodsCommentService.statisticGoodsComment(goodsIds, commentConfig);

        for (GoodsT goodsT : goodsTList) {
            // 获取和商品关联最紧密的标签信息
            GoodsLabelMpVo labelMpVo = goodsLabelMpService.getGoodsClosestLabel(goodsT);
            // 商品活动标签名处理
            goodsActivityMpService.disposeGoodsActivityTags(goodsT);

            goodsT.setLabel(labelMpVo);
            goodsT.setCommentNumStr(Integer.toString(goodsCommentMap.get(goodsT.getGoodsId())));
            goodsT.setCommentLen(goodsT.getCommentNumStr().length());
            goodsT.setGoodsImg(getImgFullUrlUtil(goodsT.getGoodsImg()));
        }
    }

    /**
     * 处理商品最终价格
     *
     * @return
     */
    private void disposeDecorateGoodsPrice(List<Integer> goodsId, List<GoodsT> targetGoodsT) {
        // 获取商品规格最低最高价
        Map<Integer, GoodsT> goodsPrdPriceInfo = goodsProductMpService.getGoodsPrdPriceInfo(goodsId);

        Map<Integer, Byte> goodsTypeMap = targetGoodsT.stream().collect(Collectors.toMap(GoodsT::getGoodsId, GoodsT::getGoodsType));

        // 获取商品对应活动的最终价格
//        Map<Integer, BigDecimal> goodsActivityPriceMap = goodsPriceService.getShowPriceByIdAndType(goodsTypeMap);
        Map<Integer, BigDecimal> goodsActivityPriceMap = new HashMap<>();

        final String priceFormat = "%.2f";

        for (GoodsT target : targetGoodsT) {
            GoodsT prdPriceBox = goodsPrdPriceInfo.get(target.getGoodsId());
            // 该值可能为null，因为可能活动失效了
            BigDecimal activityPrice = goodsActivityPriceMap.get(target.getGoodsId());

            target.setRealPriceStr(String.format(priceFormat, target.getShopPrice()));

            if (activityPrice != null) {
                target.setRealPriceStr(String.format(priceFormat, activityPrice));
            }
            // 划线价设置
            Byte goodsType = target.getGoodsType();
            if (goodsActivityMpService.isIn135610Activity(goodsType)) {
                target.setLinePriceStr(String.format(priceFormat, prdPriceBox.getMaxPrice()));
            } else {
                target.setLinePriceStr(String.format(priceFormat, target.getMarketPrice()));
            }

            target.setRealPriceLen(target.getRealPriceStr().length());
            target.setLinePriceLen(target.getLinePriceStr().length());
        }
    }


    private List<GoodsListMpVo> convertGoodsTGoodsListMpVo(List<GoodsT> goodsTList) {
        List<GoodsListMpVo> goodsListMpVos = new ArrayList<>();
        if (goodsTList == null || goodsTList.size() == 0) {
            return goodsListMpVos;
        }

        for (GoodsT t : goodsTList) {
            GoodsListMpVo vo = new GoodsListMpVo();
            vo.setGoodsId(t.getGoodsId());
            vo.setGoodsName(t.getGoodsName());
            vo.setCreateTime(t.getCreateTime());
            vo.setGoodsImg(t.getGoodsImg());
            vo.setGoodsNumber(t.getGoodsNumber());
            vo.setGoodsSaleNum(Integer.toString(t.getGoodsSaleNum() + t.getBaseSale()));
            vo.setSaleLen(vo.getGoodsSaleNum().length());
            vo.setIsOnSale(t.getIsOnSale());
            vo.setDefaultPrd(t.getDefaultPrd());
            vo.setGoodsTags(t.getGoodsTags());
            vo.setGoodsType(t.getGoodsType());
            vo.setDelFlag(t.getDelFlag());
            vo.setLabel(t.getLabel());
            vo.setShopPrice(t.getShopPrice());
            vo.setLinePrice(t.getLinePriceStr());
            vo.setLinePriceLen(t.getLinePriceLen());
            vo.setRealPrice(t.getRealPriceStr());
            vo.setMarketPrice(t.getMarketPrice());
            vo.setCommentLen(t.getCommentLen());
            vo.setCommentNum(t.getCommentNumStr());
            goodsListMpVos.add(vo);
        }
        return goodsListMpVos;
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
