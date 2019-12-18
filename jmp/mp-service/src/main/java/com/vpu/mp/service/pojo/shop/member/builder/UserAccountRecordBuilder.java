package com.vpu.mp.service.pojo.shop.member.builder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.vpu.mp.db.shop.tables.records.UserAccountRecord;

/**
* @author 黄壮壮
* @Date: 2019年11月25日
* @Description:
*/

public class UserAccountRecordBuilder {
	private UserAccountRecord userAccountRecord;
	
	private UserAccountRecordBuilder(){
		userAccountRecord = new UserAccountRecord();
	}
	private UserAccountRecordBuilder(UserAccountRecord record) {
		this.userAccountRecord = record;
	}
	
	
	public static UserAccountRecordBuilder create() {
		return new UserAccountRecordBuilder();
	}
	public static UserAccountRecordBuilder create(UserAccountRecord userAccountRecord) {
		return new UserAccountRecordBuilder(userAccountRecord);
	}

	public UserAccountRecordBuilder id (Integer id) {
		userAccountRecord.setId(id);
		return this;
	}

	public UserAccountRecordBuilder userId (Integer userId) {
		userAccountRecord.setUserId(userId);
		return this;
	}

	public UserAccountRecordBuilder adminUser (String adminUser) {
		userAccountRecord.setAdminUser(adminUser);
		return this;
	}

	public UserAccountRecordBuilder orderSn (String orderSn) {
		userAccountRecord.setOrderSn(orderSn);
		return this;
	}

	public UserAccountRecordBuilder amount (BigDecimal amount) {
		userAccountRecord.setAmount(amount);
		return this;
	}

	public UserAccountRecordBuilder adminNote (String adminNote) {
		userAccountRecord.setAdminNote(adminNote);
		return this;
	}

	public UserAccountRecordBuilder payment (String payment) {
		userAccountRecord.setPayment(payment);
		return this;
	}

	public UserAccountRecordBuilder isPaid (Byte isPaid) {
		userAccountRecord.setIsPaid(isPaid);
		return this;
	}

	public UserAccountRecordBuilder remark (String remark) {
		userAccountRecord.setRemark(remark);
		return this;
	}

	public UserAccountRecordBuilder source (Byte source) {
		userAccountRecord.setSource(source);
		return this;
	}
	
	public UserAccountRecordBuilder withdrawStatus (Byte withdrawStatus) {
		userAccountRecord.setWithdrawStatus(withdrawStatus);
		return this;
	}

	public UserAccountRecordBuilder settleAccount (BigDecimal settleAccount) {
		userAccountRecord.setSettleAccount(settleAccount);
		return this;
	}

	public UserAccountRecordBuilder createTime (Timestamp createTime) {
		userAccountRecord.setCreateTime(createTime);
		return this;
	}

	public UserAccountRecordBuilder updateTime (Timestamp updateTime) {
		userAccountRecord.setUpdateTime(updateTime);
		return this;
	}

	public UserAccountRecord build() {
		return userAccountRecord;
	}
}
