package com.vpu.mp.service.pojo.shop.market.firstspecial;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.market.firstspecial.validated.FirstSpecialUpdateValidatedGroup;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-08-16 14:55
 **/
@Data
public class FirstSpecialUpdateParam {

    /** 活动主键 */
    @NotNull
    private Integer id;

    /** 活动名称 */
    private String name;

    /** 活动优先级 */
    private Byte first;

    /** 是否永久有效 */
    private Byte isForever;

    /** 活动开始时间 */
    private Timestamp startTime;

    /** 活动结束时间 */
    private Timestamp endTime;

    /** 限购数量 */
    private Integer limitAmount;

    /** 超限购买设置标记，1禁止超限购买，0超限全部恢复原价 */
    private Byte limitFlag;

    /** 改价的商品数组 */
    @Size(min=1)
    @NotNull(groups = {FirstSpecialUpdateValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private FirstSpecialGoodsParam[] firstSpecialGoodsParams;

    /** 分享设置 */
    private ShopShareConfig shareConfig;

    /** 状态：1：启用 0：禁用 */
    @Min(0)
    @Max(1)
    private Byte status;
}
