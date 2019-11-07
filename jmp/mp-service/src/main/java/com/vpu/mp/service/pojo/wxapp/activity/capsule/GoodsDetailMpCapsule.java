package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import com.vpu.mp.service.pojo.wxapp.activity.info.GoodsLabelProcessorDataInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 小程序详情页面信息存储对象
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Getter
@Setter
public class GoodsDetailMpCapsule extends GoodsBaseCapsule{
    /**
     * 商品图片集合
     */
    private List<String> goodsImgs = new ArrayList<>();

    /**上下架状态*/
    private Byte isOnSale;
    /**是否已删除*/
    private Byte delFlag;

    private Integer goodsVideoId;
    private String goodsVideo;
    private String goodsVideoImg;
    private Double goodsVideoSize;
    private Integer videoWidth;
    private Integer videoHeight;

    private String brandName;
    private List<GoodsLabelProcessorDataInfo> labels = new ArrayList<>();
    private Integer limitBuyNum;
    private Integer limitMaxNum;
}
