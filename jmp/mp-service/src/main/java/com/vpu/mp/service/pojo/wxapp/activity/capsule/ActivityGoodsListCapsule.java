package com.vpu.mp.service.pojo.wxapp.activity.capsule;

import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 小程序-商品列表信息存储对象
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityGoodsListCapsule extends AbstractCapsule {
    private Byte goodsType;
    private BigDecimal goodsPrice;
    private BigDecimal marketPrice;
    /** 平台、商家、品牌分类id */
    private Integer catId;
    private Integer sortId;
    private Integer brandId;
    /**是否会员专享商品*/
    private boolean isCardExclusive;
    /** 商品拥有的营销信息，由各个processor添加 */
    private List<ActivityForListInfo> activities = new ArrayList<>(2);
}
