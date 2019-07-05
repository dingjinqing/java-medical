package com.vpu.mp.service.pojo.shop.store.store;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兵兵
 *
 * 2019年7月4日
 */
@Data
@NoArgsConstructor
public class StoreListQueryParam {
	public String groupName;
	public Boolean isAuthPos;
	public String Keywords;//门店名称/编码/负责人
	
	private Page page;
}
