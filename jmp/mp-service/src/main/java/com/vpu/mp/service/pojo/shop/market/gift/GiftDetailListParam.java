package com.vpu.mp.service.pojo.shop.market.gift;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 赠送明细列表入参
 *
 * @author 郑保乐
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GiftDetailListParam extends BasePageParam {

    /** 活动id **/
    @NotNull
    private Integer giftId;
    /** 用户昵称 **/
    private String username;
    /** 手机号 **/
    private String mobile;
    /** 赠送时间开始 **/
    private Timestamp startTime;
    /** 赠送时间结束 **/
    private Timestamp endTime;
}
