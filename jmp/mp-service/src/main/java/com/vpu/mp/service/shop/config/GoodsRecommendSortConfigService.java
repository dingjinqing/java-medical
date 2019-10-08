package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.WxShoppingListConfig;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsRecommendSortConfig;
import com.vpu.mp.service.shop.image.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2019年08月06日
 */
@Service
public class GoodsRecommendSortConfigService extends BaseShopConfigService {

    @Autowired ImageService imageService;

    final static String K_RECOMMEND_SORT = "recommend_sort";


    /**
     *  获取推荐分类配置信息
     * @return
     */
    public GoodsRecommendSortConfig getRecommendSortConfig() {
        String v = get(K_RECOMMEND_SORT);
        GoodsRecommendSortConfig recommendSortConfig  = Util.parseJson(v, GoodsRecommendSortConfig.class);
        recommendSortConfig.setRecommendSortImg(getImgFullUrlUtil(recommendSortConfig.getRecommendSortImgPath()));
        return recommendSortConfig;
    }

    /**
     * 设置推荐分类配置信息
     * @param recommendSortConfig
     */
    public void setRecommendSortConfig(GoodsRecommendSortConfig recommendSortConfig) {
        Map<String,Object> map=new HashMap<>(3);
        map.put("recommendSortStatus",recommendSortConfig.getRecommendSortStatus());
        map.put("recommendSortImgPath",recommendSortConfig.getRecommendSortImgPath());
        map.put("recommendImgLink",recommendSortConfig.getRecommendImgLink());
        setJsonObject(K_RECOMMEND_SORT,map);
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
