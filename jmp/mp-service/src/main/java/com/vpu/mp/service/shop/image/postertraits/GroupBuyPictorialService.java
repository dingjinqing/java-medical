package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
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
 * 拼团图片
 *
 * @author 李晓冰
 * @date 2019/12/12 18:00
 */
@Service
public class GroupBuyPictorialService extends ShopBaseService {
    @Autowired
    private GroupBuyService groupBuyService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 拼团活动-分享图片生成
     *
     * @param param 拼团分享参数
     * @return 拼团分享图片信息
     */
    public GoodsShareInfo getGroupBuyShareInfo(GroupBuyShareInfoParam param) {
        GoodsShareInfo shareInfoVo = new GoodsShareInfo();

        GroupBuyDefineRecord groupBuyDefineRecord = groupBuyService.getGroupBuyRecord(param.getActivityId());
        // 拼团活动信息不可用
        if (groupBuyDefineRecord == null) {
            groupBuyLog("分享", "拼团活动信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.ACTIVITY_DELETED);
            return shareInfoVo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        // 拼团商品信息不可用
        if (goodsRecord == null) {
            groupBuyLog("分享", "拼团商品信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.GOODS_DELETED);
            return shareInfoVo;
        }

        PictorialShareConfig shareConfig = Util.parseJson(groupBuyDefineRecord.getShareConfig(), PictorialShareConfig.class);

        // 用户自定义分享样式
        if (PictorialShareConfig.CUSTOMER_STYLE.equals(shareConfig.getShareAction())) {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                shareInfoVo.setImgUrl(goodsRecord.getGoodsImg());
            } else {
                shareInfoVo.setImgUrl(shareConfig.getShareImg());
            }
            shareInfoVo.setShareDoc(shareConfig.getShareDoc());
        } else {
            // 使用默认分享图片样式
            String imgPath = createGroupBuyShareImg(groupBuyDefineRecord, goodsRecord, param);
            if (imgPath == null) {
                shareInfoVo.setShareCode(PictorialConstant.GOODS_PIC_ERROR);
                return shareInfoVo;
            }
            shareInfoVo.setImgUrl(imgPath);
            ShopRecord shop = saas.shop.getShopById(getShopId());
            String shareDoc = null;
            shareDoc = pictorialService.getCommonConfigDoc(param.getUserName(), goodsRecord.getGoodsName(), param.getRealPrice(), shop.getShopLanguage(), false);
            if (shareDoc == null) {
                shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_BUY_DOC, "", "messages", groupBuyDefineRecord.getLimitAmount(), param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            shareInfoVo.setShareDoc(shareDoc);
        }
        shareInfoVo.setImgUrl(imageService.getImgFullUrl(shareInfoVo.getImgUrl()));
        return shareInfoVo;
    }


    /**
     * 拼团分享图片地址
     */
    private static final String PIN_GROUP_BG_IMG = "image/wxapp/pin_group_bg.jpg";

    private String createGroupBuyShareImg(GroupBuyDefineRecord groupBuyDefineRecord, GoodsRecord goodsRecord, GroupBuyShareInfoParam param) {
        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(), param.getActivityId(), PictorialConstant.GROUP_BUY_ACTION_SHARE, param.getUserId());
        // 已存在生成的图片
        if (pictorialRecord != null && pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(), goodsRecord.getUpdateTime(), groupBuyDefineRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }

        try (InputStream bgInputStream = Util.loadFile(PIN_GROUP_BG_IMG)) {
            BufferedImage bgBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            goodsBufferImg = ImageUtil.resizeImage(162, 162, goodsBufferImg);
            ImageUtil.addTwoImage(bgBufferImg, goodsBufferImg, 80, 85);

            String saveMoney = param.getLinePrice().subtract(param.getRealPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String linePrice = param.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String realPrice = param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ShopRecord shop = saas.shop.getShopById(getShopId());

            Color lineColor = new Color(255, 102, 102);
            // "开团省2元" 文字
            String startGroupMoneyText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_BUY_SAVE, null, "messages", saveMoney);
            ImageUtil.addFont(bgBufferImg, startGroupMoneyText, ImageUtil.SourceHanSansCN(Font.PLAIN, 18), 265, 130, lineColor);
            int width = ImageUtil.getTextWidth(bgBufferImg, ImageUtil.SourceHanSansCN(Font.PLAIN, 18), startGroupMoneyText);
            // 添加开团省边框
            ImageUtil.addRect(bgBufferImg, 255, 100, width + 10, 40, lineColor, new Color(255, 238, 238));


            String moneyFlag = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages");
            // 添加拼团价￥
            ImageUtil.addFont(bgBufferImg, moneyFlag, ImageUtil.SourceHanSansCN(Font.PLAIN, 20), 250, 200, lineColor);
            ImageUtil.addFont(bgBufferImg, realPrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 32), 280, 200, lineColor);

            // 添加划线价￥
            ImageUtil.addFont(bgBufferImg, moneyFlag, ImageUtil.SourceHanSansCN(Font.PLAIN, 18), 250, 230, lineColor);
            ImageUtil.addFont(bgBufferImg, linePrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 18), 280, 230, lineColor);
            // 划线
            ImageUtil.addLine(bgBufferImg, 250, 225, 250 + linePrice.length() * 10 + 28, 225, lineColor);

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(groupBuyDefineRecord.getId(), "share");
            PictorialRule pictorialRule = new PictorialRule(goodsRecord.getUpdateTime(), groupBuyDefineRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(bgBufferImg, relativePath, pictorialRule, goodsRecord.getGoodsId(), pictorialRecord, param.getUserId());

            return relativePath;
        } catch (IOException e) {
            groupBuyLog("分享", "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            groupBuyLog("分享", "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }


    /**
     * 拼团活动-海报生成
     *
     * @param param 拼团海报参数
     * @return 拼团海报图片base64
     */
    public GoodsPictorialInfo getGroupBuyPictorialInfo(GroupBuyShareInfoParam param) {
        GoodsPictorialInfo goodsPictorialInfo = new GoodsPictorialInfo();
        ShopRecord shop = saas.shop.getShopById(getShopId());
        GroupBuyDefineRecord groupBuyDefineRecord = groupBuyService.getGroupBuyRecord(param.getActivityId());

        if (groupBuyDefineRecord == null) {
            groupBuyLog("pictorial", "拼团信息已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.ACTIVITY_DELETED);
            return goodsPictorialInfo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        if (goodsRecord == null) {
            groupBuyLog("pictorial", "商品信息已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_DELETED);
            return goodsPictorialInfo;
        }
        groupBuyLog("pictorial", "读取拼团海报配置信息");
        PictorialShareConfig shareConfig = Util.parseJson(groupBuyDefineRecord.getShareConfig(), PictorialShareConfig.class);


        PictorialUserInfo pictorialUserInfo;
        try {
            groupBuyLog("pictorial", "获取用户信息");
            pictorialUserInfo = pictorialService.getPictorialUserInfo(param.getUserId(), shop);
        } catch (IOException e) {
            groupBuyLog("pictorial", "获取用户信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.USER_PIC_ERROR);
            return goodsPictorialInfo;
        }
        getGroupBuyPictorialImg(pictorialUserInfo, shareConfig, groupBuyDefineRecord, goodsRecord, shop, param, goodsPictorialInfo);
        return goodsPictorialInfo;
    }

    private void getGroupBuyPictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, GroupBuyDefineRecord groupBuyDefineRecord, GoodsRecord goodsRecord, ShopRecord shop, GroupBuyShareInfoParam param, GoodsPictorialInfo goodsPictorialInfo) {
        BufferedImage goodsImage;
        try {
            groupBuyLog("pictorial", "获取商品图片信息");
            goodsImage = pictorialService.getGoodsPictorialImage(shareConfig, goodsRecord);
        } catch (IOException e) {
            groupBuyLog("pictorial", "获取商品图片信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_PIC_ERROR);
            return;
        }

        groupBuyLog("pictorial", "获取商品分享语");
        String shareDoc = null;
        if (PictorialShareConfig.DEFAULT_STYLE.equals(shareConfig.getShareAction())) {
            shareDoc = pictorialService.getCommonConfigDoc(param.getUserName(), goodsRecord.getGoodsName(), param.getRealPrice(), shop.getShopLanguage(), true);
            if (shareDoc == null) {
                shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_BUY_DOC, null, "messages", groupBuyDefineRecord.getLimitAmount(), param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
        } else {
            shareDoc = shareConfig.getShareDoc();
        }

        // 获取分享码
        String mpQrCode;
        if (GoodsConstant.GOODS_ITEM.equals(param.getPageType())) {
            mpQrCode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("gid=%d&aid=%d&atp=%d", goodsRecord.getGoodsId(), groupBuyDefineRecord.getId(), BaseConstant.ACTIVITY_TYPE_GROUP_BUY));
        } else {
            mpQrCode = qrCodeService.getMpQrCode(QrCodeTypeEnum.POSTER_GROUP_BOOKING_INFO, String.format("group_id=%d&goods_id=%d", groupBuyDefineRecord.getId(), goodsRecord.getGoodsId(), BaseConstant.ACTIVITY_TYPE_GROUP_BUY));
        }
        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrCode));
        } catch (IOException e) {
            groupBuyLog("pictorial", "获取二维码失败");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.QRCODE_ERROR);
            return;
        }
        PictorialImgPx imgPx = new PictorialImgPx();
        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo, shop, qrCodeImage, goodsImage, shareDoc, goodsRecord.getGoodsName(), param.getRealPrice(), param.getLinePrice(), imgPx);

        // 拼装自定义内容
        BigDecimal saveMoney = param.getLinePrice().subtract(param.getRealPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);

        // "开团省2元" 文字
        String saveText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_BUY_SAVE, null, "messages", saveMoney);
        // 活动价
        String realPriceText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages") + param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
        // 划线价格
        String linePriceText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages") + param.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP);

        pictorialService.addPictorialSelfCustomerContent(bgBufferedImage, saveText, realPriceText, linePriceText, true, imgPx);

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
        return String.format("/upload/%s/%s/groupbuy/%s.jpg", getShopId(), shareOrPictorial, activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }

    private void groupBuyLog(String share, String msg) {
        logger().debug("小程序-拼团{}-{}", share, msg);
    }
}
