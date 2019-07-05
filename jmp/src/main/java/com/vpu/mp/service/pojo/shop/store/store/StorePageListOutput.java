package com.vpu.mp.service.pojo.shop.store.store;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兵兵
 *
 * 2019年7月4日
 */
@Data
@NoArgsConstructor
public class StorePageListOutput {

	public String storeName;
	public Integer posShopId;//门店编码
	public String groupName;
	public String address;
	public String manager;
	public String mobile;
	public String openingTime;
	public String closeTime;
	public Byte businessState;
}
