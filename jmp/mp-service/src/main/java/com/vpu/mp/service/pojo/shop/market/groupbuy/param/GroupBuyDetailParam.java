package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/23 13:40
 */
@Setter
@Getter
public class GroupBuyDetailParam extends BasePageParam {

    @NotNull
    private Integer activityId;


    private String mobile;

    private String nickName;

    /**
     * 拼团中 0 拼团成功 1 拼团失败 2
     */
    private Byte status;


}
