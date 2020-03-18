package com.vpu.mp.service.shop.user.message.maConfig;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author zhaojianqiang
 *
 *         2019年12月4日 上午11:08:39
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SubscribeMessageConfig {
	/** 服装/鞋/箱包 307开始 **/
	draw_result_307(SubcribeTemplateCategory.DRAW_RESULT, 307, 1116, "抽奖结果通知",
			"活动名称:{{thing1.DATA}}抽奖时间:{{date2.DATA}}抽奖结果:{{thing3.DATA}}", new int[] { 1, 2, 3 }),

	user_grade_307(SubcribeTemplateCategory.USER_GRADE, 307, 861, "会员等级变更通知",
			"会员等级{{phrase1.DATA}}变更时间{{date2.DATA}}备注说明{{thing3.DATA}}", new int[] { 1, 2, 3 }),

	audit_307(SubcribeTemplateCategory.AUDIT, 307, 818, "审核结果通知",
			"审核时间{{time3.DATA}}审核结果{{phrase2.DATA}}审核说明{{thing4.DATA}}", new int[] { 3, 2, 4 }),

	score_change_307(SubcribeTemplateCategory.SCORE_CHANGE, 307, 310, "积分变更提醒",
			"变更数量{{character_string1.DATA}}积分余额{{character_string2.DATA}}变更原因{{thing3.DATA}}", new int[] { 1, 2, 3 }),

	order_deliver_307(SubcribeTemplateCategory.ORDER_DELIVER, 307, 855, "订单发货通知",
			"商品名称:{{thing2.DATA}}订单号:{{character_string1.DATA}}快递类型:{{phrase3.DATA}}快递单号:{{character_string4.DATA}}",
			new int[] { 2, 1, 3, 4 }),

	invite_success_307(SubcribeTemplateCategory.INVITE_SUCCESS, 307, 817, "邀请成功通知",
			"活动名称{{name1.DATA}}奖品名称{{name2.DATA}}完成时间{{time3.DATA}}", new int[] { 1, 2, 3 }),

	refund_result_307(SubcribeTemplateCategory.REFUND_RESULT, 307, 1435, "退款通知",
			"退款金额{{amount2.DATA}}订单号{{character_string4.DATA}}申请时间{{date3.DATA}}商品名称{{thing5.DATA}}退款状态{{thing6.DATA}}",
			new int[] { 2, 4, 3, 5, 6 }),

	balance_change_307(SubcribeTemplateCategory.BALANCE_CHANGE, 307, 1972, "余额变动提醒",
			"变动金额{{amount1.DATA}}账户余额{{amount2.DATA}}变动时间{{time3.DATA}}变动原因{{thing4.DATA}}", new int[] { 1, 2, 3, 4 }),
	/** 服装/鞋/箱包 307结束 **/

	
	/** 食品 321开始 **/
	audit_321(SubcribeTemplateCategory.AUDIT, 321, 1492, "审核结果通知",
			"审核结果{{phrase1.DATA}}审核内容{{thing3.DATA}}审核时间{{date5.DATA}}", new int[] { 1, 3, 5 }),

	order_deliver_321(SubcribeTemplateCategory.ORDER_DELIVER, 321, 1368, "订单发货通知",
			"订单编号{{character_string1.DATA}}物流服务{{phrase4.DATA}}快递单号{{character_string5.DATA}}收货地址{{thing7.DATA}}发货时间{{time9.DATA}}",
			new int[] { 1, 4, 5, 7, 9 }),

	refund_result_321(SubcribeTemplateCategory.REFUND_RESULT, 321, 1480, "退款通知",
			"退款名称{{thing2.DATA}}退款金额{{amount3.DATA}}退款时间{{time4.DATA}}退款状态{{thing9.DATA}}", new int[] { 2, 3, 4, 9 }),
	/** 食品 321结束 **/

	
	/** 美妆/洗护 786开始 */
	order_deliver_786(SubcribeTemplateCategory.ORDER_DELIVER, 786, 1856, "订单发货通知",
			"商品名称{{thing5.DATA}}快递单号{{character_string3.DATA}}", new int[] { 5, 3 });
	/** 美妆/洗护 786结束 */

	/** 模板名称，小程序端发送名称找对应要用那个 */
	private String templeName;
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

	@JsonCreator
	public static SubscribeMessageConfig getConfig(String id) {
		for (SubscribeMessageConfig item : values()) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 根据类目和模板名称找到需要的模板
	 * 
	 * @param id
	 * @param templeName
	 * @return
	 */
	public static SubscribeMessageConfig getByTempleName(Integer id, String templeName) {
		SubscribeMessageConfig[] subscribeMessageConfigs = SubscribeMessageConfig.values();
		for (int i = 0; i < subscribeMessageConfigs.length; i++) {
			SubscribeMessageConfig subscribeMessageConfig = subscribeMessageConfigs[i];
			if (subscribeMessageConfig.getId().equals(id)
					&& subscribeMessageConfig.getTempleName().equals(templeName)) {
				return subscribeMessageConfig;
			}
		}
		return null;
	}

	/**
	 * 获取所有的二级类目Id
	 * 
	 * @return
	 */
	public static Set<Integer> getSecondIdList() {
		Set<Integer> sets = new HashSet<Integer>();
		SubscribeMessageConfig[] subscribeMessageConfigs = SubscribeMessageConfig.values();
		for (int i = 0; i < subscribeMessageConfigs.length; i++) {
			sets.add(subscribeMessageConfigs[i].id);
		}
		return sets;
	}

	/**
	 * 根据templeName找tid，因为所有templeName相同的tid都一样。匹配到一个就行
	 * 
	 * @param templeName
	 * @return
	 */
	public static Integer getTid(String templeName) {
		SubscribeMessageConfig[] subscribeMessageConfigs = SubscribeMessageConfig.values();
		for (int i = 0; i < subscribeMessageConfigs.length; i++) {
			SubscribeMessageConfig subscribeMessageConfig = subscribeMessageConfigs[i];
			if (subscribeMessageConfig.getTempleName().equals(templeName)) {
				return subscribeMessageConfig.getTid();
			}
		}
		return null;
	}
}
