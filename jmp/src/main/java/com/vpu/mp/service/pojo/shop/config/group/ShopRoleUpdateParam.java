package com.vpu.mp.service.pojo.shop.config.group;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新权限的入参
 * 
 * @author zhaojianqiang
 *
 */
@Data
@NoArgsConstructor
public class ShopRoleUpdateParam {
	private Integer accountId;
	private Integer roleId;
	private Integer recId;
}
