package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
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
import com.vpu.mp.service.pojo.wxapp.share.PictorialConstant;
import com.vpu.mp.service.pojo.wxapp.share.PictorialImgPx;
import com.vpu.mp.service.pojo.wxapp.share.PictorialRule;
import com.vpu.mp.service.pojo.wxapp.share.PictorialUserInfo;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
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
import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.PICTORIAL;

/**
 * @author zhaojianqiang
 * 2019年10月17日 下午5:19:04
 */
@Service
public class PictorialService extends ShopBaseService {
    @Autowired
    private UserService user;
    @Autowired
    private ImageService imageService;

    /**
     * 分享海报时使用的默认头像
     */
    public static final String DEFAULT_USER_AVATAR = "image/wxapp/default_user_avatar.png";

    /**
     * 获取海报中用户头像
     *
     * @param userId 用户ID
     * @return 用户海报信息
     */
    PictorialUserInfo getPictorialUserInfo(Integer userId, ShopRecord shop) throws IOException {
        UserInfo userInfo = user.getUserInfo(userId);

        String userName = StringUtils.isBlank(userInfo.getUsername()) ?
            Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_DEFAULT_USER_NAME, "messages")
            : userInfo.getUsername();
        BufferedImage userAvatarImage;
        if (StringUtils.isBlank(userInfo.getUserAvatar())) {
            try (InputStream inputStream = Util.loadFile(DEFAULT_USER_AVATAR)) {
                userAvatarImage = ImageIO.read(inputStream);
            } catch (IOException e) {
                logger().debug("小程序-生成图片-获取用户默认头像错误：" + e.getMessage());
                throw e;
            }
        } else {
            try {
                userAvatarImage = ImageIO.read(new URL(imageService.getImgFullUrl(userInfo.getUserAvatar())));
            } catch (IOException e) {
                logger().debug("小程序-生成图片-获取用户头像错误 userId{" + userId + "}：" + e.getMessage());
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
     *
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
            logger().debug("小程序-生成图片-获取商品图片错误，图片地址{}：{}", imageService.getImgFullUrl(goodsImg), e.getMessage());
            throw e;
        }
    }

    /**
     * 生成海报通用背景图
     *
     * @param userInfo  用户信息
     * @param shop      店铺配置
     * @param qrCodeImg 二维码
     * @param goodsImg  商品图片
     * @param shareDoc  海报分享文案
     * @param goodsName 商品名称
     * @param realPrice 商品原件
     * @param linePrice 商品划线价
     * @param imgPx     图片规格信息
     * @return 通过图片
     */
    BufferedImage createPictorialBgImage(PictorialUserInfo userInfo, ShopRecord shop, BufferedImage qrCodeImg, BufferedImage goodsImg, String shareDoc, String goodsName, BigDecimal realPrice, BigDecimal linePrice, PictorialImgPx imgPx, boolean needSelfCustomerRect) {
        //设置背景图
        BufferedImage bgBufferedImage = new BufferedImage(imgPx.getBgWidth(), imgPx.getBgHeight(), BufferedImage.TYPE_USHORT_555_RGB);
        ImageUtil.addRect(bgBufferedImage, 0, 0, imgPx.getBgWidth(), imgPx.getBgHeight(), null, Color.WHITE);
        // 设置用户头像
        BufferedImage userAvatarImage = ImageUtil.makeRound(userInfo.getUserAvatarImage(), imgPx.getUserHeaderDiameter());
        ImageUtil.addTwoImage(bgBufferedImage, userAvatarImage, imgPx.getBgPadding(), imgPx.getBgPadding());
        // 设置用户名
        ImageUtil.addFont(bgBufferedImage, userInfo.getUserName(), ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()), imgPx.getUserNameX(), imgPx.getUserNameY(), imgPx.getDefaultFontColor(),false);
        // 设置宣传语
        ImageUtil.addFont(bgBufferedImage, shareDoc, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()), imgPx.getShareDocX(), imgPx.getShareDocY(), imgPx.getDefaultFontColor(),false);

        // 设置商品图片
        goodsImg = ImageUtil.resizeImage(imgPx.getGoodsWidth(), imgPx.getGoodsHeight(), goodsImg);
        ImageUtil.addTwoImage(bgBufferedImage, goodsImg, imgPx.getGoodsStartX(), imgPx.getGoodsStartY());

        // 设置二维码
        qrCodeImg = ImageUtil.resizeImageTransparent(imgPx.getQrCodeWidth(), imgPx.getQrCodeWidth(), qrCodeImg);
        ImageUtil.addTwoImage(bgBufferedImage, qrCodeImg, imgPx.getQrCodeStartX(), imgPx.getBottomStartY());

        // 设置商品名称
        ImageUtil.addFont(bgBufferedImage, goodsName, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()), imgPx.getBgPadding(), imgPx.getGoodsNameStartY(), imgPx.getGoodsNameColor(),false);
        int goodsNameHeight = ImageUtil.getTextAscent(bgBufferedImage,ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()));
        imgPx.setPriceY(imgPx.getGoodsNameStartY()+goodsNameHeight+30);
        // 设置原价
        if (realPrice != null) {
            String realPriceStr = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages")
                + realPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFont(bgBufferedImage, realPriceStr, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getLargeFontSize()), imgPx.getBgPadding(), imgPx.getPriceY(), imgPx.getRealPriceColor(),false);

            Integer realPriceHeight = imgPx.getLargeFontAscent(bgBufferedImage);
            // 设置划线价
            if (linePrice != null) {
                Integer lineStartX = ImageUtil.getTextWidth(bgBufferedImage, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getLargeFontSize()), realPriceStr) + imgPx.getBgPadding() + 10;
                String linePriceStr = linePrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                ImageUtil.addFontWithLine(bgBufferedImage,lineStartX, imgPx.getPriceY()+realPriceHeight/4,linePriceStr, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()), imgPx.getLinePriceColor());
            }
        }



        // 设置商品图片上方显示自定义内容区域
        if (needSelfCustomerRect) {
            ImageUtil.addRect(bgBufferedImage, imgPx.getCustomerRectStartX(), imgPx.getCustomerRectStartY(), imgPx.getCustomerRectWidth(), imgPx.getCustomerRectHeight(), null, imgPx.getCustomerRectFillColor());
        }

        return bgBufferedImage;
    }

    /**
     * 生成海报通用背景图
     *
     * @param userInfo  用户信息
     * @param qrCodeImg 二维码
     * @param goodsImg  商品图片
     * @param shareDoc  海报分享文案
     * @param goodsName 商品名称
     * @param realPrice 商品原件
     * @param linePrice 商品划线价
     * @return 通过图片
     */
    BufferedImage createPictorialBgImage(PictorialUserInfo userInfo, BufferedImage qrCodeImg, BufferedImage goodsImg, String shareDoc, String goodsName, BigDecimal realPrice, BigDecimal linePrice,boolean needSelfCustomerRect) {
        ShopRecord shop = saas.shop.getShopById(getShopId());
        return createPictorialBgImage(userInfo, shop, qrCodeImg, goodsImg, shareDoc, goodsName, realPrice, linePrice, new PictorialImgPx(),needSelfCustomerRect);
    }

    /**
     * 生成海报通用背景图
     *
     * @param userInfo  用户信息
     * @param shop      店铺配置
     * @param qrCodeImg 二维码
     * @param goodsImg  商品图片
     * @param shareDoc  海报分享文案
     * @param goodsName 商品名称
     * @param realPrice 商品原件
     * @param linePrice 商品划线价
     * @return 通过图片
     */
    BufferedImage createPictorialBgImage(PictorialUserInfo userInfo, ShopRecord shop, BufferedImage qrCodeImg, BufferedImage goodsImg, String shareDoc, String goodsName, BigDecimal realPrice, BigDecimal linePrice,boolean needSelfCustomerRect) {
        return createPictorialBgImage(userInfo, shop, qrCodeImg, goodsImg, shareDoc, goodsName, realPrice, linePrice, new PictorialImgPx(),needSelfCustomerRect);
    }


    /**
     * 将待分享图片上传到U盘云，并在数据库缓存记录
     *
     * @param bufferedImage   待上传图片
     * @param relativePath    相对路径
     * @param pictorialRule   缓存规则
     * @param goodsId         商品ID
     * @param pictorialRecord 对应的记录行
     * @param userId          用户ID
     * @throws UpException 上传异常
     * @throws IOException 文件io异常
     */
    public void uploadToUpanYun(BufferedImage bufferedImage, String relativePath, PictorialRule pictorialRule, Integer goodsId, PictorialRecord pictorialRecord, Integer userId) throws UpException, IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            // 上传upanyun
            this.imageService.getUpYunClient().writeFile(relativePath, byteArrayOutputStream.toByteArray(), true);
        } catch (IOException e) {
            logger().debug("小程序-图片上传u盘云操作错误：" + e.getMessage());
            throw e;
        }

        // 新增
        if (pictorialRecord == null) {
            pictorialRecord = new PictorialRecord();
            pictorialRecord.setAction(PictorialConstant.GROUP_BUY_ACTION_SHARE);
            pictorialRecord.setPath(relativePath);
            pictorialRecord.setUserId(userId);
            pictorialRecord.setIdentityId(goodsId);
            pictorialRecord.setRule(Util.toJson(pictorialRule));
            addPictorialDao(pictorialRecord);
        } else {
            // 更新
            pictorialRecord.setPath(relativePath);
            pictorialRecord.setRule(Util.toJson(pictorialRule));
            updatePictorialDao(pictorialRecord);
        }
    }


    /**
     * 判断Pictorial内存的数据是否还有效
     *
     * @param rule               判断规则
     * @param goodsUpdateTime    商品更新时间
     * @param activityUpdateTime 活动更新时间
     * @return true 缓存有效，false 无效
     */
    public boolean isGoodsSharePictorialRecordCanUse(String rule, Timestamp goodsUpdateTime, Timestamp activityUpdateTime) {
        PictorialRule pictorialRule = Util.parseJson(rule, PictorialRule.class);
        // 之前生成的图片依然可用，则直接返回其在upanyun上的相对路径
        if (pictorialRule.getGoodsUpdateTime().compareTo(goodsUpdateTime) >= 0 && pictorialRule.getActivityUpdateTime().compareTo(activityUpdateTime) >= 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据过了条件查询指定的记录
     *
     * @param identityId 画报关联的实体ID，如goodsId
     * @param activityId 活动id
     * @param action     画报类型，{@link com.vpu.mp.service.pojo.wxapp.share.PictorialConstant}
     * @param userId     用户Id
     * @return 画报详情
     */
    public PictorialRecord getPictorialDao(Integer identityId,Integer activityId, Byte action, Integer userId) {
        Condition condition = PICTORIAL.IDENTITY_ID.eq(identityId).and(PICTORIAL.ACTION.eq(action));
        if (userId != null) {
            condition = condition.and(PICTORIAL.USER_ID.eq(userId));
        }
        if (activityId != null) {
            condition = condition.and(PICTORIAL.ACTIVITY_ID.eq(activityId));
        }

        return db().selectFrom(PICTORIAL).where(PICTORIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(condition)
            .fetchAny();
    }

    /**
     * 添加记录
     *
     * @param record 画报记录
     */
    public void addPictorialDao(PictorialRecord record) {
        db().executeInsert(record);
    }

    /**
     * 修改记录
     *
     * @param record 画报记录
     */
    public void updatePictorialDao(PictorialRecord record) {
        db().executeUpdate(record);
    }
}
