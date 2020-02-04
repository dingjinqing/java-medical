package com.vpu.mp.service.pojo.shop.store.service.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 王兵兵
 * @create: 2020-02-04 12:05
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCloseQueenParam {
    private Integer shopId;
    private String orderSn;
}
