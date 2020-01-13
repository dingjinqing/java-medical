package com.vpu.mp.service.pojo.wxapp.goods.groupDraw;

import com.vpu.mp.service.foundation.data.JsonResultCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小程序拼团抽奖参团详情出参
 * 
 * @author zhaojianqiang
 * @time 下午2:18:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDrawReturn {
	private JsonResultCode code;
	private GroupDrawInfoReturnVo vo;
}
