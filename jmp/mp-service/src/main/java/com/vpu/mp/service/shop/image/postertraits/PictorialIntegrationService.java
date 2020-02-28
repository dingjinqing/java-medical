package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.bargain.BargainShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.firstspecial.FirstSpecialShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.group.GroupDrawShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.presale.PreSaleShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.reduce.ReducePriceShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 画报整合类，整合了商品和活动的图片生成service类，便于调用
 *
 * @author 李晓冰
 * @date 2019年12月31日
 */
@Service
public class PictorialIntegrationService extends ShopBaseService {

    @Autowired
    private GoodsService goodsService;

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
     * 获取商品所有图片base64格式集合
     *
     * @return
     */
    public List<String> getGoodsImagesBase64(Integer goodsId) {
        List<String> urlList = goodsService.getGoodsAllImageList(goodsId);
        List<String> imgList = new ArrayList<>(urlList.size());
        for (String s : urlList) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new URL(s));
                // 降低图片质量
                bufferedImage = ImageUtil.resizeImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage);
                String base64 = ImageUtil.toBase64(bufferedImage);
                imgList.add(base64);
            } catch (IOException e) {
                logger().debug("小程序-下载商品多图-读取base64错误-img url:" + s + "\nmsg:" + e.getMessage());
            }
        }
        return imgList;
    }

    /**
     * 获取活动分享图片信息
     * @param param 各个活动对应的 GoodsShareBaseParam类
     * @return {@link GoodsShareInfo}
     */
    public GoodsShareInfo getActivityShareInfo(GoodsShareBaseParam param) {
        if (param instanceof GroupBuyShareInfoParam) {
            return groupBuyPictorialService.getGroupBuyShareInfo((GroupBuyShareInfoParam) param);
        } else if (param instanceof BargainShareInfoParam) {
            return bargainPictorialService.getBargainShareInfo((BargainShareInfoParam) param);
        } else if (param instanceof GroupDrawShareInfoParam) {
            return groupDrawPictorialService.getGroupDrawShareInfo((GroupDrawShareInfoParam) param);
        } else if (param instanceof PreSaleShareInfoParam) {
            return preSalePictorialService.getPreSaleShareInfo((PreSaleShareInfoParam) param);
        } else if (param instanceof ReducePriceShareInfoParam) {
            return reducePricePictorialService.getReducePriceShareInfo((ReducePriceShareInfoParam) param);
        } else if (param instanceof FirstSpecialShareInfoParam) {
            return firstSpecialPictorialService.getFirstSpecialShareInfo((FirstSpecialShareInfoParam) param);
        } else {
            return normalGoodsPictorialService.getNormalGoodsShareInfo(param);
        }
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
     * 拼团还报下载
     * @param param 拼团活动信息
     * @return base64图片信息
     */
    public String getGroupBuyPictorialInfo(GroupBuyShareInfoParam param) {
        return groupBuyPictorialService.getGroupBuyPictorialInfo(param);
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
     * 拼团抽奖海报获取
     * @param param 拼团抽奖参数信息
     * @return base64图片信息
     */
    public String getGroupDrawPictorialInfo(GroupDrawShareInfoParam param) {
        return groupDrawPictorialService.getGroupDrawPictorialInfo(param);
    }

    /**
     * 预售海报获取
     * @param param 拼团抽奖参数信息
     * @return base64图片信息
     */
    public String getPreSalePictorialInfo(PreSaleShareInfoParam param) {
        return preSalePictorialService.getPreSalePictorialInfo(param);
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
     * 首单特惠海报获取
     * @param param 首单特惠参数信息
     * @return base64图片信息
     */
    public String getFirstSpecialPictorialInfo(FirstSpecialShareInfoParam param) {
        return firstSpecialPictorialService.getFirstSpecialPictorialInfo(param);
    }

}
