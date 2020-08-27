package com.vpu.mp.service.pojo.shop.rebate;

import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import lombok.Data;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@Data
public class DoctorWithdrawUpdateParam {
    /**
     *审核状态
     */
    private Byte checkStatus;
    /**
     * 提现单号
     */
    private String orderSn;
    private String clientIp;
    private String refuseDesc;

}
