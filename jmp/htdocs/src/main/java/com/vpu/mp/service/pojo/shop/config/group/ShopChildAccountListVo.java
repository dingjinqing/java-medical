package com.vpu.mp.service.pojo.shop.config.group;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对应shop_child_account
 * 
 * @author zhaojianqiang
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ShopChildAccountListVo {
	private List<ShopChildAccountVo>  mobileList;
	private List<ShopRoleVo> groupRoleList;
	private List<ShopRoleAddListVo>  totalList;	
}
