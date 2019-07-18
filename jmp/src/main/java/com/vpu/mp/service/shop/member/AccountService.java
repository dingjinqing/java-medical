package com.vpu.mp.service.shop.member;

import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.shop.ShopApplication;
import static com.vpu.mp.db.shop.tables.UserAccount.USER_ACCOUNT;
import static com.vpu.mp.db.shop.tables.TradesRecord.TRADES_RECORD;
import static com.vpu.mp.db.shop.tables.RecordAdminAction.RECORD_ADMIN_ACTION;

import java.math.BigDecimal;


import org.springframework.util.StringUtils;

import static com.vpu.mp.db.shop.tables.User.USER;

/**
 * @author 黄壮壮 2019-07-18 16:47
 */
public class AccountService extends BaseService {
	MemberService memberService;
	public int addUserAccount(AccountParam param, int adminUser, Byte tradeType, Byte tradeFlow) {

		if (param.getUserId() == null || param.getAmount() == null) {
			return -1;
		}
		UserRecord user = memberService.getUserRecordById(param.getUserId());

		// 用户是否存在 是否有账户余额
		if (user == null || user.getAccount() == null) {
			return -1;
		}

		int ret = user.getAccount().compareTo(param.getAccount());
		// 检查提交的account与数据库中的account是否相等
		if (ret != 0) {
			return -1;
		}

		if (StringUtils.isEmpty(param.getRemark())) {
			param.setRemark("管理员操作");
		}

		try {
			// 插入要更新的数据
			addRow(param, adminUser);
			// 更新用户余额
			updateUserAccount(param, user);

			addTradeRecord(param, tradeType, tradeFlow);
		} catch (Exception e) {
			return -1;
		}

		return 0;
	}

	private void addTradeRecord(AccountParam param, Byte tradeType, Byte tradeFlow) {
		String tradeSn = param.getOrderSn() == null ? "" : param.getOrderSn();
		Byte zero = 0;
		db().insertInto(TRADES_RECORD, TRADES_RECORD.TRADE_TIME, TRADES_RECORD.TRADE_NUM, TRADES_RECORD.TRADE_SN,
				TRADES_RECORD.USER_ID, TRADES_RECORD.TRADE_TYPE, TRADES_RECORD.TRADE_FLOW, TRADES_RECORD.TRADE_STATUS,
				TRADES_RECORD.TRADE_STATUS)
				.values(Util.getLocalDateTime(), param.getAmount(), tradeSn, param.getUserId(), zero, tradeType,
						tradeFlow, tradeFlow == 2 ? zero : tradeFlow)
				.execute();

	}

	/**
	 * 更新user表的account字段
	 * 
	 * @param param
	 * @param user
	 */
	private void updateUserAccount(AccountParam param, UserRecord user) {
		db().update(USER).set(USER.ACCOUNT, param.getAmount().add(user.getAccount()))
				.where(USER.USER_ID.eq(user.getUserId())).execute();
	}

	/**
	 * 将数据插入到user_account
	 * 
	 * @param param
	 * @param adminUser
	 */
	private void addRow(AccountParam param, int adminUser) {
		db().insertInto(USER_ACCOUNT, USER_ACCOUNT.USER_ID, USER_ACCOUNT.AMOUNT, USER_ACCOUNT.REMARK,
				USER_ACCOUNT.ADMIN_USER)
				.values(param.getUserId(), param.getAmount(), param.getRemark(), String.valueOf(adminUser)).execute();
	}

	/**
	 * 添加操作记录到b2c_record_admin_action
	 * @param param
	 * @param user
	 * @param admin 
	 */
	public void addActionRecord(AccountParam param, UserRecord user, AdminTokenAuthInfo admin) {
		
		Integer userId = user.getUserId();
		String name = user.getUsername();
		BigDecimal zero = new BigDecimal(0);
		String account = param.getAmount().compareTo(zero)<0 ? param.getAmount().toString():"+"+param.getAmount().toString();
		String actionDesc = String.format("修改\"ID: %d  昵称: %s \"的余额 %s", userId, name, account);
		
		Byte actionType=4;
		Integer subAccountId = admin.getSubAccountId()==null?0:admin.getSubAccountId();
		String userName = admin.getUserName();

		db().insertInto(RECORD_ADMIN_ACTION,RECORD_ADMIN_ACTION.SYS_ID,RECORD_ADMIN_ACTION.ACCOUNT_ID
							,RECORD_ADMIN_ACTION.ACTION_TYPE,RECORD_ADMIN_ACTION.TEMPLATE_DATA,RECORD_ADMIN_ACTION.USER_NAME)
			.values(admin.getSysId(),subAccountId,actionType,actionDesc,userName)
			.execute();
		
		
	}

}
