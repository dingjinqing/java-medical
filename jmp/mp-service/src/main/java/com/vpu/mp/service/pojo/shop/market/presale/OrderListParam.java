package com.vpu.mp.service.pojo.shop.market.presale;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 定金膨胀活动订单入参
 *
 * @author 郑保乐
 */
@Getter
@Setter
public class OrderListParam extends BasePageParam {

    /** 活动id **/
    private Integer id;
    /** 商品名称 **/
    private String goodsName;
    /** 订单号 **/
    private String orderSn;
    /** 订单状态名 **/
    private String orderStatusName;
    /** 用户昵称 **/
    private String username;
    /** 收货人姓名 **/
    private String consigneeName;
    /** 收货人手机号 **/
    private String mobile;
    /** 下单时间 **/
    private Timestamp createTime;
    /** 省份编码 **/
    private Integer provinceCode;
    /** 地市编码 **/
    private Integer cityCode;
    /** 区县编码 **/
    private Integer districtCode;
}
