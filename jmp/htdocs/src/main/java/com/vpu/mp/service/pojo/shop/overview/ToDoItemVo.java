package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author:liufei
 * @Date:2019/7/16
 * @Description:
 */
@Data
@Component
public class ToDoItemVo {
    //待提货订单
    private int pendingOrder;
    //待发货
    private int toBeDelivered;
    // 退货退款
    private int refunds;
    //已售罄商品
    private int soldOutGoods;
    //待审核Pending Review用简写Pr表示
    //商品评价待审核
    private int productEvaluationPr;
    //分销员待审核
    private int distributorPr;
    //会员卡待审核
    private int membershipCardPr;
    //分销提现待审核
    private int distributionWithdrawalPr;
    //服务评价待审核
    private int serviceEvaluationPr;
}
