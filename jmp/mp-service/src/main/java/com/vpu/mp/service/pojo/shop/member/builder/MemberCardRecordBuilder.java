package com.vpu.mp.service.pojo.shop.member.builder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;

/**
 * @author 黄壮壮
 * @Date: 2019年11月1日
 * @Description:
 */


public class MemberCardRecordBuilder {
	private MemberCardRecord memberCardRecord;
	
	private MemberCardRecordBuilder(){
		memberCardRecord = new MemberCardRecord();
	}
	private MemberCardRecordBuilder(MemberCardRecord memberCardRecord) {
		this.memberCardRecord = memberCardRecord;
	}
	
	
	public static MemberCardRecordBuilder create() {
		return new MemberCardRecordBuilder();
	}
	
	
	public static MemberCardRecordBuilder create(MemberCardRecord memberCardRecord) {
		return new MemberCardRecordBuilder(memberCardRecord);
	}

	public MemberCardRecordBuilder id (Integer id) {
		memberCardRecord.setId(id);
		return this;
	}

	public MemberCardRecordBuilder cardName (String cardName) {
		memberCardRecord.setCardName(cardName);
		return this;
	}

	public MemberCardRecordBuilder cardType (Byte cardType) {
		memberCardRecord.setCardType(cardType);
		return this;
	}

	public MemberCardRecordBuilder bgType (Byte bgType) {
		memberCardRecord.setBgType(bgType);
		return this;
	}

	public MemberCardRecordBuilder bgColor (String bgColor) {
		memberCardRecord.setBgColor(bgColor);
		return this;
	}

	public MemberCardRecordBuilder bgImg (String bgImg) {
		memberCardRecord.setBgImg(bgImg);
		return this;
	}

	public MemberCardRecordBuilder discount (BigDecimal discount) {
		memberCardRecord.setDiscount(discount);
		return this;
	}

	public MemberCardRecordBuilder sorce (Integer sorce) {
		memberCardRecord.setSorce(sorce);
		return this;
	}

	public MemberCardRecordBuilder buyScore (String buyScore) {
		memberCardRecord.setBuyScore(buyScore);
		return this;
	}

	public MemberCardRecordBuilder expireType (Byte expireType) {
		memberCardRecord.setExpireType(expireType);
		return this;
	}

	public MemberCardRecordBuilder startTime (Timestamp startTime) {
		memberCardRecord.setStartTime(startTime);
		return this;
	}

	public MemberCardRecordBuilder endTime (Timestamp endTime) {
		memberCardRecord.setEndTime(endTime);
		return this;
	}

	public MemberCardRecordBuilder receiveDay (Integer receiveDay) {
		memberCardRecord.setReceiveDay(receiveDay);
		return this;
	}

	public MemberCardRecordBuilder dateType (Byte dateType) {
		memberCardRecord.setDateType(dateType);
		return this;
	}

	public MemberCardRecordBuilder activation (Byte activation) {
		memberCardRecord.setActivation(activation);
		return this;
	}

	public MemberCardRecordBuilder receiveCode (String receiveCode) {
		memberCardRecord.setReceiveCode(receiveCode);
		return this;
	}

	public MemberCardRecordBuilder desc (String desc) {
		memberCardRecord.setDesc(desc);
		return this;
	}

	public MemberCardRecordBuilder mobile (String mobile) {
		memberCardRecord.setMobile(mobile);
		return this;
	}

	public MemberCardRecordBuilder createTime (Timestamp createTime) {
		memberCardRecord.setCreateTime(createTime);
		return this;
	}

	public MemberCardRecordBuilder updateTime (Timestamp updateTime) {
		memberCardRecord.setUpdateTime(updateTime);
		return this;
	}

	public MemberCardRecordBuilder flag (Byte flag) {
		memberCardRecord.setFlag(flag);
		return this;
	}

	public MemberCardRecordBuilder sendMoney (Integer sendMoney) {
		memberCardRecord.setSendMoney(sendMoney);
		return this;
	}

	public MemberCardRecordBuilder chargeMoney (String chargeMoney) {
		memberCardRecord.setChargeMoney(chargeMoney);
		return this;
	}

	public MemberCardRecordBuilder useTime (Integer useTime) {
		memberCardRecord.setUseTime(useTime);
		return this;
	}

	public MemberCardRecordBuilder storeList (String storeList) {
		memberCardRecord.setStoreList(storeList);
		return this;
	}

	public MemberCardRecordBuilder count (Integer count) {
		memberCardRecord.setCount(count);
		return this;
	}

	public MemberCardRecordBuilder delFlag (Byte delFlag) {
		memberCardRecord.setDelFlag(delFlag);
		return this;
	}

	public MemberCardRecordBuilder grade (String grade) {
		memberCardRecord.setGrade(grade);
		return this;
	}

	public MemberCardRecordBuilder gradeCondition (String gradeCondition) {
		memberCardRecord.setGradeCondition(gradeCondition);
		return this;
	}

	public MemberCardRecordBuilder activationCfg (String activationCfg) {
		memberCardRecord.setActivationCfg(activationCfg);
		return this;
	}

	public MemberCardRecordBuilder examine (Byte examine) {
		memberCardRecord.setExamine(examine);
		return this;
	}

	public MemberCardRecordBuilder discountGoodsId (String discountGoodsId) {
		memberCardRecord.setDiscountGoodsId(discountGoodsId);
		return this;
	}

	public MemberCardRecordBuilder discountCatId (String discountCatId) {
		memberCardRecord.setDiscountCatId(discountCatId);
		return this;
	}

	public MemberCardRecordBuilder discountSortId (String discountSortId) {
		memberCardRecord.setDiscountSortId(discountSortId);
		return this;
	}

	public MemberCardRecordBuilder discountIsAll (Byte discountIsAll) {
		memberCardRecord.setDiscountIsAll(discountIsAll);
		return this;
	}

	public MemberCardRecordBuilder isPay (Byte isPay) {
		memberCardRecord.setIsPay(isPay);
		return this;
	}

	public MemberCardRecordBuilder payType (Byte payType) {
		memberCardRecord.setPayType(payType);
		return this;
	}

	public MemberCardRecordBuilder payFee (BigDecimal payFee) {
		memberCardRecord.setPayFee(payFee);
		return this;
	}

	public MemberCardRecordBuilder payOwnGood (Byte payOwnGood) {
		memberCardRecord.setPayOwnGood(payOwnGood);
		return this;
	}

	public MemberCardRecordBuilder receiveAction (Byte receiveAction) {
		memberCardRecord.setReceiveAction(receiveAction);
		return this;
	}

	public MemberCardRecordBuilder isExchang (Byte isExchang) {
		memberCardRecord.setIsExchang(isExchang);
		return this;
	}

	public MemberCardRecordBuilder storeUseSwitch (Byte storeUseSwitch) {
		memberCardRecord.setStoreUseSwitch(storeUseSwitch);
		return this;
	}

	public MemberCardRecordBuilder exchangGoods (String exchangGoods) {
		memberCardRecord.setExchangGoods(exchangGoods);
		return this;
	}

	public MemberCardRecordBuilder exchangFreight (Byte exchangFreight) {
		memberCardRecord.setExchangFreight(exchangFreight);
		return this;
	}

	public MemberCardRecordBuilder exchangCount (Integer exchangCount) {
		memberCardRecord.setExchangCount(exchangCount);
		return this;
	}

	public MemberCardRecordBuilder stock (Integer stock) {
		memberCardRecord.setStock(stock);
		return this;
	}

	public MemberCardRecordBuilder limit (Integer limit) {
		memberCardRecord.setLimit(limit);
		return this;
	}

	public MemberCardRecordBuilder discountBrandId (String discountBrandId) {
		memberCardRecord.setDiscountBrandId(discountBrandId);
		return this;
	}

	public MemberCardRecordBuilder sendCouponSwitch (Byte sendCouponSwitch) {
		memberCardRecord.setSendCouponSwitch(sendCouponSwitch);
		return this;
	}

	public MemberCardRecordBuilder sendCouponType (Byte sendCouponType) {
		memberCardRecord.setSendCouponType(sendCouponType);
		return this;
	}

	public MemberCardRecordBuilder sendCouponIds (String sendCouponIds) {
		memberCardRecord.setSendCouponIds(sendCouponIds);
		return this;
	}

	public MemberCardRecord build() {
		return memberCardRecord;
	}
}

