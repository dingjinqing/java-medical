package com.vpu.mp.service.pojo.wxapp.goods.goods;

import com.vpu.mp.service.pojo.wxapp.activity.info.ActivityForListInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月12日
 * 小程序和装修商品模块返回实体类
 */
@Data
public class GoodsListMpVo {
    private Integer goodsId;
    private String goodsName;
    private String goodsImg;
    private Integer goodsNumber;
    private Integer goodsSaleNum;
    private Integer commentNum;

    private Boolean defaultPrd;

    private List<ActivityForListInfo> goodsActivity;
    private GoodsLabelMpVo label;

    private BigDecimal shopPrice;
    private BigDecimal linePrice;
    private BigDecimal realPrice;
}
