package com.vpu.mp.service.foundation.jedis.data;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ChineseToPinYinUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.wxapp.goods.brand.GoodsBrandMpPinYinVo;
import com.vpu.mp.service.pojo.wxapp.goods.brand.GoodsBrandMpVo;
import com.vpu.mp.service.shop.goods.GoodsBrandService;
import com.vpu.mp.service.shop.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GoodsBrandDataHelper extends ShopBaseService implements DataHelperInterface<GoodsBrandMpVo>{

    @Autowired
    GoodsBrandService goodsBrandService;
    @Autowired
    JedisManager jedisManager;
    @Autowired
    ImageService imageService;



    @Override
    public List<GoodsBrandMpVo> get(List<Integer> ids) {
        Objects.requireNonNull(ids);
        List<GoodsBrandMpVo> results = Lists.newArrayList();
        if( ids.isEmpty() ){
            return results;
        }

        String[] field = ids.parallelStream().map(x->x.toString()).toArray(String[]::new);
        List<String> resultStrs = jedisManager.batchGetHash(getKey(),field);
        results = resultStrs.stream().
            map(x-> Util.parseJson(x,GoodsBrandMpVo.class)).
            collect(Collectors.toList());
        if ( results.size() >= ids.size() ){
            return results;
        }
        List<Integer> needIds = ids.stream().filter(x->!ids.contains(x)).collect(Collectors.toList());
        List<GoodsBrandMpVo> queryVos = goodsBrandService.getGoodsBrandVoById(needIds);
        if( queryVos.isEmpty() ){
            return results;
        }
        batchUpdate(queryVos);
        results.addAll(queryVos);
        return results;
    }

    @Override
    public void update(GoodsBrandMpVo goodsBrandMpVo) {
        jedisManager.addToHash(getKey(),goodsBrandMpVo.getId().toString(),Util.toJson(goodsBrandMpVo),TIME_OUT);

    }

    @Override
    public void update(List<Integer> id) {
        List<GoodsBrandMpVo> vos = goodsBrandService.getGoodsBrandVoById(id);
        updateLogoPath(vos);
        if( vos.isEmpty() ){
            return ;
        }
        batchUpdate(vos);
    }

    private void updateLogoPath(List<GoodsBrandMpVo> vos){
        for( GoodsBrandMpVo vo :vos ){
            String url = vo.getLogo();
            vo.setLogo(imageService.imageUrl(url));
        }
    }

    @Override
    public void batchUpdate(List<GoodsBrandMpVo> values) {
        updateLogoPath(values);
        Map<String,String> value = values.stream().collect(Collectors.toMap(c->c.getId().toString(), Util::toJson));
        jedisManager.addToHash(getKey(),value,TIME_OUT);
    }

    @Override
    public void delete(Integer id) {
        jedisManager.delHash(getKey(),id.toString());
    }

    @Override
    public String getKey() {
        return  JedisKeyConstant.GOODS_BRAND + getShopId();
    }

    public List<GoodsBrandMpPinYinVo> getAndGroup(List<Integer> ids) {
        List<GoodsBrandMpVo> vos = get(ids);
        TreeMap<String,List<GoodsBrandMpVo>> treeMap = Maps.newTreeMap();
        for (GoodsBrandMpVo vo : vos) {
            String c = ChineseToPinYinUtil.getStartAlphabet(vo.getBrandName());
            treeMap.computeIfAbsent(c, k -> Lists.newArrayList()).add(vo);
        }
        LinkedList<GoodsBrandMpPinYinVo> retList = treeMap.entrySet().
            stream().
            map(x->new GoodsBrandMpPinYinVo(x.getKey(),x.getValue())).
            collect(Collectors.toCollection(LinkedList::new));

        if (retList.peek() != null&&ChineseToPinYinUtil.OTHER_CHARACTER.equals(retList.peek().getCharacter())) {
            GoodsBrandMpPinYinVo t = retList.removeFirst();
            retList.addLast(t);
        }
        return retList;

    }
}
