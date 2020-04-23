package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSharePostConfig;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsVo;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

/**
 * @author 李晓冰
 * @date 2020年01月17日
 */
@Service
public class NormalGoodsPictorialService extends ShareBaseService {
    @Autowired
    GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 普通商品-分享图片生成
     *
     * @param param 商品分享参数
     */
    public GoodsShareInfo getNormalGoodsShareInfo(GoodsShareBaseParam param) {
        GoodsShareInfo shareInfoVo = new GoodsShareInfo();
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        if (goodsRecord == null) {
            shareLog(getActivityName(), "商品不可用");
            shareInfoVo.setShareCode(PictorialConstant.GOODS_DELETED);
            return shareInfoVo;
        }
        GoodsSharePostConfig goodsShareConfig = Util.parseJson(goodsRecord.getShareConfig(), GoodsSharePostConfig.class);
        PictorialShareConfig  shareConfig= PictorialShareConfig.createFromGoodsShareInfoConfig(goodsShareConfig);
        return parsePictorialShareConfig(shareConfig,null,goodsRecord,param);
    }

    @Override
    protected String createShareImage(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        return goodsRecord.getGoodsImg();
    }

    @Override
    protected String createDefaultShareDoc(String lang, Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        return Util.translateMessage(lang, JsonResultMessage.WX_MA_NORMAL_GOODS_SHARE_INFO, "", "messages", baseParam.getUserName(), goodsRecord.getGoodsName());
    }
    /**
     * 普通商品海报生成接口
     *
     * @param param 获取海报参数
     * @return GoodsPictorialInfo 海报获取信息
     */
    public GoodsPictorialInfo getNormalGoodsPictorialInfo(GoodsShareBaseParam param) {
        GoodsPictorialInfo goodsPictorialInfo = new GoodsPictorialInfo();

        GoodsVo goodsVo = goodsService.selectGoodsShareInfo(param.getTargetId());
        if (goodsVo == null) {
            pictorialLog(getActivityName(), "商品不可用");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_DELETED);
            return goodsPictorialInfo;
        }

        ShopRecord shop = saas.shop.getShopById(getShopId());
        GoodsSharePostConfig goodsSharePostConfig = goodsVo.getGoodsSharePostConfig();
        // 用户信息
        PictorialUserInfo pictorialUserInfo;
        try {
            pictorialLog(getActivityName(), "获取用户信息");
            pictorialUserInfo = pictorialService.getPictorialUserInfo(param.getUserId(), shop);
        } catch (IOException e) {
            pictorialLog(getActivityName(),"获取用户信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.USER_PIC_ERROR);
            return goodsPictorialInfo;
        }
        BufferedImage goodsImage;
        String shareDoc = null;
        String goodsImg;
        if (PictorialShareConfig.DEFAULT_STYLE.equals(goodsSharePostConfig.getShareAction())) {
            goodsImg = goodsVo.getGoodsImg();
            shareDoc = pictorialService.getCommonConfigDoc(param.getUserName(), goodsVo.getGoodsName(), param.getRealPrice(), shop.getShopLanguage(), true);
            if (shareDoc == null) {
                shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_NORMAL_GOODS_INFO, "messages");
            }
        } else {
            shareDoc = goodsSharePostConfig.getShareDoc();
            if (!PictorialShareConfig.DEFAULT_IMG.equals(goodsSharePostConfig.getShareImgAction())) {
                goodsImg = goodsSharePostConfig.getShareImgPath();
            } else {
                goodsImg = goodsVo.getGoodsImg();
            }
        }
        try {
            goodsImage = ImageIO.read(new URL(imageService.getImgFullUrl(goodsImg)));
        } catch (IOException e) {
            pictorialLog(getActivityName(), "获取商品图片信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_PIC_ERROR);
            return goodsPictorialInfo;
        }

        // 获取分享码
        String mpQrCode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("uid=%d&gid=%d&aid=%d&atp=%d",param.getUserId(),goodsVo.getGoodsId(), null, null));
        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrCode));
        } catch (IOException e) {
            pictorialLog(getActivityName(), "获取二维码失败");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.QRCODE_ERROR);
            return goodsPictorialInfo;
        }

        PictorialImgPx imgPx = new PictorialImgPx();
        if (BigDecimal.valueOf(0).equals(param.getLinePrice())) {
            param.setLinePrice(null);
        }
        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo, shop, qrCodeImage, goodsImage, shareDoc, goodsVo.getGoodsName(),param.getRealPrice(), param.getLinePrice(), imgPx);

        String base64 = ImageUtil.toBase64(bgBufferedImage);
        goodsPictorialInfo.setBase64(base64);
        return goodsPictorialInfo;
    }

    @Override
    protected String getActivityName() {
        return "normal_goods";
    }
}
