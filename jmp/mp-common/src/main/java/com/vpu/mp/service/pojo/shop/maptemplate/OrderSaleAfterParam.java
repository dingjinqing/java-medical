package com.vpu.mp.service.pojo.shop.maptemplate;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yangpengcheng
 * @date 2020/10/10
 **/
@Data
@Builder
public class OrderSaleAfterParam {
    public static final String REMARK = "感谢您的使用！";
    private String orderSn;
    private String createTime;
    private String orderSource;
    private String refundMoney;
    private String refundReason;
    private String remark=REMARK;
    private List<Integer> userIds;

}
