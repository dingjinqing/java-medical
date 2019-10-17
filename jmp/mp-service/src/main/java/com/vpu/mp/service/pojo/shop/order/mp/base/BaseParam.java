package com.vpu.mp.service.pojo.shop.order.mp.base;

import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础param
 * @author 王帅
 *
 */
@Getter
@Setter
public class BaseParam {
	private WxAppSessionUser wxUserInfo;
}
