package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsShowStyleConfigBo;
import com.vpu.mp.service.pojo.wxapp.goods.search.*;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.goods.es.EsGoodsSearchMpService;
import com.vpu.mp.service.shop.goods.es.EsUtilSearchService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

/**
 * 小程序-商品搜索界面逻辑处理类
 * @author 李晓冰
 * @date 2020年03月11日
 */
@Service
@Slf4j
public class GoodsSearchMpService extends ShopBaseService {
    @Autowired
    EsGoodsSearchMpService esGoodsSearchMpService;
    @Autowired
    private EsUtilSearchService esUtilSearchService;

    @Autowired
    GoodsLabelMpService goodsLabelMpService;
    @Autowired
    public GoodsBrandSortMpService goodsBrandSortMp;
    @Autowired
    GoodsMpService goodsMpService;

    @Autowired
    GroupBuyService groupBuyService;
    @Autowired
    SeckillService seckillService;
    @Autowired
    private ShopCommonConfigService shopCommonConfigService;

    /**
     * 小程序端-商品搜索界面-可使用搜索条件数据初始化
     * 由ES反向推到可用数据
     *
     * @return {@link GoodsSearchFilterConditionMpVo}
     */
    public GoodsSearchFilterConditionMpVo getGoodsSearchFilterCondition() {
        if (esUtilSearchService.esState()) {
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
     *
     * @return {@link GoodsSearchFilterConditionMpVo}
     */
    private GoodsSearchFilterConditionMpVo getGoodsSearchFilterConditionFromDb() {
        GoodsSearchFilterConditionMpVo vo = new GoodsSearchFilterConditionMpVo();
        vo.setGoodsBrands(goodsBrandSortMp.getGoodsSearchFilterCondition());
        vo.setGoodsLabels(goodsLabelMpService.getGoodsSearchFilterCondition());
        return vo;
    }


    /**
     * 商品搜索接口统一入口，
     * 判断来自不同的pageFrom的搜索条件，然后调用对应的方法
     * @param param 搜索条件
     * @return 搜索内容
     */
    public GoodsSearchContentVo searchGoodsGate(GoodsSearchMpParam param) {
        PageResult<GoodsListMpBo> pageResult = null;
        if(param.getPageFrom() != null && param.getPageFrom() > 0){
            if (GoodsSearchMpParam.PAGE_FROM_GROUP_BUY.equals(param.getPageFrom())) {
                pageResult = searchGoodsForGroupBuyQrCode(param);
            } else if(GoodsSearchMpParam.PAGE_FROM_SEC_KILL.equals(param.getPageFrom())) {
                pageResult = searchGoodsForSecKillQrCode(param);
            }else{
                pageResult = searchGoods(param);
            }
        }else{
            pageResult = searchGoods(param);
        }


        goodsMpService.disposeGoodsList(pageResult.dataList, param.getUserId());
        GoodsShowStyleConfigBo goodsShowStyle = goodsMpService.getGoodsShowStyle();
        GoodsSearchContentVo vo = new GoodsSearchContentVo();
        vo.setDelMarket(goodsShowStyle.getDelMarket());
        vo.setShowCart(goodsShowStyle.getShowCart());
        vo.setPageResult(pageResult);
        return vo;
    }

    /**
     * admin拼团活动扫码进入
     * @param param GoodsSearchMpParam
     * @return 该活动下的有效商品信息
     */
    private PageResult<GoodsListMpBo> searchGoodsForGroupBuyQrCode(GoodsSearchMpParam param) {
        int activityId = param.getActId();
        Condition goodsBaseCondition = goodsMpService.getGoodsBaseCondition();
        List<Integer> goodsIds = groupBuyService.getGroupBuyCanUseGoodsIds(activityId, goodsBaseCondition);

        List<SortField<?>> sortFields = buildSearchOrderFields(param);

        return goodsMpService.findActivityGoodsListCapsulesDao(GOODS.GOODS_ID.in(goodsIds), sortFields, param.getCurrentPage(), param.getPageRows(), null);
    }

    /**
     * admin秒杀活动扫码进入
     * @param param GoodsSearchMpParam
     * @return 该活动下的有效商品信息
     */
    private PageResult<GoodsListMpBo> searchGoodsForSecKillQrCode(GoodsSearchMpParam param) {
        int activityId = param.getActId();
        Condition goodsBaseCondition = goodsMpService.getGoodsBaseCondition();
        List<Integer> goodsIds = seckillService.getSecKillCanUseGoodsIds(activityId, goodsBaseCondition);

        List<SortField<?>> sortFields = buildSearchOrderFields(param);

        return goodsMpService.findActivityGoodsListCapsulesDao(GOODS.GOODS_ID.in(goodsIds), sortFields, param.getCurrentPage(), param.getPageRows(), null);
    }

    /**
     * 搜索小程序商品信息
     * @param param 商品信息过滤条件
     * @return 搜索出来的商品信息
     */
    private PageResult<GoodsListMpBo> searchGoods(GoodsSearchMpParam param) {
        PageResult<GoodsListMpBo> pageResult = null;
        if (esUtilSearchService.esState()) {
            //店铺的默认商品排序规则
            if(shopCommonConfigService.getSearchSort().equals(Byte.valueOf((byte)1))){
                param.setShopSortItem(goodsMpService.getShopGoodsSortEnum());
                param.setShopSortDirection(SortDirectionEnum.DESC);
            }
            try {
                log.debug("小程序-es-搜索商品");
                log.info("es搜索排序-" + param.getShopSortItem());
                pageResult = esGoodsSearchMpService.queryGoodsByParam(param);
            } catch (Exception e) {
                log.debug("小程序-es-搜索商品-转换db获取数据:" + e.getMessage());
                pageResult = searchGoodsFromDb(param);
            }
        } else {
            log.debug("小程序-db-搜索商品");
            pageResult = searchGoodsFromDb(param);
        }
        return pageResult;
    }

    /**
     * 数据库搜索商品
     * @param param 搜索条件
     * @return 搜索到的内容
     */
    private PageResult<GoodsListMpBo> searchGoodsFromDb(GoodsSearchMpParam param) {

        Condition condition = buildSearchCondition(param);

        List<SortField<?>> sortFields = buildSearchOrderFields(param);

        return goodsMpService.findActivityGoodsListCapsulesDao(condition, sortFields, param.getCurrentPage(), param.getPageRows(), null);
    }

    /**
     * 商品搜索-db-条件拼接
     * @param param 搜索条件
     * @return
     */
    private Condition buildSearchCondition(GoodsSearchMpParam param) {
        Condition condition = goodsMpService.getGoodsBaseCondition();

        if (param.getKeyWords() != null) {
            condition = condition.and(GOODS.GOODS_NAME.like(likeValue(param.getKeyWords())));
        }

        // 当es挂掉的时候，只查询和商家分类直接关联的商品，不进行子项查询。（功能退级）
        if (param.getSortIds() != null && param.getSortIds().size() > 0) {
            condition = condition.and(GOODS.SORT_ID.in(param.getSortIds()));
        }

        if (param.getBrandIds() != null && param.getBrandIds().size() > 0) {
            condition = condition.and(GOODS.BRAND_ID.in(param.getBrandIds()));
        }

        // 从数据库搜索时仅匹配直接关联商品的标签和关联了全部商品的标签
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
     * @param param  请求条件
     * @return 待排序字段集合
     */
    protected List<SortField<?>> buildSearchOrderFields(GoodsSearchMpParam param) {
        List<SortField<?>> list = new ArrayList<>(2);

        if(param.getSortItem() != null){
            //目前用户可以指定销量和价格两种排序方式
            if (SortItemEnum.SALE_NUM.equals(param.getSortItem())) {
                if (SortDirectionEnum.DESC.equals(param.getSortDirection())) {
                    list.add(GOODS.GOODS_SALE_NUM.desc());
                } else {
                    list.add(GOODS.GOODS_SALE_NUM.asc());
                }
            } else {
                // 默认价格排序
                if (SortDirectionEnum.DESC.equals(param.getSortDirection())) {
                    list.add(GOODS.SHOP_PRICE.desc());
                } else {
                    list.add(GOODS.SHOP_PRICE.asc());
                }
            }
        }

        if(shopCommonConfigService.getSearchSort().equals(Byte.valueOf((byte)1))){
            list.add(goodsMpService.getShopGoodsSort());
        }
        log.info("db搜索排序-" + list);

        return list;
    }

}
