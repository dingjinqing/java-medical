package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.JsonResultMessage;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.IntegralMallDefineRecord;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.integral.IntegralMallShareInfoParam;
import com.vpu.mp.service.shop.market.integralconvert.IntegralConvertService;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;

/**
 * 积分兑换活动海报生成器
 * @author 李晓冰
 * @date 2020年05月15日
 */
@Service
public class IntegralMallPictorialService extends ShareBaseService {

    @Autowired
    IntegralConvertService integralConvertService;

    @Override
    Record getActivityRecord(Integer activityId) {
        return integralConvertService.selectByActivityId(activityId);
    }

    @Override
    PictorialShareConfig getPictorialConfig(Record aRecord, GoodsRecord goodsRecord) {
        IntegralMallDefineRecord record = (IntegralMallDefineRecord) aRecord;
        return Util.parseJson(record.getShareConfig(), PictorialShareConfig.class);
    }

    @Override
    String createDefaultShareDoc(String lang, Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        IntegralMallShareInfoParam param = (IntegralMallShareInfoParam) baseParam;
        String score = param.getScore().toString();
        if (param.getRealPrice() == null || param.getRealPrice().equals(BigDecimal.ZERO)) {
            return Util.translateMessage(lang, JsonResultMessage.WX_MA_INTEGRAL_MALL_SHARE_NO_MONEY_DOC, "", "messages", score);
        } else {
            String priceStr = param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            return Util.translateMessage(lang, JsonResultMessage.WX_MA_INTEGRAL_MALL_SHARE_DOC, "", "messages", priceStr, score);
        }
    }

    @Override
    String createShareImage(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        return goodsRecord.getGoodsImg();
    }

    @Override
    String createMpQrCode(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        SceneValueBase sceneValueBase = new SceneValueBase(baseParam.getUserId(), goodsRecord.getGoodsId(), baseParam.getActivityId(), BaseConstant.ACTIVITY_TYPE_INTEGRAL, baseParam.getShareAwardId());
        String paramStr = addAndGetSceneStr(sceneValueBase);
        return qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM,paramStr);
    }

    @Override
    void createPictorialImg(BufferedImage qrCodeBufferImg, BufferedImage goodsImg, PictorialUserInfo userInfo, String shareDoc, Record aRecord, GoodsRecord goodsRecord, ShopRecord shop, GoodsShareBaseParam baseParam, GoodsPictorialInfo goodsPictorialInfo) {
        // 拼装背景图
        PictorialImgPx imgPx = new PictorialImgPx(getShopStyleColor());
        IntegralMallShareInfoParam param = (IntegralMallShareInfoParam) baseParam;

        // 积分兑换
        String exchangeStr = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_INTEGRAL_MALL_EXCHANGE, "", "messages");
        // ￥5.00+10积分
        String priceStrTranslate;
        if (param.getRealPrice() == null || param.getRealPrice().equals(BigDecimal.ZERO)) {
            priceStrTranslate =Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_INTEGRAL_MALL_SCORE, "", "messages",param.getScore().toString());
        } else {
            priceStrTranslate = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_INTEGRAL_MALL_PRICE_SCORE, "", "messages", baseParam.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString(), param.getScore().toString());
        }
        String linePriceStr = convertPriceWithFlag(shop.getShopLanguage(), baseParam.getLinePrice());

        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(userInfo, shop, qrCodeBufferImg, goodsImg, shareDoc, goodsRecord.getGoodsName(), null, null, priceStrTranslate, linePriceStr, imgPx);
        pictorialService.addPictorialSelfCustomerContent(bgBufferedImage, exchangeStr, priceStrTranslate, null, false, imgPx);

        String base64 = ImageUtil.toBase64(bgBufferedImage);
        goodsPictorialInfo.setBase64(base64);
        goodsPictorialInfo.setBgImg(bgBufferedImage);
    }

    @Override
    String getActivityName() {
        return "integral_mall";
    }
}
