package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.db.shop.tables.records.ReducePriceRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
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
 * @date 2020年02月06日
 */
@Service
public class ReducePricePictorialService extends ShareBaseService {

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

    @Override
     Record getActivityRecord(Integer activityId) {
        return reducePriceService.getReducePriceRecordCanDel(activityId);
    }

    @Override
     PictorialShareConfig getPictorialConfig(Record aRecord, GoodsRecord goodsRecord) {
        ReducePriceRecord record = (ReducePriceRecord) aRecord;
        return Util.parseJson(record.getShareConfig(), PictorialShareConfig.class);
    }

    /**
     * 限时降价分享背景图片地址
     */
    private static final String REDUCE_PRICE_SHARE_BG_IMG = "image/wxapp/reduce_price_share.png";
    @Override
     String createShareImage(Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        ReducePriceRecord reducePriceRecord = (ReducePriceRecord) aRecord;
        ReducePriceShareInfoParam param= (ReducePriceShareInfoParam) baseParam;

        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(), param.getActivityId(), PictorialConstant.REDUCE_PRICE_ACTION_SHARE, null);
        // 已存在生成的图片
        if (pictorialRecord != null && pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(), goodsRecord.getUpdateTime(), reducePriceRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }
        try (InputStream bgInputStream = Util.loadFile(REDUCE_PRICE_SHARE_BG_IMG)) {
            BufferedImage reducePriceBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            int goodsWidth = 458;
            int goodsHeight = 367;
            int toTop = 30;
            int toLeft = goodsWidth - 30 - reducePriceBufferImg.getWidth();

            goodsBufferImg = ImageUtil.resizeImage(goodsWidth, goodsHeight, goodsBufferImg);
            ImageUtil.addTwoImage(goodsBufferImg, reducePriceBufferImg, toLeft, toTop);

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(reducePriceRecord.getId());
            PictorialRule pictorialRule = new PictorialRule(goodsRecord.getUpdateTime(), reducePriceRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(goodsBufferImg, relativePath, pictorialRule, goodsRecord.getGoodsId(),param.getActivityId(),PictorialConstant.REDUCE_PRICE_ACTION_SHARE, pictorialRecord, param.getUserId());
            return relativePath;
        } catch (IOException e) {
            shareLog(getActivityName(),"图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            shareLog(getActivityName(), "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    @Override
     String createDefaultShareDoc(String lang, Record aRecord, GoodsRecord goodsRecord, GoodsShareBaseParam baseParam) {
        return Util.translateMessage(lang, JsonResultMessage.WX_MA_NORMAL_GOODS_SHARE_INFO, "", "messages", baseParam.getUserName(), goodsRecord.getGoodsName());
    }


    /**
     * 限时降价海报生成
     *
     * @param param 限时降价售参数
     * @return base64海报信息
     */
    public GoodsPictorialInfo getReducePricePictorialInfo(ReducePriceShareInfoParam param) {
        GoodsPictorialInfo goodsPictorialInfo = new GoodsPictorialInfo();
        ShopRecord shop = saas.shop.getShopById(getShopId());
        ReducePriceRecord reducePriceRecord = reducePriceService.getReducePriceRecordCanDel(param.getActivityId());
        if (reducePriceRecord == null) {
            pictorialLog(getActivityName(), "限时降价信息已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.ACTIVITY_DELETED);
            return goodsPictorialInfo;
        }
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        if (goodsRecord == null) {
            pictorialLog(getActivityName(), "商品信息已删除或失效");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_DELETED);
            return goodsPictorialInfo;
        }
        PictorialShareConfig shareConfig = Util.parseJson(reducePriceRecord.getShareConfig(), PictorialShareConfig.class);

        PictorialUserInfo pictorialUserInfo;
        try {
            pictorialLog(getActivityName(), "获取用户信息");
            pictorialUserInfo = pictorialService.getPictorialUserInfo(param.getUserId(), shop);
        } catch (IOException e) {
            pictorialLog(getActivityName(), "获取用户信息失败：" + e.getMessage());
            goodsPictorialInfo.setPictorialCode(PictorialConstant.USER_PIC_ERROR);
            return goodsPictorialInfo;
        }
        getReducePricePictorialImg(pictorialUserInfo, shareConfig, reducePriceRecord, goodsRecord, shop, param, goodsPictorialInfo);
        return goodsPictorialInfo;
    }

    private static final String REDUCE_PRICE_BG_IMG = "image/wxapp/reduce_price.png";

    private void getReducePricePictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, ReducePriceRecord reducePriceRecord, GoodsRecord goodsRecord, ShopRecord shop, ReducePriceShareInfoParam param, GoodsPictorialInfo goodsPictorialInfo) {
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
                shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_NORMAL_GOODS_INFO, "", "messages");
            }
        } else {
            shareDoc = shareConfig.getShareDoc();
        }
        String mpQrcode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("uid=%d&gid=%d&aid=%d&atp=%d",param.getUserId(),goodsRecord.getGoodsId(), reducePriceRecord.getId(), BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE));

        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrcode));
        } catch (IOException e) {
            pictorialLog(getActivityName(),"获取二维码失败");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.QRCODE_ERROR);
            return;
        }

        PictorialImgPx imgPx = new PictorialImgPx();

        BufferedImage reduceIconBufferImg=null;
        // 拼装价值限时降价图片和商品价格
        try (InputStream reduceIconStream = Util.loadFile(REDUCE_PRICE_BG_IMG)) {
             reduceIconBufferImg = ImageIO.read(reduceIconStream);
        } catch (IOException e) {
            pictorialLog(getActivityName(),"装载限时降价图标失败");
            goodsPictorialInfo.setPictorialCode(PictorialConstant.GOODS_PIC_ERROR);
            return;
        }
        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo, shop, qrCodeImage, goodsImage, shareDoc, goodsRecord.getGoodsName(),reduceIconBufferImg,param.getRealPrice(), param.getLinePrice(), imgPx);

        String base64 = ImageUtil.toBase64(bgBufferedImage);
        goodsPictorialInfo.setBase64(base64);
    }

    @Override
    protected String getActivityName() {
        return "reduce_price";
    }
}
