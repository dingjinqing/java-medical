package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/9/15
 **/
@Data
public class StoreSalesclerkAuthParam {
    /**
     * 账户名
     */
    private String accountName;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String mobileCheckCode;
    /**
     * 当前用户id
     */
    private Integer userId;
    private Integer shopId;

}
