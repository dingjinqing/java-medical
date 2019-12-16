package com.vpu.mp.service.shop.goods.es;

import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandSelectListVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsGradePrd;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelAndCouple;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.market.seckill.SecKillProductVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.video.GoodsVideoBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.saas.categroy.SysCatServiceHelper;
import com.vpu.mp.service.shop.goods.*;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;

@Service
@Slf4j
public class EsAssemblyDataService extends ShopBaseService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsSpecProductService goodsSpecProductService;
    @Autowired
    private GoodsSortService goodsSortService;
    @Autowired
    private GoodsBrandService goodsBrandService;
    @Autowired
    private GoodsPriceService goodsPriceService;
    @Autowired
    private GoodsLabelService goodsLabelService;
    @Autowired
    private ImageService imageService;


    public List<EsGoods> assemblyEsGoods(List<Integer> goodsIds, Integer shopId) {
        List<EsGoods> esGoodsList = new ArrayList<>(goodsIds.size());
        Map<Integer, GoodsRecord> goodsMap = goodsService.getGoodsByIds(goodsIds);
        Map<Integer, Byte> goodsTypeMap = new HashMap<>(goodsMap.size());
        Map<Integer, Integer> goodsCatMap = new HashMap<>(goodsMap.size());
        Set<Integer> goodsSortIdSet = new HashSet<>(goodsMap.size());
        Set<Integer> goodsBrandIdSet = new HashSet<>(goodsMap.size());
        goodsMap.forEach((k, v) -> {
            goodsTypeMap.put(k, v.getGoodsType());
            if (null != v.getCatId()) {
                goodsCatMap.put(k, v.getCatId());
            }
            if (null != v.getSortId()) {
                goodsSortIdSet.add(v.getSortId());
            }
            if (null != v.getBrandId()) {
                goodsBrandIdSet.add(v.getBrandId());
            }
        });
        Map<Integer, List<GoodsGradePrd>> goodsGradePrdMap = goodsService.selectGoodsGradePrdByGoodsIds(goodsIds);
        Map<Integer, GoodsVideoBo> goodsVideoMap = goodsService.getGoodsVideo(goodsIds);
        Map<Integer,List<String>> imageUrlMap = goodsService.getGoodsImageList(goodsIds);
        Map<Integer, BigDecimal> goodsShowPriceMap = goodsPriceService.getShowPriceByIdAndType(goodsTypeMap);
        Map<Integer, Result<GoodsSpecProductRecord>> goodsProductMap = goodsSpecProductService.selectByGoodsIds(goodsIds);
        Map<Integer, List<SysCatevo>> goodsCatInfoMap = getCatInfoByGoodsIds(goodsCatMap);
        Map<Integer, Sort> sortMap = batchAssemblySortInfo(goodsSortIdSet);
        Map<Integer, GoodsBrandSelectListVo> brandMap = batchAssemblyBrandAndSale(goodsBrandIdSet);
        Map<Integer, Map<Byte, List<Integer>>> goodsLabelFilterMap = new HashMap<>(goodsIds.size());
        for (Integer goodsId : goodsIds) {
            if (!goodsMap.containsKey(goodsId)) {
                log.error("\n+批量建立索引--->商品【{}】未找到无法建立索引", goodsId);
                break;
            }
            Map<Byte, List<Integer>> goodsLabelFilter = new HashMap<>();
            goodsLabelFilter.put(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode(), Collections.singletonList(goodsId));
            GoodsRecord goods = goodsMap.get(goodsId);
            EsGoods esGoods = assemblyEsGoods(goods, shopId);

            if (validationMap(goodsGradePrdMap, goodsId)) {
                assemblyVipPriceImp(esGoods, goodsGradePrdMap.get(goodsId));
            }
            if( validationMap(imageUrlMap,goodsId) ){
                esGoods.setSecondaryGoodsImages(imageUrlMap.get(goodsId));
            }
            if( validationMap(goodsVideoMap,goodsId) ){
                esGoods.setVideoInfo(Util.toJson(goodsVideoMap.get(goodsId)));
            }
            if (validationMap(goodsShowPriceMap, goodsId)) {
                esGoods.setShowPrice(goodsShowPriceMap.get(goodsId));
            } else {
                esGoods.setShowPrice(esGoods.getShopPrice());
            }
            if (validationMap(goodsProductMap, goodsId)) {
                List<GoodsSpecProductRecord> list = goodsProductMap.get(goodsId);
                list.sort(Comparator.comparing(GoodsSpecProductRecord::getPrdPrice));
                List<BigDecimal> specPrdPrices = Lists.newLinkedList();
                List<GoodsPrdMpVo> voList = Lists.newArrayList();
                StringBuilder prdSns = new StringBuilder();
                if( list.size() == 1 && StringUtils.isBlank(list.get(0).getPrdSpecs()) ){
                    esGoods.setDefPrd(true);
                }else{
                    esGoods.setDefPrd(false);
                }
                list.forEach(x -> {
                    voList.add(new GoodsPrdMpVo(x));
                    specPrdPrices.add(x.getPrdPrice());
                    prdSns.append(x.getPrdSn()).append(",");
                });
                esGoods.setPrdSns(prdSns.toString());
                int length = specPrdPrices.size();
                esGoods.setPrdJson(Util.toJson(voList));
                esGoods.setMaxSpecPrdPrices(specPrdPrices.get(length - 1));
                esGoods.setMinSpecPrdPrices(specPrdPrices.get(0));

            }
            if (validationMap(goodsCatInfoMap, goodsId)) {
                List<SysCatevo> list = goodsCatInfoMap.get(goodsId);
                goodsLabelFilter.put(GoodsLabelCoupleTypeEnum.CATTYPE.getCode(),
                    list.stream().map(SysCatevo::getCatId).collect(Collectors.toList()));
                batchAssemblyCatInfoImp(esGoods, list);
            }
            if (validationMap(sortMap, esGoods.getSortId())) {
                List<Sort> allSort = new ArrayList<>(3);
                getSort(esGoods.getSortId(), allSort, sortMap);
                goodsLabelFilter.put(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode(),
                    allSort.stream().map(Sort::getSortId).collect(Collectors.toList()));
                assemblySortInfoImp(esGoods, allSort);
            }
            if (validationMap(brandMap, esGoods.getBrandId())) {
                esGoods.setBrandName(brandMap.get(esGoods.getBrandId()).getBrandName());
            }
            goodsLabelFilterMap.put(goodsId, goodsLabelFilter);
            esGoodsList.add(esGoods);
        }
        Map<Integer, List<Integer>> goodsLabelMap = assemblyGoodsLabel(goodsLabelFilterMap, goodsIds, goodsCatInfoMap, sortMap);
        esGoodsList.forEach(x -> {
            if (validationMap(goodsLabelMap, x.getGoodsId())) {
                x.setGoodsLabel(goodsLabelMap.get(x.getGoodsId()));
            }
        });
        return esGoodsList;
    }


    private Map<Integer, GoodsBrandSelectListVo> batchAssemblyBrandAndSale(Set<Integer> brandIds) {
        return goodsBrandService.listGoodsBrandNameByIds(new ArrayList<>(brandIds));
    }

    /**
     * 封装商家分类相关信息
     *
     * @param sortIds
     */
    private Map<Integer, Sort> batchAssemblySortInfo(Set<Integer> sortIds) {
        return goodsSortService.getParentSortsByChildId(new ArrayList<>(sortIds));
    }

    private void getSort(Integer sortId, List<Sort> result, Map<Integer, Sort> allSortMap) {
        if (!allSortMap.containsKey(sortId)) {
            return;
        }
        Sort sort = allSortMap.get(sortId);
        result.add(sort);
        if (sort.getParentId() == 0) {
            return;
        }
        getSort(sort.getParentId(), result, allSortMap);
    }

    private EsGoods assemblyEsGoods(GoodsRecord goods, Integer shopId) {
        EsGoods esGoods = new EsGoods();
        BeanUtils.copyProperties(goods, esGoods);
        esGoods.setFreightTemplateId(goods.getDeliverTemplateId());
        esGoods.setShopId(shopId);
        esGoods.setGoodsImg(imageService.imageUrl(goods.getGoodsImg()));
        esGoods.setAddEsDate(DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, new Date()));
        esGoods.setUpdateDate(DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, goods.getUpdateTime()));
        return esGoods;
    }


    /**
     * 封装商家分类相关信息实现
     *
     * @param esGoods
     * @param list
     */
    private void assemblySortInfoImp(EsGoods esGoods, List<Sort> list) {
        StringBuilder sortName = new StringBuilder();
        for (Sort sort : list) {
            if (sort != null) {
                sortName.append(sort.getSortName()).append(" ");
                if (sort.getLevel() == 0) {
                    esGoods.setFirstSortId(sort.getSortId());
                } else if (sort.getLevel() == 1) {
                    esGoods.setSecondSortId(sort.getSortId());
                }
            }
            esGoods.setSortName(sortName.toString());
        }
    }

    private Map<Integer, List<SysCatevo>> getCatInfoByGoodsIds(Map<Integer, Integer> param) {
        Map<Integer, List<SysCatevo>> resultMap = new HashMap<>(param.size());
        param.forEach((k, v) -> {
            List<SysCatevo> list = SysCatServiceHelper.getSysCateVosByCatId(v);
            resultMap.put(k, list);
        });
        return resultMap;
    }


    private void batchAssemblyCatInfoImp(EsGoods esGoods, List<SysCatevo> list) {
        StringBuilder categoryName = new StringBuilder();
        list.forEach(x -> {
            categoryName.append(x.getCatName()).append(" ");
            if (x.getLevel() == 0) {
                esGoods.setFirstCatId(x.getCatId());
            } else if (x.getLevel() == 1) {
                esGoods.setSecondCatId(x.getCatId());
            } else if (x.getLevel() == 2) {
                esGoods.setThirdCatId(x.getCatId());
            }
        });
        esGoods.setCatName(categoryName.toString());
    }

    private void batchAssemblyShowPrice(List<EsGoods> goodsList) {
        Map<Byte, List<Integer>> goodsTypeMap = new HashMap<>();
        goodsList.forEach(x -> {
            Byte type = x.getGoodsType();
            List<Integer> list;
            if (goodsTypeMap.containsKey(type)) {
                list = goodsTypeMap.get(type);
            } else {
                list = new ArrayList<>();
            }
            list.add(x.getGoodsId());
            goodsTypeMap.put(type, list);
        });

    }


    private void assemblyVipPriceImp(EsGoods esGoods, List<GoodsGradePrd> goodsGradePrdList) {
        if (!goodsGradePrdList.isEmpty()) {
            for (GoodsGradePrd goodsGradePrd : goodsGradePrdList) {
                if(StringUtils.isBlank(goodsGradePrd.getGrade())){
                    continue ;
                }
                try {
                    StringBuilder vipLevelPrice;
                    Field v = esGoods.getClass().getDeclaredField(goodsGradePrd.getGrade());
                    v.setAccessible(true);
                    if( v.get(esGoods) == null ){
                        vipLevelPrice = new StringBuilder();
                    }else{
                        vipLevelPrice = new StringBuilder(v.get(esGoods).toString());
                    }
                    vipLevelPrice.append(goodsGradePrd.getPrdId()).
                        append(":").
                        append(goodsGradePrd.getGradePrice()).
                        append(";");
                    v.set(esGoods,vipLevelPrice.toString());
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("建立esGoods【id={}】索引时封装会员价失败", esGoods.getGoodsId());
                    e.printStackTrace();
                }
            }
        }
    }

    private void outPutLog(Timestamp now, Integer goodsId, Byte goodsType) {
        log.error("{}商品【{}】是{}类型但没找到相关活动", now, goodsId, goodsType);
    }


    public static void main(String[] args) {
        EsGoods goods = new EsGoods();
        goods.setV1("123");
        try {
            Field v = EsGoods.class.getDeclaredField("v1");
            v.setAccessible(true);
            v.set(goods,"321");
            System.out.println(v.get(goods).toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void assemblyGoodsLabelMap(Map<Integer, List<GoodsLabelCoupleRecord>> sortForLabelMap) {

    }

    private Map<Integer, List<Integer>> assemblyGoodsLabel(Map<Integer, Map< Byte, List<Integer>>> goodsLabelFilterMap, List<Integer> goodsIds,
                                                           Map<Integer, List<SysCatevo>> categoryMap, Map<Integer, Sort> sortMap) {
        Map<Integer, List<Integer>> resultMap = new HashMap<>();
        Set<Integer> categoryIdSet = new HashSet<>(categoryMap.size() * 3);
        categoryMap.values()
            .forEach(
                x ->
                    categoryIdSet.addAll(
                        x.stream().
                            map(SysCatevo::getCatId).
                            collect(Collectors.toList())
                    )
            );
        List<Integer> categoryIds = new ArrayList<>(categoryIdSet);
        List<Integer> sortIds = new ArrayList<>(sortMap.keySet());
        Map<Byte, List<GoodsLabelAndCouple>> typeRecord =
            goodsLabelService.getGoodsLabelByFilter(goodsIds, sortIds, categoryIds);

        Map<Integer, List<GoodsLabelAndCouple>> sortForLabelMap = new HashMap<>();
        Map<Integer, List<GoodsLabelAndCouple>> categoryForLabelMap = new HashMap<>();
        Map<Integer, List<GoodsLabelAndCouple>> goodsForLabelMap = new HashMap<>();
        List<GoodsLabelAndCouple> allGoodsForLabelList = new ArrayList<>();
        typeRecord.forEach((type, records) -> {
            if (type.equals(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode())) {
                goodsForLabelMap.putAll(records.stream().collect(Collectors.groupingBy(GoodsLabelAndCouple::getGtaId)));
            } else if (type.equals(GoodsLabelCoupleTypeEnum.CATTYPE.getCode())) {
                categoryForLabelMap.putAll(records.stream().collect(Collectors.groupingBy(GoodsLabelAndCouple::getGtaId)));
            } else if (type.equals(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode())) {
                sortForLabelMap.putAll(records.stream().collect(Collectors.groupingBy(GoodsLabelAndCouple::getGtaId)));
            } else if (type.equals(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode())) {
                allGoodsForLabelList.addAll(records);
            }
        });

        goodsLabelFilterMap.forEach((goodsId, filterMap) -> {
            List<GoodsLabelAndCouple> list = new ArrayList<>();
            filterMap.forEach((type, ids) -> {
                if (type.equals(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode())) {

                    ids.forEach(x -> {
                        if (goodsForLabelMap.containsKey(x)) {
                            list.addAll(goodsForLabelMap.get(x));
                        }
                    });
                } else if (type.equals(GoodsLabelCoupleTypeEnum.CATTYPE.getCode())) {
                    ids.forEach(x -> {
                        if (categoryForLabelMap.containsKey(x)) {
                            list.addAll(categoryForLabelMap.get(x));
                        }
                    });
                } else if (type.equals(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode())) {
                    ids.forEach(x -> {
                        if (sortForLabelMap.containsKey(x)) {
                            list.addAll(sortForLabelMap.get(x));
                        }
                    });
                }
            });
            list.addAll(allGoodsForLabelList);
            if (list.size() > 5) {
                //优先级降序
                Comparator<GoodsLabelAndCouple> byLevelDesc = Comparator.comparing(GoodsLabelAndCouple::getLevel).reversed();
                //创建时间降序
                Comparator<GoodsLabelAndCouple> byTimeDesc = Comparator.comparing(GoodsLabelAndCouple::getCreateTime).reversed();
                Comparator<GoodsLabelAndCouple> allComparator = byLevelDesc.thenComparing(byTimeDesc);
                resultMap.put(goodsId, list.stream().sorted(allComparator).limit(5).map(GoodsLabelAndCouple::getLabelId).collect(Collectors.toList()));
            } else {
                resultMap.put(goodsId, list.stream().map(GoodsLabelAndCouple::getLabelId).collect(Collectors.toList()));
            }
        });
        return resultMap;

    }

    private boolean validationMap(Map<Integer, ?> map, Integer goodsId) {
        return map != null && !map.isEmpty() && map.containsKey(goodsId);
    }
}
