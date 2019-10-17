package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsGradePrd;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.market.seckill.SecKillProductVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.saas.categroy.SysCatServiceHelper;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import com.vpu.mp.service.shop.goods.*;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;

/**
 * EsGoods相关操作
 * @author 卢光耀
 * @date 2019/10/9 10:23 上午
*/
@Slf4j
@Service
public class EsGoodsCreateService extends BaseShopConfigService {

    @Autowired
    private EsManager esManager;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GroupBuyService groupBuyService;
    @Autowired
    private BargainService bargainService;
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private ReducePriceService reducePriceService;
    @Autowired
    private PreSaleService preSaleService;
    @Autowired
    private GoodsSpecProductService goodsSpecProductService;
    @Autowired
    private GoodsSortService goodsSortService;
    @Autowired
    private GoodsBrandService goodsBrandService;
    @Autowired
    private GoodsPriceService goodsPriceService;

    public void batchCreateEsGoodsIndex( List<Integer> goodsIds,Integer shopId){
        List<EsGoods> esGoodsList = new ArrayList<>(goodsIds.size());
        Map<Integer, GoodsRecord> goodsMap = goodsService.getGoodsByIds(goodsIds);
        Map<Integer,Byte> goodsTypeMap = new HashMap<>(goodsMap.size());
        Map<Integer,Integer> goodsCatMap = new HashMap<>(goodsMap.size());
        Set<Integer> goodsSortIdSet = new HashSet<>(goodsMap.size());
        Set<Integer> goodsBrandIdSet = new HashSet<>(goodsMap.size());
        goodsMap.forEach((k,v)->{
            goodsTypeMap.put(k,v.getGoodsType());
            if( null!=v.getCatId() ){
                goodsCatMap.put(k,v.getCatId());
            }
            if( null!=v.getSortId() ){
                goodsSortIdSet.add(v.getSortId());
            }
            if( null!=v.getBrandId() ){
                goodsBrandIdSet.add(v.getBrandId());
            }
        });
        Map<Integer,List<GoodsGradePrd>> goodsGradePrdMap = goodsService.selectGoodsGradePrdByGoodsIds(goodsIds);
        Map<Integer,BigDecimal>  goodsShowPriceMap = goodsPriceService.getShowPriceByIdAndType(goodsTypeMap);
        Map<Integer, Result<GoodsSpecProductRecord>> goodsProductMap = goodsSpecProductService.selectByGoodsIds(goodsIds);
        Map<Integer,List<SysCatevo>> goodsCatInfoMap = getCatInfoByGoodsIds(goodsCatMap);
        Map<Integer,Sort> sortMap =  batchAssemblySortInfo(goodsSortIdSet);
        Map<Integer, GoodsBrandVo> brandMap =  batchAssemblyBrandAndSale(goodsBrandIdSet);
        for( Integer goodsId: goodsIds ){
            if( !goodsMap.containsKey(goodsId) ){
                log.error("\n+批量建立索引--->商品【{}】未找到无法建立索引",goodsId);
                break;
            }
            GoodsRecord goods = goodsMap.get(goodsId);
            EsGoods esGoods = new EsGoods();
            esGoods.setShopId(shopId);
            BeanUtils.copyProperties(goods,esGoods);
            if( validationMap(goodsGradePrdMap,goodsId) ){
                assemblyVipPriceImp(esGoods,goodsGradePrdMap.get(goodsId));
            }
            if( validationMap(goodsShowPriceMap,goodsId) ){
                esGoods.setShowPrice(goodsShowPriceMap.get(goodsId));
            }else{
                esGoods.setShowPrice(esGoods.getShopPrice());
            }
            if( validationMap(goodsProductMap,goodsId) ){
                esGoods.setGoodsSpecs(goodsProductMap.get(goodsId).stream()
                    .map(GoodsSpecProductRecord::getPrdSn)
                    .collect(Collectors.joining(","))
                );
            }
            if( validationMap(goodsCatInfoMap,goodsId) ){
                List<SysCatevo> list = goodsCatInfoMap.get(goodsId);
                batchAssemblyCatInfoImp(esGoods,list);
            }
            if( validationMap(sortMap,esGoods.getSortId()) ){
                List<Sort> allSort = new ArrayList<>(3);
                getSort(esGoods.getSortId(),allSort,sortMap);
                assemblySortInfoImp(esGoods,allSort);
            }
            if( validationMap(brandMap,esGoods.getBrandId()) ){
                esGoods.setBrandName(brandMap.get(esGoods.getBrandId()).getBrandName());
            }
            esGoodsList.add(esGoods);
        }
        batchCommitEsGoodsIndex(esGoodsList);

    }
    private boolean validationMap(Map<Integer,?> map,Integer goodsId){
        return !map.isEmpty() && map.containsKey(goodsId);
    }
    /**
     * 单个goodsId封装EsGoods所需信息
     * @param goodsId 商品id
     */
    public void createEsGoodsIndex(Integer goodsId){
        EsGoods esGoods = new EsGoods();
        esGoods.setShopId(getShopId());
        Optional<GoodsRecord> goodsRecordOptional= goodsService.getGoodsById(goodsId);
        if( !goodsRecordOptional.isPresent() ){
            return ;
        }
        GoodsRecord goods = goodsRecordOptional.get();
        BeanUtils.copyProperties(goods,esGoods);
        //封装会员价格
        assemblyVipPrice(esGoods);
        //封装展示价格
        assemblyShowPrice(esGoods);
        //封装商家编码
        assemblyPrdSns(esGoods);
        //封装平台分类相关信息
        assemblyCatInfo(esGoods);
        //封装商家分类相关信息
        assemblySortInfo(esGoods);
        //封装品牌、销量
        assemblyBrandAndSale(esGoods);


        createEsGoodsIndex(EsGoodsConstant.INDEX_NAME,esGoods);
    }
    private void assemblyBrandAndSale(EsGoods esGoods){
        Integer brandId = esGoods.getBrandId();
        esGoods.setBrandName(goodsBrandService.listGoodsBrandNameByIds(Collections.singletonList(brandId)).get(brandId).getBrandName());
    }
    private Map<Integer, GoodsBrandVo> batchAssemblyBrandAndSale(Set<Integer> brandIds){
        return goodsBrandService.listGoodsBrandNameByIds(new ArrayList<>(brandIds));
    }
    /**
     * 封装商家分类相关信息
     * @param sortIds
     */
    private Map<Integer,Sort> batchAssemblySortInfo(Set<Integer> sortIds){
        return goodsSortService.getParentSortsByChildId(new ArrayList<>(sortIds));
    }
    private void getSort(Integer sortId,List<Sort> result,Map<Integer,Sort> allSortMap){
        if( allSortMap.containsKey(sortId) ){
            return ;
        }
        Sort sort = allSortMap.get(sortId);
        result.add(sort);
        if(sort.getParentId() == 0 ){
            return ;
        }
        getSort(sort.getParentId(),result,allSortMap);
    }
    /**
     * 封装商家分类相关信息
     * @param esGoods
     */
    private void assemblySortInfo(EsGoods esGoods){
        Integer sortId = esGoods.getSortId();
        if( sortId == null ){
            log.error("商品【{}】没有设置商家分类",esGoods.getGoodsId());
            return ;
        }
        List<Sort> list =new ArrayList<>(goodsSortService.getParentSortsByChildId(Collections.singletonList(sortId)).values());
        assemblySortInfoImp(esGoods,list);
    }

    /**
     * 封装商家分类相关信息实现
     * @param esGoods
     * @param list
     */
    private void assemblySortInfoImp(EsGoods esGoods,List<Sort> list){
        StringBuilder sortName = new StringBuilder();
        for(Sort sort: list){
            if( sort != null ){
                sortName.append(sort.getSortName()).append(" ");
                if( sort.getLevel() == 0 ){
                    esGoods.setFirstSortId(sort.getSortId());
                }else if( sort.getLevel() == 1 ){
                    esGoods.setSecondSortId(sort.getSortId());
                }
            }
            esGoods.setSortName(sortName.toString());
        }
    }
    private Map<Integer,List<SysCatevo>> getCatInfoByGoodsIds(Map<Integer,Integer> param){
        Map<Integer,List<SysCatevo>> resultMap = new HashMap<>(param.size());
        param.forEach((k,v)->{
            List<SysCatevo> list = SysCatServiceHelper.getSysCateVosByCatId(v);
            resultMap.put(k,list);
        });
        return resultMap;
    }

    /**
     * 封装平台分类相关信息
     * @param esGoods  ES映射实体类
     */
    private void assemblyCatInfo(EsGoods esGoods) {
        List<SysCatevo> list = SysCatServiceHelper.getSysCateVosByCatId(esGoods.getCatId());
        if( list.isEmpty() ){
            return ;
        }
        batchAssemblyCatInfoImp(esGoods,list);
    }
    private void batchAssemblyCatInfoImp(EsGoods esGoods,List<SysCatevo> list){
        StringBuilder categoryName = new StringBuilder();
        list.forEach(x->{
            categoryName.append(x.getCatName()).append(" ");
            if( x.getLevel() == 0 ){
                esGoods.setFirstCatId(x.getCatId());
            }else if( x.getLevel() == 1 ){
                esGoods.setSecondCatId(x.getCatId());
            }
        });
        esGoods.setCatName(categoryName.toString());
    }
    public void assemblyCatInfo(Integer  esGoods) {
        List<Integer> list = new ArrayList<>();
        list.add(73);
        list.add(74);
        List<SysCatevo> list1 = SysCatServiceHelper.getSysCateVoByCatIds(list);
        System.out.println(list.size());
    }

    /**
     * 封装商家编码
     * @param esGoods ES映射实体类
     */
    private void assemblyPrdSns(EsGoods esGoods) {
        List<GoodsSpecProduct> list = goodsSpecProductService.selectByGoodsId(esGoods.getGoodsId());
        if( list.isEmpty() ){
            return ;
        }
        esGoods.setGoodsSpecs(list.stream().map(GoodsSpecProduct::getPrdSn).collect(Collectors.joining(",")));
    }

    private void batchAssemblyShowPrice(List<EsGoods> goodsList){
        Map<Byte,List<Integer>> goodsTypeMap = new HashMap<>();
        goodsList.forEach(x->{
            Byte type = x.getGoodsType();
            List<Integer> list;
            if( goodsTypeMap.containsKey(type) ){
                list = goodsTypeMap.get(type);
            }else{
                list = new ArrayList<>();
            }
            list.add(x.getGoodsId());
            goodsTypeMap.put(type,list);
        });

    }

    /**
     * 封装展示价格
     * @param esGoods ES映射实体类
     */
    private void assemblyShowPrice(EsGoods esGoods){
        Byte goodsType = esGoods.getGoodsType();
        Integer goodsId = esGoods.getGoodsId();
        BigDecimal shopPrice = esGoods.getShopPrice();
        BigDecimal showPrice = null;
        Timestamp now = DateUtil.getLocalDateTime();
        if( goodsType.equals(OrderConstant.GOODS_TYPE_PIN_GROUP) ){
            List<Record2<Integer,BigDecimal>> resultList = groupBuyService.getGroupBuyProductByGoodsId(goodsId,now);
            if( !resultList.isEmpty() ){
                List<BigDecimal> showPriceList = resultList.stream()
                    .map(x->x.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE))
                    .sorted(BigDecimal::compareTo)
                    .collect(Collectors.toList());
                showPrice = showPriceList.get(0);
            }else{
                outPutLog(now,goodsId,goodsType);
            }
        }
        if ( goodsType.equals(OrderConstant.GOODS_TYPE_BARGAIN) ){
            BargainRecord record = bargainService.getBargainRecordByGoodsId(goodsId,now);
            if( null != record ){
                showPrice = record.getBargainType().equals(BargainService.BARGAIN_TYPE_RANDOM)?
                    record.getFloorPrice():record.getExpectationPrice();
            }else{
                outPutLog(now,goodsId,goodsType);
            }
        }
        if( goodsType.equals(OrderConstant.GOODS_TYPE_SECKILL) ){
            Integer secId =seckillService.getSecKillIdByGoodsId(goodsId,now);
            if( secId != null ){
                List<SecKillProductVo> resultList = seckillService.getSecKillProductVo(secId);
                showPrice = resultList.stream()
                    .map(SecKillProductVo::getSecKillPrice)
                    .sorted(BigDecimal::compareTo)
                    .collect(Collectors.toList())
                    .get(0);
            }else{
                outPutLog(now,goodsId,goodsType);
            }
        }
        if( goodsType.equals(OrderConstant.GOODS_TYPE_REDUCE_PRICE) ){
            BigDecimal resultPrice = reducePriceService.getShowPriceByGoodsId(goodsId,now);
            if( null != resultPrice ){
                showPrice = resultPrice;
            }else{
                outPutLog(now,goodsId,goodsType);
            }
        }
        if( goodsType.equals(OrderConstant.GOODS_TYPE_PRE_SALE) ){
            Optional<Record2<Integer,BigDecimal>> record2Optional =
                preSaleService.getPresaleProductRecordByGoodsId(goodsId,now);
            if( record2Optional.isPresent() ){
                showPrice = record2Optional.get().get(PRESALE_PRODUCT.PRESALE_PRICE);
            }else{
                outPutLog(now,goodsId,goodsType);
            }
        }
        if( showPrice != null ){
            esGoods.setShowPrice(showPrice);
        }else{
            esGoods.setShowPrice(shopPrice);
        }
    }

    /**
     * 封装会员价格
     * @param esGoods target
     */
    private void assemblyVipPrice(EsGoods esGoods) {
        List<GoodsGradePrd> goodsGradePrdList = goodsService.selectGoodsGradePrd(esGoods.getGoodsId());
        assemblyVipPriceImp(esGoods,goodsGradePrdList);
    }
    private void assemblyVipPriceImp(EsGoods esGoods,List<GoodsGradePrd> goodsGradePrdList){
        if( !goodsGradePrdList.isEmpty() ){
            for( GoodsGradePrd goodsGradePrd: goodsGradePrdList ){
                Field v;
                try {
                    v = esGoods.getClass().getDeclaredField(goodsGradePrd.getGrade());
                    v.setAccessible(true);
                    v.set(esGoods,goodsGradePrd.getGradePrice());
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    log.error("建立esGoods【id={}】索引时封装会员价失败",esGoods.getGoodsId());
                    e.printStackTrace();
                }
            }
        }
    }

    private void outPutLog(Timestamp now,Integer goodsId,Byte goodsType){
        log.error("{}商品【{}】是{}类型但没找到相关活动",now,goodsId,goodsType);
    }
    private void createEsGoodsIndex(String indexName,EsGoods goods){
        esManager.createIndexAsync(indexName,goods);
    }
    private void batchCommitEsGoodsIndex(List<EsGoods> list){
        BulkRequest requests = new BulkRequest();
        for( EsGoods goods: list ){
            requests.add(esManager.assemblyRequest(EsGoodsConstant.INDEX_NAME,goods));
        }
        try {
            esManager.batchCreateDocuments(requests);
        } catch (IOException e) {
            log.error("批量建立索引失败");
        }
    }
}
