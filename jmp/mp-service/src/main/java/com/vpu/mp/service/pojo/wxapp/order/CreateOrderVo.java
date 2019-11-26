package com.vpu.mp.service.pojo.wxapp.order;

import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 下单vo
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class CreateOrderVo {
    private String orderSn;
    private WebPayVo webPayVo;
}
