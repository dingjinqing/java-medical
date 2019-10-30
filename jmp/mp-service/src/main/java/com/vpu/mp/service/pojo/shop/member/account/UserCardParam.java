package com.vpu.mp.service.pojo.shop.member.account;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NEED_BUY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.SUPPORT_PAY_BY_CASH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.AVAILABLE_IN_STORE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LIMIT_NUM_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.NONE_GOODS;
import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年10月22日
* @Description: 用户持有会员卡的详细信息
*/
@Data
public class UserCardParam {
	/** user_card bean */
	@JsonAlias({"userId", "user_id"})
	protected Integer userId;
	protected Integer cardId;
	protected Byte userCardFlag;
	protected String cardNo;
	protected Timestamp expireTime;
	protected Byte isDefault;
	protected BigDecimal money;
	protected Integer surplus;
	protected Timestamp activationTime;
	protected Integer exchangSurplus;
	protected Timestamp userCardCreateTime;
	protected Timestamp userCardUpdateTime;
	
	/** member_card bean */
//	protected Integer cardId;
	protected String cardName;
	protected Byte cardType;
	protected Byte bgType;
	protected String bgColor;
	protected String bgImg;
	protected BigDecimal discount;
	protected Integer sorce;
	protected String buyScore;
	protected Byte expireType;
	protected Timestamp startTime;
	protected Timestamp endTime;
	protected Integer receiveDay;
	protected Byte dateType;
	protected Byte activation;
	protected String receiveCode;
	protected String desc;
	protected String mobile;
	protected Timestamp createTime;
	protected Timestamp updateTime;
	protected Byte flag;
	protected Integer sendMoney;
	protected String chargeMoney;
	protected Integer useTime;
	protected String storeList;
	protected Integer count;
	protected Byte delFlag;
	protected String grade;
	protected String gradeCondition;
	protected String activationCfg;
	protected Byte examine;
	protected String discountGoodsId;
	protected String discountCatId;
	protected String discountSortId;
	protected Byte discountIsAll;
	protected Byte isPay;
	protected Byte payType;
	protected BigDecimal payFee;
	protected Integer cash;
	protected Byte payOwnGood;
	protected Byte receiveAction;
	protected Byte isExchang;
	protected Byte storeUseSwitch;
	protected String exchangGoods;
	protected Byte exchangFreight;
	protected Integer exchangCount;
	protected Integer stock;
	protected Integer limit;
	protected String discountBrandId;
	
	/**
	 * 是否支持需要用现金购买
	 * @return
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public boolean isPayByCash(){
		return isPay.equals(NEED_BUY) && payType.equals(SUPPORT_PAY_BY_CASH);
	}
	
	public int intCashValue() {
		return payFee.intValue();
	}
	
	public void calcCash() {
		if(isPayByCash()) {
			setCash(intCashValue());
		}
	}
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public boolean isStoreAvailable() {
		return !StringUtils.isBlank(storeList) && AVAILABLE_IN_STORE.equals(storeUseSwitch);
	}
	
	public boolean hasAvailableExchangGoods() {
		return cardType.equals(LIMIT_NUM_TYPE) && !NONE_GOODS.equals(isExchang);
	}
	/**
	 * 获取门店列表id List
	 * @return List<Integer>
	 */
	public List<Integer> retrieveStoreList(){
		return changeIncludeCommaStringToIntList(storeList);
	}
	
	/**
	 * 获取使用商品列表id List
	 * @return List<Integer>
	 */
	public List<Integer> retrieveExchangGoods(){
		return changeIncludeCommaStringToIntList(exchangGoods);
	}
	
	private List<Integer> changeIncludeCommaStringToIntList(String idStr) {
		String[] ids = idStr.replaceAll("\\s+","").split(",");
		List<Integer> idList = new ArrayList<>();
		for(String id: ids) {
			idList.add(Integer.parseInt(id));
		}
		return idList;
	}
	
	
}
