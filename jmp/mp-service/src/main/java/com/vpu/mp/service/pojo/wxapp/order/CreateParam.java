package com.vpu.mp.service.pojo.wxapp.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 下单参数
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class CreateParam extends OrderBeforeParam{
    /**发票id*/
    private Integer invoiceId;
    /**买家留言*/
    private String message;
}
