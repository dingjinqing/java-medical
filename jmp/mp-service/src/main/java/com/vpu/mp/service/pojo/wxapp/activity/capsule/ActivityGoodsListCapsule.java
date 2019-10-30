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
    /** 商品展示价格和划线价格 */
    private BigDecimal realPrice;
    private BigDecimal linePrice;
    /** 平台、商家、品牌分类id */
    private Integer catId;
    private Integer sortId;
    private Integer brandId;
    private List<ActivityForListInfo> activities = new ArrayList<>(2);
}
