package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author liufei
 * @date 2019/8/14
 * @description 分页查询出参
 */
@Data
public class PurchaseShowVo {
    private Integer id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动起始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp startTime;
    /**
     * 活动结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp endTime;
    /**
     * 活动优先级
     */
    private Short level;
    /**
     * 活动信息规则，主商品购满 [] 元可加 [] 元换购
     */
    private List<String> purchaseInfo;
    /**
     * 单笔最大换购数量
     */
    private Short maxChangePurchase;
    /**
     * 已换购数量
     */
    private Integer resaleQuantity;
    /**
     * 状态
     */
    private Byte status;
}
