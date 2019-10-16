package com.vpu.mp.service.pojo.wxapp.score;

import java.util.List;

import com.vpu.mp.service.pojo.shop.member.score.ScorePageListVo;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年10月16日 下午3:37:39
 */
@Data
public class UserScoreListVo {
	private List<ScorePageListVo> dataList;
	private ExpireVo expire;

}
