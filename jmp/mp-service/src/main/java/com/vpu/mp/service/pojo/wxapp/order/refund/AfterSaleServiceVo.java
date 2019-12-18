package com.vpu.mp.service.pojo.wxapp.order.refund;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

/**
 * 售后中心vo
 * @author 王帅
 */
@Setter
@Getter
@ToString
public class AfterSaleServiceVo {
    private String orderSn;
    private Timestamp createTime;
    private Byte returnFlag;
    private List<ReturnOrderListMp> returnOrderlist;
}
