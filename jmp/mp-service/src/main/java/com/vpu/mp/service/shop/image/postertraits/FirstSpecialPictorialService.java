package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.FirstSpecialRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.firstspecial.FirstSpecialShareInfoParam;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.firstspecial.FirstSpecialService;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author 李晓冰
 * @date 2020年02月07日
 */
@Service
public class FirstSpecialPictorialService extends ShareBaseService {
    @Autowired
    FirstSpecialService firstSpecialService;
    @Autowired
    private QrCodeService qrCodeService;

    @Override
     Record getActivityRecord(Integer activityId) {
        return  firstSpecialService.getFirstSpecialRecord(activityId);
    }

    @Override
     PictorialShareConfig getPictorialConfig(Record aRecord, GoodsRecord goodsRecord) {
        FirstSpecialRecord record= (FirstSpecialRecord) aRecord;
        return Util.parseJson(record.getShareConfig(), PictorialShareConfig.class);
    }

    /**
     * 首单特惠分享背景图片地址
     */
    private static final String FIRST_SPECIAL_SHARE_BG_IMG = "image/wxapp/first_special_share.png";

    @Override
     String createShareImage(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        FirstSpecialRecord firstSpecialRecord = (FirstSpecialRecord) aRecord;
        FirstSpecialShareInfoParam param = (FirstSpecialShareInfoParam) baseParam;

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
            String relativePath = createFilePath(firstSpecialRecord.getId());
            PictorialRule pictorialRule = new PictorialRule(goodsRecord.getUpdateTime(), firstSpecialRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(goodsBufferImg, relativePath, pictorialRule, goodsRecord.getGoodsId(),param.getActivityId(),PictorialConstant.FIRST_SPECIAL_ACTION_SHARE, pictorialRecord, param.getUserId());

            return relativePath;
        } catch (IOException e) {
            shareLog(getActivityName(), "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            shareLog(getActivityName(), "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    @Override
     String createDefaultShareDoc(String lang,Record aRecord,GoodsRecord goodsRecord,GoodsShareBaseParam baseParam) {
       return Util.translateMessage(lang, JsonResultMessage.WX_MA_NORMAL_GOODS_SHARE_INFO, null, "messages", baseParam.getUserName(), goodsRecord.getGoodsName());
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
            pictorialLog(getActivityName(), "首单特惠活动已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.ACTIVITY_DELETED);
            return goodsPictorialInfo;
        }
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        if (goodsRecord == null) {
            pictorialLog(getActivityName(), "商品已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_DELETED);
            return goodsPictorialInfo;
        }
        PictorialShareConfig shareConfig = Util.parseJson(firstSpecialRecord.getShareConfig(), PictorialShareConfig.class);

        PictorialUserInfo userInfo = getUserInfo(param.getUserId(), shop);
        if (userInfo == null) {
            goodsPictorialInfo.setPictorialCode(PictorialConstant.USER_PIC_ERROR);
            return goodsPictorialInfo;
        }
        getFirstSpecialPictorialImg(userInfo,shareConfig,firstSpecialRecord,goodsRecord,shop,param,goodsPictorialInfo);
        return goodsPictorialInfo;
    }

    private static final String FIRST_SPECIAL_BG_IMG = "image/wxapp/first_special.png";

    private void getFirstSpecialPictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, FirstSpecialRecord firstSpecialRecord, GoodsRecord goodsRecord, ShopRecord shop, FirstSpecialShareInfoParam param,GoodsPictorialInfo goodsPictorialInfo) {
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
                shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_NORMAL_GOODS_INFO, null, "messages");
            }
        } else {
            shareDoc = shareConfig.getShareDoc();
        }

        String mpQrcode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("uid=%d&gid=%d&aid=%d&atp=%d",param.getUserId(), goodsRecord.getGoodsId(), firstSpecialRecord.getId(), BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL));

        pictorialLog("pictorial", "mpQrcode:"+mpQrcode);
        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrcode));
        } catch (IOException e) {
            pictorialLog(getActivityName(), "获取二维码失败");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.QRCODE_ERROR);
            return;
        }

        PictorialImgPx imgPx = new PictorialImgPx();

        // 拼装背景图
        pictorialLog(getActivityName(), "拼装背景图");
        String firstSpecialTip = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_FIRST_SPECIAL_INFO, "messages");
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo, shop, qrCodeImage, goodsImage, shareDoc, goodsRecord.getGoodsName(), firstSpecialTip,param.getRealPrice(), param.getLinePrice(), imgPx);
        pictorialLog(getActivityName(), "拼装背景图完成");

        pictorialLog(getActivityName(), "转换base64");
        String base64 = ImageUtil.toBase64(bgBufferedImage);
        goodsPictorialInfo.setBase64(base64);
    }


    @Override
    protected String getActivityName() {
        return "first_special";
    }
}
