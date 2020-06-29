package com.vpu.jmd.service.shop.bo.child;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

import java.util.List;

/**
 * 店铺列表 审核人和售后人列表的返回
 *
 * @author zhaojianqiang
 * @date 2020年6月2日上午10:41:39
 */
@Data
public class SystemChildSetVo {
	private Boolean allCheck = false;
	private List<SystemChildShowVo> list;
	public Page page;

}
