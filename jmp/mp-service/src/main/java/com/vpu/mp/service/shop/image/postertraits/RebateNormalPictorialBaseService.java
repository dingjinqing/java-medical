package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSharePostConfig;
import com.vpu.mp.service.pojo.wxapp.share.GoodsPictorialInfo;
import com.vpu.mp.service.pojo.wxapp.share.GoodsShareBaseParam;
import com.vpu.mp.service.pojo.wxapp.share.PictorialImgPx;
import com.vpu.mp.service.pojo.wxapp.share.PictorialUserInfo;
import org.jooq.Record;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.URL;

/**
 * @author 李晓冰
 * @date 2020年06月15日
 */
public abstract class RebateNormalPictorialBaseService extends ShareBaseService{

    @Override
    PictorialShareConfig getPictorialConfig(Record aRecord, GoodsRecord goodsRecord) {
        GoodsSharePostConfig goodsShareConfig = Util.parseJson(goodsRecord.getShareConfig(), GoodsSharePostConfig.class);
        return PictorialShareConfig.createFromGoodsShareInfoConfig(goodsShareConfig);
    }

    @Override
    void createPictorialImg(BufferedImage qrCodeBufferImg, BufferedImage goodsImg, PictorialUserInfo userInfo, String shareDoc, Record aRecord, GoodsRecord goodsRecord, ShopRecord shop, GoodsShareBaseParam baseParam, GoodsPictorialInfo goodsPictorialInfo) {
        Byte shareStyle = getPictorialShareStyle();

        PictorialImgPx imgPx = new PictorialImgPx(shareStyle, getShopStyleColor());
        if (BigDecimal.valueOf(0).equals(baseParam.getLinePrice())) {
            baseParam.setLinePrice(null);
        }
        // 活动价
        String realPriceText = convertPriceWithFlag(shop.getShopLanguage(), baseParam.getRealPrice());
        // 划线价格
        String linePriceText = convertPriceWithFlag(shop.getShopLanguage(), baseParam.getLinePrice());

        // 拼装背景图
        BufferedImage bgBufferedImage = null;
        if (PictorialImgPx.BASIC_STYLE.equals(shareStyle)) {
            bgBufferedImage = pictorialService.createBasicStylePictorialBgImage(shop, qrCodeBufferImg, goodsImg, goodsRecord.getGoodsName(), realPriceText, linePriceText, imgPx);
        } else if (PictorialImgPx.SHARE_PERSON_STYLE.equals(shareStyle)) {
            bgBufferedImage = pictorialService.createSharePersonInfoPictorialBgImage(userInfo, shop, qrCodeBufferImg, goodsImg, shareDoc, goodsRecord.getGoodsName(), realPriceText, linePriceText, imgPx);
        } else if (PictorialImgPx.SHOP_STYLE.equals(shareStyle)) {
            BufferedImage shopIconImg = getShopIconImg(shop);
            if (shopIconImg == null) {
                shopIconImg = userInfo.getUserAvatarImage();
            }
            bgBufferedImage = pictorialService.createShareShopInfoPictorialBgImage(shopIconImg, shop, qrCodeBufferImg, goodsImg, goodsRecord.getGoodsName(), realPriceText, linePriceText, imgPx);
        } else if (PictorialImgPx.SHARE_PERSON_SHOP_STYLE.equals(shareStyle)) {
            BufferedImage shopIconImg = getShopIconImg(shop);
            bgBufferedImage = pictorialService.createSharePersonShopInfoPictorialBgImage(userInfo, shop, qrCodeBufferImg,shopIconImg, goodsImg,shareDoc, goodsRecord.getGoodsName(), realPriceText, linePriceText, imgPx);
        } else {
            bgBufferedImage = pictorialService.createPictorialBgImage(userInfo, shop, qrCodeBufferImg, goodsImg, shareDoc, goodsRecord.getGoodsName(),null,null, realPriceText, linePriceText, imgPx);
        }

        String base64 = ImageUtil.toBase64(bgBufferedImage);
        goodsPictorialInfo.setBase64(base64);
        goodsPictorialInfo.setBgImg(bgBufferedImage);
    }


    private BufferedImage getShopIconImg(ShopRecord shop){
        BufferedImage shopIconImg = null;
        try {
            String url = imageService.getImgFullUrl(shop.getShopAvatar());
            shopIconImg = ImageIO.read(new URL(url));
        } catch (Exception e) {
            logger().debug("店铺头像获取异常");
        }
        return shopIconImg;
    }
}
