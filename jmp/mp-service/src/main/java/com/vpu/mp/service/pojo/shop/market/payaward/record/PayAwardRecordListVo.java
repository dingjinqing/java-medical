package com.vpu.mp.service.pojo.shop.market.payaward.record;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/10/31 16:55
 */
@Getter
@Setter
public class PayAwardRecordListVo {
    private Integer userId;
    private String username;
    private String mobile;
    private String orderSn;
    private Integer giftType;
    private Timestamp createTime;

}

