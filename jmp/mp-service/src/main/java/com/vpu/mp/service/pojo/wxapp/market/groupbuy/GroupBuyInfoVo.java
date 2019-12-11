package com.vpu.mp.service.pojo.wxapp.market.groupbuy;

import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 拼团详情信息
 * @author 孔德成
 * @date 2019/12/10 18:01
 */
@Getter
@Setter
public class GroupBuyInfoVo {

    private Integer hour;
    private Integer minute;
    private Integer second;
    private Byte bindMobile;
    private Boolean newUser;
    /**
     * 状态信息
     */
    private GroupBuyStatusInfo statusInfo;
    /**
     * 用户信息
     */
    private List<GroupBuyUserInfo> userInfoList;
    /**
     * 拼团商品信息
     */
    private  GroupBuyGoodsInfo goodsInfo;
    /**
     * 规格信息
     */
    private List<GroupBuyProductInfo> productList;
    /**
     * 商品sku
     */
    List<GoodsPrdMpVo> prdSpecsList;
}
