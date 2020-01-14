package com.vpu.mp.service.shop.market.freeshipping;

import com.vpu.mp.db.shop.tables.records.FreeShippingRecord;
import com.vpu.mp.db.shop.tables.records.FreeShippingRuleRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShowCartConfig;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingRuleVo;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchParam;
import com.vpu.mp.service.pojo.wxapp.market.freeshipping.FreeShipGoodsSearchVo;
import com.vpu.mp.service.pojo.wxapp.market.freeshipping.FreeShippingGoodsListParam;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.goods.mp.GoodsMpService;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.service.foundation.data.BaseConstant.GOODS_AREA_TYPE_SECTION;

/**
 * 满包邮商品列表业务
 * @author 孔德成
 * @date 2019/12/12 16:00
 */
@Service
public class FreeShippingGoodsService extends ShopBaseService {

    @Autowired
    private FreeShippingRuleService freeShipRuleService;
    @Autowired
    private FreeShippingService freeShipService;
    @Autowired
    private GoodsMpService goodsMpService;
    @Autowired
    private ConfigService configService;
    /**
     * 满包邮商品列表
     * @param param
     * @return
     */
    public FreeShipGoodsSearchVo freeShipGoodsList(FreeShippingGoodsListParam param){
        /*
         * 活动查询
         */
        // 获取活动规则
        FreeShippingRecord freeShip = freeShipService.getFreeShippingByRuleId(param.getRuleId());
        if (freeShip==null){
            return null;
        }
        //包邮规则
        Result<FreeShippingRuleRecord> freeShippingRule = freeShipRuleService.getRuleListByFreeShippingId(freeShip.getId());
        FreeShippingVo freeShippingVo = freeShip.into(FreeShippingVo.class);
        freeShippingVo.setRuleList(freeShippingRule.into(FreeShippingRuleVo.class));

        //查询参数
        GoodsSearchParam goodsSearchParam = handleSearchParam(param, freeShip);
        //查询商品
        PageResult<GoodsListMpBo> goodsListNormal = goodsMpService.getGoodsListNormal(goodsSearchParam);
        //添加营销活动
        goodsMpService.disposeGoodsList(goodsListNormal.dataList, param.getUserId());
        //是否显示划线价开关
        Byte delMarket = configService.shopCommonConfigService.getDelMarket();
        //是否显示购买按钮
        ShowCartConfig showCart = configService.shopCommonConfigService.getShowCart();
        FreeShipGoodsSearchVo vo =new FreeShipGoodsSearchVo();
        vo.setDelMarket(delMarket);
        vo.setShowCart(showCart);
        vo.setPageResult(goodsListNormal);
        vo.setFreeShipping(freeShippingVo);
        return vo;
    }

    private GoodsSearchParam handleSearchParam(FreeShippingGoodsListParam param, FreeShippingRecord freeShip) {
        //活动商品范围
        List<Integer> goodsIds =null;
        List<Integer> catIds =null;
        List<Integer> sortIds =null;
        if (freeShip.getType().equals(GOODS_AREA_TYPE_SECTION)){
            if (!freeShip.getRecommendCatId().trim().isEmpty()){
                catIds = new ArrayList<>(Util.splitValueToList(freeShip.getRecommendCatId()));
            }
            if (!freeShip.getRecommendSortId().trim().isEmpty()){
                sortIds = new ArrayList<>(Util.splitValueToList(freeShip.getRecommendCatId()));
            }
            if (!freeShip.getRecommendGoodsId().trim().isEmpty()){
                goodsIds = new ArrayList<>(Util.splitValueToList(freeShip.getRecommendCatId()));
            }
        }
        //售罄是否显示
        Boolean soldOutGoods = GoodsConstant.SOLD_OUT_GOODS_SHOW.equals(configService.shopCommonConfigService.getSoldOutGoods());
        //获取售罄商品展示设置
        GoodsSearchParam goodsSearchParam  =new GoodsSearchParam();
        goodsSearchParam.setCurrentPage(param.getCurrentPage());
        goodsSearchParam.setPageRows(param.getPageRows());
        goodsSearchParam.setCatIds(catIds);
        goodsSearchParam.setGoodsIds(goodsIds);
        goodsSearchParam.setSortIds(sortIds);
        goodsSearchParam.setKeyWords(param.getSearchText());
        goodsSearchParam.setUserId(param.getUserId());
        goodsSearchParam.setShowSoldOut(soldOutGoods);
        return goodsSearchParam;
    }
}
