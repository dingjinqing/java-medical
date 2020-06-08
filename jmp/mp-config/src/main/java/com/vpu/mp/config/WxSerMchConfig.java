package com.vpu.mp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangshuai
 */
@Configuration
@Data
public class WxSerMchConfig {
    @Value(value = "${wx.srv.app.id}")
    protected String wxSrvAppId;
    @Value(value = "${wx.srv.mch.id}")
    protected String wxSrvMchId;
    @Value(value = "${wx.srv.key}")
    protected String wxSrvKey;
}
