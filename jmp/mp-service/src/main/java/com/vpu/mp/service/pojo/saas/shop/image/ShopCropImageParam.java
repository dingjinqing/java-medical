package com.vpu.mp.service.pojo.saas.shop.image;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/7/17 10:23
 */
@Data
public class ShopCropImageParam {
    /**
     * 图片存放地址
     */
    public String remoteImgPath;
    /**
     * 剪切后宽
     */
    public Integer cropWidth;
    /**
     * 剪切后高
     */
    public Integer cropHeight;
    /**
     * 剪切左上位置
     */
    public Integer x;
    /**
     * 剪切左上位置
     */
    public Integer y;
    /**
     * 剪切大小
     */
    public Integer w;
    public Integer h;
    /**
     * 缩放比例
     */
    public Double imgScaleW;
    public Integer imgCatId;
    public Integer remoteImgId;
}
