package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GroupDrawRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSharePostConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.pojo.wxapp.share.group.GroupDrawShareInfoParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawService;
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
 * 拼团抽将分享图片生成器
 * @author 李晓冰
 * @date 2020年02月03日
 */
@Service
public class GroupDrawPictorialService extends ShopBaseService {
    @Autowired
    private GroupDrawService groupDrawService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PictorialService pictorialService;
    @Autowired
    private QrCodeService qrCodeService;

    /**
     * 拼团抽奖活动获取分享图片
     * @param param 分享参数
     * @return 分享信息
     */
    @SuppressWarnings("all")
    public GoodsShareInfo getGroupDrawShareInfo(GroupDrawShareInfoParam param) {
        GoodsShareInfo goodsShareInfo = new GoodsShareInfo();

        GroupDrawRecord groupDrawRecord = groupDrawService.getById(param.getActivityId());

        // 拼团活动信息不可用
        if (groupDrawRecord == null) {
            groupDrawLog("分享", "拼团抽奖活动信息不可用");
            goodsShareInfo.setShareCode(GoodsShareInfo.ACTIVITY_DELETED);
            return goodsShareInfo;
        }

        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());
        // 拼团商品信息不可用
        if (goodsRecord == null) {
            groupDrawLog("分享", "拼团抽奖商品信息不可用");
            goodsShareInfo.setShareCode(GoodsShareInfo.GOODS_DELETED);
            return goodsShareInfo;
        }
        GoodsSharePostConfig shareConfig = Util.parseJson(goodsRecord.getShareConfig(), GoodsSharePostConfig.class);
        goodsShareInfo.setShareAction(shareConfig.getShareAction());


        // 用户自定义分享样式
        if (PictorialShareConfig.CUSTOMER_STYLE.equals(shareConfig.getShareAction())) {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                goodsShareInfo.setImgUrl(goodsRecord.getGoodsImg());
            } else {
                // 此时分享配置的shareImgUrl是直接获取数据库里面的内容，是相对路径
                goodsShareInfo.setImgUrl(shareConfig.getShareImgUrl());
            }
            goodsShareInfo.setShareDoc(shareConfig.getShareDoc());
        } else {
            // 使用默认分享图片样式
            ShopRecord shop = saas.shop.getShopById(getShopId());
            String imgPath = createGroupDrawShareImg(groupDrawRecord, goodsRecord, param);
            String doc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_DRAW_SHARE_DOC, "messages",param.getRealPrice());
            goodsShareInfo.setImgUrl(imgPath);
            goodsShareInfo.setShareDoc(doc);
        }
        goodsShareInfo.setImgUrl(imageService.getImgFullUrl(goodsShareInfo.getImgUrl()));

        return goodsShareInfo;
    }

    /**
     * 拼团抽奖分享背景图片地址
     */
    private static final String GROUP_DRAW_BG_IMG = "image/wxapp/group_draw.png";

    /**
     * 生成商拼团抽奖分享图
     * @param groupDrawRecord 活动信息
     * @param goodsRecord 商品信息
     * @param param 图片参数
     * @return
     */
    private String createGroupDrawShareImg(GroupDrawRecord groupDrawRecord, GoodsRecord goodsRecord, GroupDrawShareInfoParam param){
        PictorialRecord pictorialRecord = pictorialService.getPictorialDao(goodsRecord.getGoodsId(),param.getActivityId(), PictorialConstant.GROUP_DRAW_ACTION_SHARE,null);

        // 已存在生成的图片
        if (pictorialRecord != null&&pictorialService.isGoodsSharePictorialRecordCanUse(pictorialRecord.getRule(),goodsRecord.getUpdateTime(),groupDrawRecord.getUpdateTime())) {
            return pictorialRecord.getPath();
        }

        try (InputStream bgInputStream = Util.loadFile(GROUP_DRAW_BG_IMG)){

            BufferedImage bgBufferImg = ImageIO.read(bgInputStream);
            BufferedImage goodsBufferImg = ImageIO.read(new URL(imageService.getImgFullUrl(goodsRecord.getGoodsImg())));

            int goodsWidth = 162;
            int toTop = 85;
            int toLeft = 80;

            goodsBufferImg = ImageUtil.resizeImage(goodsWidth, goodsWidth, goodsBufferImg);
            ImageUtil.addTwoImage(bgBufferImg, goodsBufferImg, toTop, toLeft);

            ShopRecord shop = saas.shop.getShopById(getShopId());

            int textStartX = toLeft+goodsWidth+20;
            Color lineColor = new Color(255, 102, 102);
            //添加拼团抽奖文字
            String msg = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_DRAW_SHARE_INFO, "messages");
            ImageUtil.addFontWithRect(bgBufferImg,textStartX,toTop+20,msg,ImageUtil.SourceHanSansCN(Font.PLAIN, 16),lineColor,new Color(255, 238, 238),lineColor);

            //添加真实价格
            String moneyFlag = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages");
            String realPrice = param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFont(bgBufferImg,moneyFlag+realPrice,ImageUtil.SourceHanSansCN(Font.PLAIN, 20),textStartX,toTop+80,lineColor);
            //添加划线价格
            String linePrice = param.getLinePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFontWithLine(bgBufferImg,textStartX,toTop+100,linePrice,ImageUtil.SourceHanSansCN(Font.PLAIN, 18),new Color(153,153,153));

            // 上传u盘云并缓存入库
            String relativePath = createFilePath(groupDrawRecord.getId(), "share");
            PictorialRule pictorialRule =new PictorialRule(goodsRecord.getUpdateTime(),groupDrawRecord.getUpdateTime());
            pictorialService.uploadToUpanYun(bgBufferImg, relativePath,pictorialRule, goodsRecord.getGoodsId(),pictorialRecord, param.getUserId());

            return relativePath;

        } catch (IOException e) {
            groupDrawLog("分享", "图片生成错误：" + e.getMessage());
        } catch (UpException e) {
            groupDrawLog("分享", "UpanYun上传错误：" + e.getMessage());
        }
        return null;
    }

    /**
     * 拼团抽奖-海报生成
     * @param param 拼团抽奖海报参数
     * @return 拼团抽奖海报图片base64
     */
    public String getGroupDrawPictorialInfo(GroupDrawShareInfoParam param){
        ShopRecord shop = saas.shop.getShopById(getShopId());
        GroupDrawRecord groupDrawRecord = groupDrawService.getById(param.getActivityId());
        GoodsRecord goodsRecord = goodsService.getGoodsRecordById(param.getTargetId());

        if (groupDrawRecord == null || goodsRecord == null) {
            groupDrawLog("pictorial", "商品或拼团信息已删除或失效");
            return null;
        }


        GoodsSharePostConfig goodsShareConfig = Util.parseJson(goodsRecord.getShareConfig(), GoodsSharePostConfig.class);
        PictorialShareConfig shareConfig =new PictorialShareConfig();

        shareConfig.setShareImgAction(goodsShareConfig.getShareImgAction());
        shareConfig.setShareImg(goodsShareConfig.getShareImgUrl());
        shareConfig.setShareDoc(goodsShareConfig.getShareDoc());
        shareConfig.setShareAction(goodsShareConfig.getShareAction());

        PictorialUserInfo pictorialUserInfo;
        try {
            groupDrawLog("pictorial", "获取用户信息");
            pictorialUserInfo = pictorialService.getPictorialUserInfo(param.getUserId(),shop);
        } catch (IOException e) {
            groupDrawLog("pictorial", "获取用户信息失败：" + e.getMessage());
            return null;
        }
        return getGroupDrawPictorialImg(pictorialUserInfo,shareConfig,groupDrawRecord,goodsRecord,shop,param);
    }

    private String getGroupDrawPictorialImg(PictorialUserInfo pictorialUserInfo, PictorialShareConfig shareConfig, GroupDrawRecord groupDrawRecord, GoodsRecord goodsRecord, ShopRecord shop, GroupDrawShareInfoParam param){
        BufferedImage goodsImage;
        try {
            groupDrawLog("pictorial", "获取商品图片信息");
            goodsImage = pictorialService.getGoodsPictorialImage(shareConfig, goodsRecord);
        } catch (IOException e) {
            groupDrawLog("pictorial", "获取商品图片信息失败：" + e.getMessage());
            return null;
        }

        groupDrawLog("pictorial", "获取商品分享语");
        String shareDoc;
        if (PictorialShareConfig.DEFAULT_STYLE.equals(shareConfig.getShareAction())) {
            shareDoc = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_DRAW_PICTORIAL_DOC, "messages",param.getRealPrice());
        } else {
            shareDoc = shareConfig.getShareDoc();
        }

        String mpQrcode;
        if (GoodsConstant.GOODS_ITEM.equals(param.getPageType())) {
            mpQrcode = qrCodeService.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, String.format("gid=%d&aid=%d&atp=%d", goodsRecord.getGoodsId(), groupDrawRecord.getId(), BaseConstant.ACTIVITY_TYPE_GROUP_DRAW));
        } else {
            mpQrcode = qrCodeService.getMpQrCode(QrCodeTypeEnum.LOTTERY, String.format("gid=%d&aid=%d&atp=%d", goodsRecord.getGoodsId(), groupDrawRecord.getId(), BaseConstant.ACTIVITY_TYPE_GROUP_DRAW));
        }
        BufferedImage qrCodeImage;
        try {
            qrCodeImage = ImageIO.read(new URL(mpQrcode));
        } catch (IOException e) {
            groupDrawLog("pictorial", "获取二维码失败");
            return null;
        }
        PictorialImgPx imgPx = new PictorialImgPx();
        // 拼装背景图
        BufferedImage bgBufferedImage = pictorialService.createPictorialBgImage(pictorialUserInfo,shop,qrCodeImage, goodsImage, shareDoc, goodsRecord.getGoodsName(),param.getRealPrice(),param.getLinePrice(),imgPx,true);

        // 拼团抽奖文字
        String groupDrawText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_GROUP_DRAW_SHARE_INFO, "messages");
        int rectWidth = ImageUtil.addFontWithRect(bgBufferedImage, imgPx.getCustomerTextStartX(), imgPx.getCustomerSecondTextStartY(), groupDrawText, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()), imgPx.getCustomerTextFontColor(), null, imgPx.getCustomerTextFontColor());

        // 活动价格
        String realPriceText = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages")+param.getRealPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        int realPriceTextStartX = imgPx.getCustomerTextStartX()+rectWidth+10;
        ImageUtil.addFont(bgBufferedImage,realPriceText,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getLargeFontSize()),realPriceTextStartX,imgPx.getCustomerTextStartY(),imgPx.getCustomerTextFontColor(),false);
        Integer realPriceTextWidth = ImageUtil.getTextWidth(bgBufferedImage,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getLargeFontSize()),realPriceText);

        // 划线价格
        String linePriceText =  Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages")+param.getLinePrice().setScale(2,BigDecimal.ROUND_HALF_UP);
        int linePriceTextStartX = realPriceTextStartX+realPriceTextWidth+5;
        ImageUtil.addFontWithLine(bgBufferedImage,linePriceTextStartX,imgPx.getCustomerSecondTextStartY(),linePriceText,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getSmallFontSize()),imgPx.getCustomerTextFontColor());

        return ImageUtil.toBase64(bgBufferedImage);
    }

    /**
     * 创建云盘上的相对路径
     * @param activityId       活动Id
     * @param shareOrPictorial "share" 或 "pictorial"
     * @return 相对路径
     */
    private String createFilePath(Integer activityId, String shareOrPictorial) {
        return String.format("/upload/%s/%s/groupdraw/%s.jpg", getShopId(), shareOrPictorial, activityId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
    }

    private void groupDrawLog(String share,String msg){
        logger().debug("小程序-拼团抽奖{}-{}", share, msg);
    }
}
