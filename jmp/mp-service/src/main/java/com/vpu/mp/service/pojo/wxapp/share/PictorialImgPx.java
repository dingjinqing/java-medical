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
     * 背景图宽
     */
    private Integer bgWidth = 750;
    /**
     * 背景图内边距
     */
    private Integer bgPadding = 10;
    private Color defaultFontColor = new Color(85, 85, 85);
    private Integer smallFontSize = 24;
    private Integer mediumFontSize = 30;
    private Integer largeFontSize = 35;
    /**
     * 图片头部
     */
    private Integer headerHeight = 140;
    /**
     * 用户头像直径
     */
    private Integer userHeaderDiameter = 96;

    /**
     * 用户名开始x,y坐标
     */
    private Integer userNameX = bgPadding + userHeaderDiameter + 10;
    private Integer userNameY = bgPadding + userHeaderDiameter / 5;

    /**
     * 商品shareDoc内容
     */
    private Integer shareDocX = bgPadding;
    private Integer shareDocY = bgPadding + userHeaderDiameter;
    /**
     * 商品宽高,位置x,y
     */
    private Integer goodsWidth = bgWidth - 2 * bgPadding;
    private Integer goodsHeight = bgWidth - 2 * bgPadding-70;
    private Integer goodsStartX = bgPadding;
    private Integer goodsStartY = bgPadding + headerHeight;

    /** 图片上方，各个活动自定义内容区域 */
    /**
     * 区域内部填充颜色
     */
    Color customerRectFillColor = new Color(255, 115, 76, 140);

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
    private Integer customerIconStartY = customerRectStartY +20;

    private Integer customerTextPadding = 8;
    private Integer customerTextStartY = customerRectStartY + 5;
    private Integer customerTextStartX = customerRectStartX+20;

    private Integer customerSecondTextStartY = customerTextStartY +12;
    private Color customerTextFontColor = new Color(255, 255, 255);

    /*****************海报底部图片配置参数******************/
    /**
     * 底部高度
     */
    private Integer bottomHeight = 200;
    /**
     * 图片总高度
     */
    private Integer bgHeight = headerHeight + bottomHeight + goodsHeight + bgPadding * 2;

    /**
     * 底部开始Y
     */
    private Integer bottomStartY = headerHeight + goodsHeight + bgPadding;
    private Color goodsNameColor = new Color(52, 52, 52);

    /**
     * 二维码直径
     */
    private Integer qrCodeWidth = 150;
    /**
     * 二维码X
     */
    private Integer qrCodeStartX = bgWidth - (bgPadding + qrCodeWidth);

    /**
     * 商品名称可使用的最大宽度
     */
    private Integer goodsNameCanUseWidth = bgWidth - 2*bgPadding-qrCodeWidth-20;

    /**
     * 商品名称开始Y
     */
    private Integer goodsNameStartY = bottomStartY + 10;

    /**
     * 底部商品价格开始y,需要根据商品名称行数计算得到
     */
    private Integer priceY = goodsNameStartY + 20;
    private Color realPriceColor = new Color(255, 102, 102);
    /**
     * 底部划线价，线的Y
     */
    private Integer priceLineY = priceY - smallFontSize;
    private Color linePriceColor = new Color(146, 146, 146);


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
