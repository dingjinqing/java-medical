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
public class StorePageListVo {

	public String storeName;
	/**
	 * 门店编码（对接pos之后的posId）
	 */
	public Integer posShopId;
	public String groupName;
	public String provinceCode;
	public String cityCode;
	public String districtCode;
	public String address;
	public String manager;
	public String mobile;
	public String openingTime;
	public String closeTime;
	/**营业状态1:营业，0:关店*/
	public Byte businessState;
}
