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
public class OrderOperateQueryParam implements IOrderBase{
	/**
	 * TODO 
	 */
	@NotNull(message = JsonResultMessage.MSG_ORDER)
	private Integer orderId;
	@NotBlank(message = JsonResultMessage.MSG_ORDER)
	private String orderSn;
	@NotNull(message = JsonResultMessage.MSG_ORDER)
	private Byte action;
	/**区分前后台操作*/
	private Boolean isMp;
	private AdminTokenAuthInfo adminInfo;
	private WxUserInfo wxUserInfo;
	@Override
	public OrderServiceCode getServiceCode() {
		//enum类型values取得数组利用默认排序顺序获取对应service
		return OrderServiceCode.values()[action];
	}
}
