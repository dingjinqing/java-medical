package com.vpu.mp.service.pojo.shop.market.groupbuy.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author 孔德成
 * @date 2019/7/26 9:54
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupBuyMenberParam extends BasePageGroupBuyParam{



    private String mobile;
    private String userName;
    /**
     * 邀请人
     */
    private String inviteUserName;

    /**
     * 拼团活动id
     */
    @NonNull
    private Integer activityId;

}
