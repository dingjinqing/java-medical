package com.vpu.mp.service.shop.user.message.maConfig;

import lombok.Data;

/**
 * 
 * @author zhaojianqiang
 *
 *         2019年12月5日 上午11:23:19
 */
@Data
public class SubcribeTemplateCategory {
	// 'audit', 'user_grade', 'score_change', 'order_deliver', 'draw_result',
	// 'draw_result', 'refund_result',

	/** 抽奖结果通知 */
	public static final String DRAW_RESULT = "draw_result";
	/** 审核结果通知 */
	public static final String AUDIT = "audit";
	/** 会员等级变更通知 */
	public static final String USER_GRADE = "user_grade";
	/** 积分变更提醒 */
	public static final String SCORE_CHANGE = "score_change";
	/** 订单发货通知 */
	public static final String ORDER_DELIVER = "draw_result";
	/** 退款通知 */
	public static final String REFUND_RESULT = "refund_result";
	/** 邀请成功通知 */
	public static final String INVITE_SUCCESS = "invite_success";

}
