package com.vpu.mp.service.pojo.shop.order;

import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 孔德成
 * @date 2020/4/26
 */
@Getter
@Setter
public class OrderRepurchaseVo {

    private Byte status;
    private String content;
    private Integer cardId;
    ResultMessage resultMessage;
}
