package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSharePostConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsRebateConfigParam;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.rebate.RebateSceneValue;
import com.vpu.mp.service.pojo.wxapp.share.rebate.RebateShareInfoParam;
import org.jooq.Record;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

/**
 * 分销分销海报生成器
 *
 * @author 李晓冰
 * @date 2020年04月27日
 */
@Service
public class RebatePictorialService extends ShareBaseService {

    @Override
    Record getActivityRecord(Integer activityId) {
        return null;
    }

    @Override
    String createDefaultShareDoc(String lang, Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        return Util.translateMessage(lang, JsonResultMessage.WX_MA_REBATE_DOC, "", "messages");
    }

    @Override
    PictorialShareConfig getPictorialConfig(Record aRecord, GoodsRecord goodsRecord) {
        GoodsSharePostConfig goodsShareConfig = Util.parseJson(goodsRecord.getShareConfig(), GoodsSharePostConfig.class);
        return PictorialShareConfig.createFromGoodsShareInfoConfig(goodsShareConfig);
    }

    /**
     * 分销分享背景图片地址
     */
    private static final String REBATE_SHARE_BG_IMG = "image/wxapp/rebate_bg.png";

    @Override
    String createShareImage(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        Color shopStyleColor = getShopStyleColor();
        try (InputStream bgInputStream = Util.loadFile(REBATE_SHARE_BG_IMG)) {
            BufferedImage rebateBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            int goodsHeight = 132;
            int goodsWidth = 132;
            int toTop = 128;
            int toLeft = 60;

            goodsBufferImg = ImageUtil.resizeImage(goodsWidth, goodsHeight, goodsBufferImg);
            ImageUtil.addTwoImage(rebateBufferImg,goodsBufferImg, toLeft, toTop);

            int textStartX = toLeft + goodsWidth + 20;
            ShopRecord shop = saas.shop.getShopById(getShopId());
            String moneyFlag = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages");
            String realPrice = moneyFlag+baseParam.getRealPrice().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
            /**‘专享价格’文字*/
//            String specialText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_REBATE_SPECIAL_DOC, "messages");

            // 添加商品名称
            pictorialService.addTextWithBreak(rebateBufferImg,goodsRecord.getGoodsName(),textStartX,toTop + 10,220,3,ImageUtil.SourceHanSansCN(Font.PLAIN, 18));
            // 添加‘真实价格’文字
            ImageUtil.addFont(rebateBufferImg, realPrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 22), textStartX, toTop + goodsBufferImg.getHeight()-30, getShopStyleColor(),false);
            Integer textWidth = ImageUtil.getTextWidth(rebateBufferImg, ImageUtil.SourceHanSansCN(Font.PLAIN, 22), realPrice);
            // 添加划线价￥
            if (baseParam.getLinePrice()!=null&&!BigDecimal.valueOf(0).equals(baseParam.getLinePrice())){
                String linePrice = moneyFlag + baseParam.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                ImageUtil.addFontWithLine(rebateBufferImg, textStartX + textWidth + 20, toTop + goodsBufferImg.getHeight()-25, linePrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 18), PictorialImgPx.LINE_PRICE_COLOR);
            }

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(goodsRecord.getGoodsId());
            PictorialRule pictorialRule = new PictorialRule(goodsRecord.getUpdateTime(), null,shopStyleColor.getRed(),shopStyleColor.getGreen(),shopStyleColor.getBlue());
            pictorialService.uploadToUpanYun(rebateBufferImg, relativePath, pictorialRule, goodsRecord.getGoodsId(), null, PictorialConstant.REBATE_ACTION_SHARE, null, baseParam.getUserId());

            return relativePath;
        } catch (IOException e) {
            shareLog(getActivityName(), "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            shareLog(getActivityName(), "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    @Override
    public GoodsShareInfo getShareInfo(GoodsShareBaseParam param) {
        GoodsShareInfo shareInfoVo = new GoodsShareInfo();
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        // 商品信息不可用
        if (goodsRecord == null) {
            shareLog(getActivityName(), "商品信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.GOODS_DELETED);
            return shareInfoVo;
        }
        ShopRecord shop = saas.shop.getShopById(getShopId());

        String imgPath = createShareImage(null, goodsRecord, param);
        if (imgPath == null) {
            shareInfoVo.setShareCode(PictorialConstant.GOODS_PIC_ERROR);
            return shareInfoVo;
        }
        shareInfoVo.setShareDoc(createDefaultShareDoc(shop.getShopLanguage(),null,goodsRecord,param));
        shareInfoVo.setImgUrl(imageService.getImgFullUrl(imgPath));
        return shareInfoVo;
    }

    @Override
    String createMpQrCode(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        RebateShareInfoParam rebateShareInfoParam = (RebateShareInfoParam) baseParam;
        RebateSceneValue sceneValue = new RebateSceneValue();
        sceneValue.setUid(baseParam.getUserId());
        sceneValue.setGid(baseParam.getTargetId());
        sceneValue.setShareAwardId(baseParam.getShareAwardId());
        sceneValue.setRebateConfig(rebateShareInfoParam.getRebateConfig());
        String paramStr = addAndGetSceneStr(sceneValue);
        return qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM,paramStr);
    }

    @Override
    @SuppressWarnings("all")
    void createPictorialImg(BufferedImage qrCodeBufferImg, BufferedImage goodsImg, PictorialUserInfo userInfo, String shareDoc, Record aRecord, GoodsRecord goodsRecord, ShopRecord shop, GoodsShareBaseParam baseParam, GoodsPictorialInfo goodsPictorialInfo) {
        RebateShareInfoParam rebateShareInfoParam = (RebateShareInfoParam) baseParam;;
        PictorialImgPx imgPx = new PictorialImgPx(getShopStyleColor());
        if (BigDecimal.valueOf(0).equals(baseParam.getLinePrice())) {
            baseParam.setLinePrice(null);
        }
        // 活动价
        String realPriceText = convertPriceWithFlag(shop.getShopLanguage(), baseParam.getRealPrice());

        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(userInfo, shop, qrCodeBufferImg, goodsImg, shareDoc, goodsRecord.getGoodsName(),null,null, realPriceText, null, imgPx);

        String rebateConfig = rebateShareInfoParam.getRebateConfig();
        GoodsRebateConfigParam goodsRebateConfigParam = Util.parseJson(rebateConfig, GoodsRebateConfigParam.class);
        Long time =null;
        if (goodsRebateConfigParam == null) {
            time = System.currentTimeMillis();
        } else {
            time = goodsRebateConfigParam.getRebateTime()*1000;
        }
        String endStr = DateUtil.dateFormat(DateUtil.DATE_FORMATE_MONTH,new Date(time+24*60*60*1000));

        String timeTipStr = "此价格在"+endStr+"前有效";
        ImageUtil.addFont(bgBufferedImage, timeTipStr, imgPx.getLinePriceFont(), imgPx.getBottomTextStartX(), imgPx.getPriceY()+40, imgPx.LINE_PRICE_COLOR, false);

        String base64 = ImageUtil.toBase64(bgBufferedImage);
        goodsPictorialInfo.setBase64(base64);
        goodsPictorialInfo.setBgImg(bgBufferedImage);
    }

    @Override
    String getActivityName() {
        return "rebate";
    }
}
