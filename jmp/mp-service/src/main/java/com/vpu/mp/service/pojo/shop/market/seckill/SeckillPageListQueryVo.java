package com.vpu.mp.service.pojo.shop.market.seckill;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 王兵兵
 *
 * 2019年8月5日
 */
@Data
public class SeckillPageListQueryVo {
    /**主键*/
    private Integer skId;

    private String goodsName;

    private Timestamp startTime;
    private Timestamp endTime;

    /**
     * 停用启用的状态：1可用，0停用
     */
    private Byte status;

    /**商品交易数量*/
    private Integer saleNum;

    /**单用户最大购买数量*/
    private Byte limitAmount;
}
