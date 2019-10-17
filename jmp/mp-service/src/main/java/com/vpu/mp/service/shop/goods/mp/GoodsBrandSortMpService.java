package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandConfig;
import com.vpu.mp.service.pojo.wxapp.goods.brand.GoodsBrandMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.brand.GoodsBrandMpWithClassifyVo;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.goods.GoodsBrandService;
import com.vpu.mp.service.shop.goods.GoodsSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年10月17日
 * 处理小程商家分类和品牌相关内用
 */
@Service
public class GoodsBrandSortMpService extends ShopBaseService{
    @Autowired
    ConfigService configService;
    @Autowired
    GoodsSortService goodsSortService;
    @Autowired
    GoodsBrandService goodsBrandService;

    public Map<String,Object> goodsSortPageInit() {
        GoodsBrandConfig goodsBrandConfig = configService.goodsBrandConfigService.getGoodsBrandConfig();

        Map<String, List<GoodsBrandMpVo>> allBrandGroupByPinYinName = goodsBrandService.getAllBrandGroupByPinYinName();
        List<GoodsBrandMpVo> allRecommendBrand = goodsBrandService.getAllRecommendBrand();
        List<List<GoodsBrandMpWithClassifyVo>> allRecommendBrandGroupByClassify = goodsBrandService.getAllRecommendBrandGroupByClassify();

        Map<String,Object> map=new HashMap<>();
        map.put("allBrandGroupByPinYinName",allBrandGroupByPinYinName);
        map.put("allRecommendBrand",allRecommendBrand);
        map.put("allRecommendBrandGroupByClassify",allRecommendBrandGroupByClassify);
        return map;
    }

}
