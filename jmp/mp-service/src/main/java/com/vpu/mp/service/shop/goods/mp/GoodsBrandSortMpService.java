package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.db.shop.tables.records.SortRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandConfig;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsRecommendSortConfig;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListParam;
import com.vpu.mp.service.pojo.wxapp.goods.brand.GoodsBrandMpPinYinVo;
import com.vpu.mp.service.pojo.wxapp.goods.goodssort.GoodsSortMenuContentVo;
import com.vpu.mp.service.pojo.wxapp.goods.goodssort.GoodsSortMenuParam;
import com.vpu.mp.service.pojo.wxapp.goods.goodssort.GoodsSortMenuVo;
import com.vpu.mp.service.pojo.wxapp.goods.sort.GoodsSortMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.sort.GoodsSortParentMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.sort.SortGroupByParentParam;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.goods.GoodsBrandService;
import com.vpu.mp.service.shop.goods.GoodsSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    GoodsMpService goodsMpService;
    @Autowired
    GoodsSortService goodsSortService;
    @Autowired
    GoodsBrandService goodsBrandService;

    /**
     * 商品分类页面初始接口
     * @return
     */
    public List<GoodsSortMenuVo> goodsSortPageInit() {
        GoodsBrandConfig goodsBrandConfig = configService.goodsBrandConfigService.getGoodsBrandConfig();
        GoodsRecommendSortConfig recommendSortConfig = configService.recommendSortConfigService.getRecommendSortConfig();
        List<GoodsSortMenuVo> menuVo= new ArrayList<>();
        // 全部品牌
        if (GoodsBrandConfig.SHOW_ALL_BRAND.equals(goodsBrandConfig.getShowAllBrand())) {
            GoodsSortMenuVo item = new GoodsSortMenuVo();
            item.setMenuType(GoodsConstant.ALL_BRAND_TYPE);
            GoodsSortMenuContentVo allBrandContent = getAllBrandContent();
            item.setMenuContent(allBrandContent);
            menuVo.add(item);
        }
        // 推荐品牌
        if (GoodsBrandConfig.SHOW_RECOMMEND_LIST.equals(goodsBrandConfig.getShowRecommendBrandType())||GoodsBrandConfig.SHOW_RECOMMEND_CLASSIFY.equals(goodsBrandConfig.getShowRecommendBrandType())) {
            GoodsSortMenuVo item =new GoodsSortMenuVo();
            item.setMenuType(GoodsConstant.RECOMMEND_BRAND_TYPE);
            item.setMenuName(goodsBrandConfig.getRecommendTitle());
            if (menuVo.size()==0) {
                GoodsSortMenuContentVo recommendBrandContent = getRecommendBrandContent(goodsBrandConfig);
                item.setMenuContent(recommendBrandContent);
            }
            menuVo.add(item);
        }

        // 推荐分类设置
        if (GoodsRecommendSortConfig.SHOW_RECOMMEND_SORT.equals(recommendSortConfig.getRecommendSortStatus())) {
            GoodsSortMenuVo item =new GoodsSortMenuVo();
            item.setMenuType(GoodsConstant.RECOMMEND_SORT_TYPE);
            if (menuVo.size()==0) {
                GoodsSortMenuContentVo recommendSortContent = getRecommendSortContent(recommendSortConfig);
                item.setMenuContent(recommendSortContent);
            }
            menuVo.add(item);
            logger().debug(Util.toJson(menuVo));
        }

        boolean isFirst = false;
        if (menuVo.size() == 0) {
            isFirst = true;
        }
        // 一级普通分类
        GoodsSortListParam param = new GoodsSortListParam();
        param.setType(GoodsConstant.NORMAL_SORT);
        param.setParentId(GoodsConstant.ROOT_PARENT_ID);
        List<SortRecord> sortRecords = goodsSortService.getSortListDao(param);

        for (SortRecord record : sortRecords) {
            GoodsSortMenuVo item =new GoodsSortMenuVo();
            item.setMenuType(GoodsConstant.NORMAL_SORT_TYPE);
            item.setMenuName(record.getSortName());
            item.setMenuId(record.getSortId());
            if (isFirst) {
                item.setMenuContent(getNormalSortContent(record));
                isFirst=false;
            }
            menuVo.add(item);
        }

        return menuVo;
    }

    /**
     * 根据{@link GoodsSortMenuParam#menuType}分别获取推荐分类
     * @param param
     * @return
     */
    public GoodsSortMenuContentVo getGoodsSortMenuContent(GoodsSortMenuParam param) {
        Byte menuType = param.getMenuType();
        if (GoodsConstant.ALL_BRAND_TYPE.equals(menuType)) {
            return getAllBrandContent();
        }
        if (GoodsConstant.RECOMMEND_BRAND_TYPE.equals(menuType)) {
            return getRecommendBrandContent();
        }
        if (GoodsConstant.RECOMMEND_SORT_TYPE.equals(menuType)) {
            return getRecommendSortContent();
        }
        if (GoodsConstant.NORMAL_SORT_TYPE.equals(menuType)) {
            return getNormalSortContent(param.getMenuId());
        }

        return new GoodsSortMenuContentVo();
    }
    /**
     * 获取所有品牌返回内容
     * @return
     */
    private GoodsSortMenuContentVo getAllBrandContent(){
        GoodsSortMenuContentVo content = new GoodsSortMenuContentVo();
        List<GoodsBrandMpPinYinVo> allBrandGroupByPinYinNameMp = goodsBrandService.getAllBrandGroupByPinYinNameMp();
        content.setContentList(allBrandGroupByPinYinNameMp);
        content.setMenuContentType(GoodsConstant.ALL_BRAND_TYPE);
        return content;
    }

    /**
     * 获取推荐品牌
     * @return
     */
    private GoodsSortMenuContentVo getRecommendBrandContent(){
        GoodsBrandConfig goodsBrandConfig = configService.goodsBrandConfigService.getGoodsBrandConfig();
        return  getRecommendBrandContent(goodsBrandConfig);
    }
    /**
     * 获取推荐品牌
     * @param goodsBrandConfig
     * @return
     */
    private GoodsSortMenuContentVo getRecommendBrandContent(GoodsBrandConfig goodsBrandConfig){
        GoodsSortMenuContentVo content = new GoodsSortMenuContentVo();
        if (GoodsBrandConfig.SHOW_RECOMMEND_LIST.equals(goodsBrandConfig.getShowRecommendBrandType())) {
            content.setContentList(goodsBrandService.getAllRecommendBrandMp());
            content.setMenuContentType(GoodsConstant.RECOMMEND_BRAND_LIST_TYPE);
        } else {
            content.setContentList(goodsBrandService.getAllRecommendBrandGroupByClassifyMp());
            content.setMenuContentType(GoodsConstant.RECOMMEND_BRAND_CLASSIFY_TYPE);
        }
        return content;
    }

    /**
     * 获取推荐分类
     * @return
     */
    private GoodsSortMenuContentVo getRecommendSortContent(){
        GoodsRecommendSortConfig recommendSortConfig = configService.recommendSortConfigService.getRecommendSortConfig();
        return getRecommendSortContent(recommendSortConfig);
    }
    /**
     * 获取推荐分类
     * @param recommendSortConfig
     * @return
     */
    private GoodsSortMenuContentVo getRecommendSortContent(GoodsRecommendSortConfig recommendSortConfig){
        GoodsSortMenuContentVo content = new GoodsSortMenuContentVo();
        content.setMenuContentType(GoodsConstant.RECOMMEND_SORT_TYPE);
        content.setMenuImg(recommendSortConfig.getRecommendSortImg());
        content.setMenuImgLink(recommendSortConfig.getRecommendImgLink());

        SortGroupByParentParam param = new SortGroupByParentParam();
        param.setParentIds(Arrays.asList(GoodsConstant.ROOT_PARENT_ID));
        param.setIsRecommend(GoodsConstant.RECOMMEND_SORT);
        List<GoodsSortParentMpVo> sortGroupByParentMp = goodsSortService.getSortGroupByParentMp(param);
        content.setContentList(sortGroupByParentMp);
        return content;
    }

    /**
     * 获取普通分类下的集合内容,其返回值可能是普通二级分类,也可能是商品信息
     * @param sortId
     * @return
     */
    private GoodsSortMenuContentVo getNormalSortContent(Integer sortId) {
        SortRecord record = goodsSortService.getSortDao(sortId);
        if (record == null) {
            return null;
        }
        return getNormalSortContent(record);
    }
    /**
     * 获取普通分类下的集合内容,其返回值可能是普通二级分类,也可能是商品信息
     * @param sort
     * @return
     */
    private GoodsSortMenuContentVo getNormalSortContent(SortRecord sort) {
        GoodsSortMenuContentVo content=new GoodsSortMenuContentVo();
        content.setMenuContentType(GoodsConstant.NORMAL_SORT_TYPE);
        content.setMenuImg(sort.getSortImg());
        content.setMenuImgLink(sort.getImgLink());

        SortGroupByParentParam param = new SortGroupByParentParam();
        param.setIsRecommend(GoodsConstant.NORMAL_SORT);
        param.setSortIds(Collections.singletonList(sort.getSortId()));
        List<GoodsSortParentMpVo> sortGroupByParentMp = goodsSortService.getSortGroupByParentMp(param);

        if (sortGroupByParentMp.size()==0) {
            content.setContentList(new ArrayList<>());
        } else {
            GoodsSortParentMpVo goodsSortParentMpVo = sortGroupByParentMp.get(0);
            List<GoodsSortMpVo> goodsSorts = goodsSortParentMpVo.getGoodsSorts();
            content.setContentList(goodsSorts);
        }
        return content;
    }

}
