package com.vpu.mp.service.pojo.shop.market.groupdraw.order;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 郑保乐
 */
@Data
public class OrderListVo {

    private Integer orderId;
    private String orderSn;
    private String goodsName;
    private String goodsImg;
    private Integer userId;
    private String mobile;
    private String consigneeRealName;
    private Boolean isWinDraw;
    private LocalDateTime createTime;
    private String orderStatusName;

    private Boolean grouped;
    private Short codeCount;
}
