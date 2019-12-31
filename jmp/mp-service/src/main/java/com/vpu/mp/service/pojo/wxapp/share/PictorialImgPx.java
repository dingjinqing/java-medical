package com.vpu.mp.service.pojo.wxapp.share;

import lombok.Data;

import java.awt.*;

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
    private Integer bgPadding = 40;
    private Color defaultFontColor = new Color(85, 85, 85);
    private Integer smallFontSize = 24;
    private Integer mediumFontSize = 30;
    private Integer largeFontSize = 35;
    /**
     * 图片头部
     */
    private Integer headerHeight = 190;
    /**
     * 用户头像直径
     */
    private Integer userHeaderDiameter = 96;
    /**
     * 用户名开始x,y坐标,颜色，字体大小
     */
    private Integer userNameX = bgPadding + userHeaderDiameter + 10;
    private Integer userNameY = bgPadding + userHeaderDiameter / 2 - 5;

    /**
     * 商品shareDoc内容
     */
    private Integer shareDocX = bgPadding;
    private Integer shareDocY = bgPadding + userHeaderDiameter + mediumFontSize +5;
    /** 商品宽高*/
    private Integer goodsWidth = bgWidth - 2 * bgPadding;
    private Integer goodsHeight = bgWidth - 2 * bgPadding;
    /**底部高度*/
    private Integer bottomHeight = 160;
    /**图片总高度*/
    private Integer bgHeight = headerHeight + bottomHeight + goodsHeight + bgPadding * 2;

    /**底部开始Y*/
    private Integer bottomStartY = bgHeight - bgPadding - bottomHeight;
    private Color goodsNameColor = new Color(52, 52, 52);

    /**二维码直径*/
    private Integer qrCodeDiameter = 150;
    /**二维码X*/
    private Integer qrCodeStartX = bgWidth - (bgPadding + qrCodeDiameter);

    /** 底部商品价格 */
    private Integer priceY = bgHeight - bgPadding-largeFontSize;
    private Color realPriceColor = new Color(255, 102, 102);
    /**底部划线价，线的Y*/
    private Integer priceLineY = priceY - smallFontSize;
    private Color linePriceColor = new Color(146, 146, 146);
}
