package com.vpu.mp.service.pojo.shop.member.score;

import com.vpu.mp.service.foundation.util.I18N;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年10月22日
* @Description: 前端积分导出参数
*/
@Data
public class ScoreFrontVo {
	@I18N(propertiesFileName = "member")
	public String title;
	public String document;
}
