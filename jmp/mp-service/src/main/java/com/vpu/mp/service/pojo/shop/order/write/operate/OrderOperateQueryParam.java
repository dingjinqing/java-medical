package com.vpu.mp.service.pojo.shop.order.write.operate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser.WxUserInfo;
import com.vpu.mp.service.shop.order.action.base.IOrderBase;

import lombok.Data;

/**
 *	 订单操作前置查询，得到可以操作的商品
 * 
 * @author 王帅
 *
 */
@Data
public class OrderOperateQueryParam extends  AbstractOrderOperateQueryParam{
	/**
	 * TODO 
	 */
	@NotNull(message = JsonResultMessage.MSG_ORDER)
	private Integer orderId;
	@NotBlank(message = JsonResultMessage.MSG_ORDER)
	private String orderSn;

}
