package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.activity.capsule.ActivityGoodsListCapsule;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsLabelMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsListMpVo;
import com.vpu.mp.service.shop.activity.factory.GoodsListMpProcessorFactory;
import com.vpu.mp.service.shop.activity.factory.ProcessorFactoryBuilder;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.image.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    GoodsLabelMpService goodsLabelMpService;

    @Autowired
    ProcessorFactoryBuilder processorFactoryBuilder;

    public List<ActivityGoodsListCapsule> test() {
        GoodsListMpProcessorFactory processorFactory = processorFactoryBuilder.getProcessorFactory(GoodsListMpProcessorFactory.class);
        List<ActivityGoodsListCapsule> capsules = new ArrayList<>();
        ActivityGoodsListCapsule c = new ActivityGoodsListCapsule();
        c.setGoodsId(1);
        c.setCatId(2);
        c.setSortId(28);
        c.setGoodsType((byte) 0);
        c.setShopPrice(BigDecimal.valueOf(50));
        c.setMarketPrice(BigDecimal.valueOf(200));
        capsules.add(c);
        processorFactory.doProcess(capsules, 123);
        return capsules;
    }

    /**
     * 装修页面 商品列表模块中获取配置后的商品集合数据
     *
     * @param param  装修页面配置的商品获取过滤条件
     * @param userId
     * @return 对应的商品集合信息
     */
    public List<GoodsListMpVo> getGoodsList(GoodsListMpParam param, Integer userId) {
        Byte soldOutGoods = configService.shopCommonConfigService.getSoldOutGoods();
        if (soldOutGoods == 1) {
            param.setSoldOutGoodsShow(soldOutGoods);
        }
        List<ActivityGoodsListCapsule> goodsListCapsules;
        // 手动推荐展示
        if (0 != param.getRecommendType()) {
            // 手动推荐商品逻辑
            if (param.getGoodsItems() == null || param.getGoodsItems().size() == 0) {
                return new ArrayList<>();
            }
            goodsListCapsules = getPointGoodsList(param);
        } else {
            // 自动推荐展示
            goodsListCapsules = getAutoGoodsList(param);
        }

        disposeGoodsList(goodsListCapsules, userId);

        List<GoodsListMpVo> goodsListMpVos = convertGoodsCapsuleTGoodsListMpVo(goodsListCapsules);

        return goodsListMpVos;
    }

    /**
     * 根据指定的商品id值获取处理好信息的商品结果集合（商家分类展示一级分类关联的商品时使用到）
     *
     * @param goodsIds 指定的商品结果结合
     * @return
     */
    public List<GoodsListMpVo> getGoodsList(List<Integer> goodsIds, Integer userId) {
        GoodsListMpParam param = new GoodsListMpParam();
        param.setGoodsItems(goodsIds);
        return getGoodsList(param, userId);
    }

    /**
     * 获取指定商品模式下的商品信息
     * @param param 自动推荐过滤条件
     * @return 商品结果集合
     */
    private List<ActivityGoodsListCapsule> getPointGoodsList(GoodsListMpParam param) {
        List<Integer> goodsIds = param.getGoodsItems();

        Condition condition = GOODS.GOODS_ID.in(goodsIds).and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));
        // 是否展示已指定但是售罄的商品
        if (param.getSoldOutGoodsShow() == GoodsConstant.SOLD_OUT_GOODS_SHOW) {
            condition = condition.and(GOODS.GOODS_NUMBER.gt(0));
        }

        Map<Integer, ActivityGoodsListCapsule> goodsListCapsulesMap = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_TYPE, GOODS.SHOP_PRICE, GOODS.MARKET_PRICE,
            GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_IMG,
            GOODS.GOODS_NUMBER, GOODS.SORT_ID, GOODS.CAT_ID, GOODS.BRAND_ID)
            .from(GOODS).where(condition).fetchMap(GOODS.GOODS_ID, ActivityGoodsListCapsule.class);

        List<ActivityGoodsListCapsule> goodsListCapsules = new ArrayList<>();
        goodsIds.forEach(goodsId-> goodsListCapsules.add(goodsListCapsulesMap.get(goodsIds)));

        return goodsListCapsules;
    }

    /**
     * 获取自动推荐时的商品信息
     *
     * @param param 自动推荐过滤条件
     * @return 商品结果集合
     */
    private List<ActivityGoodsListCapsule> getAutoGoodsList(GoodsListMpParam param) {
        // 处理过滤条件
        Condition condition = buildConditionForAutoGoodsList(param);

        SelectConditionStep<?> select = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_TYPE, GOODS.SHOP_PRICE, GOODS.MARKET_PRICE,
            GOODS.GOODS_SALE_NUM, GOODS.BASE_SALE, GOODS.GOODS_IMG,
            GOODS.GOODS_NUMBER, GOODS.SORT_ID, GOODS.CAT_ID, GOODS.BRAND_ID)
            .from(GOODS).where(condition);

        if (GoodsListMpParam.SALE_NUM_SORT.equals(param.getSortType())) {
            select.orderBy(GOODS.GOODS_SALE_NUM.desc());
        } else if (GoodsListMpParam.SHOP_PRICE_SORT.equals(param.getSortType())) {
            select.orderBy(GOODS.SHOP_PRICE);
        } else {
            select.orderBy(GOODS.GOODS_ID.desc());
        }
        // TODO: 排序时逻辑未添加对活动类型为1,3,5的类型的特殊处理，php处理了，但是目前开发未涉及到，不清楚具体业务场景

        List<ActivityGoodsListCapsule> goodsListCapsules = select.limit(param.getGoodsNum()).fetchInto(ActivityGoodsListCapsule.class);

        return goodsListCapsules;
    }

    /**
     * 创建自动推荐商品过滤条件
     *
     * @param param 过滤参数
     * @return 拼接后的条件
     */
    private Condition buildConditionForAutoGoodsList(GoodsListMpParam param) {
        // 获取在售商品且商品数量大于0
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));

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
        if (GoodsListMpParam.GOODS_TYPE_IS_CARD_EXCLUSIVE.equals(param.getGoodsType())) {
            condition = condition.and(GOODS.IS_CARD_EXCLUSIVE.eq(GoodsConstant.CARD_EXCLUSIVE));
        } else {
            if (param.getGoodsType() != null) {
                condition = condition.and(GOODS.GOODS_TYPE.eq(param.getGoodsType()));
            }
        }

        return condition;
    }

    /**
     * 处理获取的推荐商品规格，评价，标签，活动tag,最终划线价和商品价格
     *
     * @param goodsListCapsules 通过过滤条件获取的商品信息
     * @param userId            用户id 可为null(在admin页面装修的时候传入的就是null)
     */
    private void disposeGoodsList(List<ActivityGoodsListCapsule> goodsListCapsules, Integer userId) {
        GoodsListMpProcessorFactory processorFactory = processorFactoryBuilder.getProcessorFactory(GoodsListMpProcessorFactory.class);
        // 处理规格，评价，标签，活动tag,最终划线价和商品价格
        processorFactory.doProcess(goodsListCapsules, userId);
    }

    /**
     * ActivityGoodsListCapsule 转换为 GoodsListMpVo
     *
     * @param goodsListCapsules 待转换数据
     * @return 转换后的数据
     */
    private List<GoodsListMpVo> convertGoodsCapsuleTGoodsListMpVo(List<ActivityGoodsListCapsule> goodsListCapsules) {
        List<GoodsListMpVo> goodsListMpVos = new ArrayList<>();
        if (goodsListCapsules == null || goodsListCapsules.size() == 0) {
            return goodsListMpVos;
        }

        for (ActivityGoodsListCapsule t : goodsListCapsules) {
            GoodsListMpVo vo = new GoodsListMpVo();
            vo.setGoodsId(t.getGoodsId());
            vo.setGoodsName(t.getGoodsName());
            vo.setGoodsImg(getImgFullUrlUtil(t.getGoodsImg()));
            vo.setGoodsNumber(t.getGoodsNumber());
            vo.setGoodsSaleNum(t.getGoodsSaleNum() + t.getBaseSale());
            vo.setCommentNum(t.getCommentNum());
            vo.setDefaultPrd(t.getDefaultPrd());
            vo.setShopPrice(t.getShopPrice());
            vo.setLinePrice(t.getLinePrice());
            vo.setRealPrice(t.getRealPrice());

            if (t.getGoodsLabel() != null) {
                vo.setLabel(new GoodsLabelMpVo(t.getGoodsLabel().getName(),t.getGoodsLabel().getListPattern()));
            }
            t.setActivities(vo.getGoodsActivity());
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
