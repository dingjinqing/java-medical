package com.vpu.mp.service.pojo.shop.order.write.remark;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import lombok.Data;

/**
 * 卖家备注参数
 * 
 * @author 王帅
 *
 */
@Data
public class SellerRemarkParam {
	/**0获取参数;1更新参数*/
	public final static byte TYPE_QUERY= 0;
	public final static byte TYPE_UPDATE= 1;
	@NotBlank(message = JsonResultMessage.MSG_ORDER_REMARK_ORDERSN_NOT_NULL)
	private String OrderSn;
	@NotBlank(message = JsonResultMessage.MSG_ORDER_REMARK_TYPE_NOT_NULL)
	@Min(0)
	@Max(1)
	private Byte type;
	@NotBlank(message = JsonResultMessage.MSG_ORDER_REMARK_NOT_NULL)
	private String remark;
}
