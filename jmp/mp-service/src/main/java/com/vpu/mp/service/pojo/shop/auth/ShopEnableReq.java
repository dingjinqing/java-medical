package com.vpu.mp.service.pojo.shop.auth;



import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年9月23日 下午4:06:12
 */
@Data
@NoArgsConstructor
public class ShopEnableReq {

	public String isEnable;
	public Integer shopId;
	public String hidBottom;
	

}
