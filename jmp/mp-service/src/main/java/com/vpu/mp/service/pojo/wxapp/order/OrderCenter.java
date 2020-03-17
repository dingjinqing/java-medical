package com.vpu.mp.service.pojo.wxapp.order;

import com.vpu.mp.service.foundation.util.PageResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 微信订单中心
 * @author 王帅
 */
@Getter
@Setter
@ToString
public class OrderCenter {
    private PageResult<OrderListMpVo> orders;
    private Map<Byte, Integer> orderStatuCount;
}
