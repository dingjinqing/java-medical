package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author liufei
 * @date 2019/8/15
 * @description
 */
@Data
public class RedemptionOrderVo {
    /** 订单号 */
    private String orderSn;

    /** 辅助参数 */
    @JsonIgnore
    private String concatId;
    @JsonIgnore
    private String concatName;
    @JsonIgnore
    private String concatNumber;
    @JsonIgnore
    private String activityIds;
    @JsonIgnore
    private String activityRules;
    @JsonIgnore
    private String concatImg;

    /** 主商品列表 */
    private List<RedemptionGoodsInfo> mainGoods;
    /** 换购商品列表 */
    private List<RedemptionGoodsInfo> redemptionGoods;

    /** 下单时间 */
    private Timestamp createTime;
    /** 收货人信息 姓名---手机号*/
    private String consignee;
    private String mobile;
    /** 订单状态 */
    private String orderStatusName;

}
