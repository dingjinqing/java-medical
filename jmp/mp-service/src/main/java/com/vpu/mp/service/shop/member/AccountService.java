package com.vpu.mp.service.shop.member;

import static com.vpu.mp.db.shop.tables.RecordAdminAction.RECORD_ADMIN_ACTION;
import static com.vpu.mp.db.shop.tables.TradesRecord.TRADES_RECORD;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserAccount.USER_ACCOUNT;


import java.math.BigDecimal;
import java.sql.Timestamp;


import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.jooq.tools.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageListParam;
import com.vpu.mp.service.pojo.shop.member.score.ScorePageListVo;
import com.vpu.mp.service.pojo.shop.member.account.AccountPageListVo;

/**
 * @author 黄壮壮 2019-07-18 16:47
 */
@Service

public class AccountService extends ShopBaseService {
	@Autowired MemberService memberService;
	/**
	 * @param param
	 * @param adminUser
	 * @param tradeType
	 * @param tradeFlow
	 * @return
	 */
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
		//加事务
		this.transaction(() ->{
			// 插入要更新的数据
			addRow(param, adminUser);
			// 更新用户余额
			updateUserAccount(param, user);

			addTradeRecord(param, tradeType, tradeFlow);
		});

		return 1;
	}

	private void addTradeRecord(AccountParam param, Byte tradeType, Byte tradeFlow) {
		String tradeSn = param.getOrderSn() == null ? "" : param.getOrderSn();
		Byte zero = 0;
		db().insertInto(TRADES_RECORD, TRADES_RECORD.TRADE_TIME, TRADES_RECORD.TRADE_NUM, TRADES_RECORD.TRADE_SN,
				TRADES_RECORD.USER_ID, TRADES_RECORD.TRADE_TYPE, TRADES_RECORD.TRADE_FLOW, TRADES_RECORD.TRADE_STATUS,
				TRADES_RECORD.TRADE_STATUS)
				.values(DateUtil.getLocalDateTime(), param.getAmount(), tradeSn, param.getUserId(), zero, tradeType,
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
	
	public BigDecimal getUserAccount(Integer userId) {
		UserRecord user = memberService.getUserRecordById(userId);
		if (user == null || user.getAccount() == null) {
			return BigDecimal.ZERO;
		}else {
			return user.getAccount();
		}
	}

	
	/**
	 * 分页查询会员用户余额详情
	 */
	public PageResult<AccountPageListVo> getPageListOfAccountDetails(AccountPageListParam param) {
		
		SelectConditionStep<Record6<String, String, String, BigDecimal, Timestamp, String>> select = db()
				.select(USER.USERNAME,USER.MOBILE,USER_ACCOUNT.ORDER_SN,USER_ACCOUNT.AMOUNT,USER_ACCOUNT.CREATE_TIME,USER_ACCOUNT.REMARK)
				.from(USER_ACCOUNT.join(USER).on(USER.USER_ID.eq(USER_ACCOUNT.USER_ID))).where(USER_ACCOUNT.USER_ID.eq(param.getUserId()));
		
		
		/** 查询条件的其他选项 */
		buildOptions(select,param);
		
		/** 以时间降序 */
		select.orderBy(USER_ACCOUNT.CREATE_TIME.desc());
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), AccountPageListVo.class);
	}

	

	
	/**
	 * 分页查询用户余额明细-积分明细时其他查询条件
	 */
	private void buildOptions(SelectConditionStep<Record6<String, String, String, BigDecimal, Timestamp, String>> select,
			AccountPageListParam param) {
		logger().info("正在构建查询条件");
		
		/** 订单号 */
		if(!StringUtils.isEmpty(param.getOrderSn())) {
			select.and(USER_ACCOUNT.ORDER_SN.eq(param.getOrderSn()));
		}
		
		/** 时间范围 */
		/** 开始时间 */
		if(param.getStartTime() != null) {
			select.and(USER_ACCOUNT.CREATE_TIME.ge(param.getStartTime()));
		}
		/** 结束时间 */
		if(param.getEndTime() != null) {
			select.and(USER_ACCOUNT.CREATE_TIME.le(param.getEndTime()));
		}
	}



}
