package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.pojo.wxapp.share.GoodsPictorialInfo;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.bargain.BargainShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.firstspecial.FirstSpecialShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.group.GroupDrawShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.presale.PreSaleShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.reduce.ReducePriceShareInfoParam;
import com.vpu.mp.service.pojo.wxapp.share.seckill.SeckillShareInfoParam;
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

    @Autowired
    private SeckillPictorialService seckillPictorialService;

    /**
     * 获取商品所有图片base64格式集合
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

    private ShareBaseService findShareBaseService(GoodsShareBaseParam param){
        ShareBaseService shareBaseService;
        if (param instanceof GroupBuyShareInfoParam) {
            shareBaseService = groupBuyPictorialService;
        } else if (param instanceof BargainShareInfoParam) {
            shareBaseService = bargainPictorialService;
        } else if (param instanceof GroupDrawShareInfoParam) {
            shareBaseService = groupDrawPictorialService;
        } else if (param instanceof PreSaleShareInfoParam) {
            shareBaseService = preSalePictorialService;
        } else if (param instanceof ReducePriceShareInfoParam) {
            shareBaseService = reducePricePictorialService;
        } else if (param instanceof FirstSpecialShareInfoParam) {
            shareBaseService = firstSpecialPictorialService;
        } else if(param instanceof SeckillShareInfoParam){
            shareBaseService = seckillPictorialService;
        } else {
            shareBaseService = normalGoodsPictorialService;
        }
        return shareBaseService;
    }
    /**
     * 获取活动分享图片信息
     * @param param 各个活动对应的 GoodsShareBaseParam类
     * @return {@link GoodsShareInfo}
     */
    public GoodsShareInfo getActivityShareInfo(GoodsShareBaseParam param) {
        ShareBaseService shareBaseService = findShareBaseService(param);
        return shareBaseService.getShareInfo(param);
    }

    public GoodsPictorialInfo getActivityPictorialInfo(GoodsShareBaseParam param) {
        ShareBaseService shareBaseService = findShareBaseService(param);
        return shareBaseService.getPictorialInfo(param);
    }
}
