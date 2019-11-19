package com.vpu.mp.service.pojo.wxapp.goods.goods.list;

import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsBaseMp;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月12日
 * 小程序和装修商品模块返回实体Vo类
 */
@Setter
@Getter
public class GoodsListMpVo extends GoodsBaseMp {


    //************ElasticSearch中的数据**************start
    /**商品主图*/
    protected String goodsImg;
    /**初始值可设置为商品表goods_type字段，指定该商品的详情页营销活动类型*/
    protected Byte activityType;
    //************ElasticSearch中的数据**************end

    /**商品装修列表前端展示划线价,初始为goods表内shop_price*/
    protected BigDecimal linePrice;
    /**商品装修列表前端最终显示价格,初始为goods表内market_price*/
    protected BigDecimal realPrice;
    /**商品关联最紧密标签*/
    protected GoodsLabelMpVo label;

    /**商品所拥有的活动处理信息*/
    protected List<GoodsActivityBaseMp> goodsActivities = new ArrayList<>(2);
    /**指定该商品的详情页营销活动id*/
    protected Integer activityId;

}
