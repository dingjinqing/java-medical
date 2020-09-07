package com.vpu.mp.service.pojo.shop.maptemplate;

import lombok.Data;

import java.util.List;

/**
 * @author chenjie
 * @date 2020年09月04日
 */
@Data
public class OrderNewParam {
    private String orderSn;
    private String userName;
    private String userRemark;
    private List<Integer> userIds;
}
