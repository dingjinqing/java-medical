package com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead;

import com.vpu.mp.service.pojo.shop.order.OrderParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 王帅
 * 代付详情
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InsteadPayDetailsParam extends OrderParam {
    private Integer currentPage;
    private Integer pageRows;
}
