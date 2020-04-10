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
	private MemberCardRecord record;
	
	private MemberCardRecordBuilder(){
		record = new MemberCardRecord();
	}
	private MemberCardRecordBuilder(MemberCardRecord record) {
		this.record = record;
	}
	
	
	public static MemberCardRecordBuilder create() {
		return new MemberCardRecordBuilder();
	}
	
	
	public static MemberCardRecordBuilder create(MemberCardRecord record) {
		return new MemberCardRecordBuilder(record);
	}

	public MemberCardRecordBuilder id (Integer id) {
		if(id != null){
			record.setId(id);
		}
		return this;
	}

	public MemberCardRecordBuilder cardName (String cardName) {
		if(cardName != null){
			record.setCardName(cardName);
		}
		return this;
	}

	public MemberCardRecordBuilder cardType (Byte cardType) {
		if(cardType != null){
			record.setCardType(cardType);
		}
		return this;
	}

	public MemberCardRecordBuilder bgType (Byte bgType) {
		if(bgType != null){
			record.setBgType(bgType);
		}
		return this;
	}

	public MemberCardRecordBuilder bgColor (String bgColor) {
		if(bgColor != null){
			record.setBgColor(bgColor);
		}
		return this;
	}

	public MemberCardRecordBuilder bgImg (String bgImg) {
		if(bgImg != null){
			record.setBgImg(bgImg);
		}
		return this;
	}

	public MemberCardRecordBuilder discount (BigDecimal discount) {
		if(discount != null){
			record.setDiscount(discount);
		}
		return this;
	}

	public MemberCardRecordBuilder sorce (Integer sorce) {
		if(sorce != null){
			record.setSorce(sorce);
		}
		return this;
	}

	public MemberCardRecordBuilder buyScore (String buyScore) {
		if(buyScore != null){
			record.setBuyScore(buyScore);
		}
		return this;
	}

	public MemberCardRecordBuilder expireType (Byte expireType) {
		if(expireType != null){
			record.setExpireType(expireType);
		}
		return this;
	}

	public MemberCardRecordBuilder startTime (Timestamp startTime) {
		if(startTime != null){
			record.setStartTime(startTime);
		}
		return this;
	}

	public MemberCardRecordBuilder endTime (Timestamp endTime) {
		if(endTime != null){
			record.setEndTime(endTime);
		}
		return this;
	}

	public MemberCardRecordBuilder receiveDay (Integer receiveDay) {
		if(receiveDay != null){
			record.setReceiveDay(receiveDay);
		}
		return this;
	}

	public MemberCardRecordBuilder dateType (Byte dateType) {
		if(dateType != null){
			record.setDateType(dateType);
		}
		return this;
	}

	public MemberCardRecordBuilder activation (Byte activation) {
		if(activation != null){
			record.setActivation(activation);
		}
		return this;
	}

	public MemberCardRecordBuilder receiveCode (String receiveCode) {
		if(receiveCode != null){
			record.setReceiveCode(receiveCode);
		}
		return this;
	}

	public MemberCardRecordBuilder desc (String desc) {
		if(desc != null){
			record.setDesc(desc);
		}
		return this;
	}

	public MemberCardRecordBuilder mobile (String mobile) {
		if(mobile != null){
			record.setMobile(mobile);
		}
		return this;
	}

	public MemberCardRecordBuilder createTime (Timestamp createTime) {
		if(createTime != null){
			record.setCreateTime(createTime);
		}
		return this;
	}

	public MemberCardRecordBuilder updateTime (Timestamp updateTime) {
		if(updateTime != null){
			record.setUpdateTime(updateTime);
		}
		return this;
	}

	public MemberCardRecordBuilder flag (Byte flag) {
		if(flag != null){
			record.setFlag(flag);
		}
		return this;
	}

	public MemberCardRecordBuilder sendMoney (Integer sendMoney) {
		if(sendMoney != null){
			record.setSendMoney(sendMoney);
		}
		return this;
	}

	public MemberCardRecordBuilder chargeMoney (String chargeMoney) {
		if(chargeMoney != null){
			record.setChargeMoney(chargeMoney);
		}
		return this;
	}

	public MemberCardRecordBuilder useTime (Integer useTime) {
		if(useTime != null){
			record.setUseTime(useTime);
		}
		return this;
	}

	public MemberCardRecordBuilder storeList (String storeList) {
		if(storeList != null){
			record.setStoreList(storeList);
		}
		return this;
	}

	public MemberCardRecordBuilder count (Integer count) {
		if(count != null){
			record.setCount(count);
		}
		return this;
	}

	public MemberCardRecordBuilder delFlag (Byte delFlag) {
		if(delFlag != null){
			record.setDelFlag(delFlag);
		}
		return this;
	}

	public MemberCardRecordBuilder grade (String grade) {
		if(grade != null){
			record.setGrade(grade);
		}
		return this;
	}

	public MemberCardRecordBuilder gradeCondition (String gradeCondition) {
		if(gradeCondition != null){
			record.setGradeCondition(gradeCondition);
		}
		return this;
	}

	public MemberCardRecordBuilder activationCfg (String activationCfg) {
		if(activationCfg != null){
			record.setActivationCfg(activationCfg);
		}
		return this;
	}

	public MemberCardRecordBuilder examine (Byte examine) {
		if(examine != null){
			record.setExamine(examine);
		}
		return this;
	}

	public MemberCardRecordBuilder discountGoodsId (String discountGoodsId) {
		if(discountGoodsId != null){
			record.setDiscountGoodsId(discountGoodsId);
		}
		return this;
	}

	public MemberCardRecordBuilder discountCatId (String discountCatId) {
		if(discountCatId != null){
			record.setDiscountCatId(discountCatId);
		}
		return this;
	}

	public MemberCardRecordBuilder discountSortId (String discountSortId) {
		if(discountSortId != null){
			record.setDiscountSortId(discountSortId);
		}
		return this;
	}

	public MemberCardRecordBuilder discountIsAll (Byte discountIsAll) {
		if(discountIsAll != null){
			record.setDiscountIsAll(discountIsAll);
		}
		return this;
	}

	public MemberCardRecordBuilder isPay (Byte isPay) {
		if(isPay != null){
			record.setIsPay(isPay);
		}
		return this;
	}

	public MemberCardRecordBuilder payType (Byte payType) {
		if(payType != null){
			record.setPayType(payType);
		}
		return this;
	}

	public MemberCardRecordBuilder payFee (BigDecimal payFee) {
		if(payFee != null){
			record.setPayFee(payFee);
		}
		return this;
	}

	public MemberCardRecordBuilder payOwnGood (Byte payOwnGood) {
		if(payOwnGood != null){
			record.setPayOwnGood(payOwnGood);
		}
		return this;
	}

	public MemberCardRecordBuilder receiveAction (Byte receiveAction) {
		if(receiveAction != null){
			record.setReceiveAction(receiveAction);
		}
		return this;
	}

	public MemberCardRecordBuilder isExchang (Byte isExchang) {
		if(isExchang != null){
			record.setIsExchang(isExchang);
		}
		return this;
	}

	public MemberCardRecordBuilder storeUseSwitch (Byte storeUseSwitch) {
		if(storeUseSwitch != null){
			record.setStoreUseSwitch(storeUseSwitch);
		}
		return this;
	}

	public MemberCardRecordBuilder exchangGoods (String exchangGoods) {
		if(exchangGoods != null){
			record.setExchangGoods(exchangGoods);
		}
		return this;
	}

	public MemberCardRecordBuilder exchangFreight (Byte exchangFreight) {
		if(exchangFreight != null){
			record.setExchangFreight(exchangFreight);
		}
		return this;
	}

	public MemberCardRecordBuilder exchangCount (Integer exchangCount) {
		if(exchangCount != null){
			record.setExchangCount(exchangCount);
		}
		return this;
	}

	public MemberCardRecordBuilder stock (Integer stock) {
		if(stock != null){
			record.setStock(stock);
		}
		return this;
	}

	public MemberCardRecordBuilder limit (Integer limit) {
		if(limit != null){
			record.setLimit(limit);
		}
		return this;
	}

	public MemberCardRecordBuilder discountBrandId (String discountBrandId) {
		if(discountBrandId != null){
			record.setDiscountBrandId(discountBrandId);
		}
		return this;
	}

	public MemberCardRecordBuilder sendCouponSwitch (Byte sendCouponSwitch) {
		if(sendCouponSwitch != null){
			record.setSendCouponSwitch(sendCouponSwitch);
		}
		return this;
	}

	public MemberCardRecordBuilder sendCouponType (Byte sendCouponType) {
		if(sendCouponType != null){
			record.setSendCouponType(sendCouponType);
		}
		return this;
	}

	public MemberCardRecordBuilder sendCouponIds (String sendCouponIds) {
		if(sendCouponIds != null){
			record.setSendCouponIds(sendCouponIds);
		}
		return this;
	}

	public MemberCardRecordBuilder customRights (String customRights) {
		if(customRights != null){
			record.setCustomRights(customRights);
		}
		return this;
	}

	public MemberCardRecordBuilder freeshipLimit (Byte freeshipLimit) {
		if(freeshipLimit != null){
			record.setFreeshipLimit(freeshipLimit);
		}
		return this;
	}

	public MemberCardRecordBuilder freeshipNum (Integer freeshipNum) {
		if(freeshipNum != null){
			record.setFreeshipNum(freeshipNum);
		}
		return this;
	}

	public MemberCardRecordBuilder renewMemberCard (Byte renewMemberCard) {
		if(renewMemberCard != null){
			record.setRenewMemberCard(renewMemberCard);
		}
		return this;
	}

	public MemberCardRecordBuilder renewType (Byte renewType) {
		if(renewType != null){
			record.setRenewType(renewType);
		}
		return this;
	}

	public MemberCardRecordBuilder renewNum (BigDecimal renewNum) {
		if(renewNum != null){
			record.setRenewNum(renewNum);
		}
		return this;
	}

	public MemberCardRecordBuilder renewTime (Integer renewTime) {
		if(renewTime != null){
			record.setRenewTime(renewTime);
		}
		return this;
	}

	public MemberCardRecordBuilder renewDateType (Byte renewDateType) {
		if(renewDateType != null){
			record.setRenewDateType(renewDateType);
		}
		return this;
	}

	public MemberCardRecordBuilder cannotUseCoupon (Byte cannotUseCoupon) {
		if(cannotUseCoupon != null){
			record.setCannotUseCoupon(cannotUseCoupon);
		}
		return this;
	}

	public MemberCardRecordBuilder customRightsFlag (Byte customRightsFlag) {
		if(customRightsFlag != null){
			record.setCustomRightsFlag(customRightsFlag);
		}
		return this;
	}

	public MemberCardRecordBuilder customOptions (String customOptions) {
		if(customOptions != null){
			record.setCustomOptions(customOptions);
		}
		return this;
	}

	public MemberCardRecord build() {
		return record;
	}
}

