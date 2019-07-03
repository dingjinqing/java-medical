package com.vpu.mp.service.foundation;

import lombok.Getter;
/**
 * 删除标识枚举类
 * @author wangshuai
 *
 */
@Getter
public enum DelFlag {
	//正常状态
	NORMAL((byte)0),
	//删除状态
	DISABLE((byte)1);
	
	private byte code;

	private DelFlag(byte code) {
		this.code = code;
	}
}
