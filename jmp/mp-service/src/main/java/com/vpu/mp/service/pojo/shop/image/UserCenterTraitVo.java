package com.vpu.mp.service.pojo.shop.image;

import com.vpu.mp.service.foundation.data.JsonResultCode;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年10月17日 下午5:28:33
 */
@Data
public class UserCenterTraitVo {
	private Byte status;
	private String image;
	private JsonResultCode msg;
}
