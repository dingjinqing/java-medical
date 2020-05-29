package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import lombok.Data;

/**
 * 小程序-商品详情参数
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Data
public class GoodsDetailMpParam {
    private Byte fromPage;

    private Integer goodsId;

    private Integer userId;

    /**用户当前位置经度*/
    private String lon;
    /**
     * 用户当前位置纬度
     */
    private String lat;

    /**
     * 指定该商品的详情页营销活动id
     */
    private Integer activityId;
    /**
     * 指定该商品的详情页营销活动类型
     */
    private Byte activityType;
    /**分销商品改价信息*/
    private GoodsRebateConfigParam rebateConfig;

    /**
     * 分享有礼--分享者的ID
     */
    private Integer shareAwardLaunchUserId;
    /**
     * 分享有礼--分享有礼活动ID
     */
    private Integer shareAwardId;

    private String userGrade;
}
