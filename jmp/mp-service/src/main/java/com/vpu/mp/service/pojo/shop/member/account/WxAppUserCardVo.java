package com.vpu.mp.service.pojo.shop.member.account;

import java.time.LocalDate;

import com.vpu.mp.service.foundation.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

/**
* @author 黄壮壮
* @Date: 2019年10月28日
* @Description:
*/
@Getter
@Setter
public class WxAppUserCardVo extends UserCardParam {
	final static Byte ALREADY_EXPIRED = 1;
	final static Byte NOT_EXPIRED = 0;
	final static Byte PERMANENT = 2;
	final static Byte NON_RENEWAL = 0;
	final static Byte AVAILABLE_RENEWAL = 1;
	// 是否过期
	protected Byte expire;
	// 是否可续费
	protected Byte renewal;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected String avatar;
	
	public void calcCardIsExpired(){
		if(isExpire()) {
			this.expire = ALREADY_EXPIRED;
		}
		this.expire = NOT_EXPIRED;
	}
	public void calcRenewal() {
		if(isPermanent()) {
			setRenewal(NON_RENEWAL);
		}
		setRenewal(AVAILABLE_RENEWAL);
	}
	public void calcUsageTime() {
		setStartDate(getUserCardCreateTime().toLocalDateTime().toLocalDate());
		setEndDate(getExpireTime().toLocalDateTime().toLocalDate());
	}
	private boolean isExpire() {
		// means endless time,maximum time
		if(!hasExpireTime() && isPermanent()) {
			return false;
		}
		return getExpireTime().before(DateUtil.getLocalDateTime());
		
	}
	private boolean hasExpireTime() {
		return getExpireTime() != null;
	}
	
	private boolean isPermanent() {
		return PERMANENT.equals(getExpireType());
	}
}
