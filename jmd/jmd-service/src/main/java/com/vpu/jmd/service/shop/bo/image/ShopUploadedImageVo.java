package com.vpu.jmd.service.shop.bo.image;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/7/17 9:56
 */
@Getter
@Setter
public class ShopUploadedImageVo {
    private Integer   imgId;
    private String    imgType;
    private Integer   imgSize;
    private String    imgName;
    private String    imgPath;
    private String    imgUrl;
    private Integer   imgCatId;
    private Integer   imgWidth;
    private Integer   imgHeight;
    private Timestamp createTime;
}
