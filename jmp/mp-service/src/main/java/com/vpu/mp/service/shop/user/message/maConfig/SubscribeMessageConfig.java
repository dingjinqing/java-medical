package com.vpu.mp.service.shop.user.message.maConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年12月4日 上午11:08:39
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SubscribeMessageConfig {
	draw_result(307,1116, "抽奖结果通知", "活动名称:{{thing1.DATA}}抽奖时间:{{date2.DATA}}抽奖结果:{{thing3.DATA}}",
			new int[] { 1, 2, 3 });

	/** 账号的类目id */
	private Integer id;
	/** 模板标题 tid */
	private Integer tid;
	/** 模板标题 */
	private String title;
	/** 模版内容 */
	private String content;
	/** 开发者自行组合好的模板关键词列表 */
	private int[] kidList;
	
	
	/**
	 * 获取所有的二级类目Id
	 * @return
	 */
	public Set<Integer> getSecondIdList() {
		Set<Integer> sets=new HashSet<Integer>();
		SubscribeMessageConfig[] subscribeMessageConfigs = SubscribeMessageConfig.values();
		for (int i = 0; i < subscribeMessageConfigs.length; i++) {
			sets.add(subscribeMessageConfigs[i].id);
		}
		return sets;
	}
}
