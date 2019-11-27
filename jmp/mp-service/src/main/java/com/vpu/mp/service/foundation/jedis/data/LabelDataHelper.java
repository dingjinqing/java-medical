package com.vpu.mp.service.foundation.jedis.data;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsLabelRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.jedis.data.label.GoodsLabelInfo;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;


/**
 * 商品标签数据cache
 * @author 卢光耀
 * @date 2019/11/19 5:07 下午
 *
*/
@Component
@Slf4j
public class LabelDataHelper extends MainBaseService implements CommandLineRunner{

    private static Map<Integer, Map<Integer, GoodsLabelInfo>> labelMap;

    private static final Integer EXPIRED_TIME = 60*60*24;

    @Autowired
    private JedisManager jedisManager;

    /**
     * 数据预热
     * @throws Exception 异常
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("GoodsLabel data init start...");
        Result<ShopRecord> result = db().selectFrom(SHOP).
            //IS_ENABLED不太准确暂时忽略
//            where(SHOP.IS_ENABLED.eq((byte)1)).
            fetch();
        result.stream().map(ShopRecord::getShopId).forEach(this::initData);
        log.info("GoodsLabel data init end...");
    }
    private void initData(Integer shopId){
        String key = JedisKeyConstant.GOODS_LABEL+shopId;
        try{
            Condition param = GOODS_LABEL.DEL_FLAG.eq( DelFlag.NORMAL.getCode());
            List<GoodsLabelRecord> list = saas().getShopApp(shopId).goods.goodsLabel.getByCondition(param);
            log.info("DB_{} 'b2c_goods_label' SIZE ={}",shopId,list.size());
            if( !list.isEmpty() ){
                int len = list.size();
                Map<String,String> data = new HashMap<>(len);
                for (int i = 0; i < len; i++) {
                    GoodsLabelInfo info = GoodsLabelInfo.getGoodsLabelInfo().convert(list.get(i));
                    data.put(info.getId().toString(),Util.toJson(info));
                }
                jedisManager.delete(key);
                jedisManager.addToHash(key,data,EXPIRED_TIME);
            }
        }catch ( DataAccessException e){
            log.error("DB_{} 'b2c_goods_label' doesn't exist",shopId);
        }
    }

    /**
     * 根据店铺id获取所有商品标签数据
     * @param shopId 店铺id
     * @return {List<GoodsLabelInfo>}
     */
    public List<GoodsLabelInfo> getLabelByShopId(Integer shopId){
        String key = JedisKeyConstant.GOODS_LABEL+shopId;
        Map<String,String> map = jedisManager.getAllHash(key);
        return map.values().stream().
            map(x->Util.parseJson(x,GoodsLabelInfo.class)).
            collect(Collectors.toList());
    }

    /**
     * 获取goodsLabel by Cache
     * @param ids ids
     * @param shopId getShopId()
     * @return List<GoodsLabelInfo>
     */
    public List<GoodsLabelInfo> getLabelById(List<Integer> ids,Integer shopId){
        String key = JedisKeyConstant.GOODS_LABEL+shopId;
        String[] fields = ids.stream()
            .map(Object::toString)
            .toArray(String[]::new);
        List<String> cacheData = jedisManager.getValueAndSaveForHash(
                key,
                fields,
                EXPIRED_TIME,
                ()->{
                    Condition param = GOODS_LABEL.DEL_FLAG.eq( DelFlag.NORMAL.getCode())
                        .and(GOODS_LABEL.ID.in(ids));
                    List<GoodsLabelRecord> list = saas().getShopApp(shopId).goods.goodsLabel.getByCondition(param);
                    return list.stream()
                        .map(y->GoodsLabelInfo.getGoodsLabelInfo().convert(y))
                        .map(Util::toJson)
                        .collect(Collectors.toCollection(LinkedList::new));
                }
            );
        return cacheData.stream().map(x->Util.parseJson(x,GoodsLabelInfo.class)).collect(Collectors.toList());

    }

    /**
     * 获取goodsLabel by Cache
     * @param id id
     * @param shopId getShopId()
     * @return GoodsLabelInfo
     */
    public GoodsLabelInfo getLabelById(Integer id,Integer shopId){
        String key = JedisKeyConstant.GOODS_LABEL+shopId;
        String cacheData = jedisManager.getValueAndSaveForHash(
            key,
            id.toString(),
            EXPIRED_TIME,
            ()->{
                Condition param = GOODS_LABEL.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
                    .and(GOODS_LABEL.ID.eq(id));
                List<GoodsLabelRecord> list = saas().getShopApp(shopId).goods.goodsLabel.getByCondition(param);
                return Util.toJson(list.get(0));
            }
        );
        return Util.parseJson(cacheData,GoodsLabelInfo.class);
    }


    /**
     * 同步更新
     * @param shopId
     * @param ids
     */
    public void update(Integer shopId,List<Integer> ids){
        String key = JedisKeyConstant.GOODS_LABEL+shopId;
        try{
            Condition param = GOODS_LABEL.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
                .and(GOODS_LABEL.ID.in(ids));
            List<GoodsLabelRecord> list = saas().getShopApp(shopId).goods.goodsLabel.getByCondition(param);
//            if( list.size() < goodsIds.size() ){
//                Map<Integer,GoodsLabelRecord> labelMap = list.stream()
//                    .collect(Collectors.toMap(GoodsLabelRecord::getId, Function.identity()));
//                String[] needDelete = goodsIds.stream()
//                    .filter(x->!labelMap.containsKey(x))
//                    .map(Objects::toString)
//                    .toArray(String[]::new);
//                jedisManager.delFoHash(key,needDelete);
//            }
            log.info("DB_{} 'b2c_goods_label' SIZE ={}",shopId,list.size());
            if( !list.isEmpty() ){
                int len = list.size();
                Map<String,String> data = new HashMap<>(len);
                String[] fields = new String[len];
                for (int i = 0; i < len; i++) {
                    GoodsLabelInfo info = GoodsLabelInfo.getGoodsLabelInfo().convert(list.get(i));
                    fields[i] = info.getId().toString();
                    data.put(fields[i],Util.toJson(info));
                }
                jedisManager.delFoHash(key,fields);
                jedisManager.addToHash(key,data,EXPIRED_TIME);
            }
        }catch ( DataAccessException e){
            log.error("DB_{} 'b2c_goods_label' doesn't exist",shopId);
        }
    }

}
