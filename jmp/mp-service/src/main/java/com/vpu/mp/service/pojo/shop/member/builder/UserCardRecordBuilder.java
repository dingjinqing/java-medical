package com.vpu.mp.service.pojo.shop.member.builder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.vpu.mp.db.shop.tables.records.UserCardRecord;

/**
 * @author 黄壮壮
 * @Date: 2019年11月1日
 * @Description:
 */
public class UserCardRecordBuilder {
	private UserCardRecord record;

	private UserCardRecordBuilder() {
		record = new UserCardRecord();
	}

	private UserCardRecordBuilder(UserCardRecord record) {
		this.record = record;
	}

	public static UserCardRecordBuilder create() {
		return new UserCardRecordBuilder();
	}

	public static UserCardRecordBuilder create(UserCardRecord record) {
		return new UserCardRecordBuilder(record);
	}

	public UserCardRecordBuilder userId(Integer userId) {
		record.setUserId(userId);
		return this;
	}

	public UserCardRecordBuilder cardId(Integer cardId) {
		record.setCardId(cardId);
		return this;
	}

	public UserCardRecordBuilder flag(Byte flag) {
		record.setFlag(flag);
		return this;
	}

	public UserCardRecordBuilder cardNo(String cardNo) {
		record.setCardNo(cardNo);
		return this;
	}

	public UserCardRecordBuilder expireTime(Timestamp expireTime) {
		record.setExpireTime(expireTime);
		return this;
	}

	public UserCardRecordBuilder isDefault(Byte isDefault) {
		record.setIsDefault(isDefault);
		return this;
	}

	public UserCardRecordBuilder money(BigDecimal money) {
		record.setMoney(money);
		return this;
	}

	public UserCardRecordBuilder surplus(Integer surplus) {
		record.setSurplus(surplus);
		return this;
	}

	public UserCardRecordBuilder activationTime(Timestamp activationTime) {
		record.setActivationTime(activationTime);
		return this;
	}

	public UserCardRecordBuilder exchangSurplus(Integer exchangSurplus) {
		record.setExchangSurplus(exchangSurplus);
		return this;
	}

	public UserCardRecordBuilder createTime(Timestamp createTime) {
		record.setCreateTime(createTime);
		return this;
	}

	public UserCardRecordBuilder updateTime(Timestamp updateTime) {
		record.setUpdateTime(updateTime);
		return this;
	}

	public UserCardRecord build() {
		return record;
	}
}
