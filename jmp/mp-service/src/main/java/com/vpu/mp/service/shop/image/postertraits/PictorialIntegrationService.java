package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.bargain.BargainShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.firstspecial.FirstSpecialShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.group.GroupDrawShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.presale.PreSaleShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.reduce.ReducePriceShareInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 画报整合类，整合了商品和活动的图片生成service类，便于调用
 * @author 李晓冰
 * @date 2019年12月31日
 */
@Service
public class PictorialIntegrationService extends ShopBaseService {

    @Autowired
    private NormalGoodsPictorialService normalGoodsPictorialService;

    @Autowired
    private GroupBuyPictorialService groupBuyPictorialService;

    @Autowired
    private BargainPictorialService bargainPictorialService;

    @Autowired
    private GroupDrawPictorialService groupDrawPictorialService;

    @Autowired
    private PreSalePictorialService preSalePictorialService;

    @Autowired
    private ReducePricePictorialService reducePricePictorialService;

    @Autowired
    private FirstSpecialPictorialService firstSpecialPictorialService;
    /**
     * 获取普通商品分享信息
     * @param param 分享参数
     * @return 分享信息
     */
    public GoodsShareInfo getNormalGoodsShareInfo(GoodsShareBaseParam param) {
        return normalGoodsPictorialService.getNormalGoodsShareInfo(param);
    }

    /**
     * 商品还报下载
     * @param param 商品信息
     * @return base64图片信息
     */
    public String getNormalPictorialInfo(GoodsShareBaseParam param) {
        return normalGoodsPictorialService.getNormalGoodsPictorialInfo(param);
    }


    /**
     * 拼团分享信息生成
     * @param param 拼团活动信息
     * @return 拼团活动分享信息
     */
    public GoodsShareInfo getGroupBuyShareInfo(GroupBuyShareInfoParam param){
        return groupBuyPictorialService.getGroupBuyShareInfo(param);
    }

    /**
     * 拼团还报下载
     * @param param 拼团活动信息
     * @return base64图片信息
     */
    public String getGroupBuyPictorialInfo(GroupBuyShareInfoParam param){
        return groupBuyPictorialService.getGroupBuyPictorialInfo(param);
    }

    /**
     * 砍价分享信息生成
     * @param param 砍价活动信息
     * @return 砍价活动分享信息
     */
    public GoodsShareInfo getBargainShareInfo(BargainShareInfoParam param){
        return bargainPictorialService.getBargainShareInfo(param);
    }

    /**
     * 砍价还报下载
     * @param param 拼团活动信息
     * @return base64图片信息
     */
    public String getBargainPictorialInfo(BargainShareInfoParam param) {
        return bargainPictorialService.getBargainPictorialInfo(param);
    }

    /**
     * 拼团抽奖分享图片获取
     * @param param 拼团抽奖参数信息
     * @return 分享图片信息
     */
    public GoodsShareInfo getGroupDrawShareInfo(GroupDrawShareInfoParam param) {
        return groupDrawPictorialService.getGroupDrawShareInfo(param);
    }

    /**
     * 拼团抽奖海报获取
     * @param param 拼团抽奖参数信息
     * @return base64图片信息
     */
    public String getGroupDrawPictorialInfo(GroupDrawShareInfoParam param) {
        return groupDrawPictorialService.getGroupDrawPictorialInfo(param);
    }

    /**
     * 拼团抽奖分享图片获取
     * @param param 拼团抽奖参数信息
     * @return 分享图片信息
     */
    public GoodsShareInfo getPreSaleShareInfo(PreSaleShareInfoParam param) {
        return preSalePictorialService.getPreSaleShareInfo(param);
    }

    /**
     * 拼团抽奖海报获取
     * @param param 拼团抽奖参数信息
     * @return base64图片信息
     */
    public String getPreSalePictorialInfo(PreSaleShareInfoParam param) {
        return preSalePictorialService.getPreSalePictorialInfo(param);
    }

    /**
     * 限时降价分享图片获取
     * @param param 拼团抽奖参数信息
     * @return 分享图片信息
     */
    public GoodsShareInfo getReducePriceShareInfo(ReducePriceShareInfoParam param) {
        return reducePricePictorialService.getReducePriceShareInfo(param);
    }

    /**
     * 限时降价海报获取
     * @param param 拼团抽奖参数信息
     * @return base64图片信息
     */
    public String getReducePricePictorialInfo(ReducePriceShareInfoParam param) {
        return reducePricePictorialService.getReducePricePictorialInfo(param);
    }

    /**
     * 首单特惠分享图片获取
     * @param param 首单特惠参数信息
     * @return 分享图片信息
     */
    public GoodsShareInfo getFirstSpecialShareInfo(FirstSpecialShareInfoParam param) {
        return firstSpecialPictorialService.getFirstSpecialShareInfo(param);
    }

    /**
     * 首单特惠海报获取
     * @param param 首单特惠参数信息
     * @return base64图片信息
     */
    public String getFirstSpecialPictorialInfo(FirstSpecialShareInfoParam param) {
        return firstSpecialPictorialService.getFirstSpecialPictorialInfo(param);
    }

}
