package com.vpu.mp.service.pojo.shop.store.authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sub {
	private String enName;
	private String name;
	private Byte check;
	/** 2仅店长端账户可见；1仅店员端可见；0不限制 */
	private Byte isOnly;
}
