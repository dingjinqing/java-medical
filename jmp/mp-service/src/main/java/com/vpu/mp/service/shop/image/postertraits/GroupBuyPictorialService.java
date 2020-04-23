package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.groupbuy.GroupBuyShareInfoParam;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;


/**
 * 拼团图片
 *
 * @author 李晓冰
 * @date 2019/12/12 18:00
 */
@Service
public class GroupBuyPictorialService extends ShareBaseService {
    @Autowired
    private GroupBuyService groupBuyService;
    @Autowired
    private QrCodeService qrCodeService;

    @Override
     Record getActivityRecord(Integer activityId) {
        return  groupBuyService.getGroupBuyRecord(activityId);
    }

    @Override
     PictorialShareConfig getPictorialConfig(Record aRecord, GoodsRecord goodsRecord) {
        GroupBuyDefineRecord record= (GroupBuyDefineRecord) aRecord;
        return Util.parseJson(record.getShareConfig(), PictorialShareConfig.class);
    }

    /**
     * 拼团分享图片地址
     */
    private static final String PIN_GROUP_BG_IMG = "image/wxapp/pin_group_bg.jpg";

    @Override
     String createShareImage(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        GroupBuyDefineRecord groupBuyDefineRecord = (GroupBuyDefineRecord) aRecord;
        GroupBuyShareInfoParam param = (GroupBuyShareInfoParam) baseParam;

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
            ShopRecord shop = saas.shop.getShopById(getShopId());
            String moneyFlag = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages");
            String linePrice =moneyFlag+ param.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            String realPrice =moneyFlag+ param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();


            // "开团省2元" 文字
            String startGroupMoneyText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_BUY_SAVE, null, "messages", saveMoney);
            ImageUtil.addFontWithRect(bgBufferImg, 250, 130, startGroupMoneyText, ImageUtil.SourceHanSansCN(Font.PLAIN, 18), PictorialImgPx.REAL_PRICE_COLOR, PictorialImgPx.SHARE_IMG_RECT_INNER_COLOR, PictorialImgPx.REAL_PRICE_COLOR);

            // 添加拼团价￥
            ImageUtil.addFont(bgBufferImg, realPrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 20), 250, 200, PictorialImgPx.REAL_PRICE_COLOR);
            // 添加划线价￥
            ImageUtil.addFontWithLine(bgBufferImg,250,220,linePrice,ImageUtil.SourceHanSansCN(Font.PLAIN, 18),PictorialImgPx.LINE_PRICE_COLOR);

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(groupBuyDefineRecord.getId());
            PictorialRule pictorialRule = new PictorialRule(goodsRecord.getUpdateTime(), groupBuyDefineRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(bgBufferImg, relativePath, pictorialRule, goodsRecord.getGoodsId(), param.getActivityId(), PictorialConstant.GROUP_BUY_ACTION_SHARE, pictorialRecord, param.getUserId());

            return relativePath;
        } catch (IOException e) {
            shareLog(getActivityName(), "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            shareLog(getActivityName(), "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    @Override
     String createDefaultShareDoc(String lang, Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        GroupBuyDefineRecord activityRecord = (GroupBuyDefineRecord) aRecord;
        return Util.translateMessage(lang, JsonResultMessage.WX_MA_GROUP_BUY_DOC, "", "messages", activityRecord.getLimitAmount(), baseParam.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
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
            pictorialLog(getActivityName(), "拼团信息已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.ACTIVITY_DELETED);
            return goodsPictorialInfo;
        }
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        if (goodsRecord == null) {
            pictorialLog(getActivityName(), "商品信息已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_DELETED);
            return goodsPictorialInfo;
        }
        pictorialLog(getActivityName(), "读取拼团海报配置信息");
        PictorialShareConfig shareConfig = Util.parseJson(groupBuyDefineRecord.getShareConfig(), PictorialShareConfig.class);

        PictorialUserInfo userInfo = getUserInfo(param.getUserId(), shop);
        if (userInfo == null) {
            goodsPictorialInfo.setPictorialCode(PictorialConstant.USER_PIC_ERROR);
            return goodsPictorialInfo;
        }
        getGroupBuyPictorialImg(userInfo, shareConfig, groupBuyDefineRecord, goodsRecord, shop, param, goodsPictorialInfo);
        return goodsPictorialInfo;
    }

    private void getGroupBuyPictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, GroupBuyDefineRecord groupBuyDefineRecord, GoodsRecord goodsRecord, ShopRecord shop, GroupBuyShareInfoParam param, GoodsPictorialInfo goodsPictorialInfo) {
        BufferedImage goodsImage;
        try {
            pictorialLog(getActivityName(), "获取商品图片信息");
            goodsImage = pictorialService.getGoodsPictorialImage(shareConfig, goodsRecord);
        } catch (IOException e) {
            pictorialLog(getActivityName(), "获取商品图片信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_PIC_ERROR);
            return;
        }

        pictorialLog(getActivityName(), "获取商品分享语");
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
            mpQrCode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("uid=%d&gid=%d&aid=%d&atp=%d", param.getUserId(), goodsRecord.getGoodsId(), groupBuyDefineRecord.getId(), BaseConstant.ACTIVITY_TYPE_GROUP_BUY));
        } else {
            mpQrCode = qrCodeService.getMpQrCode(QrCodeTypeEnum.POSTER_GROUP_BOOKING_INFO, String.format("uid=%d&group_id=%d", param.getUserId(), param.getGroupId()));
        }

        pictorialLog("pictorial", "mpQrcode:" + mpQrCode);
        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrCode));
        } catch (IOException e) {
            pictorialLog(getActivityName(), "获取二维码失败");
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

    @Override
    protected String getActivityName() {
        return "group_buy";
    }
}
