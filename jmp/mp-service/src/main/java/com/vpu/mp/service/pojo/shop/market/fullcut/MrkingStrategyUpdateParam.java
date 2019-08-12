package com.vpu.mp.service.pojo.shop.market.fullcut;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2019-08-12 17:04
 **/
@Data
public class MrkingStrategyUpdateParam {

    /** 主键 */
    @NotNull
    private Integer id;

    /** 状态：1可用，0停用 */
    private Byte status;

    /** 满折满减活动名称 */
    private String actName;
}
