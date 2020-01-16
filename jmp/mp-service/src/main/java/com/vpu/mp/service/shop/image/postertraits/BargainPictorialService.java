package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.db.shop.tables.records.CodeRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.market.bargain.BargainRecordInfo;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.bargain.BargainShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;

import static com.vpu.mp.db.shop.tables.Code.CODE;
import static java.lang.String.format;

/**
 * 砍价分享图片生成器
 * @author 李晓冰
 * @date 2020年01月03日
 */
@Service
public class BargainPictorialService extends ShopBaseService {
    @Autowired
    private BargainService bargainService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 砍价活动-商品详情分享图片生成
     * @param param 砍价分享参数
     * @return 砍价分享图片信息
     */
    public GoodsShareInfo getBargainShareInfo(BargainShareInfoParam param) {

        GoodsShareInfo shareInfoVo = new GoodsShareInfo();
        BargainRecord bargainRecord = bargainService.getBargainActById(param.getActivityId());

        // 拼团活动信息不可用
        if (bargainRecord == null) {
            bargainLog("分享", "砍价活动信息不可用");
            shareInfoVo.setShareCode(GoodsShareInfo.ACTIVITY_DELETED);
            return shareInfoVo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(bargainRecord.getGoodsId());
        // 拼团商品信息不可用
        if (goodsRecord == null) {
            bargainLog("分享", "砍价商品信息不可用");
            shareInfoVo.setShareCode(GoodsShareInfo.GOODS_DELETED);
            return shareInfoVo;
        }

        PictorialShareConfig shareConfig = Util.parseJson(bargainRecord.getShareConfig(), PictorialShareConfig.class);
        shareInfoVo.setShareAction(shareConfig.getShareAction());

        // 用户自定义分享样式
        if (PictorialShareConfig.CUSTOMER_STYLE.equals(shareConfig.getShareAction())) {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                shareInfoVo.setImgUrl(goodsRecord.getGoodsImg());
            } else {
                shareInfoVo.setImgUrl(shareConfig.getShareImg());
            }
            shareInfoVo.setShareDoc(shareConfig.getShareDoc());
        } else {
            String imgPath = createBargainShareImg(bargainRecord, goodsRecord, param);
            shareInfoVo.setImgUrl(imgPath);
        }

        shareInfoVo.setImgUrl(imageService.getImgFullUrl(shareInfoVo.getImgUrl()));
        return shareInfoVo;
    }
    /**
     * 砍价商品详情分享图片地址
     */
    private static final String BARGAIN_BG_IMG = "image/wxapp/bargain_bg.png";


    /**
     * 生成商品详情分享图
     * @param bargainRecord
     * @param goodsRecord
     * @param param
     * @return
     */
    private String createBargainShareImg(BargainRecord bargainRecord, GoodsRecord goodsRecord,BargainShareInfoParam param) {
        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(), PictorialConstant.BARGAIN_ACTION_SHARE, param.getUserId());
        // 已存在生成的图片
        if (pictorialRecord != null&&pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(),goodsRecord.getUpdateTime(),bargainRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }

        try (InputStream bgInputStream = Util.loadFile(BARGAIN_BG_IMG)){

            BufferedImage bgBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            goodsBufferImg = ImageUtil.resizeImage(132, 132, goodsBufferImg);
            ImageUtil.addTwoImage(bgBufferImg, goodsBufferImg, 60, 120);

            ShopRecord shop = saas.shop.getShopById(getShopId());
            // 添加商品名称
            ImageUtil.addFont(bgBufferImg,goodsRecord.getGoodsName(),ImageUtil.SourceHanSansCN(Font.PLAIN,14),200,140,new Color(51,51,51));

            // 添加原价
            String realPriceStr = param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP)+ Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY, "messages");
            ImageUtil.addFont(bgBufferImg,realPriceStr,ImageUtil.SourceHanSansCN(Font.PLAIN,23),200,250,new Color(255,102,102));
            Integer realPriceLength = ImageUtil.getTextWidth(bgBufferImg,ImageUtil.SourceHanSansCN(Font.PLAIN,23),realPriceStr);

            // 添加划线价
            String linePriceStr = param.getLinePrice().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFont(bgBufferImg,linePriceStr,ImageUtil.SourceHanSansCN(Font.PLAIN,18),200+realPriceLength+5,250,new Color(153,153,153));
            Integer linePriceLength = ImageUtil.getTextWidth(bgBufferImg,ImageUtil.SourceHanSansCN(Font.PLAIN,18),linePriceStr);
            ImageUtil.addLine(bgBufferImg,200+realPriceLength+2,244,200+realPriceLength+linePriceLength+7,244,new Color(153,153,153));

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(bargainRecord.getId(), "share");
            PictorialRule pictorialRule =new PictorialRule(goodsRecord.getUpdateTime(),bargainRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(bgBufferImg, relativePath,pictorialRule, goodsRecord.getGoodsId(),pictorialRecord, param.getUserId());

            return relativePath;
        }catch (IOException e) {
            bargainLog("分享", "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            bargainLog("分享", "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    /**
     * 砍价活动-获取海报
     * @param param 砍价分享参数
     * @return 砍价海报图片base64
     */
    public String getBargainPictorialInfo(BargainShareInfoParam param) {
        ShopRecord shop = saas.shop.getShopById(getShopId());
        BargainRecord bargainRecord = bargainService.getBargainActById(param.getActivityId());
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(bargainRecord.getGoodsId());

        if (bargainRecord == null || goodsRecord == null) {
            bargainLog("pictorial", "商品或拼团信息已删除或失效");
            return null;
        }
        bargainLog("pictorial", "读取拼团海报配置信息");
        PictorialShareConfig shareConfig = Util.parseJson(bargainRecord.getShareConfig(), PictorialShareConfig.class);


        PictorialUserInfo pictorialUserInfo;
        try {
            bargainLog("pictorial", "获取用户信息");
            pictorialUserInfo = pictorialService.getPictorialUserInfo(param.getUserId(),shop);
        } catch (IOException e) {
            bargainLog("pictorial", "获取用户信息失败：" + e.getMessage());
            return null;
        }
        return getBargainPictorialImg(pictorialUserInfo,shareConfig,bargainRecord,goodsRecord,shop,param);
    }

    /** 砍价海报中的硬币图片 */
    private static final String BARGAIN_MONEY_ICON_IMG="image/wxapp/money_icon.png";

    private String getBargainPictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, BargainRecord bargainRecord, GoodsRecord goodsRecord, ShopRecord shop, BargainShareInfoParam param){
        BufferedImage goodsImage;
        try {
            bargainLog("pictorial", "获取商品图片信息");
            goodsImage = pictorialService.getGoodsPictorialImage(shareConfig, goodsRecord);
        } catch (IOException e) {
            bargainLog("pictorial", "获取商品图片信息失败：" + e.getMessage());
            return null;
        }
        bargainLog("pictorial", "获取商品分享语");
        String shareDoc;
        if (PictorialShareConfig.DEFAULT_STYLE.equals(shareConfig.getShareAction())) {
            shareDoc =Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_BARGAIN_DOC, "messages",param.getRealPrice().setScale(2,BigDecimal.ROUND_HALF_UP));
        } else {
            shareDoc = shareConfig.getShareDoc();
        }

        // 获取分享码
        String mpQrCode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("gid=%d&aid=%d&atp=%d", goodsRecord.getGoodsId(), bargainRecord.getId(), BaseConstant.ACTIVITY_TYPE_BARGAIN));
        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrCode));
        } catch (IOException e) {
            bargainLog("pictorial", "获取二维码失败");
            return null;
        }
        PictorialImgPx imgPx = new PictorialImgPx();
        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo,shop,qrCodeImage, goodsImage, shareDoc, goodsRecord.getGoodsName(),param.getRealPrice(),param.getLinePrice(),imgPx);

        try(InputStream moneyIconIo = Util.loadFile(BARGAIN_MONEY_ICON_IMG)) {
            BufferedImage moneyIconImg = ImageIO.read(moneyIconIo);
            moneyIconImg = ImageUtil.resizeImage(40,30,moneyIconImg);
            ImageUtil.addTwoImage(bgBufferedImage,moneyIconImg,imgPx.getCustomerTextStartX(),imgPx.getCustomerTextStartY()-imgPx.getMediumFontSize());
        }catch (IOException e){
            bargainLog("pictorial", "读取本地图片money_icon错误"+e.getMessage());
            return null;
        }

        // 原价
        String realPriceText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_BARGAIN_TAKE, "messages",param.getRealPrice().setScale(2,BigDecimal.ROUND_HALF_UP));
        ImageUtil.addFont(bgBufferedImage, realPriceText,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getLargeFontSize()),imgPx.getCustomerTextStartX()+42,imgPx.getCustomerTextStartY(),imgPx.getCustomerTextFontColor());
        Integer realPriceLength=  ImageUtil.getTextWidth(bgBufferedImage,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getLargeFontSize()),realPriceText);

        // 划线价
        String linePriceText =Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages")+param.getLinePrice().setScale(2,BigDecimal.ROUND_HALF_UP);
        Integer linePriceStartX = imgPx.getCustomerTextStartX()+42+realPriceLength;
        ImageUtil.addFont(bgBufferedImage,linePriceText,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getMediumFontSize()),linePriceStartX,imgPx.getCustomerTextStartY(),imgPx.getCustomerTextFontColor());
        Integer linePriceLength=  ImageUtil.getTextWidth(bgBufferedImage,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getMediumFontSize()),linePriceText);
        // 画线
        ImageUtil.addLine(bgBufferedImage,linePriceStartX-2,imgPx.getCustomerTextStartY()-imgPx.getMediumFontSize()/3,linePriceStartX+linePriceLength+4,imgPx.getCustomerTextStartY()-imgPx.getMediumFontSize()/3,imgPx.getCustomerTextFontColor());

        return ImageUtil.toBase64(bgBufferedImage);
    }

    /**
     * 砍价活动砍价详情-分享图片生成
     * @param bargainRecordInfo 砍价发起信息
     *                          bargain
     * @return 砍价分享图片信息
     */
    public String getBargainInfoShareImg(BargainRecordInfo bargainRecordInfo) {
        // 获取分享码
        CodeRecord mpQrCode = qrCodeService.getMpQrCode(QrCodeTypeEnum.BARGAIN_INFO_SHARE, bargainRecordInfo.getId());
        if(mpQrCode == null || bargainRecordInfo.getUpdateTime().after(mpQrCode.getCreateTime())){
            String relativePath = createBargainInfoShareImg(bargainRecordInfo);
            return relativePath;
        }else {
            return mpQrCode.getQrcodeImg();
        }
    }

    /**
     * 砍价详情分享图背景图
     */
    private static final String BARGAIN_INFO_BG_IMG = "image/wxapp/bargain_bg.png";


    /**
     * 生成砍价详情分享图
     * @param bargainRecordInfo
     * @return
     */
    private String createBargainInfoShareImg(BargainRecordInfo bargainRecordInfo) {

        String dir = qrCodeService.getQrCodeImgRelativePath(QrCodeTypeEnum.BARGAIN_INFO_SHARE.getType());
        String fileName = format("T%sP%s_%s.jpg", QrCodeTypeEnum.BARGAIN_INFO_SHARE.getType(), bargainRecordInfo.getId(), DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE));

        try (InputStream bgInputStream = Util.loadFile(BARGAIN_INFO_BG_IMG)){

            BufferedImage bgBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(bargainRecordInfo.getGoodsImg())));

            goodsBufferImg = ImageUtil.resizeImage(132, 132, goodsBufferImg);
            ImageUtil.addTwoImage(bgBufferImg, goodsBufferImg, 60, 120);

            ShopRecord shop = saas.shop.getShopById(getShopId());
            // 添加商品名称
            ImageUtil.addFont(bgBufferImg,bargainRecordInfo.getGoodsName(),ImageUtil.SourceHanSansCN(Font.PLAIN,14),200,140,new Color(51,51,51));

            // 活动价
            BigDecimal bargainPrice = bargainRecordInfo.getBargainType().equals(BargainService.BARGAIN_TYPE_RANDOM) ? bargainRecordInfo.getFloorPrice() : bargainRecordInfo.getExpectationPrice();
            String bargainPriceString = bargainPrice.setScale(2, BigDecimal.ROUND_HALF_UP)+ Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY, "messages");
            ImageUtil.addFont(bgBufferImg,bargainPriceString,ImageUtil.SourceHanSansCN(Font.PLAIN,23),200,250,new Color(255,102,102));


            Integer bargainPriceLength = ImageUtil.getTextWidth(bgBufferImg,ImageUtil.SourceHanSansCN(Font.PLAIN,23),bargainPriceString);
            // 添加划线价
            String linePriceStr = bargainRecordInfo.getGoodsPrice().setScale(2,BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFont(bgBufferImg,linePriceStr,ImageUtil.SourceHanSansCN(Font.PLAIN,13),220+bargainPriceLength,251,new Color(153,153,153));
            Integer linePriceLength = ImageUtil.getTextWidth(bgBufferImg,ImageUtil.SourceHanSansCN(Font.PLAIN,10),linePriceStr);
            ImageUtil.addLine(bgBufferImg,220+bargainPriceLength,245,222 + bargainPriceLength+linePriceLength,245,new Color(153,153,153));

            // 上传u盘云并缓存入库
            String relativePath = dir + fileName;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bgBufferImg, "jpg", byteArrayOutputStream);
            // 上传又拍云
            imageService.getUpYunClient().writeFile(relativePath, byteArrayOutputStream.toByteArray(), true);

            CodeRecord codeRecord = db().newRecord(CODE);
            codeRecord.setType(QrCodeTypeEnum.BARGAIN_INFO_SHARE.getType());
            codeRecord.setParamId(bargainRecordInfo.getId().toString());
            codeRecord.setTypeUrl("");
            codeRecord.setQrcodeImg(relativePath);
            codeRecord.insert();

            return relativePath;
        }catch (IOException e) {
            logger().error("分享图片生成错误", e);
        } catch (UpException e) {
            logger().error("分享图片生成错误", e);
        }
        return null;
    }

                                            /**
     * 创建云盘上的相对路径
     * @param activityId       活动Id
     * @param shareOrPictorial "share" 或 "pictorial"
     * @return 相对路径
     */
    private String createFilePath(Integer activityId, String shareOrPictorial) {
        return String.format("/upload/%s/%s/bargain/%s.jpg", getShopId(), shareOrPictorial, activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }


    private void bargainLog(String share, String msg) {
        logger().debug("小程序-砍价{}-{}", share, msg);
    }
}
