package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareInfo;
import com.vpu.mp.service.pojo.wxapp.share.PictorialConstant;
import com.vpu.mp.service.pojo.wxapp.share.PictorialRule;
import com.vpu.mp.service.pojo.wxapp.share.bargain.BargainShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * 砍价分享图片生成器
 * @author 李晓冰
 * @date 2020年01月03日
 */
@Service
public class BargainPictorialService extends ShopBaseService {
    @Autowired
    private BargainService bargainService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 砍价活动-分享图片生成
     * @param param 砍价分享参数
     * @return 砍价分享图片信息
     */
    public GoodsShareInfo getBargainShareInfo(BargainShareInfoParam param) {

        GoodsShareInfo shareInfoVo = new GoodsShareInfo();
        BargainRecord bargainRecord = bargainService.getBargainActById(param.getActivityId());

        // 拼团活动信息不可用
        if (bargainRecord == null) {
            bargainLog("分享", "砍价活动信息不可用");
            shareInfoVo.setShareCode(GoodsShareInfo.ACTIVITY_DELETED);
            return shareInfoVo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(bargainRecord.getGoodsId());
        // 拼团商品信息不可用
        if (goodsRecord == null) {
            bargainLog("分享", "砍价商品信息不可用");
            shareInfoVo.setShareCode(GoodsShareInfo.GOODS_DELETED);
            return shareInfoVo;
        }

        PictorialShareConfig shareConfig = Util.parseJson(bargainRecord.getShareConfig(), PictorialShareConfig.class);
        shareInfoVo.setShareAction(shareConfig.getShareAction());

        // 用户自定义分享样式
        if (PictorialShareConfig.CUSTOMER_STYLE.equals(shareConfig.getShareAction())) {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                shareInfoVo.setImgUrl(goodsRecord.getGoodsImg());
            } else {
                shareInfoVo.setImgUrl(shareConfig.getShareImg());
            }
            shareInfoVo.setShareDoc(shareConfig.getShareDoc());
        } else {
            String imgPath = createBargainShareImg(bargainRecord, goodsRecord, param);
            shareInfoVo.setImgUrl(imgPath);
        }

        shareInfoVo.setImgUrl(imageService.getImgFullUrl(shareInfoVo.getImgUrl()));
        return shareInfoVo;
    }
    /**
     * 砍价分享图片地址
     */
    private static final String BARGAIN_BG_IMG = "image/wxapp/bargain_bg.png";

    private String createBargainShareImg(BargainRecord bargainRecord, GoodsRecord goodsRecord,BargainShareInfoParam param) {
        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(), PictorialConstant.BARGAIN_ACTION_SHARE, param.getUserId());
        // 已存在生成的图片
        if (pictorialRecord != null&&pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(),goodsRecord.getUpdateTime(),bargainRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }

        try (InputStream bgInputStream = Util.loadFile(BARGAIN_BG_IMG)){

            BufferedImage bgBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            goodsBufferImg = ImageUtil.resizeImage(132, 132, goodsBufferImg);
            ImageUtil.addTwoImage(bgBufferImg, goodsBufferImg, 60, 120);

            ShopRecord shop = saas.shop.getShopById(getShopId());
            // 添加商品名称
            ImageUtil.addFont(bgBufferImg,goodsRecord.getGoodsName(),ImageUtil.SourceHanSansCN(Font.PLAIN,14),200,140,new Color(51,51,51));

            // 添加原价
            String realPriceStr = param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP)+ Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY, "messages");
            ImageUtil.addFont(bgBufferImg,realPriceStr,ImageUtil.SourceHanSansCN(Font.PLAIN,23),200,250,new Color(255,102,102));
            Integer realPriceLength = ImageUtil.getTextWidth(bgBufferImg,ImageUtil.SourceHanSansCN(Font.PLAIN,23),realPriceStr);

            // 添加划线价
            String linePriceStr = param.getLinePrice().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFont(bgBufferImg,linePriceStr,ImageUtil.SourceHanSansCN(Font.PLAIN,18),200+realPriceLength+5,250,new Color(153,153,153));
            Integer linePriceLength = ImageUtil.getTextWidth(bgBufferImg,ImageUtil.SourceHanSansCN(Font.PLAIN,18),linePriceStr);
            ImageUtil.addLine(bgBufferImg,200+realPriceLength+2,244,200+realPriceLength+linePriceLength+7,244,new Color(153,153,153));

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(bargainRecord.getId(), "share");
            PictorialRule pictorialRule =new PictorialRule(goodsRecord.getUpdateTime(),bargainRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(bgBufferImg, relativePath,pictorialRule, goodsRecord.getGoodsId(),pictorialRecord, param.getUserId());

            return relativePath;
        }catch (IOException e) {
            bargainLog("分享", "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            bargainLog("分享", "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    /**
     * 创建云盘上的相对路径
     *
     * @param activityId       活动Id
     * @param shareOrPictorial "share" 或 "pictorial"
     * @return 相对路径
     */
    private String createFilePath(Integer activityId, String shareOrPictorial) {
        return String.format("/upload/%s/%s/bargain/%s.jpg", getShopId(), shareOrPictorial, activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }


    private void bargainLog(String share, String msg) {
        logger().debug("小程序-砍价{}-{}", share, msg);
    }
}
