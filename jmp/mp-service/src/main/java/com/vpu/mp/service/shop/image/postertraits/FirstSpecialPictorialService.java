package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.FirstSpecialRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.firstspecial.FirstSpecialShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.firstspecial.FirstSpecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * @author 李晓冰
 * @date 2020年02月07日
 */
@Service
public class FirstSpecialPictorialService extends ShopBaseService {
    @Autowired
    FirstSpecialService firstSpecialService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 首单特惠获取分享图片
     *
     * @param param 分享参数
     * @return 分享信息
     */
    public GoodsShareInfo getFirstSpecialShareInfo(FirstSpecialShareInfoParam param) {
        GoodsShareInfo shareInfoVo = new GoodsShareInfo();
        FirstSpecialRecord firstSpecialRecord = firstSpecialService.getFirstSpecialRecord(param.getActivityId());
        // 活动信息不可用
        if (firstSpecialRecord == null) {
            pictorialLog("分享", "首单特惠活动信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.ACTIVITY_DELETED);
            return shareInfoVo;
        }
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        // 商品信息不可用
        if (goodsRecord == null) {
            pictorialLog("分享", "首单特惠商品信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.GOODS_DELETED);
            return shareInfoVo;
        }

        PictorialShareConfig shareConfig = Util.parseJson(firstSpecialRecord.getShareConfig(), PictorialShareConfig.class);

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
            String imgPath = createFirstSpecialShareImg(firstSpecialRecord, goodsRecord, param);
            if (imgPath == null) {
                shareInfoVo.setShareCode(PictorialConstant.GOODS_PIC_ERROR);
                return shareInfoVo;
            }
            shareInfoVo.setImgUrl(imgPath);
            ShopRecord shop = saas.shop.getShopById(getShopId());
            String doc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_NORMAL_GOODS_SHARE_INFO, null, "messages", param.getUserName(), goodsRecord.getGoodsName());
            shareInfoVo.setShareDoc(doc);
        }
        shareInfoVo.setImgUrl(imageService.getImgFullUrl(shareInfoVo.getImgUrl()));

        return shareInfoVo;
    }

    /**
     * 首单特惠分享背景图片地址
     */
    private static final String FIRST_SPECIAL_SHARE_BG_IMG = "image/wxapp/first_special_share.png";

    /**
     * 首单特惠分享图
     *
     * @param firstSpecialRecord
     * @param goodsRecord
     * @param param
     * @return
     */
    private String createFirstSpecialShareImg(FirstSpecialRecord firstSpecialRecord, GoodsRecord goodsRecord, FirstSpecialShareInfoParam param) {
        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(),param.getActivityId(), PictorialConstant.FIRST_SPECIAL_ACTION_SHARE, null);
        // 已存在生成的图片
        if (pictorialRecord != null && pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(), goodsRecord.getUpdateTime(), firstSpecialRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }

        try (InputStream bgInputStream = Util.loadFile(FIRST_SPECIAL_SHARE_BG_IMG)) {

            BufferedImage firstSpecialBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            int goodsHeight = 367;
            int goodsWidth = 458;
            int toTop = 30;
            int toLeft = goodsWidth - 30 - firstSpecialBufferImg.getWidth();

            goodsBufferImg = ImageUtil.resizeImage(goodsWidth, goodsHeight, goodsBufferImg);
            ImageUtil.addTwoImage(goodsBufferImg, firstSpecialBufferImg, toLeft, toTop);

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(firstSpecialRecord.getId(), "share");
            PictorialRule pictorialRule = new PictorialRule(goodsRecord.getUpdateTime(), firstSpecialRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(goodsBufferImg, relativePath, pictorialRule, goodsRecord.getGoodsId(), pictorialRecord, param.getUserId());

            return relativePath;
        } catch (IOException e) {
            pictorialLog("分享", "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            pictorialLog("分享", "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    /**
     * 首单特惠海报生成
     * @param param 限时降价售参数
     * @return base64海报信息
     */
    @SuppressWarnings("all")
    public GoodsPictorialInfo getFirstSpecialPictorialInfo(FirstSpecialShareInfoParam param) {
        GoodsPictorialInfo goodsPictorialInfo = new GoodsPictorialInfo();
        ShopRecord shop = saas.shop.getShopById(getShopId());
        FirstSpecialRecord firstSpecialRecord = firstSpecialService.getFirstSpecialRecord(param.getActivityId());
        if (firstSpecialRecord == null) {
            pictorialLog("pictorial", "首单特惠活动已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.ACTIVITY_DELETED);
            return goodsPictorialInfo;
        }
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        if (goodsRecord == null) {
            pictorialLog("pictorial", "商品已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_DELETED);
            return goodsPictorialInfo;
        }
        PictorialShareConfig shareConfig = Util.parseJson(firstSpecialRecord.getShareConfig(), PictorialShareConfig.class);

        PictorialUserInfo pictorialUserInfo;
        try {
            pictorialLog("pictorial", "获取用户信息");
            pictorialUserInfo = pictorialService.getPictorialUserInfo(param.getUserId(), shop);
        } catch (IOException e) {
            pictorialLog("pictorial", "获取用户信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.USER_PIC_ERROR);
            return goodsPictorialInfo;
        }

        getFirstSpecialPictorialImg(pictorialUserInfo,shareConfig,firstSpecialRecord,goodsRecord,shop,param,goodsPictorialInfo);
        return goodsPictorialInfo;
    }

    private static final String FIRST_SPECIAL_BG_IMG = "image/wxapp/first_special.png";

    private void getFirstSpecialPictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, FirstSpecialRecord firstSpecialRecord, GoodsRecord goodsRecord, ShopRecord shop, FirstSpecialShareInfoParam param,GoodsPictorialInfo goodsPictorialInfo) {
        BufferedImage goodsImage;
        try {
            pictorialLog("pictorial", "获取商品图片信息");
            goodsImage = pictorialService.getGoodsPictorialImage(shareConfig, goodsRecord);
        } catch (IOException e) {
            pictorialLog("pictorial", "获取商品图片信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_PIC_ERROR);
            return;
        }
        pictorialLog("pictorial", "获取商品分享语");

        String shareDoc;
        if (PictorialShareConfig.DEFAULT_STYLE.equals(shareConfig.getShareAction())) {
            shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_NORMAL_GOODS_INFO, null, "messages");
        } else {
            shareDoc = shareConfig.getShareDoc();
        }
        String mpQrcode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("gid=%d&aid=%d&atp=%d", goodsRecord.getGoodsId(), firstSpecialRecord.getId(), BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL));

        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrcode));
        } catch (IOException e) {
            pictorialLog("pictorial", "获取二维码失败");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.QRCODE_ERROR);
            return;
        }

        PictorialImgPx imgPx = new PictorialImgPx();

        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo, shop, qrCodeImage, goodsImage, shareDoc, goodsRecord.getGoodsName(), null, null, imgPx, false);

        BufferedImage iconImage;
        // 拼装价值限时降价图片和商品价格
        try (InputStream firstSpecialIconStream = Util.loadFile(FIRST_SPECIAL_BG_IMG)) {
             iconImage = ImageIO.read(firstSpecialIconStream);
        } catch (IOException e) {
            pictorialLog("pictorial", "装载首单特惠图标失败");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_PIC_ERROR);
            return;
        }

        String realPriceText =  Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages")+param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String linePriceText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages")+param.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        // 自定义区域添加内容
        pictorialService.addPictorialSelfCustomerContent(bgBufferedImage,iconImage,realPriceText,linePriceText,true,imgPx);


        String base64 = ImageUtil.toBase64(bgBufferedImage);
        goodsPictorialInfo.setBase64(base64);
    }

    /**
     * 创建云盘上的相对路径
     *
     * @param activityId       活动Id
     * @param shareOrPictorial "share" 或 "pictorial"
     * @return 相对路径
     */
    private String createFilePath(Integer activityId, String shareOrPictorial) {
        return String.format("/upload/%s/%s/firstspecial/%s.jpg", getShopId(), shareOrPictorial, activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }

    private void pictorialLog(String share, String msg) {
        logger().debug("小程序-首单特惠{}-{}", share, msg);
    }
}
