package com.vpu.mp.service.foundation.jedis.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goodssort.GoodsSortCacheInfo;
import com.vpu.mp.service.shop.goods.GoodsSortService;
import com.vpu.mp.service.shop.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class SortDataHelper extends ShopBaseService implements DataHelperInterface<GoodsSortCacheInfo> {

    @Autowired
    GoodsSortService goodsSortServices;
    @Autowired
    JedisManager jedisManager;
    @Autowired
    ImageService imageService;



    @Override
    public List<GoodsSortCacheInfo> get(List<Integer> ids) {
        Objects.requireNonNull(ids);
        List<GoodsSortCacheInfo> results = Lists.newArrayList();
        if( ids.isEmpty() ){
            return results;
        }

        String[] field = ids.parallelStream().map(x->x.toString()).toArray(String[]::new);
        List<String> resultStrs = jedisManager.batchGetHash(getKey(),field);
        results = resultStrs.stream().
            map(x-> Util.parseJson(x,GoodsSortCacheInfo.class)).
            collect(Collectors.toList());
        if ( results.size() >= ids.size() ){
            return results;
        }
        List<Integer> needIds = ids.stream().filter(x->!ids.contains(x)).collect(Collectors.toList());

        List<GoodsSortCacheInfo> queryVos = goodsSortServices.getGoodsSortCacheInfoById(needIds);
        if( queryVos.isEmpty() ){
            return results;
        }
        batchUpdate(queryVos);
        results.addAll(queryVos);
        return results;
    }

    @Override
    public void update(GoodsSortCacheInfo goodsSortCacheInfo) {
        jedisManager.addToHash(getKey(),goodsSortCacheInfo.getSortId().toString(),Util.toJson(goodsSortCacheInfo),TIME_OUT);
    }

    @Override
    public void update(List<Integer> ids) {
        List<GoodsSortCacheInfo> infos = goodsSortServices.getGoodsSortCacheInfoById(ids);
        if( infos.isEmpty() ){
            return ;
        }
        batchUpdate(infos);
    }

    @Override
    public void batchUpdate(List<GoodsSortCacheInfo> values) {
        Map<String,String> value = values.stream().collect(Collectors.toMap(c->c.getSortId().toString(), Util::toJson));
        jedisManager.addToHash(getKey(),value,TIME_OUT);
    }

    @Override
    public void delete(Integer id) {
        jedisManager.delHash(getKey(),id.toString());
    }

    @Override
    public String getKey() {
        return JedisKeyConstant.GOODS_SORT + getShopId();
    }


    public Map<Short,List<GoodsSortCacheInfo>> getAndGroup(List<Integer> ids){
        Map<Short,List<GoodsSortCacheInfo>> results = Maps.newHashMap();
        List<GoodsSortCacheInfo> infos = get(ids);
        Map<Short,List<GoodsSortCacheInfo>> levelMap =
            infos.stream().collect(Collectors.groupingBy(GoodsSortCacheInfo::getLevel));
        results.put(GoodsConstant.SECOND_LEVEL,levelMap.get(GoodsConstant.ROOT_LEVEL));
        List<Integer> parentIds = levelMap.get(GoodsConstant.ROOT_LEVEL).stream().
            map(GoodsSortCacheInfo::getSortId).
            collect(Collectors.toList());
        List<GoodsSortCacheInfo> parentInfos = get(parentIds);
        results.put(GoodsConstant.ROOT_LEVEL,parentInfos);
        return results;
    }
}
