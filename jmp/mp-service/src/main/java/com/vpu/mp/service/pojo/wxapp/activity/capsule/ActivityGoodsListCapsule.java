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
    private BigDecimal realPrice;
    private BigDecimal linePrice;
    private List<ActivityForListInfo> activities = new ArrayList<>(2);
}
