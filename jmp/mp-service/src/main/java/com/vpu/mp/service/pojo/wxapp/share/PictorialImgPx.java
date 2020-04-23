package com.vpu.mp.service.pojo.wxapp.share;

import com.vpu.mp.service.foundation.util.ImageUtil;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 海报背景图片内容相关像素
 *
 * @author 李晓冰
 * @date 2019年12月31日
 */
@Data
public class PictorialImgPx {

    /**
     * 头部文字颜色
     */
    public static final Color HEAD_FONT_COLOR = new Color(85, 85, 85);
    /**
     * 自定义区域内部填充颜色
     */
    public static final Color CUSTOMER_RECT_FILL_COLOR = new Color(255, 115, 76, 140);
    /**
     * 自定义区域内部字体颜色
     */
    public static final Color CUSTOMER_TEXT_FONT_COLOR = new Color(255, 255, 255);
    /**
     * 商品名称颜色
     */
    public static final Color GOODS_NAME_COLOR = new Color(52, 52, 52);

    /**
     * 底部商品价格颜色
     */
    public static final Color REAL_PRICE_COLOR = new Color(255, 102, 102);
    /**
     * 底部划线价颜色
     */
    public static final Color LINE_PRICE_COLOR = new Color(146, 146, 146);

    public static final Color SHARE_IMG_RECT_INNER_COLOR = new Color(255, 241, 234);


    /**
     * 背景图宽
     */
    private Integer bgWidth = 750;
    /**
     * 背景图内边距
     */
    private Integer bgPadding = 30;
    /**
     * 图片底部padding
     */
    private Integer bottomPadding = 10;
    /**
     * 头像，宣传语，商品名，价格，二维码边距
     */
    private Integer smallFontSize = 24;
    private Integer mediumFontSize = 30;
    private Integer largeFontSize = 35;
    /**
     * 图片头部
     */
    private Integer headerHeight = 160;
    /**
     * 用户头像直径
     */
    private Integer userHeaderDiameter = 96;

    private Integer headerStartX = bgPadding;
    private Integer headerStartY = bgPadding;

    /**
     * 用户名开始x,y坐标
     */
    private Integer userNameX = headerStartX + userHeaderDiameter + 15;
    private Integer userNameY = headerStartY + userHeaderDiameter / 5+5;

    /**
     * 商品shareDoc内容
     */
    private Integer shareDocX = bgPadding;
    private Integer shareDocY = headerStartY + userHeaderDiameter+7;
    /**
     * 商品宽高,位置x,y
     */
    private Integer goodsWidth = bgWidth - 2 * bgPadding;
    private Integer goodsHeight = bgWidth - 2 * bgPadding-80;
    private Integer goodsStartX = bgPadding;
    private Integer goodsStartY = bgPadding + headerHeight;

    /** 图片上方，各个活动自定义内容区域 */

    /**
     * 自定义内容区域,背景边框位置和大小
     */
    private Integer customerRectHeight = 60;
    private Integer customerRectWidth = goodsWidth;
    private Integer customerRectStartX = bgPadding;
    private Integer customerRectStartY = bgPadding + headerHeight + goodsHeight - customerRectHeight;
    /**
     * 自定内容图标宽高和位置
     */
    private Integer customerIconWidth =40;
    private Integer customerIconHeight = 30;
    private Integer customerIconStartX = customerRectStartX+20;
    private Integer customerIconStartY = customerRectStartY +17;

    private Integer customerTextPadding = 8;
    private Integer customerTextStartY = customerRectStartY + 10;
    private Integer customerTextStartX = customerRectStartX+20;

    private Integer customerSecondTextStartY = customerTextStartY +10;


    /*****************海报底部图片配置参数******************/
    /**
     * 底部高度
     */
    private Integer bottomHeight = 200;
    /**
     * 图片总高度,底部padding设置为10
     */
    private Integer bgHeight = headerHeight + bottomHeight + goodsHeight + bgPadding+bottomPadding ;

    /**
     * 底部开始Y
     */
    private Integer bottomStartY = headerHeight + goodsHeight + bgPadding+5;

    /**
     * 二维码直径
     */
    private Integer qrCodeWidth = 150;
    /**
     * 二维码X
     */
    private Integer qrCodeStartX = bgWidth - (bgPadding + qrCodeWidth);
    private Integer qrCodeStartY =bottomStartY+15;

    /**
     * 商品名称可使用的最大宽度
     */
    private Integer goodsNameCanUseWidth = bgWidth - 2*bgPadding-qrCodeWidth-20;

    /**
     * 商品名称开始Y
     */
    private Integer goodsNameStartY = bottomStartY + 10;

    private Integer bottomTextStartX = bgPadding;

    /**
     * 商品价格和名字的距离
     */
    private Integer priceNamePadding = 34;
    /**
     * 底部商品价格开始y,需要根据商品名称行数计算得到
     */
    private Integer priceY = goodsNameStartY + priceNamePadding;

    /**
     *价格之间的距离
     */
    private Integer priceMargin = 10;

    public Integer getActivityTipTextY(){
        return priceY+11;
    }
    /**
     * 底部划线价，线的Y
     */
    public Integer getPriceLineY(){
        return priceY+6;
    }


    public PictorialImgPx() {
    }

    public Integer getSmallFontAscent(BufferedImage bufferedImage) {
        return ImageUtil.getTextAscent(bufferedImage, ImageUtil.SourceHanSansCN(Font.PLAIN, smallFontSize));
    }

    public Integer getMediumFontAscent(BufferedImage bufferedImage) {
        return ImageUtil.getTextAscent(bufferedImage, ImageUtil.SourceHanSansCN(Font.PLAIN, mediumFontSize));
    }

    public Integer getLargeFontAscent(BufferedImage bufferedImage) {
        return ImageUtil.getTextAscent(bufferedImage, ImageUtil.SourceHanSansCN(Font.PLAIN, largeFontSize));
    }
}
