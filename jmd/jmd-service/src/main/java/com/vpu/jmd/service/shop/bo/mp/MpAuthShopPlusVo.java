package com.vpu.jmd.service.shop.bo.mp;

import com.vpu.mp.service.pojo.saas.shop.child.SystemManagerVo;
import lombok.Data;

import java.util.List;

/**
 * 增加审核人和售后人信息
 *
 * @author zhaojianqiang
 * @date 2020年6月9日下午5:18:34
 */
@Data
public class MpAuthShopPlusVo extends MpAuthShopVo {
	private List<SystemManagerVo> accountIds;
	private List<SystemManagerVo> afterSaleIds;
}
