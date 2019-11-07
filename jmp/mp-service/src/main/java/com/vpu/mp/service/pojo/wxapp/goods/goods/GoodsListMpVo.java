package com.vpu.mp.service.pojo.wxapp.goods.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    //商品所拥有的活动处理信息
    private List<Map<String,Object>> goodsActivity = new ArrayList<>();
    private GoodsLabelMpVo label;

    private BigDecimal shopPrice;
    private BigDecimal linePrice;
    private BigDecimal realPrice;
}
