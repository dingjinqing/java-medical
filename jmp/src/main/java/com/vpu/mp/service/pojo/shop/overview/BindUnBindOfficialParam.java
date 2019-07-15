package com.vpu.mp.service.pojo.shop.overview;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/15
 * @Description:
 */
@Data
public class BindUnBindOfficialParam {
    /** 是否是子账户，1是，0否 */
    @JsonProperty(value = "is_sub_account")
    private Byte isSubAccount;
    @JsonProperty(value = "sys_id")
    private int sysId;
    @JsonProperty(value = "account_id")
    private int accountId;
    @JsonProperty(value = "is_bind")
    private Byte isBind;
}
