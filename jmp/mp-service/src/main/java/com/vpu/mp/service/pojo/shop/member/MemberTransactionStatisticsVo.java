package com.vpu.mp.service.pojo.shop.member;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author 黄壮壮
 * @Date:  2019年8月20日
 * @Description: 会员交易统计信息-出参
 */
@Setter
@Getter
public class MemberTransactionStatisticsVo {
	/** 最近下单时间 数字-D,数字-M,数字-Y,日，月，年 ,0表示没有下单*/
	private String lastAddOrder;
	
	/** 累计消费订单数 */
	private Integer orderNum;
	
	/** 累计退款金额 */
	private BigDecimal returnOrderMoney;

	/** 累计退款订单数 */
	private Integer returnOrderNum;
	
	/** 获返利订单数量  */
	private Integer rebateOrderNum;
	
	/** 返利商品总金额(元) */
	private BigDecimal totalCanFanliMoney;
	
	/** 获返利佣金总额(元) */
	private BigDecimal rebateMoney;
	
	/** 已提现佣金总额(元) */
	private BigDecimal withdrawCash;
	
	/** 下级用户数 */
	private String sublayerNumber;
	
	/** 分销员等级 */
	private String levelName;
	
	/** 分销员分组 */
	private String groupName;
}
