package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.db.shop.tables.records.PresaleRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.presale.PreSaleShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
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
 * @author 李晓冰
 * @date 2020年02月05日
 */
@Service
public class PreSalePictorialService extends ShareBaseService {

    @Autowired
    PreSaleService preSaleService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 预售活动获取分享图片
     *
     * @param param 分享参数
     * @return 分享信息
     */
    public GoodsShareInfo getPreSaleShareInfo(PreSaleShareInfoParam param) {
        GoodsShareInfo shareInfoVo = new GoodsShareInfo();
        PresaleRecord presaleRecord = preSaleService.getPresaleRecord(param.getActivityId());

        // 预售活动信息不可用
        if (presaleRecord == null) {
            shareLog(getActivityName(), "定金膨胀活动信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.ACTIVITY_DELETED);
            return shareInfoVo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        // 商品信息不可用
        if (goodsRecord == null) {
            shareLog(getActivityName(), "定金膨胀商品信息不可用");
            shareInfoVo.setShareCode(PictorialConstant.GOODS_DELETED);
            return shareInfoVo;
        }

        PictorialShareConfig shareConfig = Util.parseJson(presaleRecord.getShareConfig(), PictorialShareConfig.class);
        return parsePictorialShareConfig(shareConfig,presaleRecord,goodsRecord,param);
    }

    /**
     * 定金膨胀分享背景图片地址
     */
    private static final String PRE_SALE_BG_IMG = "image/wxapp/presale.png";
    @Override
    protected String createShareImage(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        PresaleRecord presaleRecord = (PresaleRecord) aRecord;
        PreSaleShareInfoParam param = (PreSaleShareInfoParam) baseParam;

        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(), param.getActivityId(), PictorialConstant.PRE_SALE_ACTION_SHARE, null);
        // 已存在生成的图片
        if (pictorialRecord != null && pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(), goodsRecord.getUpdateTime(), presaleRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }
        try (InputStream bgInputStream = Util.loadFile(PRE_SALE_BG_IMG)) {
            BufferedImage bgBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            int goodsWidth = 162;
            int toTop = 85;
            int toLeft = 80;

            goodsBufferImg = ImageUtil.resizeImage(goodsWidth, goodsWidth, goodsBufferImg);
            ImageUtil.addTwoImage(bgBufferImg, goodsBufferImg, toTop, toLeft);

            ShopRecord shop = saas.shop.getShopById(getShopId());

            int textStartX = toLeft + goodsWidth + 20;
            //定金膨胀文字
            String msg = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PRESALE_SHARE_INFO, "messages");
            ImageUtil.addFontWithRect(bgBufferImg, textStartX, toTop + 20, msg, ImageUtil.SourceHanSansCN(Font.PLAIN, 16), PictorialImgPx.REAL_PRICE_COLOR, PictorialImgPx.SHARE_IMG_RECT_INNER_COLOR, PictorialImgPx.REAL_PRICE_COLOR);

            //添加真实价格
            String moneyFlag = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages");
            String realPrice = param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFont(bgBufferImg, moneyFlag + realPrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 20), textStartX, toTop + 80, PictorialImgPx.REAL_PRICE_COLOR);
            //添加划线价格
            String linePrice = param.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFontWithLine(bgBufferImg, textStartX, toTop + 100, moneyFlag + linePrice, ImageUtil.SourceHanSansCN(Font.PLAIN, 18),PictorialImgPx.LINE_PRICE_COLOR);

            // 上传u盘云并缓存入库
            String relativePath = moneyFlag+createFilePath(presaleRecord.getId());
            PictorialRule pictorialRule = new PictorialRule(goodsRecord.getUpdateTime(), presaleRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(bgBufferImg, relativePath, pictorialRule, goodsRecord.getGoodsId(),param.getActivityId(),PictorialConstant.PRE_SALE_ACTION_SHARE, pictorialRecord, param.getUserId());

            return relativePath;
        } catch (IOException e) {
            shareLog(getActivityName(), "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            shareLog(getActivityName(), "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    @Override
    protected String createDefaultShareDoc(String lang, Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        PreSaleShareInfoParam param= (PreSaleShareInfoParam) baseParam;
        return Util.translateMessage(lang, JsonResultMessage.WX_MA_PRESALE_SHARE_DOC, "", "messages", param.getDepositPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }


    /**
     * 定金膨胀海报生成
     *
     * @param param 定金膨胀参数
     * @return base64海报信息
     */
    public GoodsPictorialInfo getPreSalePictorialInfo(PreSaleShareInfoParam param) {
        GoodsPictorialInfo goodsPictorialInfo = new GoodsPictorialInfo();
        ShopRecord shop = saas.shop.getShopById(getShopId());
        PresaleRecord presaleRecord = preSaleService.getPresaleRecord(param.getActivityId());
        if (presaleRecord == null) {
            pictorialLog(getActivityName(), "预售信息已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.ACTIVITY_DELETED);
            return goodsPictorialInfo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        if (goodsRecord == null) {
            pictorialLog(getActivityName(),  "商品信息已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_DELETED);
            return goodsPictorialInfo;
        }
        PictorialShareConfig shareConfig = Util.parseJson(presaleRecord.getShareConfig(), PictorialShareConfig.class);

        PictorialUserInfo pictorialUserInfo;
        try {
            pictorialLog(getActivityName(), "获取用户信息");
            pictorialUserInfo = pictorialService.getPictorialUserInfo(param.getUserId(), shop);
        } catch (IOException e) {
            pictorialLog(getActivityName(), "获取用户信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.USER_PIC_ERROR);
            return goodsPictorialInfo;
        }
        getPreSalePictorialImg(pictorialUserInfo, shareConfig, presaleRecord, goodsRecord, shop, param, goodsPictorialInfo);
        return goodsPictorialInfo;
    }

    private void getPreSalePictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, PresaleRecord presaleRecord, GoodsRecord goodsRecord, ShopRecord shop, PreSaleShareInfoParam param, GoodsPictorialInfo goodsPictorialInfo) {
        BufferedImage goodsImage;
        try {
            pictorialLog(getActivityName(),  "获取商品图片信息");
            goodsImage = pictorialService.getGoodsPictorialImage(shareConfig, goodsRecord);
        } catch (IOException e) {
            pictorialLog(getActivityName(),  "获取商品图片信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_PIC_ERROR);
            return;
        }

        pictorialLog(getActivityName(),  "获取商品分享语");
        String shareDoc = null;
        if (PictorialShareConfig.DEFAULT_STYLE.equals(shareConfig.getShareAction())) {
            shareDoc = pictorialService.getCommonConfigDoc(param.getUserName(), goodsRecord.getGoodsName(), param.getRealPrice(), shop.getShopLanguage(), true);
            if (shareDoc == null) {
                shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PRESALE_PICTORIAL_DOC, "", "messages", param.getDepositPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
        } else {
            shareDoc = shareConfig.getShareDoc();
        }
        String mpQrcode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("uid=%d&gid=%d&aid=%d&atp=%d",param.getUserId(),goodsRecord.getGoodsId(), presaleRecord.getId(), BaseConstant.ACTIVITY_TYPE_PRE_SALE));

        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrcode));
        } catch (IOException e) {
            pictorialLog(getActivityName(),  "获取二维码失败");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.QRCODE_ERROR);
            return;
        }
        PictorialImgPx imgPx = new PictorialImgPx();

        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo, shop, qrCodeImage, goodsImage, shareDoc, goodsRecord.getGoodsName(), param.getRealPrice(), param.getLinePrice(), imgPx);

        //定金膨胀文字
        String preSaleText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PRESALE_SHARE_INFO, "messages");
        // 定金：33.55
        String depositPriceText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PRESALE_DEPOSIT, null, "messages", param.getDepositPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        pictorialService.addPictorialSelfCustomerContent(bgBufferedImage, preSaleText, depositPriceText, null, true, imgPx);

        String base64 = ImageUtil.toBase64(bgBufferedImage);
        goodsPictorialInfo.setBase64(base64);
    }

    @Override
    protected String getActivityName() {
        return "presale";
    }
}
