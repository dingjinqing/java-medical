package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.db.shop.tables.records.ReducePriceRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.reduce.ReducePriceShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
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
 * @author 李晓冰
 * @date 2020年02月06日
 */
@Service
public class ReducePricePictorialService extends ShopBaseService {

    @Autowired
    private ReducePriceService reducePriceService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 限时降价获取分享图片
     * @param param 分享参数
     * @return 分享信息
     */
        public GoodsShareInfo getReducePriceShareInfo(ReducePriceShareInfoParam param){
        GoodsShareInfo shareInfoVo = new GoodsShareInfo();
        ReducePriceRecord reducePriceRecord = reducePriceService.getReducePriceRecord(param.getActivityId());

        // 活动信息不可用
        if (reducePriceRecord == null) {
            pictorialLog("分享", "限时降价活动信息不可用");
            shareInfoVo.setShareCode(GoodsShareInfo.ACTIVITY_DELETED);
            return shareInfoVo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        // 商品信息不可用
        if (goodsRecord == null) {
            pictorialLog("分享", "限时降价商品信息不可用");
            shareInfoVo.setShareCode(GoodsShareInfo.GOODS_DELETED);
            return shareInfoVo;
        }

        PictorialShareConfig shareConfig = Util.parseJson(reducePriceRecord.getShareConfig(), PictorialShareConfig.class);
        shareInfoVo.setShareAction(shareConfig.getShareAction());
        // 用户自定义分享样式
        if (PictorialShareConfig.CUSTOMER_STYLE.equals(shareConfig.getShareAction())) {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                shareInfoVo.setImgUrl(goodsRecord.getGoodsImg());
            } else {
                shareInfoVo.setImgUrl(shareConfig.getShareImg());
            }
            shareInfoVo.setShareDoc(shareConfig.getShareDoc());
        } else{
            // 使用默认分享图片样式
            ShopRecord shop = saas.shop.getShopById(getShopId());
            String doc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_NORMAL_GOODS_SHARE_INFO, null,"messages",param.getUserName(),goodsRecord.getGoodsName());
            shareInfoVo.setShareDoc(doc);
            String imgPath = createReducePriceShareImg(reducePriceRecord, goodsRecord, param);
            shareInfoVo.setImgUrl(imgPath);
        }
        shareInfoVo.setImgUrl(imageService.getImgFullUrl(shareInfoVo.getImgUrl()));

        return shareInfoVo;
    }

    /**
     * 限时降价分享背景图片地址
     */
    private static final String REDUCE_PRICE_SHARE_BG_IMG = "image/wxapp/reduce_price_share.png";

    /**
     * 生成限时降价分享图
     * @param reducePriceRecord
     * @param goodsRecord
     * @param param
     * @return
     */
    private String createReducePriceShareImg(ReducePriceRecord reducePriceRecord, GoodsRecord goodsRecord, ReducePriceShareInfoParam param){
        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(), PictorialConstant.REDUCE_PRICE_ACTION_SAHRE,null);
        // 已存在生成的图片
        if (pictorialRecord != null&&pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(),goodsRecord.getUpdateTime(),reducePriceRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }

        try (InputStream bgInputStream = Util.loadFile(REDUCE_PRICE_SHARE_BG_IMG)){

            BufferedImage reducePriceBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            int goodsWidth = 458;
            int goodsHeight = 367;
            int toTop = 30;
            int toLeft = goodsWidth -30 -reducePriceBufferImg.getWidth();

            goodsBufferImg = ImageUtil.resizeImage(goodsWidth, goodsHeight, goodsBufferImg);
            ImageUtil.addTwoImage(goodsBufferImg, reducePriceBufferImg, toLeft, toTop);

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(reducePriceRecord.getId(), "share");
            PictorialRule pictorialRule =new PictorialRule(goodsRecord.getUpdateTime(),reducePriceRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(goodsBufferImg, relativePath,pictorialRule, goodsRecord.getGoodsId(),pictorialRecord, param.getUserId());

            return relativePath;
        } catch (IOException e) {
            pictorialLog("分享", "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
           pictorialLog("分享", "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }


    /**
     * 限时降价海报生成
     * @param param 限时降价售参数
     * @return base64海报信息
     */
    public String getReducePricePictorialInfo(ReducePriceShareInfoParam param){
        ShopRecord shop = saas.shop.getShopById(getShopId());
        ReducePriceRecord reducePriceRecord = reducePriceService.getReducePriceRecord(param.getActivityId());
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());

        if (reducePriceRecord == null || goodsRecord == null) {
            pictorialLog("pictorial", "商品或拼团信息已删除或失效");
            return null;
        }
        PictorialShareConfig shareConfig = Util.parseJson(reducePriceRecord.getShareConfig(), PictorialShareConfig.class);

        PictorialUserInfo pictorialUserInfo;
        try {
            pictorialLog("pictorial", "获取用户信息");
            pictorialUserInfo = pictorialService.getPictorialUserInfo(param.getUserId(),shop);
        } catch (IOException e) {
            pictorialLog("pictorial", "获取用户信息失败：" + e.getMessage());
            return null;
        }

        return getReducePricePictorialImg(pictorialUserInfo,shareConfig,reducePriceRecord,goodsRecord,shop,param);
    }

    private static final String REDUCE_PRICE_BG_IMG = "image/wxapp/reduce_price.png";

    private String getReducePricePictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, ReducePriceRecord presaleRecord, GoodsRecord goodsRecord, ShopRecord shop, ReducePriceShareInfoParam param){
        BufferedImage goodsImage;
        try {
            pictorialLog("pictorial", "获取商品图片信息");
            goodsImage = pictorialService.getGoodsPictorialImage(shareConfig, goodsRecord);
        } catch (IOException e) {
            pictorialLog("pictorial", "获取商品图片信息失败：" + e.getMessage());
            return null;
        }
        pictorialLog("pictorial", "获取商品分享语");

        String shareDoc;
        if (PictorialShareConfig.DEFAULT_STYLE.equals(shareConfig.getShareAction())) {
            shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_NORMAL_GOODS_INFO, null,"messages");
        } else {
            shareDoc = shareConfig.getShareDoc();
        }
        String mpQrcode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("gid=%d&aid=%d&atp=%d", goodsRecord.getGoodsId(), presaleRecord.getId(), BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE));

        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrcode));
        } catch (IOException e) {
            pictorialLog("pictorial", "获取二维码失败");
            return null;
        }

        PictorialImgPx imgPx = new PictorialImgPx();

        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo,shop,qrCodeImage, goodsImage, shareDoc, goodsRecord.getGoodsName(),null,null,imgPx,false);

        // 拼装价值限时降价图片和商品价格
        try (InputStream reduceIconStream = Util.loadFile(REDUCE_PRICE_BG_IMG)){
            BufferedImage reduceIconBufferImg = ImageIO.read(reduceIconStream);
            ImageUtil.addTwoImage(bgBufferedImage,reduceIconBufferImg,imgPx.getBgPadding(), imgPx.getPriceY());
            int realPriceStartX = imgPx.getBgPadding()+reduceIconBufferImg.getWidth()+20;
            int realPriceHeight = imgPx.getLargeFontAscent(bgBufferedImage);
            ImageUtil.addFont(bgBufferedImage,param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString(),ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getLargeFontSize()),realPriceStartX,imgPx.getPriceY()-realPriceHeight/4,imgPx.getRealPriceColor(),false);

        } catch (IOException e) {
            pictorialLog("pictorial", "装载限时降价图标失败");
            return null;
        }

        return ImageUtil.toBase64(bgBufferedImage);
    }

    /**
     * 创建云盘上的相对路径
     * @param activityId       活动Id
     * @param shareOrPictorial "share" 或 "pictorial"
     * @return 相对路径
     */
    private String createFilePath(Integer activityId, String shareOrPictorial) {
        return String.format("/upload/%s/%s/reduceprice/%s.jpg", getShopId(), shareOrPictorial, activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }

    private void pictorialLog(String share,String msg){ logger().debug("小程序-限时降价{}-{}", share, msg); }
}
