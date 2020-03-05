package com.vpu.mp.service.pojo.wxapp.cart.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 购物车业务类
 * @author 孔德成
 * @date 2019/11/11 9:49
 */
@Builder
@Getter
@Setter
public class WxAppCartBo {

    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 商品总数量
     */
    private Integer totalGoodsNum;
    /**
     * 是否能支付
     */
    private Byte isCanPayment;
    /**
     * 是否全选
     */
    private Byte isAllCheck;
    /**
     * 商品列表
     */
    List<WxAppCartGoods> cartGoodsList;
    /**
     * 购物车 - 失效商品
     */
    private List<WxAppCartGoods> invalidCartList;

    @JsonIgnore
    private Integer userId;
    @JsonIgnore
    private Boolean isNewUser;
    @JsonIgnore
    private List<Integer> productIdList;
    @JsonIgnore
    private List<Integer> goodsIdList;
    @JsonIgnore
    private Timestamp date;
    @JsonIgnore
    private Integer storeId;

}
