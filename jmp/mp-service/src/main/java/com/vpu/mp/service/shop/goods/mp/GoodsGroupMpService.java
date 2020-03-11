package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsGroupListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsShowStyleConfigBo;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchContentVo;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchMpParam;
import com.vpu.mp.service.shop.goods.es.EsGoodsSearchMpService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vpu.mp.db.shop.Tables.GOODS;

/**
 * @author 李晓冰
 * @date 2020年03月11日
 */
@Service
@Slf4j
public class GoodsGroupMpService extends ShopBaseService {
    @Autowired
    EsGoodsSearchMpService esGoodsSearchMpService;
    @Autowired
    GoodsLabelMpService goodsLabelMpService;
    @Autowired
    public GoodsBrandSortMpService goodsBrandSortMp;
    @Autowired
    GoodsMpService goodsMpService;
    @Autowired
    GoodsSearchMpService goodsSearchMpService;

    /**
     * 商品分组跳转至搜索页时，展示数据使用方法，和原搜索页过滤条件不同
     * @param param GoodsSearchMpParam 搜索条件，由商品分组页跳转时传递的
     * @return GoodsSearchContentVo
     */
    public GoodsSearchContentVo getGoodsGroupListFromGoodsSearch(GoodsSearchMpParam param) {
        Condition condition = buildGoodsGroupCondition(param.getGoodsIds(), param.getSortIds(), param.getLabelIds(), param.getBrandIds());

        List<SortField<?>> sortFields =goodsSearchMpService.buildSearchOrderFields(param);

        PageResult<GoodsListMpBo> pageResult = goodsMpService.findActivityGoodsListCapsulesDao(condition, sortFields, param.getCurrentPage(), param.getPageRows(), null);

        goodsMpService.disposeGoodsList(pageResult.dataList, param.getUserId());
        GoodsShowStyleConfigBo goodsShowStyle = goodsMpService.getGoodsShowStyle();

        GoodsSearchContentVo vo = new GoodsSearchContentVo();
        vo.setDelMarket(goodsShowStyle.getDelMarket());
        vo.setShowCart(goodsShowStyle.getShowCart());
        vo.setPageResult(pageResult);
        return vo;
    }

    /**
     * 商品分组组件获取数据,会将满足{@link GoodsGroupListMpParam#getSortGroupArr()}列表内的过滤条的所有数据都取出
     * 当且仅当条件时查询某一个分组且通过制定商品id展示数据时才会对数据进行排序
     * @param param 需要满的过滤条件集合
     * @return 满足条件的商品信息
     */
    public List<? extends GoodsListMpVo> getGoodsGroupList(GoodsGroupListMpParam param) {
        List<GoodsGroupListMpParam.SortGroup> sortGroupArr = param.getSortGroupArr();
        // 当查询某一分组的数据，且该分组是指定商品的时候会被放入id值，否则一直为null
        List<Integer> needOrderGoodsIds = null;
        List<Integer> goodsIds = new ArrayList<>(6);
        List<Integer> sortIds = new ArrayList<>(6);
        List<Integer> brandIds = new ArrayList<>(6);
        List<Integer> labelIds = new ArrayList<>(6);

        for (GoodsGroupListMpParam.SortGroup sortGroup : sortGroupArr) {
            if (GoodsGroupListMpParam.SECTION_POINT_GOODS.equals(sortGroup.getIsAll())) {
                List<Integer> ids = Stream.of(sortGroup.getGroupGoodsId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
                goodsIds.addAll(ids);
                if (sortGroupArr.size() == 1) {
                    needOrderGoodsIds = new ArrayList<>(6);
                    needOrderGoodsIds.addAll(ids);
                }
            } else {
                GoodsListMpParam goodsListMpParam = new GoodsListMpParam();
                goodsListMpParam.setRecommendType(GoodsConstant.AUTO_RECOMMEND);
                goodsListMpParam.setPageRows(GoodsGroupListMpParam.NUM_TO_SHOW);
                // 标签
                if (GoodsGroupListMpParam.LABEL_TYPE.equals(sortGroup.getSortType())) {
                    labelIds.add(sortGroup.getSortId());
                } else if (GoodsGroupListMpParam.BRAND_TYPE.equals(sortGroup.getSortType())) {
                    // 品牌
                    brandIds.add(sortGroup.getSortId());
                } else {
                    // 商家分类
                    List<Integer> targetIds = goodsBrandSortMp.getChildrenIds(sortGroup.getSortId());
                    sortIds.addAll(targetIds);
                }
            }
        }
        Condition condition = buildGoodsGroupCondition(goodsIds, sortIds, labelIds, brandIds);
        PageResult<GoodsListMpBo> pageResult = goodsMpService.findActivityGoodsListCapsulesDao(condition, null, 1, GoodsGroupListMpParam.NUM_TO_SHOW, needOrderGoodsIds);
        goodsMpService.disposeGoodsList(pageResult.getDataList(), param.getUserId());
        return pageResult.getDataList();
    }

    /**
     * 拼接商品分组所需要的过滤条件
     * @param goodsIds 商品id集合
     * @param sortIds 平台分类id集合
     * @param labelIds 标签id集合
     * @param brandIds 品牌id集合
     * @return 过滤条件
     */
    private Condition buildGoodsGroupCondition(List<Integer> goodsIds, List<Integer> sortIds,List<Integer> labelIds,List<Integer> brandIds){
        // 获取在售商品
        Condition condition = GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(GOODS.IS_ON_SALE.eq(GoodsConstant.ON_SALE));
        boolean showSoldOutGoods = goodsMpService.canShowSoldOutGoods();
        // 不展示售罄
        if (!showSoldOutGoods) {
            condition = condition.and(GOODS.GOODS_NUMBER.gt(0));
        }

        List<Integer> catIds = new ArrayList<>(0);
        // 判断是否有关联全部商品的标签
        if (labelIds.size() > 0) {
            List<Integer> allIds = goodsLabelMpService.getGoodsLabelCouple(labelIds, GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());
            if (allIds.size() == 0) {
                sortIds.addAll(goodsLabelMpService.getGoodsLabelCouple(labelIds, GoodsLabelCoupleTypeEnum.SORTTYPE.getCode()));
                goodsIds.addAll(goodsLabelMpService.getGoodsLabelCouple(labelIds, GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()));
                catIds.addAll(goodsLabelMpService.getGoodsLabelCouple(labelIds, GoodsLabelCoupleTypeEnum.CATTYPE.getCode()));
            } else {
                return condition;
            }
        }
        Condition idCondition = DSL.falseCondition();
        if (goodsIds.size() > 0) {
            idCondition = idCondition.or(GOODS.GOODS_ID.in(goodsIds));
        }
        if (sortIds.size() > 0) {
            idCondition = idCondition.or(GOODS.SORT_ID.in(sortIds));
        }
        if (brandIds.size() > 0) {
            idCondition = idCondition.or(GOODS.BRAND_ID.in(brandIds));
        }
        if (catIds.size() > 0) {
            idCondition = idCondition.or(GOODS.CAT_ID.in(catIds));
        }
        condition = condition.and(idCondition);

        return condition;
    }

}
