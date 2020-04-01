package com.vpu.mp.service.shop.image.postertraits;

import com.upyun.UpException;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.ImageUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.GoodsShareConfig;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.pojo.wxapp.share.*;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jooq.Condition;
import org.jooq.lambda.tuple.Tuple2;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.vpu.mp.db.shop.Tables.PICTORIAL;
import static com.vpu.mp.service.foundation.util.DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

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
    @Autowired
    private ShopCommonConfigService shopCommonConfigService;

    /**
     * 分享海报时使用的默认头像
     */
    public static final String DEFAULT_USER_AVATAR = "image/wxapp/default_user_avatar.png";


    /**
     * 表单分享海报图片上的指纹图片
     */
    public static final String FINGER_IMG = "/image/admin/usr_codes.png";

    /**
     * 获取海报中用户头像
     *
     * @param userId 用户ID
     * @return 用户海报信息
     */
    public PictorialUserInfo getPictorialUserInfo(Integer userId, ShopRecord shop) throws IOException {
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
    public BufferedImage getGoodsPictorialImage(PictorialShareConfig shareConfig, GoodsRecord goodsRecord) throws IOException {
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
    public BufferedImage createPictorialBgImage(PictorialUserInfo userInfo, ShopRecord shop, BufferedImage qrCodeImg, BufferedImage goodsImg, String shareDoc, String goodsName, BigDecimal realPrice, BigDecimal linePrice, PictorialImgPx imgPx) {
        //设置背景图
        BufferedImage bgBufferedImage = new BufferedImage(imgPx.getBgWidth(), imgPx.getBgHeight(), BufferedImage.TYPE_USHORT_555_RGB);
        ImageUtil.addRect(bgBufferedImage, 0, 0, imgPx.getBgWidth(), imgPx.getBgHeight(), null, Color.WHITE);
        // 设置用户头像
        BufferedImage userAvatarImage = ImageUtil.makeRound(userInfo.getUserAvatarImage(), imgPx.getUserHeaderDiameter());
        ImageUtil.addTwoImage(bgBufferedImage, userAvatarImage, imgPx.getBgPadding(), imgPx.getBgPadding());
        // 设置用户名
        ImageUtil.addFont(bgBufferedImage, userInfo.getUserName(), ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()), imgPx.getUserNameX(), imgPx.getUserNameY(), imgPx.getDefaultFontColor(), false);
        // 设置宣传语
        ImageUtil.addFont(bgBufferedImage, shareDoc, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()), imgPx.getShareDocX(), imgPx.getShareDocY(), imgPx.getDefaultFontColor(), false);

        // 设置商品图片
        goodsImg = ImageUtil.resizeImage(imgPx.getGoodsWidth(), imgPx.getGoodsHeight(), goodsImg);
        ImageUtil.addTwoImage(bgBufferedImage, goodsImg, imgPx.getGoodsStartX(), imgPx.getGoodsStartY());

        // 设置二维码
        qrCodeImg = ImageUtil.resizeImageTransparent(imgPx.getQrCodeWidth(), imgPx.getQrCodeWidth(), qrCodeImg);
        ImageUtil.addTwoImage(bgBufferedImage, qrCodeImg, imgPx.getQrCodeStartX(), imgPx.getBottomStartY());

        // 设置商品名称
        int goodsNameHeight = pictorialAddFontName(bgBufferedImage, goodsName, imgPx);
        imgPx.setPriceY(imgPx.getGoodsNameStartY() + goodsNameHeight + 30);
        // 设置原价
        if (realPrice != null) {
            String realPriceStr = Util.translateMessage(shop.getShopLanguage(), JsonResultMessage.WX_MA_PICTORIAL_MONEY_FLAG, "messages")
                + realPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            ImageUtil.addFont(bgBufferedImage, realPriceStr, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getLargeFontSize()), imgPx.getBgPadding(), imgPx.getPriceY(), imgPx.getRealPriceColor(), false);

            Integer realPriceHeight = imgPx.getLargeFontAscent(bgBufferedImage);
            // 设置划线价
            if (linePrice != null) {
                Integer lineStartX = ImageUtil.getTextWidth(bgBufferedImage, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getLargeFontSize()), realPriceStr) + imgPx.getBgPadding() + 10;
                String linePriceStr = linePrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                ImageUtil.addFontWithLine(bgBufferedImage, lineStartX, imgPx.getPriceY() + realPriceHeight / 4, linePriceStr, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()), imgPx.getLinePriceColor());
            }
        }
        return bgBufferedImage;
    }

    /**
     * 海报添加商品名称，根据长度自动折行或截断商品名称
     *
     * @param bgBufferedImage 背景图bufferImage
     * @param goodsName       商品名称
     * @param imgPx           图片规格信息
     * @return 商品结束出Y值
     */
    private int pictorialAddFontName(BufferedImage bgBufferedImage, String goodsName, PictorialImgPx imgPx) {
        // 名称单个字符高度
        int nameCharHeight = imgPx.getMediumFontAscent(bgBufferedImage);
        // 名称总长度
        int nameTextLength = ImageUtil.getTextWidth(bgBufferedImage, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()), goodsName);

        if (nameTextLength <= imgPx.getGoodsNameCanUseWidth()) {
            ImageUtil.addFont(bgBufferedImage, goodsName, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()), imgPx.getBgPadding(), imgPx.getGoodsNameStartY(), imgPx.getGoodsNameColor(), false);
            return nameCharHeight;
        } else {
            double oneCharWidth = Math.ceil(nameTextLength * 1.0 / goodsName.length());
            int oneLineCharNum = (int) Math.floor(imgPx.getGoodsNameCanUseWidth() / oneCharWidth);
            if (goodsName.length() >oneLineCharNum * 3) {
                goodsName = goodsName.substring(0, oneLineCharNum * 2 + oneLineCharNum / 2) + "...";
            }

            int nextTextStartY = imgPx.getGoodsNameStartY();
            String text;
            for (int i = 0; i < goodsName.length(); i += oneLineCharNum) {
                if (i + oneLineCharNum >= goodsName.length()) {
                    text = goodsName.substring(i);
                } else {
                    text = goodsName.substring(i, i + oneLineCharNum);
                }
                ImageUtil.addFont(bgBufferedImage, text, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getMediumFontSize()), imgPx.getBgPadding(), nextTextStartY, imgPx.getGoodsNameColor(), false);
                nextTextStartY += nameCharHeight;
            }
            return nextTextStartY - imgPx.getGoodsNameStartY();
        }
    }

    /**
     * 给海报添加自定义内容区域内容
     *
     * @param bufferedImg       海报背景对象
     * @param iconBufferImg     图标对象，没有为null
     * @param firstContentText  一级自定义内容
     * @param secondContentText 二级自定义内容
     * @param secondNeedLine    二级内容是否需要划线
     * @param imgPx             图片规格信息
     */
    public void addPictorialSelfCustomerContent(BufferedImage bufferedImg, BufferedImage iconBufferImg, String firstContentText, String secondContentText, boolean secondNeedLine, PictorialImgPx imgPx) {
        addPictorailSelfCustomerContent(bufferedImg, iconBufferImg, null, firstContentText, secondContentText, secondNeedLine, imgPx);
    }

    /**
     * 给海报添加自定义内容区域内容
     *
     * @param bufferedImg        海报背景对象
     * @param activityPosterText 海报上自定义内容带边框的宣传文字，没有为null
     * @param firstContentText   一级自定义内容
     * @param secondContentText  二级自定义内容
     * @param secondNeedLine     二级内容是否需要划线
     * @param imgPx              图片规格信息
     */
    public void addPictorialSelfCustomerContent(BufferedImage bufferedImg, String activityPosterText, String firstContentText, String secondContentText, boolean secondNeedLine, PictorialImgPx imgPx) {
        addPictorailSelfCustomerContent(bufferedImg, null, activityPosterText, firstContentText, secondContentText, secondNeedLine, imgPx);
    }

    /**
     * 给海报添加自定义内容区域内容
     *
     * @param bufferedImg        海报背景对象
     * @param iconBufferImg      图标对象，没有为null
     * @param activityPosterText 海报上自定义内容带边框的宣传文字，没有为null
     * @param firstContentText   一级自定义内容
     * @param secondContentText  二级自定义内容
     * @param secondNeedLine     二级内容是否需要划线
     * @param imgPx              图片规格信息
     */
    private void addPictorailSelfCustomerContent(BufferedImage bufferedImg, BufferedImage iconBufferImg, String activityPosterText, String firstContentText, String secondContentText, boolean secondNeedLine, PictorialImgPx imgPx) {

        ImageUtil.addRect(bufferedImg, imgPx.getCustomerRectStartX(), imgPx.getCustomerRectStartY(), imgPx.getCustomerRectWidth(), imgPx.getCustomerRectHeight(), null, imgPx.getCustomerRectFillColor());
        // 添加自定义图标
        if (iconBufferImg != null) {
            iconBufferImg = ImageUtil.resizeImageTransparent(imgPx.getCustomerIconWidth(), imgPx.getCustomerIconHeight(), iconBufferImg);
            ImageUtil.addTwoImage(bufferedImg, iconBufferImg, imgPx.getCustomerIconStartX(), imgPx.getCustomerIconStartY());
            imgPx.setCustomerTextStartX(imgPx.getCustomerIconStartX() + imgPx.getCustomerIconWidth() + imgPx.getCustomerTextPadding());
        }
        if (activityPosterText != null && iconBufferImg == null) {
            int width = ImageUtil.addFontWithRect(bufferedImg, imgPx.getCustomerIconStartX(), imgPx.getCustomerIconStartY(), activityPosterText, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()), imgPx.getCustomerTextFontColor(), null, imgPx.getCustomerTextFontColor());
            imgPx.setCustomerTextStartX(imgPx.getCustomerIconStartX() + width + imgPx.getCustomerTextPadding());
        }
        // 添加自定义一级内容（原价等）
        ImageUtil.addFont(bufferedImg, firstContentText, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getLargeFontSize()), imgPx.getCustomerTextStartX(), imgPx.getCustomerTextStartY(), imgPx.getCustomerTextFontColor(), false);
        Integer realContentTextLength = ImageUtil.getTextWidth(bufferedImg, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getLargeFontSize()), firstContentText);
        if (secondContentText != null) {
            // 添加二级内容(带划线)
            Integer secondContentTextStartX = imgPx.getCustomerTextStartX() + realContentTextLength + imgPx.getCustomerTextPadding();
            if (secondNeedLine) {
                ImageUtil.addFontWithLine(bufferedImg, secondContentTextStartX, imgPx.getCustomerSecondTextStartY(), secondContentText, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()), imgPx.getCustomerTextFontColor());
            } else {
                ImageUtil.addFont(bufferedImg, secondContentText, ImageUtil.SourceHanSansCN(Font.PLAIN, imgPx.getSmallFontSize()), secondContentTextStartX, imgPx.getCustomerSecondTextStartY(), imgPx.getCustomerTextFontColor(), false);
            }
        }

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
    public <T extends Rule> void uploadToUpanYun(BufferedImage bufferedImage, String relativePath, T pictorialRule, Integer goodsId, PictorialRecord pictorialRecord, Integer userId) throws UpException, IOException {
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
     * Gets pictorial from db.从库中获取海报信息
     *
     * @param userId the user id
     * @param id     the id
     * @param action the action
     * @return the pictorial from db
     */
    public PictorialRecord getPictorialFromDb(int userId, int id, byte action) {
        return db().selectFrom(PICTORIAL)
            .where(PICTORIAL.USER_ID.eq(userId))
            .and(PICTORIAL.IDENTITY_ID.eq(id))
            .and(PICTORIAL.ACTION.eq(action))
            .and(PICTORIAL.DEL_FLAG.eq(BYTE_ZERO))
            .fetchOneInto(PICTORIAL);
    }


    /**
     * Is need new pictorial boolean.是否需要创建新海报
     *
     * @param rule   the rule
     * @param record the record
     * @return the boolean
     */
    public boolean isNeedNewPictorial(String rule, PictorialRecord record) {
        return !Objects.isNull(record) && rule.equals(record.getRule());
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
    public PictorialRecord getPictorialDao(Integer identityId, Integer activityId, Byte action, Integer userId) {
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

    /**
     * 根据店铺通用配置，获取用户配置的分享和海报下载时宣语
     * @param userName 用户名
     * @param goodsName 商品名
     * @param goodsPrice 商品价格
     * @param isPictorial true下载海报，false商品分享
     * @return null 表示使用的是默认宣传语，否则用户定义的宣传语，已进行长度截断
     */
    public String getCommonConfigDoc(String userName,String goodsName,BigDecimal goodsPrice,String lang,Boolean isPictorial){
        final String userNameTag = "【分享人昵称】",goodsNameTag = "【商品名称】",goodsPriceTag = "【商品价格】";

        GoodsShareConfig goodsShareConfig = shopCommonConfigService.getGoodsShareConfig();
        String shareDoc = null;
        // 分享
        if (!isPictorial) {
            // 自定义文案
            if (!GoodsShareConfig.DEFAULT_VALUE.equals(goodsShareConfig.getGoodsShareCommon())){
                shareDoc = goodsShareConfig.getCommonDoc();
            }
        } else {
            // 自定义文案
            if (!GoodsShareConfig.DEFAULT_VALUE.equals(goodsShareConfig.getGoodsSharePictorial())){
                shareDoc = goodsShareConfig.getPictorialDoc();
            }
        }
        if (shareDoc != null) {
            String moneyFlag = Util.translateMessage(lang, JsonResultMessage.WX_MA_PICTORIAL_MONEY, "messages");
            shareDoc = shareDoc.replace(userNameTag,userName);
            shareDoc = shareDoc.replace(goodsNameTag,goodsName);
            shareDoc = shareDoc.replace(goodsPriceTag,goodsPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+moneyFlag);
            if (!isPictorial) {
                shareDoc = shareDoc.length() > 23 ? shareDoc.substring(0, 23) + "..." : shareDoc;
            } else {
                shareDoc = shareDoc.length() > 20 ? shareDoc.substring(0, 20) + "..." : shareDoc;
            }
        }
        return shareDoc;
    }
    /**
     * 生成表单海报背景图
     *
     * @param userAvatarImg 用户头像
     * @param qrCodeImg     二维码
     * @param bgImg         背景图
     * @param imgPx         图片规格信息
     * @return 通过图片 buffered image
     */
    public BufferedImage createFormPictorialBgImage(BufferedImage userAvatarImg, BufferedImage qrCodeImg, BufferedImage bgImg
        , PictorialFormImgPx imgPx) {
        //设置白画布
        BufferedImage bgBufferedImage = new BufferedImage(imgPx.getBgWidth(), imgPx.getBgHeight(), BufferedImage.TYPE_USHORT_555_RGB);
        ImageUtil.addRect(bgBufferedImage, 0, 0, imgPx.getBgWidth(), imgPx.getBgHeight(), null, Color.WHITE);

        // 设置用户头像
        BufferedImage userAvatarImage = ImageUtil.makeRound(userAvatarImg, imgPx.getUserHeaderDiameter());
        ImageUtil.addTwoImage(bgBufferedImage, userAvatarImage, imgPx.getBgPadding(), imgPx.getBgPadding());

        // 设置背景图
        bgImg = ImageUtil.resizeImage(imgPx.getBgImgWidth(), imgPx.getBgImgWidth(), bgImg);
        ImageUtil.addTwoImage(bgBufferedImage, bgImg, imgPx.getBgPadding(), imgPx.getHeaderHeight());

        // 设置二维码
        qrCodeImg = ImageUtil.resizeImageTransparent(imgPx.getQrCodeWidth(), imgPx.getQrCodeWidth(), qrCodeImg);
        ImageUtil.addTwoImage(bgBufferedImage, qrCodeImg, imgPx.getQrCodeStartX(), imgPx.getQrCodeStartY());

        // 设置指纹
        try {
            BufferedImage fingerImg = ImageIO.read(new URL(imageService.getImgFullUrl(FINGER_IMG)));
            fingerImg = ImageUtil.resizeImageTransparent(imgPx.getQrCodeWidth(), imgPx.getQrCodeWidth(), fingerImg);
            ImageUtil.addTwoImage(bgBufferedImage, fingerImg, imgPx.getFingerStartX(), imgPx.getFingerStartY());
        } catch (IOException e) {
            logger().error("加载指纹图片{}异常:{}", FINGER_IMG, ExceptionUtils.getStackTrace(e));
            throw new BusinessException(JsonResultCode.CODE_FAIL);
        }

        return bgBufferedImage;
    }

    public static final String UPLOAD = "upload/";
    public static final String PICTORIAL_S = "pictorial/";
    public static final String PICTORIAL_ = "pictorial_";
    public static final String SLASH = "/";
    public static final String UNDERLINE = "_";
    public static final String IMG_PNG = ".png";

    /**
     * Gets img dir.获取海报图片路径
     *
     * @param action   the action
     * @param fileName the file name
     * @return the img dir value1:relativePath, value2:fullPath
     */
    public Tuple2<String, String> getImgDir(int action, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        String relativePath = stringBuilder.append(UPLOAD).append(this.getShopId()).append(SLASH).append(PICTORIAL_S).append(action).append(SLASH).append(fileName).toString();
        String fullPath = imageService.getImgFullUrl(relativePath);
        return new Tuple2<>(relativePath, fullPath);
    }

    /**
     * Gets img file name.获取海报图片文件名
     *
     * @param pageId  the page id
     * @param action  the action
     * @param action1 the action 1
     * @return the img file name
     */
    public String getImgFileName(String pageId, String action, String action1) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_FULL_NO_UNDERLINE));
        return String.join(StringUtils.EMPTY, PICTORIAL_, action, UNDERLINE, pageId, UNDERLINE, action1, UNDERLINE, date, IMG_PNG);
    }
}
