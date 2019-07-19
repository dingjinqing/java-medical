package com.vpu.mp.service.pojo.shop.member.account;

import lombok.Data;

/**
 * @author 黄壮壮
 * 2019-07-19 14:38
 */
@Data
public class ScoreParam {
	private String remark;
	private Integer[] userId;
	private Integer score;
	private Integer scoreDis;
	
}
