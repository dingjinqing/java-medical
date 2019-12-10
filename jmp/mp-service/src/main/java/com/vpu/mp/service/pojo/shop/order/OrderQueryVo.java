package com.vpu.mp.service.pojo.shop.order;

import com.vpu.mp.service.foundation.util.PageResult;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class OrderQueryVo {
    private PageResult<? extends OrderListInfoVo> list;
    private Map<Byte, Integer> count;
}
