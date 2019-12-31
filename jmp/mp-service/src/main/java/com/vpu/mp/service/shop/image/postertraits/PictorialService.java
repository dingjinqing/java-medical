package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.pojo.wxapp.share.PictorialImgPx;
import com.vpu.mp.service.pojo.wxapp.share.PictorialUserInfo;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;

import static com.vpu.mp.db.shop.Tables.PICTORIAL;

/**
 *
 * @author zhaojianqiang
 * 2019年10月17日 下午5:19:04
 */
@Service
public class PictorialService extends ShopBaseService {
    @Autowired
    private UserService user;
    @Autowired
    private ImageService imageService;

    /** 分享海报时使用的默认头像 */
    public static final String DEFAULT_USER_AVATAR = "image/wxapp/default_user_avatar.png";

    /**
     * 获取海报中用户头像
     * @param userId 用户ID
     * @return 用户海报信息
     */
    PictorialUserInfo getPictorialUserInfo(Integer userId,ShopRecord shop) throws IOException {
        UserInfo userInfo = user.getUserInfo(userId);

        String userName = StringUtils.isBlank(userInfo.getUsername()) ?
            Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_DEFAULT_USER_NAME, "messages", null)
            : userInfo.getUsername();
        BufferedImage userAvatarImage;
        if (StringUtils.isBlank(userInfo.getUserAvatar())) {
            try (InputStream inputStream = Util.loadFile(DEFAULT_USER_AVATAR)){
                userAvatarImage = ImageIO.read(inputStream);
            } catch (IOException e) {
               logger().debug("小程序-生成图片-获取用户默认头像错误："+e.getMessage());
               throw e;
            }
        } else {
            try {
                userAvatarImage = ImageIO.read(new URL(imageService.getImgFullUrl(userInfo.getUserAvatar())));
            } catch (IOException e) {
                logger().debug("小程序-生成图片-获取用户头像错误 userId{"+userId+"}："+e.getMessage());
                throw e;
            }
        }
        PictorialUserInfo pictorialUserInfo = new PictorialUserInfo();
        pictorialUserInfo.setUserName(userName);
        pictorialUserInfo.setUserAvatarImage(userAvatarImage);

        return pictorialUserInfo;
    }

    /**
     * 获取海报分享中用到的商品图片对象
     * @param shareConfig 分享配置
     * @param goodsRecord 商品对象
     * @return 商品图片
     */
    BufferedImage getGoodsPictorialImage(PictorialShareConfig shareConfig, GoodsRecord goodsRecord) throws IOException {
        String goodsImg;
        if (PictorialShareConfig.DEFAULT_STYLE.equals(shareConfig.getShareAction())) {
            goodsImg = goodsRecord.getGoodsImg();
        } else {
            if (PictorialShareConfig.DEFAULT_IMG.equals(shareConfig.getShareImgAction())) {
                goodsImg = goodsRecord.getGoodsImg();
            } else {
                goodsImg = shareConfig.getShareImg();
            }
        }
        try {
            return ImageIO.read(new URL(imageService.getImgFullUrl(goodsImg)));
        } catch (IOException e) {
            logger().debug("小程序-生成图片-获取商品图片错误，图片地址{}：{}",imageService.getImgFullUrl(goodsImg),e.getMessage());
            throw e;
        }
    }

    /**
     * 生成海报通用背景图
     * @param userInfo 用户信息
     * @param shop 店铺配置
     * @param qrCodeImg 二维码
     * @param goodsImg 商品图片
     * @param shareDoc 海报分享文案
     * @param goodsName 商品名称
     * @param realPrice 商品原件
     * @param linePrice 商品划线价
     * @param imgPx 图片规格信息
     * @return 通过图片
     */
    BufferedImage createPictorialBgImage(PictorialUserInfo userInfo,ShopRecord shop,BufferedImage qrCodeImg, BufferedImage goodsImg, String shareDoc, String goodsName,BigDecimal realPrice,BigDecimal linePrice, PictorialImgPx imgPx) {
        //设置背景图
        BufferedImage bgBufferedImage = new BufferedImage(imgPx.getBgWidth(),imgPx.getBgHeight(),BufferedImage.TYPE_INT_RGB);
        ImageUtil.addRect(bgBufferedImage,0,0,imgPx.getBgWidth(),imgPx.getBgHeight(),null, Color.WHITE);
        // 设置用户头像
        BufferedImage userAvatarImage = ImageUtil.makeRound(userInfo.getUserAvatarImage(), imgPx.getUserHeaderDiameter());
        ImageUtil.addTwoImage(bgBufferedImage, userAvatarImage, imgPx.getBgPadding(), imgPx.getBgPadding());
        // 设置用户名
        ImageUtil.addFont(bgBufferedImage,userInfo.getUserName(),ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()),imgPx.getUserNameX(),imgPx.getUserNameY(),imgPx.getDefaultFontColor());
        // 设置宣传语
        ImageUtil.addFont(bgBufferedImage,shareDoc,ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()),imgPx.getShareDocX(),imgPx.getShareDocY(),imgPx.getDefaultFontColor());

        // 设置商品图片
        goodsImg = ImageUtil.resizeImage(imgPx.getGoodsWidth(), imgPx.getGoodsHeight(), goodsImg);
        ImageUtil.addTwoImage(bgBufferedImage,goodsImg,imgPx.getBgPadding(),imgPx.getHeaderHeight());

        // 设置商品名称
        ImageUtil.addFont(bgBufferedImage,goodsName,ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()),imgPx.getBgPadding(),imgPx.getBottomStartY()+imgPx.getMediumFontSize(),imgPx.getGoodsNameColor());
        // 设置二维码
        qrCodeImg = ImageUtil.makeRound(qrCodeImg, imgPx.getQrCodeDiameter());
        ImageUtil.addTwoImage(bgBufferedImage,qrCodeImg,imgPx.getQrCodeStartX(),imgPx.getBottomStartY());

        // 设置原价
        String realPriceStr = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages", null)
            +realPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        ImageUtil.addFont(bgBufferedImage,realPriceStr,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getLargeFontSize()),imgPx.getBgPadding(),imgPx.getPriceY(),imgPx.getRealPriceColor());

        // 设置划线价
        Integer lineStartX =ImageUtil.getTextWidth(ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getLargeFontSize()),realPriceStr)+imgPx.getBgPadding()+10;
        String linePriceStr = linePrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
        ImageUtil.addFont(bgBufferedImage,linePriceStr,ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getSmallFontSize()),lineStartX,imgPx.getPriceY(),imgPx.getLinePriceColor());

        Integer lineEndX = lineStartX+ImageUtil.getTextWidth(ImageUtil.SourceHanSansCN(Font.PLAIN,imgPx.getSmallFontSize()),linePriceStr);
        Integer lineY = imgPx.getPriceY() - imgPx.getSmallFontSize()/3;
        ImageUtil.addLine(bgBufferedImage,lineStartX,lineY,lineEndX,lineY,imgPx.getLinePriceColor());

        return bgBufferedImage;
    }

    BufferedImage createPictorialBgImage(PictorialUserInfo userInfo,BufferedImage qrCodeImg, BufferedImage goodsImg, String shareDoc, String goodsName,BigDecimal realPrice,BigDecimal linePrice){
        ShopRecord shop = saas.shop.getShopById(getShopId());
        return  createPictorialBgImage(userInfo,shop,qrCodeImg,goodsImg,shareDoc,goodsName,realPrice,linePrice,new PictorialImgPx());
    }
    BufferedImage createPictorialBgImage(PictorialUserInfo userInfo,ShopRecord shop,BufferedImage qrCodeImg, BufferedImage goodsImg, String shareDoc, String goodsName,BigDecimal realPrice,BigDecimal linePrice){
        return  createPictorialBgImage(userInfo,shop,qrCodeImg,goodsImg,shareDoc,goodsName,realPrice,linePrice,new PictorialImgPx());
    }

    /**
     * 根据过了条件查询指定的记录
     * @param identityId 画报关联的实体ID，如goodsId
     * @param action  画报类型，{@link com.vpu.mp.service.pojo.wxapp.share.PictorialConstant}
     * @param userId 用户Id
     * @return 画报详情
     */
    public PictorialRecord getPictorialDao(Integer identityId, Byte action, Integer userId) {
        return db().selectFrom(PICTORIAL).where(PICTORIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(PICTORIAL.USER_ID.eq(userId)).and(PICTORIAL.IDENTITY_ID.eq(identityId)).and(PICTORIAL.ACTION.eq(action))
            .fetchAny();
    }

    /**
     * 添加记录
     * @param record 画报记录
     */
    public void addPictorialDao(PictorialRecord record) {
        db().executeInsert(record);
    }

    /**
     * 修改记录
     * @param record 画报记录
     */
    public void updatePictorialDao(PictorialRecord record) {
        db().executeUpdate(record);
    }
}
