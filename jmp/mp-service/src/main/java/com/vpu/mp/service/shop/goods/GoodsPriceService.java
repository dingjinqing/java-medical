package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;

/**
 * 商品获取展示价格通用service
 * @author 卢光耀
 * @date 2019/10/15 4:44 下午
 *
*/
@Service
@Slf4j
public class GoodsPriceService extends ShopBaseService {

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

    public Map<Integer, BigDecimal> getShowPriceByGoodsIds(List<EsGoods> goodsList){
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
        return null;
    }
    public Map<Integer, BigDecimal> getShowPriceByIdAndType(Map<Integer,Byte> goodsIdMap){
        Map<Byte,List<Integer>> goodsTypeMap = new HashMap<>(goodsIdMap.size());
        goodsIdMap.forEach((key, type) -> {
            List<Integer> list;
            if (goodsTypeMap.containsKey(type)) {
                list = goodsTypeMap.get(type);
            } else {
                list = new ArrayList<>();
            }
            list.add(key);
            goodsTypeMap.put(type, list);
        });
        return getShowPriceByIds(goodsTypeMap);
    }
    private Map<Integer,BigDecimal> getShowPriceByIds(Map<Byte,List<Integer>> param){
        Map<Integer,BigDecimal> price = new HashMap<>(param.size());
        for(Map.Entry<Byte,List<Integer>> entry:param.entrySet()){
            Byte goodsType = entry.getKey();
            List<Integer> goodsIds = entry.getValue();
            Timestamp now = DateUtil.getLocalDateTime();
            if( goodsType.equals(BaseConstant.ACTIVITY_TYPE_GROUP_BUY) ){
                Map<Integer,List<Record2<Integer,BigDecimal>>> resultMap =
                    groupBuyService.getGroupBuyProductByGoodsIds(goodsIds,now);
                for( Map.Entry<Integer,List<Record2<Integer,BigDecimal>>> recordEntry:resultMap.entrySet() ){
                    List<Record2<Integer,BigDecimal>> resultList = recordEntry.getValue();
                    Integer goodsId = recordEntry.getKey();
                    if( !resultList.isEmpty() ){
                        List<BigDecimal> showPriceList = resultList.stream()
                            .map(x->x.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE))
                            .sorted(BigDecimal::compareTo)
                            .collect(Collectors.toList());
                        price.put(goodsId,showPriceList.get(0));
                    }else{
                        outPutLog(now,goodsId,goodsType);
                    }
                }
            }
            if ( goodsType.equals(BaseConstant.ACTIVITY_TYPE_BARGAIN) ){
                Map<Integer,List<BargainRecord>> resultMap = bargainService.getBargainRecordByGoodsIds(goodsIds,now);
                for( Map.Entry<Integer,List<BargainRecord>> recordEntry:resultMap.entrySet() ) {
                    List<BargainRecord> resultList = recordEntry.getValue();
                    Integer goodsId = recordEntry.getKey();
                    if ( !resultList.isEmpty() ) {
                        price.put(goodsId,resultList.get(0).getBargainType().equals(BargainService.BARGAIN_TYPE_RANDOM) ?
                            resultList.get(0).getFloorPrice() : resultList.get(0).getExpectationPrice());
                    } else {
                        outPutLog(now, goodsId, goodsType);
                    }
                }
            }
            if( goodsType.equals(BaseConstant.ACTIVITY_TYPE_SEC_KILL) ){
                Map<Integer,BigDecimal> resultMap = seckillService.getSecKillProductVo(goodsIds,now);
                if( resultMap.isEmpty() ) {
                    break;
                }
                price.putAll(resultMap);
            }
            if( goodsType.equals(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE) ){
                Map<Integer,BigDecimal> resultPrice = reducePriceService.getShowPriceByGoodsIds(goodsIds,now);
                if( resultPrice.isEmpty() ) {
                    break;
                }
                price.putAll(resultPrice);
            }
            if( goodsType.equals(BaseConstant.ACTIVITY_TYPE_PRE_SALE) ){
                Map<Integer,BigDecimal> resultPrice =
                    preSaleService.getPresaleProductRecordByGoodsIds(goodsIds,now);
                if( resultPrice.isEmpty() ) {
                    break;
                }
                price.putAll(resultPrice);
            }
        }
        return price;
    }

    /**
     * 取商品规格成本价
     * @param prdId
     * @return
     */
    public BigDecimal getCostPrice(Integer prdId){
        if(prdId == null || prdId <= 0){
            return BigDecimal.ZERO;
        }
        Record record =  db().select(GOODS_SPEC_PRODUCT.PRD_COST_PRICE).from(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.eq(prdId)).fetchOne();
        if(record == null){
            return null;
        }
        return record.into(BigDecimal.class);
    }

    private void outPutLog(Timestamp now,Integer goodsId,Byte goodsType){
        log.error("{}商品【{}】是{}类型但没找到相关活动",now,goodsId,goodsType);
    }
    private void outPutLog(Timestamp now,String goodsId,Byte goodsType){
        log.error("{}商品【{}】是{}类型但没找到相关活动",now,goodsId,goodsType);
    }
    private boolean checkMapData(Map<Object,Object> map,String goodsIds,Timestamp now,Byte goodsType){
        boolean result = map.isEmpty();
        if(result ){
            outPutLog(now, goodsIds,goodsType);
        }
        return result;
    }
}
