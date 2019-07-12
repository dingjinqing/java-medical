package com.vpu.mp.service.pojo.shop.config.group;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 店铺子账户管理-保存
 * 
 * @author zhaojianqiang
 *
 */
@Data
@NoArgsConstructor
public class ShopRoleAddListVo {

	private Integer recId;
	private Integer accountId;
	private String accountName;
	private String mobile;
	private Integer roleId;
	private String roleName;
	//TODO 加公众号绑定相关字段
}
