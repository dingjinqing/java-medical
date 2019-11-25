package com.vpu.mp.service.shop.member.dao;

import static com.vpu.mp.db.shop.tables.User.USER;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
* @author 黄壮壮
* @Date: 2019年11月25日
* @Description:
*/
@Service
public class AccountDao extends ShopBaseService{
	/**
	 * 更新用户余额
	 */
	public void updateUserAccount(BigDecimal account, Integer userId) {
		db().update(USER).set(USER.ACCOUNT, account).where(USER.USER_ID.eq(userId)).execute();
	}
	

}
