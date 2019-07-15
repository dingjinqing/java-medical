package com.vpu.mp.service.pojo.shop.applets;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 小程序跳转
 * @author 孔德成
 * @date 2019/7/11 17:30
 */
@Data
public class AppletsJumpAddPrarm {

    /**
     *小程序AppID
     */
    @NotBlank
    private String appId;
    /**
     * 小程序名称
     */
    @NotBlank
    private String appName;
}
