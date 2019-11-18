package com.vpu.mp.service.shop.order.refund.record;

import static com.vpu.mp.db.shop.tables.RefundAmountRecord.REFUND_AMOUNT_RECORD;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.RefundAmountRecord;
import com.vpu.mp.db.shop.tables.records.RefundAmountRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.shop.order.info.OrderInfoService;;

/**
 * Table:REFUND_AMOUNT_RECORD
 * @author 王帅
 *
 */
@Service
public class RefundAmountRecordService extends ShopBaseService{
	
	public final RefundAmountRecord TABLE = REFUND_AMOUNT_RECORD;
	/**会员卡余额退款*/
	public static String MEMBER_CARD_BALANCE = "member_card_balance";
	/**余额退款*/
	public static String USE_ACCOUNT = "use_account";
	/**积分退款*/
	public static String SCORE_DISCOUNT = "score_discount";	
	/**微信退款*/
	public static String MONEY_PAID = "money_paid";	
	@Autowired 
	private OrderInfoService orderInfo;
	/**
	 * 	获取该订单退款汇总信息(存在优先级)
	 * 
	 * @param orderSns
	 * @return Map<支付种类(细分) , 金额>
	 */
	public LinkedHashMap<String , BigDecimal> getReturnAmountMap(List<String> orderSns ,Integer retId){
		//构造成功退款的汇总
		LinkedHashMap<String, BigDecimal> result = new LinkedHashMap<String , BigDecimal>(orderInfo.PAY_SUBDIVISION.length);
		Map<String, Result<Record2<String, BigDecimal>>> map = getOrderRefundAmount(orderSns , retId);
		for (String key : orderInfo.PAY_SUBDIVISION) {
			if(map.get(key) == null || map.get(key).size() == 0) {
				result.put(key, BigDecimal.ZERO);
			}else {
				for (Record2<String, BigDecimal> record : map.get(key)) {
					if(map.get(key) == null) {
						result.put(key, record.value2());
					}else {
						result.put(key, BigDecimalUtil.add(result.get(key),record.value2()));
					}
				}
			}
		}		
		return result;
	}
	
	/**
	 * 	获取该订单退款记录map
	 * @param orderSns
	 * @return Map<String, Record2<String, BigDecimal>>>
	 */
	public Map<String, Result<Record2<String, BigDecimal>>> getOrderRefundAmount(List<String> orderSns ,Integer retId){
		SelectConditionStep<Record2<String, BigDecimal>> where = db().select(TABLE.REFUND_FIELD,TABLE.REFUND_MONEY).from(TABLE).where(TABLE.ORDER_SN.in(orderSns));
		if(Objects.nonNull(retId)) {
			where.and(TABLE.RET_ID.eq(retId));
		}
		Map<String, Result<Record2<String, BigDecimal>>> map = where.fetchGroups(TABLE.REFUND_FIELD);
		return map;
	}

    /**
     * 退款动作完成记录留痕
     * @param orderSn 订单号
     * @param userId 用户id
     * @param refundField 类型
     * @param money 金额
     * @param retId 退款订单id
     */
	public void addRecord(String orderSn , Integer userId ,String refundField ,BigDecimal money ,Integer retId) {
		RefundAmountRecordRecord record = db().newRecord(TABLE);
		record.setOrderSn(orderSn);
		record.setUserId(userId);
		record.setRefundField(refundField);
		record.setRefundMoney(money);
		record.setRetId(retId);
		record.insert();
	}
}
