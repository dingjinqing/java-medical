package com.vpu.mp.service.pojo.wxapp.cart;

import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import lombok.Data;

/**
 * @author chenjie
 * @date 2020年07月23日
 */
@Data
public class WxAppCartGoodsResultVo {
    private ResultMessage resultMessage;
    private Integer prdId;
}
