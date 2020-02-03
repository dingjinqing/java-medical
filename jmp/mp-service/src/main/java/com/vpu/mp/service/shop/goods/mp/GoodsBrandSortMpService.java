package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.db.shop.tables.records.SortRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandConfig;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsRecommendSortConfig;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListParam;
import com.vpu.mp.service.pojo.wxapp.goods.brand.GoodsBrandMpPinYinVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goodssort.*;
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
    public List<GoodsSortMenuVo> goodsSortPageInit(Integer userId) {
        GoodsBrandConfig goodsBrandConfig = configService.goodsBrandConfigService.getGoodsBrandConfig();
        GoodsRecommendSortConfig recommendSortConfig = configService.recommendSortConfigService.getRecommendSortConfig();
        List<GoodsSortMenuVo> menuVos= new ArrayList<>();
        // 全部品牌
        if (GoodsBrandConfig.SHOW_ALL_BRAND.equals(goodsBrandConfig.getShowAllBrand())) {
            GoodsSortMenuVo item = new GoodsSortMenuVo();
            item.setMenuType(GoodsConstant.ALL_BRAND_TYPE);
            GoodsSortMenuContentVo allBrandContent = getAllBrandContent();
            item.setMenuContent(allBrandContent);
            menuVos.add(item);
        }
        // 推荐品牌
        if (GoodsBrandConfig.SHOW_RECOMMEND_LIST.equals(goodsBrandConfig.getShowRecommendBrandType())||GoodsBrandConfig.SHOW_RECOMMEND_CLASSIFY.equals(goodsBrandConfig.getShowRecommendBrandType())) {
            GoodsSortMenuVo item =new GoodsSortMenuVo();
            item.setMenuType(GoodsConstant.RECOMMEND_BRAND_TYPE);
            item.setMenuName(goodsBrandConfig.getRecommendTitle());
            if (menuVos.size()==0) {
                GoodsSortMenuContentVo recommendBrandContent = getRecommendBrandContent(goodsBrandConfig);
                item.setMenuContent(recommendBrandContent);
            }
            menuVos.add(item);
        }

        // 推荐分类设置
        if (GoodsRecommendSortConfig.SHOW_RECOMMEND_SORT.equals(recommendSortConfig.getRecommendSortStatus())) {
            GoodsSortMenuVo item =new GoodsSortMenuVo();
            item.setMenuType(GoodsConstant.RECOMMEND_SORT_TYPE);
            if (menuVos.size()==0) {
                GoodsSortMenuContentVo recommendSortContent = getRecommendSortContent(recommendSortConfig);
                item.setMenuContent(recommendSortContent);
            }
            menuVos.add(item);
            logger().debug(Util.toJson(menuVos));
        }

        boolean isFirst = false;
        if (menuVos.size() == 0) {
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
                item.setMenuContent(getNormalSortContent(record,userId));
                isFirst=false;
            }
            menuVos.add(item);
        }

        return menuVos;
    }

    /**
     * 根据{@link GoodsSortMenuParam#menuType}分别获取推荐分类
     * @param param
     * @return
     */
    public MenuContentBaseVo getGoodsSortMenuContent(GoodsSortMenuParam param,Integer userId) {
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
            return getNormalSortContent(param.getMenuId(),userId);
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
    private MenuContentBaseVo getNormalSortContent(Integer sortId,Integer userId) {
        SortRecord record = goodsSortService.getSortDao(sortId);
        if (record == null) {
            return null;
        }
        return getNormalSortContent(record,userId);
    }
    /**
     * 获取普通分类下的集合内容,其返回值可能是普通二级分类,也可能是商品信息
     * @param sort
     * @return
     */
    private MenuContentBaseVo getNormalSortContent(SortRecord sort, Integer userId) {

        SortGroupByParentParam param = new SortGroupByParentParam();
        param.setIsRecommend(GoodsConstant.NORMAL_SORT);
        param.setSortIds(Collections.singletonList(sort.getSortId()));
        List<GoodsSortParentMpVo> sortGroupByParentMp = goodsSortService.getSortGroupByParentMp(param);

        GoodsSortParentMpVo goodsSortParentMpVo = sortGroupByParentMp.get(0);
        List<GoodsSortMpVo> goodsSorts = goodsSortParentMpVo.getGoodsSorts();
        // 普通分类下没有二级分类
        if (goodsSorts.size() != 0) {
            GoodsSortMenuContentVo content=new GoodsSortMenuContentVo();
            content.setMenuContentType(GoodsConstant.NORMAL_SORT_TYPE);
            content.setMenuImg(sort.getSortImg());
            content.setMenuImgLink(sort.getImgLink());
            content.setContentList(goodsSorts);
            return content;
        } else {
            GoodsMenuContentVo content = new GoodsMenuContentVo();
            List<Integer> goodsIds = goodsMpService.getGoodsIdsBySortIdDao(sort.getSortId());
            List<? extends GoodsListMpVo> goodsListNormal = goodsMpService.getGoodsListNormal(goodsIds, userId);
            //是否显示划线价开关
            Byte delMarket = configService.shopCommonConfigService.getDelMarket();
            //是否显示购买按钮
            ShowCartConfig showCart = configService.shopCommonConfigService.getShowCart();

            // 尝试查询该分类下是否有商品信息
            content.setMenuContentType(GoodsConstant.GOODS_TYPE);
            content.setGoodsListMpVos(goodsListNormal);
            content.setDelMarket(delMarket);
            content.setShowCart(showCart);
            return content;
        }
    }

    /**
     * 获取小程序商品搜索页-搜索条件-品牌信息
     * @return 品牌信息{@link GoodsBrandMpPinYinVo}
     */
    public  List<GoodsBrandMpPinYinVo> getGoodsSearchFilterCondition() {
        return goodsBrandService.getBrandAssociatedWithGoodsGroupByPinYinNameMp();
    }

    /**
     * 根据parentId获取其所有子孙节点id,返回值包含parentId
     * @param parentId 父节点id
     * @return 要查询的子孙节点id
     */
    public List<Integer> getChildrenIds(Integer parentId){
         return goodsSortService.getChildrenIdByParentIdsDao(Collections.singletonList(parentId));
    }
}
