package com.vpu.mp.service.pojo.shop.member.buy;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 会员卡下单结算
 * @author 孔德成
 * @date 2020/4/9
 */
@Getter
@Setter
@ToString
public class CardBuyClearingParam {

    @NotNull(message = JsonResultMessage.MSG_MEMBER_CARD_ID_EMPTY)
    private Integer cardId;

    private Integer userId;

    private String cardNo;
}
