package com.vpu.mp.service.pojo.wxapp.cart.activity;

import com.vpu.mp.service.foundation.data.BaseConstant;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 购物车订单 商品数据数据
 * @author 孔德成
 * @date 2019/12/4 15:54
 */
@Getter
@Setter
public class OrderCartProductBo {

    private Integer productId;
    private Integer goodsNumber;
    private Byte isChecked;
    private List<GoodsActivityInfo> activityInfo =new ArrayList<>();


    public BigDecimal getFirstSpecialPrice(){
        Optional<BigDecimal> first = activityInfo.stream().filter(activity -> activity.getStatus().equals(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL))
                .map(GoodsActivityInfo::getFirstSpecialPrice).findFirst();
        return first.orElse(null);
    }

}
