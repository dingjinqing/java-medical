package com.vpu.mp.service.pojo.shop.overview.commodity;

import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/22
 * @Description: 商品概况
 */
@Data
    public class ProductOverviewVo {
    /**  在售商品数  */
    private int goodsIdNumber;
    /**  访问商品数  */
    private int goodsIdVisit;
    /**  商品访客数uv  */
    private int goodsUserVisit;
    /**  商品浏览量 pv */
    private int goodsVisit;
    /**  加购人数  */
    private int cartUsernumber;
    /**  加购件数  */
    private int cartGoodsNumber;
    /**  付款商品数  */
    private int paidGoodsNumber;
    /**  付费用户数  */
    private int paidUserNumber;
    /** 商品访问付款转化率 */
    private double visit2paid;
}
