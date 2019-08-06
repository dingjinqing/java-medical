package com.vpu.mp.service.pojo.shop.market.groupdraw.order;

import lombok.Data;

import java.time.LocalDate;

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
    private Boolean isWinDraw;
    private LocalDate createTime;
    private String orderStatusName;

    private Boolean grouped;
    private Short codeCount;
}
